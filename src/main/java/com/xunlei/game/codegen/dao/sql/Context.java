package com.xunlei.game.codegen.dao.sql;

public interface Context {

	public Context concat(Context context);

	public String toSql();
}
