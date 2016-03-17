package com.shavika.agritech.api.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.apache.tomcat.jdbc.pool.DataSource;

import com.shavika.agritech.utils.PropertyReader;

public class DBConnection {

	private static final Logger LOGGER = Logger.getLogger(DBConnection.class);

	private static String resourceName = PropertyReader.getPropertyValue("Application", "DataSource.lookup");
	private static DBConnection singleInstance;
	private static DataSource dataSource = null;

	public DBConnection() throws SQLException {
		try {
			LOGGER.info("Initializing DataSource Manager from JNDI....");
			Context envContext = (Context) new InitialContext().lookup("java:/comp/env");
			dataSource = (DataSource) envContext.lookup(resourceName);
		} catch (NamingException e) {
			LOGGER.error("Unknown db poolname: " + resourceName);
		}
	}

	public static DBConnection getSingleInstance() {
		if (singleInstance == null) {
			synchronized (DBConnection.class) {
				if (singleInstance == null) {
					try {
						singleInstance = new DBConnection();
					} catch (SQLException e) {
						LOGGER.error("Failed to get DBConnection in singleInstance...", e);
					}
				}
			}
		}
		return singleInstance;
	}

	public static Connection getConnection() {
		try {
			return getSingleInstance().dataSource.getConnection();
		} catch (Exception e) {
			LOGGER.error("Fail to etConnection...", e);
		}
		return null;
	}

	public static void CloseConnection() {
		singleInstance = null;
	}
}
