package com.xunlei.game.codegen.dao;

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

public class BaseDao<T> {
	protected Logger log;
	protected DataSource dataSource;

	public BaseDao(DataSource dataSource, Logger log) {
		this.dataSource = dataSource;
		this.log = log;
	}

	public int save(String sql, T object) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		int result = 0;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			setRow(ps, object);
			result = ps.executeUpdate();
			log.debug("exe sql:{},param:{}", new String[] { sql, object.toString() });
		} catch (SQLException e) {
			log.error("exe sql:{},param:{} exception.{}", new String[] { sql, object.toString(), e.getMessage() });
			throw e;
		} finally {
			closeConnection(ps, conn);
		}
		return result;
	}

	public int[] saveList(String sql, List<T> list) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		int[] result = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			for (T object : list) {
				setRow(ps, object);
				ps.addBatch();
			}
			result = ps.executeBatch();
			log.debug("exe sql:{},param:{}", new String[] { sql, list.toString() });
		} catch (SQLException e) {
			log.error("exe sql:{},param:{} exception.{}", new String[] { sql, list.toString(), e.getMessage() });
			throw e;
		} finally {
			closeConnection(ps, conn);
		}
		return result;
	}

	public List<T> queryList(String sql, String[] params) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<T> result = new ArrayList<T>();
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			setValues(ps, params);
			rs = ps.executeQuery();
			while (rs.next()) {
				result.add(mapRow(rs));
			}
			log.debug("exe sql:{},param:{}", new String[] { sql, toString(params) });
		} catch (SQLException e) {
			log.error("exe sql:{},param:{} exception.{}", new String[] { sql, toString(params), e.getMessage() });
			throw e;
		} finally {
			closeConnection(rs, ps, conn);
		}
		return result;
	}

	public List<T> queryList(String sql, Object[] params) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<T> result = new ArrayList<T>();
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			setValues(ps, params);
			rs = ps.executeQuery();
			while (rs.next()) {
				result.add(mapRow(rs));
			}
			log.debug("exe sql:{},param:{}", new String[] { sql, toString(params) });
		} catch (SQLException e) {
			log.error("exe sql:{},param:{} exception.{}", new String[] { sql, toString(params), e.getMessage() });
			throw e;
		} finally {
			closeConnection(rs, ps, conn);
		}
		return result;
	}

	public T query(String sql, String[] params) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		T result = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			setValues(ps, params);
			rs = ps.executeQuery();
			if (rs.next()) {
				result = mapRow(rs);
			}
			log.debug("exe sql:{},param:{}", new String[] { sql, toString(params) });
		} catch (SQLException e) {
			log.error("exe sql:{},param:{} exception.{}", new String[] { sql, toString(params), e.getMessage() });
			throw e;
		} finally {
			closeConnection(rs, ps, conn);
		}
		return result;
	}
	
	public T query(String sql, Object[] params) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		T result = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			setValues(ps, params);
			rs = ps.executeQuery();
			if (rs.next()) {
				result = mapRow(rs);
			}
			log.debug("exe sql:{},param:{}", new String[] { sql, toString(params) });
		} catch (SQLException e) {
			log.error("exe sql:{},param:{} exception.{}", new String[] { sql, toString(params), e.getMessage() });
			throw e;
		} finally {
			closeConnection(rs, ps, conn);
		}
		return result;
	}

	protected T mapRow(ResultSet rs) throws SQLException {
		throw new SQLException("mapRow method not implemented yet.");
	}

	protected void setRow(PreparedStatement ps, T object) throws SQLException {
		throw new SQLException("setRow method not implemented yet.");
	}

	protected String composeIds(String[] ids) {
		StringBuilder sb = new StringBuilder();
		for (String id : ids) {
			sb.append("'").append(id).append("',");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	public void closeConnection(ResultSet rs, Statement st, Connection conn) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void closeConnection(Statement st, Connection conn) {
		closeConnection(null, st, conn);
	}

	public void closeConnection(Connection conn) {
		closeConnection(null, null, conn);
	}

	public void setValues(PreparedStatement ps, String[] params) throws SQLException {
		if (ps == null || params == null || params.length == 0) {
			return;
		}
		for (int i = 0; i < params.length; i++) {
			ps.setString(i + 1, params[i]);
		}
	}

	public void setValues(PreparedStatement ps, Object[] params) throws SQLException {
		if (ps == null || params == null || params.length == 0) {
			return;
		}
		for (int i = 0; i < params.length; i++) {
			ps.setObject(i + 1, params[i]);
		}
	}

	/**
	 * to string the string array
	 * 
	 * @param arr
	 * @return
	 */
	public String toString(String[] array) {
		StringBuilder sb = new StringBuilder();
		if (array != null && array.length > 0) {
			for (String str : array) {
				sb.append(str).append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	public String toString(Object[] array) {
		StringBuilder sb = new StringBuilder();
		if (array != null && array.length > 0) {
			for (Object str : array) {
				sb.append(str).append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	public int update(String sql) throws SQLException {
		Connection conn = null;
		Statement st = null;
		int result = 0;
		try {
			conn = getConnection();
			st = conn.createStatement();
			result = st.executeUpdate(sql);
			log.debug("exe sql:{}", sql);
		} catch (SQLException e) {
			log.error("exe sql:{} exception.{}", new String[] { sql, e.getMessage() });
			throw e;
		} finally {
			closeConnection(st, conn);
		}
		return result;
	}

	public int update(String sql, String[] params) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		int result = 0;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			setValues(ps, params);
			result = ps.executeUpdate();
			log.debug("exe sql:{},param:{}", new String[] { sql, toString(params) });
		} catch (SQLException e) {
			log.error("exe sql:{},param:{} exception.{}", new String[] { sql, toString(params), e.getMessage() });
			throw e;
		} finally {
			closeConnection(ps, conn);
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
			log.debug("exe sql:{},param:{}", new String[] { sql, Arrays.toString(params) });
		} catch (SQLException e) {
			log.error("exe sql:{},param:{} exception.{}", new String[] { sql, Arrays.toString(params), e.getMessage() });
			throw e;
		} finally {
			closeConnection(null, ps, con);
		}
		return result;
	}

	public int[] batchUpdate(String[] sqls) throws SQLException {
		if (sqls == null || sqls.length == 0) {
			return null;
		}
		Connection conn = null;
		Statement st = null;
		int[] result = null;
		try {
			conn = getConnection();
			st = conn.createStatement();
			for (String sql : sqls) {
				st.addBatch(sql);
				log.debug("batch exe sql:{}", sql);
			}
			result = st.executeBatch();
		} catch (SQLException e) {
			log.error("batch exe sql:{} exception.{}", new String[] { toString(sqls), e.getMessage() });
			throw e;
		} finally {
			closeConnection(st, conn);
		}
		return result;
	}

	public int[] batchUpdate(String sql, String[] ids) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		int[] result = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			for (String id : ids) {
				ps.setString(1, id);

				ps.addBatch();
			}
			result = ps.executeBatch();
			log.debug("batch exe sql:{};{}", new String[] { sql, toString(ids) });
		} catch (SQLException e) {
			log.error("batch exe sql:{};{} exception.{}", new String[] { sql, toString(ids), e.getMessage() });
			throw e;
		} finally {
			closeConnection(ps, conn);
		}
		return result;
	}

	public Long queryForLong(String sql) throws SQLException {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		Long result = null;
		try {
			conn = getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			log.debug("exe sql:{}", new String[] { sql });
			if (rs.next()) {
				result = rs.getLong(1);
			}
		} catch (SQLException e) {
			log.error("exe sql:{} exception.{}", new String[] { sql, e.getMessage() });
			throw e;
		} finally {
			closeConnection(rs, st, conn);
		}
		return result;
	}

	public Long queryForLong(String sql, String[] params) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long result = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			setValues(ps, params);
			rs = ps.executeQuery();
			log.debug("exe sql:{},param:{}", new String[] { sql, toString(params) });
			if (rs.next()) {
				result = rs.getLong(1);
			}
		} catch (SQLException e) {
			log.error("exe sql:{},param:{} exception.{}", new String[] { sql, toString(params), e.getMessage() });
			throw e;
		} finally {
			closeConnection(rs, ps, conn);
		}
		return result;
	}

	public Integer queryForInt(String sql) throws SQLException {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		Integer result = null;
		try {
			conn = getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			log.debug("exe sql:{}", new String[] { sql });
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			log.error("exe sql:{} exception.{}", new String[] { sql, e.getMessage() });
			throw e;
		} finally {
			closeConnection(rs, st, conn);
		}
		return result;
	}

	public Integer queryForInt(String sql, String[] params) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer result = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			setValues(ps, params);
			rs = ps.executeQuery();
			log.debug("exe sql:{},param:{}", new String[] { sql, toString(params) });
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			log.error("exe sql:{},param:{} exception.{}", new String[] { sql, toString(params), e.getMessage() });
			throw e;
		} finally {
			closeConnection(rs, ps, conn);
		}
		return result;
	}

	public String queryForString(String sql) throws SQLException {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String result = null;
		try {
			conn = getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			log.debug("exe sql:{}", new String[] { sql });
			if (rs.next()) {
				result = rs.getString(1);
			}
		} catch (SQLException e) {
			log.error("exe sql:{} exception.{}", new String[] { sql, e.getMessage() });
			throw e;
		} finally {
			closeConnection(rs, st, conn);
		}
		return result;
	}

	public String queryForString(String sql, String[] params) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String result = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			setValues(ps, params);
			rs = ps.executeQuery();
			log.debug("exe sql:{},param:{}", new String[] { sql, toString(params) });
			if (rs.next()) {
				result = rs.getString(1);
			}
		} catch (SQLException e) {
			log.error("exe sql:{},param:{} exception.{}", new String[] { sql, toString(params), e.getMessage() });
			throw e;
		} finally {
			closeConnection(rs, ps, conn);
		}
		return result;
	}
}
