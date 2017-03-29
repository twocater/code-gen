package com.twocater.daocodegen.velocity.datatype;

import java.util.Properties;

import com.twocater.daocodegen.codegen.dao.datatype.DataType;
import com.twocater.daocodegen.velocity.util.PropertyUtil;

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
