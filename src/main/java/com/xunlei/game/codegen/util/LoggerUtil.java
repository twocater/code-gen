package com.xunlei.game.codegen.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface LoggerUtil {
	Logger serverLog = LoggerFactory.getLogger("server");
	Logger servletLog = LoggerFactory.getLogger("servlet");
	Logger errorLog = LoggerFactory.getLogger("error");
	Logger dbLog = LoggerFactory.getLogger("db");
}
