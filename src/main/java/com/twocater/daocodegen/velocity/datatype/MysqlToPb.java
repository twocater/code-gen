package com.twocater.daocodegen.velocity.datatype;

import java.util.Properties;

import com.twocater.daocodegen.codegen.dao.datatype.DataType;
import com.twocater.daocodegen.velocity.util.PropertyUtil;

public class MysqlToPb implements DataType {
	private static Properties properties = PropertyUtil.getPropertiesFromResource("mysql2pb.properties");
	public static final String DEFAULT_TYPE = "string";

	@Override
	public String getDataType(String dataType) {
		String type = null;
		dataType = dataType.toLowerCase();
		if (properties.containsKey(dataType)) {
			type = properties.getProperty(dataType);
		} else {
			type = DEFAULT_TYPE;
		}
		return type;
	}

}
