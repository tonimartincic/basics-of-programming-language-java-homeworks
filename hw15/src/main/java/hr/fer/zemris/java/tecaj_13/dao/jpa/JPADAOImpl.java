package hr.fer.zemris.java.tecaj_13.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Method implements {@link DAO} and represents dao which stores data in database.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		
		return blogEntry;
	}

	@Override
	public boolean doesNickAlreadyExist(String nick) {
		EntityManager entityManager = JPAEMProvider.getEntityManager();
		
		@SuppressWarnings("unchecked")
		List<BlogUser> blogUsers = (List<BlogUser>) entityManager.createQuery("select bu from BlogUser as bu where bu.nick=:nick")
		                                			.setParameter("nick", nick)
		                                			.getResultList();
		
		return !blogUsers.isEmpty();
	}

	@Override
	public void registerNewBlogUser(BlogUser blogUser) {
		JPAEMProvider.getEntityManager().persist(blogUser);
	}

	@Override
	public String getPasswordHashByNick(String nick) {
		EntityManager entityManager = JPAEMProvider.getEntityManager();
		
		return (String) entityManager.createQuery("select bu.passwordHash from BlogUser as bu where bu.nick=:nick")
							.setParameter("nick", nick)
							.getSingleResult();
	}

	@Override
	public BlogUser getBlogUserByNick(String nick) {
		EntityManager entityManager = JPAEMProvider.getEntityManager();
		
		return (BlogUser) entityManager.createQuery("select bu from BlogUser as bu where bu.nick=:nick")
									   .setParameter("nick", nick)
									   .getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllNicks() {
		EntityManager entityManager = JPAEMProvider.getEntityManager();
		
		return (List<String>) entityManager.createQuery("select bu.nick from BlogUser as bu").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BlogEntry> getBlogEntriesByNick(String nick) {
		EntityManager entityManager = JPAEMProvider.getEntityManager();
		
		return (List<BlogEntry>) entityManager.createQuery("select be from BlogEntry as be INNER JOIN BlogUser as bu ON be.creator.nick = bu.nick where bu.nick=:nick")
											  .setParameter("nick", nick)
											  .getResultList();
	}

	@Override
	public void addNewBlogEntry(BlogEntry blogEntry) {
		JPAEMProvider.getEntityManager().persist(blogEntry);
	}

	@Override
	public void addNewBlogComment(BlogComment blogComment) {
		JPAEMProvider.getEntityManager().persist(blogComment);
	}
}