package com.xunlei.game.codegen.dao.sql.condition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.xunlei.game.codegen.dao.sql.SqlStatement;

public class CombinedCondition extends AbstractCondition {

	private final Operator operator;
	private final List<Condition> conditions;

	CombinedCondition(Operator operator, Collection<Condition> conditions) {
		if (operator == null) {
			throw new IllegalArgumentException("The argument 'operator' must not be null");
		}
		if (conditions == null) {
			throw new IllegalArgumentException("The argument 'conditions' must not be null");
		}
		for (Condition condition : conditions) {
			if (condition == null) {
				throw new IllegalArgumentException("The argument 'conditions' must contain null");
			}
		}

		this.operator = operator;
		this.conditions = new ArrayList<Condition>();

		init(operator, conditions);
	}

	private final void init(Operator op, Collection<Condition> cond) {
		for (Condition condition : cond) {
			if (condition instanceof CombinedCondition) {
				CombinedCondition combinedCondition = (CombinedCondition) condition;
				if (combinedCondition.operator == op) {
					this.conditions.addAll(combinedCondition.conditions);
				} else {
					this.conditions.add(condition);
				}
			} else {
				this.conditions.add(condition);
			}
		}
	}

	@Override
	public String toSql() {
		SqlStatement context = new SqlStatement();
		if (conditions.isEmpty()) {
			context.sql(new TrueCondition());
		} else if (conditions.size() == 1) {
			context.sql(conditions.get(0));
		} else {
			context.sql("(");
			String operatorName = operator.toSQL();
			String separator = "";
			for (int i = 0; i < conditions.size(); i++) {
				context.sql(separator);
				context.sql(conditions.get(i));
				separator = operatorName;
			}
			context.sql(")");
		}
		return context.toString();
	}

}
