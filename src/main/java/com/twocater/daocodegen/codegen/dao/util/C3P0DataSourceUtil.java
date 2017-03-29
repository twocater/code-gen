package com.twocater.daocodegen.codegen.dao.util;

import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

public class C3P0DataSourceUtil {
	public static final String DRIVER = "driver";
	public static final String URL = "url";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String IDLECONNECTIONTESTPERIOD = "idleConnectionTestPeriod";
	public static final String MAXPOOLSIZE = "maxPoolSize";
	public static final String MINPOOLSIZE = "minPoolSize";
	public static final String ACQUIREINCREMENT = "acquireIncrement";
	public static final String TIMEOUT = "timeout";
	public static final String MAXIDLETIME = "maxIdleTime";

	public static DataSource createDataSource(String propertyfile) {
		Properties properties = PropertyUtil.getPropertiesFromResource(propertyfile);
		return createDataSource(properties);
	}

	public static DataSource createDataSource(Properties properties) {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		try {
			dataSource.setDriverClass(properties.getProperty(DRIVER));
		} catch (PropertyVetoException e) {
			throw new IllegalArgumentException("database driver load fail.", e.getCause());
		}
		dataSource.setJdbcUrl(properties.getProperty(URL));
		dataSource.setUser(properties.getProperty(USERNAME));
		dataSource.setPassword(properties.getProperty(PASSWORD));
		dataSource.setIdleConnectionTestPeriod(Integer.parseInt(properties.getProperty(IDLECONNECTIONTESTPERIOD)));
		dataSource.setMaxPoolSize(Integer.parseInt(properties.getProperty(MAXPOOLSIZE)));
		dataSource.setInitialPoolSize(Integer.parseInt(properties.getProperty(MINPOOLSIZE)));
		dataSource.setMinPoolSize(Integer.parseInt(properties.getProperty(MINPOOLSIZE)));
		dataSource.setAcquireIncrement(Integer.parseInt(properties.getProperty(ACQUIREINCREMENT)));
		dataSource.setCheckoutTimeout(Integer.parseInt(properties.getProperty(TIMEOUT)));
		dataSource.setMaxIdleTime(Integer.parseInt(properties.getProperty(MAXIDLETIME)));
		return dataSource;
	}

	public static void destroyDataSource(DataSource dataSource) throws SQLException {
		DataSources.destroy(dataSource);
	}
}
