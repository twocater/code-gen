package com.xunlei.game.velocity;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.xunlei.game.codegen.dao.datatype.DataType;
import com.xunlei.game.velocity.dao.DescribeDao;
import com.xunlei.game.velocity.datatype.MysqlToJava;
import com.xunlei.game.velocity.server.VelocityServer;
import com.xunlei.game.velocity.util.CommandLineArgsUtil;
import com.xunlei.game.velocity.util.FileUtil;
import com.xunlei.game.velocity.util.ObjectUtil;
import com.xunlei.game.velocity.util.PropertyUtil;
import com.xunlei.game.velocity.vo.Column;

public class CreateBoMain {
	private static final String vm = "bo.vm";
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
		String dir = codedir + separator + "server";
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
		Map<String, List<Column>> mappingMap = new HashMap<String, List<Column>>();
		for (String table : tables) {
			try {
				List<Column> list = describeDAO.getDescribe(table, dataType);
				String clazz = ObjectUtil.getEntryNmaeByTableName(table);
				mappingMap.put(clazz, list);
			} catch (SQLException e) {
				System.out.print("db error");
			}	
		}	
		String server = velocityServer.createMappingServer(vm, packageName, mappingMap);
		String serverFile = dir + File.separator + "ServerBo.java";
		try {
			FileUtil.writeFile(serverFile, server.getBytes(), false);
		} catch (IOException e) {
			System.out.print("write file failed");
		}
		System.out.println("create server bo ok");
	}
}
