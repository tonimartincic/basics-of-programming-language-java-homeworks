package hr.fer.zemris.java.p12.dao;

import hr.fer.zemris.java.p12.dao.sql.SQLDAO;

/**
 * Method contains only one method 'public static DAO getDao()' which returns instance of {@link DAO}. 
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class DAOProvider {

	/**
	 * Instance of {@link DAO}.
	 */
	private static DAO dao = new SQLDAO();
	
	/**
	 * Method returns instance of {@link DAO}.
	 * 
	 * @return instance of {@link DAO}
	 */
	public static DAO getDao() {
		return dao;
	}
	
}