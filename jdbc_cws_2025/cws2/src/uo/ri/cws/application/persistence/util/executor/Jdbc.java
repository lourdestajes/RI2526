package uo.ri.cws.application.persistence.util.executor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import uo.ri.conf.Conf;

public class Jdbc {

	private static ThreadLocal<Connection> threadConnection = new ThreadLocal<>();

	private static String URL = Conf.getProperty("DB_URL");
	private static String USER = Conf.getProperty("DB_USER");
	private static String PASS = Conf.getProperty("DB_PASS");
	
	public static Connection createThreadConnection() throws SQLException {
		Connection con = DriverManager.getConnection(URL, USER, PASS);
		threadConnection.set(con);
		return con;
	}

	public static Connection getCurrentConnection() {
		return threadConnection.get();
	}

}
