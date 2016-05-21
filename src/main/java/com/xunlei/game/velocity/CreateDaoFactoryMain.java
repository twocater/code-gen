package com.xunlei.game.velocity;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import com.xunlei.game.velocity.server.VelocityServer;
import com.xunlei.game.velocity.util.CommandLineArgsUtil;
import com.xunlei.game.velocity.util.FileUtil;
import com.xunlei.game.velocity.util.ObjectUtil;
import com.xunlei.game.velocity.util.PropertyUtil;

public class CreateDaoFactoryMain {
	private static final String vm = "vm/daofactory.vm";
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

		Map<String, String> daoMap = new HashMap<String, String>();
		VelocityServer velocityServer = new VelocityServer();
		try {
			velocityServer.init("");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		for (String table : tables) {
			String dao = ObjectUtil.getDAONameByTableName(table);
			String object = ObjectUtil.getObjectName(dao);
			daoMap.put(dao, object);
		}
		String factory = velocityServer.createDaoFactory(vm, packageName, daoMap);
		String factoryFile = dir + File.separator + "DaoFactory.java";
		try {
			FileUtil.writeFile(factoryFile, factory.getBytes(), true);
		} catch (IOException e) {
			System.out.print("write file failed");
		}
		System.out.println("create dao factory ok");
	}

}
