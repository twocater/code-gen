package com.twocater.daocodegen.codegen.dao.sql;

public enum ContextOperator {

	/**
	 * The concat operator
	 */
	CONCAT(" ");
	private String sql;

	private ContextOperator(String sql) {
		this.sql = sql;
	}

	public String toSQL() {
		return sql;
	}

}
