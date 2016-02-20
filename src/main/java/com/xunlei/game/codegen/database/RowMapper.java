package com.xunlei.game.codegen.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper<T> {

	/**
	 * map interface
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public T mapRow(ResultSet rs) throws SQLException;

}
