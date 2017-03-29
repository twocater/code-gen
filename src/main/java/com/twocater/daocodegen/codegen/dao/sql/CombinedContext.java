package com.twocater.daocodegen.codegen.dao.sql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CombinedContext extends AbstractContext {
	private final List<Context> contexts;

	public CombinedContext(Collection<Context> contexts) {
		if (contexts == null) {
			throw new IllegalArgumentException("The argument 'contexts' must not be null");
		}
		for (Context context : contexts) {
			if (context == null) {
				throw new IllegalArgumentException("The argument 'contexts' must contain null");
			}
		}
		this.contexts = new ArrayList<Context>();
		this.contexts.addAll(contexts);
	}

	@Override
	public String toSql() {
		SqlStatement statement = new SqlStatement();
		String operatorName = ContextOperator.CONCAT.toSQL();
		String separator = "";
		for (int i = 0; i < contexts.size(); i++) {
			statement.sql(separator);
			statement.sql(contexts.get(i));
			separator = operatorName;
		}
		return statement.toString();
	}

}
