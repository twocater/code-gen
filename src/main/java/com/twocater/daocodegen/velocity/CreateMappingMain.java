package com.twocater.daocodegen.velocity;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.twocater.daocodegen.codegen.dao.datatype.DataType;
import com.twocater.daocodegen.velocity.dao.DescribeDao;
import com.twocater.daocodegen.velocity.datatype.MysqlToJava;
import com.twocater.daocodegen.velocity.server.VelocityServer;
import com.twocater.daocodegen.velocity.util.FileUtil;
import com.twocater.daocodegen.velocity.util.ObjectUtil;
import com.twocater.daocodegen.velocity.util.PropertyUtil;
import com.twocater.daocodegen.velocity.vo.Column;

public class CreateMappingMain {

	public static void main(String[] args) {
		String separator = File.separator;
		Properties properties = PropertyUtil.getPropertiesFromResource("config.properties");
		String[] tables = properties.getProperty("tables").split(",");
		String packageStr = properties.getProperty("package");
		String codedir = System.getProperty("user.dir") + separator + "src" + separator + ObjectUtil.getPathFromPackage(packageStr);
		
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
		String serverdir = codedir + separator + "server";
		file = new File(serverdir);
		if (!file.exists()) {
			if (!file.mkdirs()) {
				System.out.println("create server file");
				return;
			}
		}

		String mappingvm = "mapping.vm";
		Map<String, List<Column>> mappingMap = new HashMap<String, List<Column>>();
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
				String clazz = ObjectUtil.getEntryNmaeByTableName(table);
				mappingMap.put(clazz, list);
			} catch (SQLException e) {
				System.out.print("db error");
			} 
		}
		String mapping = velocityServer.createMappingServer(mappingvm, packageStr, mappingMap);
		String mappingFile = serverdir + File.separator + "MappingServer.java";
		try {
			FileUtil.writeFile(mappingFile, mapping.getBytes(), false);
		} catch (IOException e) {
			System.out.print("write file error");
		}
		
		System.out.println("create mapping server ok");
	}

}
