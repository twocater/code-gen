package com.twocater.daocodegen.codegen.dao.sql.condition;

import com.twocater.daocodegen.codegen.dao.sql.Context;

public interface Condition extends Context {

	public Condition and(Condition other);

	public Condition andNot(Condition other);

	public Condition or(Condition other);

	public Condition orNot(Condition other);

	public Condition not();

}
