package com.twocater.daocodegen.codegen.dao.sql.condition;

import com.twocater.daocodegen.codegen.dao.sql.SqlStatement;

public class NotCondition extends AbstractCondition {
	private final Condition condition;

	NotCondition(Condition condition) {
		this.condition = condition;
	}

	@Override
	public String toSql() {
		SqlStatement context = new SqlStatement();
		context.sql(" not(").sql(condition).sql(") ");
		return context.toString();
	}

}
