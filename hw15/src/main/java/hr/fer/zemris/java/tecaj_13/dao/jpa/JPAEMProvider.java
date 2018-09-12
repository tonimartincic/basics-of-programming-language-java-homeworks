package hr.fer.zemris.java.tecaj_13.dao.jpa;

import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import javax.persistence.EntityManager;

/**
 * Class provides {@link EntityManager}.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class JPAEMProvider {

	/**
	 * Instance of {@link ThreadLocal}.
	 */
	private static ThreadLocal<LocalData> locals = new ThreadLocal<>();

	/**
	 * Method gets {@link EntityManager}.
	 * 
	 * @return {@link EntityManager}
	 */
	public static EntityManager getEntityManager() {
		LocalData ldata = locals.get();
		
		if(ldata==null) {
			ldata = new LocalData();
			ldata.em = JPAEMFProvider.getEmf().createEntityManager();
			ldata.em.getTransaction().begin();
			locals.set(ldata);
		}
		
		return ldata.em;
	}

	/**
	 * Method closes {@link EntityManager}.
	 * 
	 * @throws DAOException if it is unable to commit transaction or close {@link EntityManager}
	 */ 
	public static void close() throws DAOException {
		LocalData ldata = locals.get();
		if(ldata==null) {
			return;
		}
		
		DAOException dex = null;
		try {
			ldata.em.getTransaction().commit();
		} catch(Exception ex) {
			dex = new DAOException("Unable to commit transaction.", ex);
		}
		
		try {
			ldata.em.close();
		} catch(Exception ex) {
			if(dex!=null) {
				dex = new DAOException("Unable to close entity manager.", ex);
			}
		}
		
		locals.remove();
		if(dex!=null) throw dex;
	}
	
	/**
	 * Class contains {@link EntityManager}.
	 * 
	 * @author Toni Martinčić
	 * @version 1.0
	 */
	private static class LocalData {
		
		/**
		 * Instance of {@link EntityManager}.
		 */
		EntityManager em;
	}
	
}