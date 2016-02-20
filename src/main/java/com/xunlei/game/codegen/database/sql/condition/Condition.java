package com.xunlei.game.codegen.database.sql.condition;

import com.xunlei.game.codegen.database.sql.Context;

public interface Condition extends Context {

	public Condition and(Condition other);

	public Condition andNot(Condition other);

	public Condition or(Condition other);

	public Condition orNot(Condition other);

	public Condition not();

}
