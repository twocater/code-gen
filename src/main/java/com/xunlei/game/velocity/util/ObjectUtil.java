package com.xunlei.game.velocity.util;

import java.io.File;

public class ObjectUtil {

	public static final String VO = "Vo";
	public static final String DAO = "Dao";
	public static final String SERVLET = "Servlet";
	public static final String SERVER = "Server";
	public static final String ROWMAPPER = "RowMapper";
	public static final String GET = "get";
	public static final String SET = "set";
	public static final String LEFT_PARENTHESIS = "(";
	public static final String RIGHT_PARENTHESIS = ")";
	public static final String PARENTHESIS = "()";
	public static final String DOT = ".";
	public static final String UNDERLINE = "_";

	/**
	 * 更加VO类名获取entry名称
	 * 
	 * @param vo
	 * @return
	 */
	public static String getEntryNameByVOName(String vo) {
		if (!isEmpty(vo)) {
			if (!vo.endsWith(VO)) {
				throw new IllegalArgumentException("vo " + vo + " dont endsWith 'VO' ");
			}
			return vo.replace(VO, "");
		}
		return null;
	}

	/**
	 * 根据VO类名获取DAO类名
	 * 
	 * @param vo
	 * @return
	 */
	public static String getDAONameByVOName(String vo) {
		if (!isEmpty(vo)) {
			if (!vo.endsWith(VO)) {
				throw new IllegalArgumentException("vo " + vo + " dont endsWith 'VO' ");
			}
			return vo.replace(VO, DAO);
		}
		return null;
	}

	/**
	 * 根据数据库表名获取Servlet类名
	 * 
	 * @param tableName
	 * @return
	 */
	public static String getServletNameByTableName(String tableName) {
		if (!isEmpty(tableName)) {
			String change = change(tableName);
			return change + SERVLET;
		}
		return null;
	}

	/**
	 * 根据数据库表名获取Vo类名
	 * 
	 * @param tableName
	 * @return
	 */
	public static String getVONameByTableName(String tableName) {
		if (!isEmpty(tableName)) {
			String change = change(tableName);
			return change + VO;
		}
		return null;
	}

	/**
	 * 根据数据库表名获取VO类名
	 * 
	 * @param tableName
	 * @return
	 */
	public static String getServerNameByTableName(String tableName) {
		if (!isEmpty(tableName)) {
			String change = change(tableName);
			return change + SERVER;
		}
		return null;
	}

	/**
	 * 根据数据库表名获取DAO类名
	 * 
	 * @param tableName
	 * @return
	 */
	public static String getDAONameByTableName(String tableName) {
		if (!isEmpty(tableName)) {
			String change = change(tableName);
			return change + DAO;
		}
		return null;
	}

	/**
	 * 根据数据库表名获取entry名
	 * 
	 * @param tableName
	 * @return
	 */
	public static String getEntryNmaeByTableName(String tableName) {
		if (!isEmpty(tableName)) {
			String change = change(tableName);
			return change;
		}
		return null;
	}

	/**
	 * 根据数据库表名获取rowmapper名
	 * 
	 * @param tableName
	 * @return
	 */
	public static String getRowMapperNmaeByTableName(String tableName) {
		if (!isEmpty(tableName)) {
			String change = change(tableName);
			return change + ROWMAPPER;
		}
		return null;
	}

	/**
	 * 根据类名获取对象名称
	 * 
	 * @param className
	 * @return
	 */
	public static String getObjectName(String className) {
		if (className != null) {
			return className.substring(0, 1).toLowerCase() + className.substring(1);
		}
		return null;
	}

	/**
	 * 获取属性的getter
	 * 
	 * @param fieldName
	 * @return
	 */
	public static String getGetter(String fieldName) {
		return GET + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1) + PARENTHESIS;
	}

	/**
	 * 获取对象的属性的getter，
	 * 
	 * @param fieldName
	 * @return
	 */
	public static String getGetter(String objectName, String fieldName) {
		return objectName + DOT + getGetter(fieldName);
	}

	/**
	 * 获取属性的setter
	 * 
	 * @param fieldName
	 * @return
	 */
	public static String getSetter(String fieldName) {
		return SET + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}

	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	public static String change(String str) {
		String result = "";
		if (str.contains(UNDERLINE)) {
			String[] strs = str.split(UNDERLINE);

			for (String s : strs) {
				result += (s.substring(0, 1).toUpperCase() + s.substring(1));
			}
		} else {
			result += (str.substring(0, 1).toUpperCase() + str.substring(1));
		}
		return result;

	}

	public static String getPathFromPackage(String packageStr) {
		return packageStr.replace(".", File.separator);
	}
}
