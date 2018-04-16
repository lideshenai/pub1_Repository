/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author Administrator
 */
public class ConnUtil {
	private static Properties properties;
	public static final String URI = "db.properties";
	private static String url;
	private static String driver;
	private static String username;
	private static String password;
	@SuppressWarnings("unused")
	private static long maxWait;
	private static Connection con;
	static {
		properties = new Properties();
		try {
			properties.load(new FileInputStream(URI));
			url = properties.getProperty("url");
			driver = properties.getProperty("driver");
			password = properties.getProperty("password");
			username = properties.getProperty("username");
			maxWait = Long.valueOf(properties.getProperty("maxWait"));
		} catch (FileNotFoundException e) {
			doIfFileNotFount();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		con = DriverManager.getConnection(url, username, password);
		return con;
	}

	private static void doIfFileNotFount() {
		driver = "com.mysql.jdbc.Driver";
		url = "jdbc:mysql:///code?useUnicode=true&characterEncoding"
				+ "=UTF-8&allowMultiQueries=true";
		username = "yy";
		password = "zhyu";
		File db = new File(URI);
		try {
			db.createNewFile();
		} catch (IOException e) {
		}
	}

	public static void closeConnection() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
