package hr.fer.zemris.java.tecaj_13.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * Class provides {@link EntityManagerFactory}.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class JPAEMFProvider {

	/**
	 * Instance of {@link EntityManagerFactory}.
	 */
	public static EntityManagerFactory emf;
	
	/**
	 * Method gets {@link EntityManagerFactory}.
	 * 
	 * @return {@link EntityManagerFactory}
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}
	
	/**
	 * Method sets {@link EntityManagerFactory}.
	 * 
	 * @param emf {@link EntityManagerFactory}
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}