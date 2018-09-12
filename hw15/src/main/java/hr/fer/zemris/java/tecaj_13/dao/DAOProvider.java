package hr.fer.zemris.java.tecaj_13.dao;

import hr.fer.zemris.java.tecaj_13.dao.jpa.JPADAOImpl;

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
	private static DAO dao = new JPADAOImpl();
	
	/**
	 * Method returns instance of {@link DAO}.
	 * 
	 * @return instance of {@link DAO}
	 */
	public static DAO getDAO() {
		return dao;
	}
}