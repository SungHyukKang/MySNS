package util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionPool {
	private static ConnectionPool _instance = null;
	private static DataSource _ds = null;
	
	public static ConnectionPool getInstance() throws NamingException {
		if (_instance == null) {
			_instance = new ConnectionPool();
			_ds = (DataSource) (new InitialContext()).lookup("java:comp/env/jdbc/mysns");
		}
		return _instance;
	}

	public Connection getConn() throws SQLException {
		return _ds.getConnection();
	}
}
