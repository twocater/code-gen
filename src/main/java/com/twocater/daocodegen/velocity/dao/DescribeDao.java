package com.twocater.daocodegen.velocity.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import com.twocater.daocodegen.codegen.dao.datatype.DataType;
import com.twocater.daocodegen.velocity.util.PropertyUtil;
import com.twocater.daocodegen.velocity.vo.Column;
import com.twocater.daocodegen.velocity.vo.Index;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DescribeDao {
    Logger errorLog = LoggerFactory.getLogger("error");
    Logger dbLog = LoggerFactory.getLogger("db");
    public static String databaseName = "";

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
            int i = url.indexOf("?");
            int j = url.substring(0, i).lastIndexOf("/");
            databaseName = url.substring(j + 1, i);
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
            errorLog.error("exe sql:{} exception.{}", new String[]{sql, e.getMessage()});
            throw e;
        } finally {
            closeConnection(rs, ps, con);
        }
        return list;
    }

    public List<Index> getIndex(String tableName) throws SQLException {
        Connection con = null;
        List<Index> list = new LinkedList<Index>();
        String sql = "show index from " + tableName;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Index index = new Index();
                index.setTable(rs.getString("Table"));
                index.setNonUnique(rs.getString("Non_unique"));
                index.setKeyName(rs.getString("Key_name"));
                index.setSeqInIndex(rs.getString("Seq_in_index"));
                index.setColumnName(rs.getString("Column_name"));
                index.setCollation(rs.getString("Collation"));
                index.setCardinality(rs.getString("Cardinality"));
                index.setSubPart(rs.getString("Sub_part"));
                index.setIsNull(rs.getString("Null"));
                index.setIndexType(rs.getString("Index_type"));
                index.setComment(rs.getString("Comment"));
                index.setIndexComment("Index_comment");
                list.add(index);
            }
        } catch (SQLException e) {
            errorLog.error("exe sql:{} exception.{}", new String[]{sql, e.getMessage()});
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
            errorLog.error("exe sql:{} exception.{}", new String[]{sql, e.getMessage()});
            throw e;
        } finally {
            closeConnection(rs, ps, con);
        }
        return list;
    }
}
