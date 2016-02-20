package com.xunlei.game.codegen.database.sql;

public class OrderByContext extends AbstractContext {
	public static final String ORDER_BY = " order by ";
	private SortOrder order;
	private String[] fields;

	public OrderByContext(SortOrder order, String[] fields) {
		super();
		this.order = order;
		this.fields = fields;
	}

	@Override
	public String toSql() {
		SqlStatement context = new SqlStatement();
		context.sql(ORDER_BY);
		String separator = "";
		for (String str : fields) {
			context.sql(separator);
			context.sql(str);
			separator = ",";
		}
		if (order != null) {
			context.sql(order.toSQL());
		}
		return context.toString();
	}
}