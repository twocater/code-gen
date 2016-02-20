package com.xunlei.game.velocity.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {
	private static final String PATH_SEPARATOR = "/";

	public static Properties getPropertiesFromResource(String file) {
		Properties properties = new Properties();
		InputStream in = null;
		if (file.indexOf(PATH_SEPARATOR) != 0) {
			file = PATH_SEPARATOR + file;
		}
		try {
			in = PropertyUtil.class.getResourceAsStream(file);
			properties.load(in);
		} catch (IOException e) {
			throw new IllegalStateException("read file fail.", e.getCause());
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
		return properties;
	}

	public static Properties getPropertiesFromFile(String file) {
		Properties properties = new Properties();
		FileInputStream fileIn = null;
		try {
			fileIn = new FileInputStream(file);
			properties.load(fileIn);
		} catch (IOException e) {
			throw new IllegalStateException("read file fail.", e.getCause());
		} finally {
			if (fileIn != null) {
				try {
					fileIn.close();
				} catch (IOException e) {
				}
			}
		}
		return properties;
	}

	public static void main(String[] args) {
		System.out.println(PropertyUtil.getPropertiesFromResource("db.properties"));
	}

}
