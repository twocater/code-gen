package com.xunlei.game.codegen.dao.sql.condition;

import com.xunlei.game.codegen.dao.sql.SqlStatement;

public class TrueCondition extends AbstractCondition {

	@Override
	public String toSql() {
		SqlStatement context = new SqlStatement();
		context.sql("1 = 1");
		return context.toString();
	}

}
