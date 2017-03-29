package com.twocater.daocodegen.codegen.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;

public class JdbcSupport<T> {
	private DataSource dataSource;
	private Logger errorLog;
	private Logger dbLog;

	public JdbcSupport(DataSource dataSource, Logger dbLog, Logger errorLog) {
		this.dataSource = dataSource;
		this.dbLog = dbLog;
		this.errorLog = dbLog;
	}

	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	public void closeConnection(ResultSet rs, Statement st, Connection conn) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
			}
		}
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
	}

	public void closeConnection(Statement st, Connection conn) {
		closeConnection(null, st, conn);
	}

	public void closeConnection(Connection conn) {
		closeConnection(null, null, conn);
	}

	public void setValues(PreparedStatement ps, Object[] params) throws SQLException {
		if (ps == null || params == null || params.length == 0) {
			return;
		}
		for (int i = 0; i < params.length; i++) {
			ps.setObject(i + 1, params[i]);
		}
	}

	public int update(String sql) throws SQLException {
		Connection con = null;
		Statement st = null;
		int result = 0;
		try {
			con = getConnection();
			st = con.createStatement();
			result = st.executeUpdate(sql);
			dbLog.debug("exe sql:{}", new String[] { sql });
		} catch (SQLException e) {
			errorLog.error("exe sql:{} exception.{}", new String[] { sql, e.getMessage() });
			throw e;
		} finally {
			closeConnection(null, st, con);
		}
		return result;
	}

	public int update(String sql, Object[] params) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		int result = 0;
		try {
			con = getConnection();
			ps = con.prepareStatement(sql);
			setValues(ps, params);
			result = ps.executeUpdate();
			dbLog.debug("exe sql:{},param:{}", new String[] { sql, Arrays.toString(params) });
		} catch (SQLException e) {
			errorLog.error("exe sql:{},param:{} exception.{}",
					new String[] { sql, Arrays.toString(params), e.getMessage() });
			throw e;
		} finally {
			closeConnection(null, ps, con);
		}
		return result;
	}

	public int[] batchUpdate(String[] sqls) throws SQLException {
		Connection con = null;
		Statement st = null;
		int[] result = null;
		try {
			con = getConnection();
			st = con.createStatement();
			for (String sql : sqls) {
				st.addBatch(sql);
			}
			result = st.executeBatch();
			dbLog.debug("exe sql:{}", new String[] { Arrays.toString(sqls) });
		} catch (SQLException e) {
			errorLog.error("exe sql:{} exception.{}", new String[] { Arrays.toString(sqls), e.getMessage() });
			throw e;
		} finally {
			closeConnection(null, st, con);
		}
		return result;
	}

	public int[] batchUpdate(String sql, List<Object[]> paramsList) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		int[] result = null;
		try {
			con = getConnection();
			ps = con.prepareStatement(sql);
			for (Object[] params : paramsList) {
				setValues(ps, params);
				ps.addBatch();
			}
			result = ps.executeBatch();
			dbLog.debug("exe sql:{},param:{}", new String[] { sql, toListString(paramsList) });
		} catch (SQLException e) {
			errorLog.error("exe sql:{},param:{} exception.{}",
					new String[] { sql, toListString(paramsList), e.getMessage() });
			throw e;
		} finally {
			closeConnection(null, ps, con);
		}
		return result;
	}

	public int count(String sql) throws SQLException {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		int result = 0;
		try {
			con = getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sql);
			if (rs.next()) {
				result = rs.getInt(1);
			}
			dbLog.debug("exe sql:{}", new String[] { sql });
		} catch (SQLException e) {
			errorLog.error("exe sql:{} exception.{}", new String[] { sql, e.getMessage() });
			throw e;
		} finally {
			closeConnection(null, st, con);
		}
		return result;
	}

	public int count(String sql, Object[] params) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int result = 0;
		try {
			con = getConnection();
			ps = con.prepareStatement(sql);
			setValues(ps, params);
			rs = ps.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			}
			dbLog.debug("exe sql:{},param:{}", new String[] { sql, Arrays.toString(params) });
		} catch (SQLException e) {
			errorLog.error("exe sql:{},param:{} exception.{}",
					new String[] { sql, Arrays.toString(params), e.getMessage() });
			throw e;
		} finally {
			closeConnection(null, ps, con);
		}
		return result;
	}

	public T queryForOne(String sql, RowMapper<T> rowMapper) throws SQLException {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sql);
			dbLog.debug("exe sql:{}", new String[] { sql });
			T obj = null;
			if (rs.next()) {
				obj = rowMapper.mapRow(rs);
			}
			return obj;
		} catch (SQLException e) {
			errorLog.error("exe sql:{} exception.{}", new String[] { sql, e.getMessage() });
			throw e;
		} finally {
			closeConnection(rs, st, con);
		}
	}

	public T queryForOne(String sql, Object[] params, RowMapper<T> rowMapper) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			ps = con.prepareStatement(sql);
			setValues(ps, params);
			rs = ps.executeQuery();
			dbLog.debug("exe sql:{},param:{}", new String[] { sql, Arrays.toString(params) });
			T obj = null;
			if (rs.next()) {
				obj = rowMapper.mapRow(rs);
			}
			return obj;
		} catch (SQLException e) {
			errorLog.error("exe sql:{},param:{} exception.{}",
					new String[] { sql, Arrays.toString(params), e.getMessage() });
			throw e;
		} finally {
			closeConnection(rs, ps, con);
		}
	}

	public List<T> query(String sql, RowMapper<T> rowMapper) throws SQLException {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sql);
			dbLog.debug("exe sql:{}", new String[] { sql });
			List<T> list = new ArrayList<T>();
			while (rs.next()) {
				T obj = rowMapper.mapRow(rs);
				list.add(obj);
			}
			return list;
		} catch (SQLException e) {
			errorLog.error("exe sql:{} exception.{}", new String[] { sql, e.getMessage() });
			throw e;
		} finally {
			closeConnection(rs, st, con);
		}
	}

	public List<T> query(String sql, Object[] params, RowMapper<T> rowMapper) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			ps = con.prepareStatement(sql);
			setValues(ps, params);
			rs = ps.executeQuery();
			dbLog.debug("exe sql:{},param:{}", new String[] { sql, Arrays.toString(params) });
			List<T> list = new ArrayList<T>();
			while (rs.next()) {
				T obj = rowMapper.mapRow(rs);
				list.add(obj);
			}
			return list;
		} catch (SQLException e) {
			errorLog.error("exe sql:{},param:{} exception.{}",
					new String[] { sql, Arrays.toString(params), e.getMessage() });
			throw e;
		} finally {
			closeConnection(rs, ps, con);
		}
	}

	private String toListString(List<Object[]> paramsList) {
		List<String> stringList = new ArrayList<String>();
		for (Object[] params : paramsList) {
			stringList.add(Arrays.toString(params));
		}
		return stringList.toString();
	}

	public static String createId() {
		String id = java.util.UUID.randomUUID().toString();
		id = id.replace("-", "");
		return id;
	}

}
