package com.xunlei.game.codegen.dao.sql;

public enum SortOrder {

	/**
	 * Ascending sort order
	 */
	ASC("asc"),

	/**
	 * Descending sort order
	 */
	DESC("desc");

	private final String sql;

	private SortOrder(String sql) {
		this.sql = sql;
	}

	public String toSQL() {
		return sql;
	}
}
