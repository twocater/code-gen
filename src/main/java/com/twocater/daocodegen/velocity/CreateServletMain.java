package com.twocater.daocodegen.velocity;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.twocater.daocodegen.velocity.datatype.MysqlToJava;
import com.twocater.daocodegen.velocity.util.CommandLineArgsUtil;
import com.twocater.daocodegen.velocity.util.FileUtil;
import com.twocater.daocodegen.velocity.util.PropertyUtil;
import com.twocater.daocodegen.codegen.dao.datatype.DataType;
import com.twocater.daocodegen.velocity.dao.DescribeDao;
import com.twocater.daocodegen.velocity.server.VelocityServer;
import com.twocater.daocodegen.velocity.util.ObjectUtil;
import com.twocater.daocodegen.velocity.vo.Column;

public class CreateServletMain {
	private static final String vm = "vm/servlet.vm";
	public static final String separator = File.separator;
	public static String codedir;
	public static Properties properties = PropertyUtil.getPropertiesFromResource("config.properties");
	public static String packageName;
	public static String[] tables;

	public static void main(String[] args) {
		Map<String, String> map = CommandLineArgsUtil.resolveArgs(args);
		tables = properties.getProperty("tables").trim().split(",");
		packageName = properties.getProperty("package").trim();
		if (ObjectUtil.isEmpty(packageName)) {
			throw new IllegalArgumentException("no package name.");
		}
		if (ObjectUtil.isEmpty(map.get("-codeSrcDir"))) {
			codedir = System.getProperty("user.dir") + separator + "src" + separator + ObjectUtil.getPathFromPackage(packageName);
		} else {
			codedir = map.get("-codeSrcDir").trim() + separator + ObjectUtil.getPathFromPackage(packageName);
		}

		CreatePackageMain.createPackage(codedir);

		String dir = codedir + separator + "servlet";
		CreatePackageMain.createPackage(dir);

		VelocityServer velocityServer = new VelocityServer();
		try {
			velocityServer.init("");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		DataType dataType = new MysqlToJava();
		DescribeDao describeDAO = new DescribeDao();
		for (String table : tables) {
			try {
				List<Column> list = describeDAO.getDescribe(table, dataType);
				String servlet = ObjectUtil.getServletNameByTableName(table);
				String daoFile = dir + File.separator + servlet + ".java";
				String java = velocityServer.createServlet(vm, table, packageName, list);
				FileUtil.writeFile(daoFile, java.getBytes(), false);
			} catch (SQLException e) {
				System.out.print("db error");
			} catch (IOException e) {
				System.out.print("write file failed");
				System.out.print(e);
			}
		}
		System.out.println("create servlet file ok");
	}

}
