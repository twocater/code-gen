package com.twocater.daocodegen.codegen.dao.util;

public interface IdGen {
	int getIdLength();

	long getCounter();

	String createId();
}
