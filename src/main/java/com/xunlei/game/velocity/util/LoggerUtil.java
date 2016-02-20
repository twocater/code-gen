package com.xunlei.game.velocity.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface LoggerUtil {
	Logger errorLog = LoggerFactory.getLogger("error");
	Logger dbLog = LoggerFactory.getLogger("db");
}
