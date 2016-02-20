package com.xunlei.game.codegen.database.sql;

import com.xunlei.game.codegen.database.sql.condition.Condition;

public class GroupByContext extends AbstractContext {

	public static final String GROUP_BY = " group by ";
	public static final String HAVING = " having ";

	private String[] fields;
	private Condition havingCondition;

	public GroupByContext(String[] fields, Condition havingCondition) {
		super();
		this.fields = fields;
		this.havingCondition = havingCondition;
	}

	@Override
	public String toSql() {
		SqlStatement context = new SqlStatement();
		context.sql(GROUP_BY);
		String separator = "";
		for (String str : fields) {
			context.sql(separator);
			context.sql(str);
			separator = ",";
		}
		if (havingCondition != null) {
			context.sql(HAVING);
			context.sql(havingCondition);
		}
		return context.toString();
	}

}
