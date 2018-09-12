package hr.fer.zemris.java.p12.dao.sql;

import java.sql.Connection;

/**
 * Class stores connections to the database in instance of {@link ThreadLocal} object.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class SQLConnectionProvider {

	/**
	 * Instance of {@link ThreadLocal} which stores connections to the database.
	 */
	private static ThreadLocal<Connection> connections = new ThreadLocal<>();
	
	/**
	 * Method sets connection for the current thread or removes connection if the accepted argument
	 * is null value.
	 * 
	 * @param con {@link Connection}
	 */
	public static void setConnection(Connection con) {
		if(con==null) {
			connections.remove();
		} else {
			connections.set(con);
		}
	}
	
	/**
	 * Method gets {@link Connection} for the current thread.
	 * 
	 * @return {@link Connection} for the current thread
	 */
	public static Connection getConnection() {
		return connections.get();
	}	
}