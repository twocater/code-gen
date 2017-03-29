package com.twocater.daocodegen.velocity.vo;

import com.twocater.daocodegen.codegen.dao.datatype.DataType;

public class Column {

	private String field;
	private String type;
	private String isNull;
	private String key;
	private String defaultValue;
	private String extra;
	private DataType dataType; 
	public Column(DataType dataType){
		this.dataType = dataType;
	}
	
	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		if(type.contains("(")){
			int length = type.indexOf("(");
			type = type.substring(0,length);
		}
		this.type = dataType.getDataType(type);
		
	}

	public String getIsNull() {
		return isNull;
	}

	public void setIsNull(String isNull) {
		this.isNull = isNull;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	@Override
	public String toString() {
		return "Column [field=" + field + ", type=" + type + ", isNull=" + isNull + ", key=" + key + ", defaultValue="
				+ defaultValue + ", extra=" + extra + "]";
	}
}
