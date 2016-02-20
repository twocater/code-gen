package com.xunlei.game.codegen.database.sql.condition;

import com.xunlei.game.codegen.database.sql.SqlStatement;

public class CompareCondition extends AbstractCondition {

	private final String field;
	private final Class<?> fieldType;
	private final String value;
	private final Comparator comparator;

	public CompareCondition(String field, Class<?> fieldType, String value, Comparator comparator) {
		super();
		this.field = field;
		this.fieldType = fieldType;
		this.value = value;
		this.comparator = comparator;
	}

	@Override
	public String toSql() {
		SqlStatement context = new SqlStatement();
		context.sql(field).sql(comparator.toSQL());
		if (fieldType == String.class) {
			if (comparator == Comparator.LIKE || comparator == Comparator.NOT_LIKE) {
				context.literalLike(value);
			} else {
				context.literal(value);
			}

		} else {
			context.sql(value);
		}
		return context.toString();
	}
}
