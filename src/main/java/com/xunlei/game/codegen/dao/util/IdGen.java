package com.xunlei.game.codegen.dao.util;

public interface IdGen {
	int getIdLength();

	long getCounter();

	String createId();
}
