package com.xunlei.game.codegen.database.sql;

import java.util.Arrays;

abstract class AbstractContext implements Context {
	AbstractContext() {
	}

	@Override
	public final Context concat(Context other) {
		return new CombinedContext(Arrays.asList(this, other));
	}
}
