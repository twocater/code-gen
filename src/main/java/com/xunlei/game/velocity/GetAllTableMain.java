package com.xunlei.game.velocity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import com.xunlei.game.velocity.dao.DescribeDao;

public class GetAllTableMain {
	public static final String CONFIG_FILE = "config.properties";

	public static void main(String[] args) {
		Properties properties = new Properties();
		String url = GetAllTableMain.class.getClassLoader().getResource(CONFIG_FILE).toString();
		System.out.println(url);
		url = url.substring(6, url.length());
		try {
			url = URLDecoder.decode(url, "UTF-8");
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}
		FileInputStream fis;
		try {
			fis = new FileInputStream(url);
			properties.load(fis);
			fis.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		DescribeDao describeDAO = new DescribeDao();
		List<String> tableList = null;
		try {
			tableList = describeDAO.getTablesName();
		} catch (SQLException e) {
			System.out.println("db error");
		}
		String separator = "";
		String tables = "";
		for (String table : tableList) {
			tables += separator;
			tables += table;
			separator = ",";
		}
		properties.put("tables", tables);
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(url);
			properties.store(fos, "Copyright (c) Boxcode Studio");
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
