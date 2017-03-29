package com.twocater.daocodegen.codegen.dao.sql;

public class LimitContext extends AbstractContext {

	public static final String LIMIT = " limit ";
	private int offset;
	private int rows;

	public LimitContext(int offset, int rows) {
		super();
		this.offset = offset;
		this.rows = rows;
	}

	public LimitContext(int rows) {
		super();
		this.offset = 0;
		this.rows = rows;
	}

	@Override
	public String toSql() {
		SqlStatement context = new SqlStatement();
		context.sql(LIMIT);
		context.sql(offset);
		context.sql(",");
		context.sql(rows);
		return context.toString();
	}

}
