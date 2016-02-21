package com.xunlei.game.codegen.dao.util;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class UuidGen implements IdGen {
	private static final int ID_LENGTH = 32;
	private AtomicLong increase = new AtomicLong(0);

	@Override
	public String createId() {
		increase.incrementAndGet();
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	@Override
	public int getIdLength() {
		return ID_LENGTH;
	}

	@Override
	public long getCounter() {
		return increase.get();
	}

}
