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

public class CreateVoMain {
	private static final String vm = "vo.vm";

	public static final String separator = File.separator;
	public static String codedir;
	public static Properties properties = PropertyUtil.getPropertiesFromResource("config.properties");
	public static String packageName;
	public static String[] tables;
	public static String[] packages;

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

		String dir = codedir + separator + "vo";
		CreatePackageMain.createPackage(dir);

		Map<String, String> daoMap = new HashMap<String, String>();
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
				String name = ObjectUtil.getVONameByTableName(table);
				String object = ObjectUtil.getObjectName(name);
				daoMap.put(name, object);
				String javaFile = dir + File.separator + name + ".java";
				System.out.println("table:" + table);
				System.out.println("name:" + name);
				System.out.println("object:" + object);
				for (Column column : list) {
					System.out.println(column);
				}
				String java = velocityServer.createVo(vm, table, packageName, list);
				FileUtil.writeFile(javaFile, java.getBytes(), true);
			} catch (SQLException e) {
				System.out.print("db error");
			} catch (IOException e) {
				System.out.print("write file failed");
				System.out.print(e);
			}
		}
		System.out.println("create vo file ok");

	}
}
