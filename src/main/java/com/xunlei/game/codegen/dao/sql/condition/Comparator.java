package com.xunlei.game.codegen.dao.sql.condition;

/**
 * A comparator to be used in conditions
 * 
 * @author Lukas Eder
 */
public enum Comparator {

	EQUALS("="),

	NOT_EQUALS("<>"),

	LESS("<"),

	LESS_OR_EQUAL("<="),

	GREATER(">"),

	GREATER_OR_EQUAL(">="),

	LIKE("like"),

	NOT_LIKE("not like"),

	LIKE_IGNORE_CASE("ilike"),

	NOT_LIKE_IGNORE_CASE("not ilike"),

	IN("in");

	private final String sql;

	private Comparator(String sql) {
		this.sql = sql;
	}

	public String toSQL() {
		return sql;
	}
}