package com.xunlei.game.codegen.database.sql.condition;

import java.util.Arrays;

import com.xunlei.game.codegen.database.sql.CombinedContext;
import com.xunlei.game.codegen.database.sql.Context;

abstract class AbstractCondition implements Condition {
	
	AbstractCondition() {
	}

	@Override
	public final Condition and(Condition other) {
		return new CombinedCondition(Operator.AND, Arrays.asList(this, other));
	}

	@Override
	public final Condition or(Condition other) {
		return new CombinedCondition(Operator.OR, Arrays.asList(this, other));
	}
	
	@Override
	public final Context concat(Context other) {
		return new CombinedContext(Arrays.asList(this, other));
	}
    @Override
    public final Condition andNot(Condition other) {
        return and(other.not());
    }

    @Override
    public final Condition orNot(Condition other) {
        return or(other.not());
    }
    @Override
    public final Condition not() {
        return new NotCondition(this);
	}

}
