package com.xunlei.game.velocity.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xunlei.game.codegen.database.datatype.DataType;
import com.xunlei.game.velocity.util.PropertyUtil;
import com.xunlei.game.velocity.vo.Column;

public class DescribeDao {
	Logger errorLog = LoggerFactory.getLogger("error");
	Logger dbLog = LoggerFactory.getLogger("db");

	public static Connection getConnection() {
		Properties properties = PropertyUtil.getPropertiesFromResource("db.properties");
		Connection conn = null;
		try {
			String driver = properties.getProperty("driver");
			String url = properties.getProperty("url");
			String username = properties.getProperty("username");
			String password = properties.getProperty("password");
			Class.forName(driver);
			conn = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static void closeConnection(ResultSet rs, Statement st, Connection conn) {
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

	public List<Column> getDescribe(String tableName, DataType dbType) throws SQLException {
		Connection con = null;
		List<Column> list = new ArrayList<Column>();
		String sql = "describe " + tableName;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				Column column = new Column(dbType);
				column.setField(rs.getString(1));
				column.setType(rs.getString(2));
				column.setIsNull(rs.getString(3));
				column.setKey(rs.getString(4));
				column.setDefaultValue(rs.getString(5));
				column.setExtra(rs.getString(6));
				list.add(column);
			}
		} catch (SQLException e) {
			errorLog.error("exe sql:{} exception.{}", new String[] { sql, e.getMessage() });
			throw e;
		} finally {
			closeConnection(rs, ps, con);
		}
		return list;
	}

	public List<String> getTablesName() throws SQLException {
		Connection con = null;
		List<String> list = new ArrayList<String>();
		String sql = "show tables ";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(rs.getString(1));
			}
		} catch (SQLException e) {
			errorLog.error("exe sql:{} exception.{}", new String[] { sql, e.getMessage() });
			throw e;
		} finally {
			closeConnection(rs, ps, con);
		}
		return list;
	}
}
