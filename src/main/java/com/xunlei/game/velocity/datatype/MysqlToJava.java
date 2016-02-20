package com.xunlei.game.velocity.datatype;

import java.util.Properties;

import com.xunlei.game.codegen.database.datatype.DataType;
import com.xunlei.game.velocity.util.PropertyUtil;

public class MysqlToJava implements DataType {

	private static Properties properties = PropertyUtil.getPropertiesFromResource("mysql2java.properties");
	public static final String DEFAULT_TYPE = "String";

	@Override
	public String getDataType(String dataType) {
		String javaType = null;
		dataType = dataType.toLowerCase();
		if (properties.containsKey(dataType)) {
			javaType = properties.getProperty(dataType);
		} else {
			javaType = DEFAULT_TYPE;
		}
		return javaType;
	}
}
