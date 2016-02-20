package com.xunlei.game.velocity.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class FileUtil {
	private static final String crlf = System.getProperty("line.separator");

	/**
	 * 读文件
	 * 
	 */
	public static byte[] readFile(String filename) throws IOException {
		File file = new File(filename);
		if (!file.exists()) {
			return null;
		}
		ByteArrayOutputStream bais = null;
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		byte[] result = null;
		try {
			bais = new ByteArrayOutputStream();
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			byte[] cache = new byte[2048];
			int length = bis.read(cache);
			while (length != -1) {
				bais.write(cache, 0, length);
				length = bis.read(cache);
			}
			result = bais.toByteArray();
		} catch (IOException e) {
			throw e;
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (bais != null) {
				try {
					bais.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;

	}

	/**
	 * 写文件
	 * 
	 */
	public static void writeFile(String filename, byte[] data, boolean override) throws IOException {
		File file = new File(filename);
		if (!override && file.exists()) {
			return;
		}
		if (!file.exists()) {
			try {
				if (!file.createNewFile()) {
					throw new IllegalArgumentException("创建文件失败!");
				}
			} catch (IOException e) {
				throw e;
			}
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			fos.write(data);
			fos.flush();
		} catch (IOException e) {
			throw e;
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 读清单文件
	 * 
	 */
	public static List<String> readListFile(String fileName) throws IOException {
		File file = new File(fileName);
		if (!file.exists()) {
			return null;
		}
		List<String> list = new ArrayList<String>();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String str = reader.readLine();
			while (str != null) {
				str = str.trim();
				if (!str.equals("")) {
					list.add(str);
				}
				str = reader.readLine();
			}
		} catch (IOException e) {
			throw e;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	/**
	 * 写清单文件
	 * 
	 */
	public static void writeListFile(String filename, List<String> list) throws IOException {
		File file = new File(filename);
		if (!file.exists()) {
			try {
				if (!file.createNewFile()) {
					throw new IllegalArgumentException("创建文件失败!");
				}
			} catch (IOException e) {
				throw e;
			}
		}
		FileWriter writer = null;
		try {
			writer = new FileWriter(file);
			Iterator<String> iterator = list.iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				writer.write(key + crlf);
				writer.flush();
			}
		} catch (IOException e) {
			throw e;
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 读属性文件
	 * 
	 */
	public static Map<String, String> readPropertiesFile(String filename) throws IOException {
		File file = new File(filename);
		if (!file.exists()) {
			return null;
		}
		Map<String, String> map = new HashMap<String, String>();
		FileInputStream fileIn = null;
		try {
			fileIn = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(fileIn);
			Iterator<Object> iterator = properties.keySet().iterator();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				String value = properties.getProperty(key);
				map.put(key, value);
			}
		} catch (IOException e) {
			throw e;
		} finally {
			if (fileIn != null) {
				try {
					fileIn.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return map;
	}

	/**
	 * 写属性文件
	 * 
	 */
	public static void writePropertiesFile(String filename, Map<String, String> map) throws IOException {
		File file = new File(filename);
		if (!file.exists()) {
			try {
				if (!file.createNewFile()) {
					throw new IllegalArgumentException("创建文件失败!");
				}
			} catch (IOException e) {
				throw e;
			}
		}
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(filename);
			Properties properties = new Properties();
			Set<String> set = map.keySet();
			Iterator<String> it = set.iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				properties.setProperty(key, (String) map.get(key));
			}
			properties.store(out, null);
		} catch (IOException e) {
			throw e;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
