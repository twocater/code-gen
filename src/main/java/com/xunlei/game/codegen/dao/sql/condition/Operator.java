package com.xunlei.game.codegen.dao.sql.condition;

public enum Operator {

	/**
	 * The and operator
	 */
	AND("and"),

	/**
	 * The or operator
	 */
	OR("or");

	private String sql;

	private Operator(String sql) {
		this.sql = sql;
	}

	public String toSQL() {
		return sql;
	}

}