package com.xunlei.game.codegen.database.sql;

public class SqlContext extends AbstractContext {

	private String sql;

	public SqlContext(String sql) {
		super();
		this.sql = sql;
	}

	@Override
	public String toSql() {
		return this.sql;
	}

}
