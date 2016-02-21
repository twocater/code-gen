package com.xunlei.game.velocity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import com.xunlei.game.codegen.dao.datatype.DataType;
import com.xunlei.game.velocity.dao.DescribeDao;
import com.xunlei.game.velocity.datatype.MysqlToPb;
import com.xunlei.game.velocity.server.VelocityServer;
import com.xunlei.game.velocity.util.FileUtil;
import com.xunlei.game.velocity.util.ObjectUtil;
import com.xunlei.game.velocity.util.PropertyUtil;
import com.xunlei.game.velocity.vo.Column;

public class CreatePbAndVoMain {

	public static void main(String[] args) {
		String basedir = ".";
		String separator = File.separator;
		Properties properties = PropertyUtil.getPropertiesFromResource("config.properties");
		String[] tables = properties.getProperty("tables").split(",");
		String packageStr = properties.getProperty("package");
		//String codedir = properties.getProperty("codedir");
		String codedir = basedir + separator + "src" + separator + ObjectUtil.getPathFromPackage(packageStr);
		String batpath = "pb.bat";
		
		if (ObjectUtil.isEmpty(batpath)) {
			System.out.println("no protocolbuf");
			return;
		}
		if (ObjectUtil.isEmpty(packageStr)) {
			System.out.println("no package name");
			return;
		}
		
		if (tables == null || tables.length == 0) {
			System.out.println("no table");
			return;
		}

		File file = new File(codedir);
		if (!file.exists()) {
			if (!file.mkdirs()) {
				System.out.println("create code dir failed");
				return;
			}

		}
//		String pbdir = properties.getProperty("pbdir");
		String pbdir = codedir + separator + "proto";
		file = new File(pbdir);
		if (!file.exists()) {
			if (!file.mkdirs()) {
				System.out.println("create pb dir failed");
				return;
			}
		}

		try {
			StringBuilder sb = new StringBuilder();
			sb.append("set proto_path=").append(pbdir).append("\r\n");
			sb.append("set java_out=").append(basedir + separator + "src").append("\r\n");
			sb.append("set proto_src=").append(pbdir + separator + "*.proto").append("\r\n");
			sb.append("protoc -I=%proto_path% --java_out=%java_out% %proto_src%");
			FileUtil.writeFile(batpath, sb.toString().getBytes(), false);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String pbvm = "protocol.vm";
		VelocityServer velocityServer = new VelocityServer();
		try {
			velocityServer.init("");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		DataType pbType = new MysqlToPb();
		DescribeDao describeDAO = new DescribeDao();
		for (String table : tables) {
			try {
				List<Column> list = describeDAO.getDescribe(table, pbType);
				String pbFile = pbdir + File.separator + table + ".proto";
				String pb = velocityServer.createVoByProtocol(pbvm, table, packageStr, list);
				FileUtil.writeFile(pbFile, pb.getBytes(), false);
			} catch (SQLException e) {
				System.out.print("db error");
			} catch (IOException e) {
				System.out.print("write error");
				System.out.print(e);
			}
		}
		System.out.println("create pb file ok");
		try {
			Process proc = Runtime.getRuntime().exec(batpath);
			InputStream in = proc.getInputStream();
			@SuppressWarnings("unused")
			int c;
			while ((c = in.read()) != -1) {
//				System.out.print(c);// don't need the print, then note this line.
			}
			in.close();
			proc.waitFor();
			System.out.println("create vo file ok");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

