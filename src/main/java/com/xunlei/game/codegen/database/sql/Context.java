package com.xunlei.game.codegen.database.sql;

public interface Context {

	public Context concat(Context context);

	public String toSql();
}
