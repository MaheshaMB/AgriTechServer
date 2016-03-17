package com.shavika.test.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

	public static String DRIVER = "com.mysql.jdbc.Driver";
	public static String URL = "jdbc:mysql://localhost:3306/agritech";
	public static String USERNAME = "root";
	public static String PASSWORD = "root";

	private static Connection connection = null;

	/**
	 * @method getConnection
	 * @return Connection
	 * @throws Exception
	 * @throws SQLException
	 */
	public static Connection getConnection() throws Exception, SQLException {
		if (connection != null)
			return connection;
		else {
			try {
				Class.forName(DRIVER);
				connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				connection.setAutoCommit(false);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return connection;
		}
	}

	/**
	 * @method CloseConnection
	 * @throws Exception
	 * @throws SQLException
	 */
	public static void closeConnection() throws Exception, SQLException {
		if (connection != null) {
			if (!connection.isClosed()) {
				connection.commit();
				connection.close();
			}
			connection = null;
		}
	}
}
