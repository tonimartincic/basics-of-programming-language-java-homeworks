package hr.fer.zemris.java.tecaj_13.dao;

import java.util.List;

import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Interface that provides access to an underlying database or any other persistence storage.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public interface DAO {

	/**
	 * Method gets {@link BlogEntry} for the accepted id.
	 * 
	 * @param id id
	 * @return {@link BlogEntry}
	 * @throws DAOException if anything goes wrong in method
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;
	
	/**
	 * Method checks if accepted nick already exist.
	 * 
	 * @param nick nick
	 * @return true if nick already exist, false otherwise
	 * @throws DAOException if anything goes wrong in method
	 */
	public boolean doesNickAlreadyExist(String nick) throws DAOException;
	
	/**
	 * Method registers new {@link BlogUser}
	 * 
	 * @param blogUser new {@link BlogUser}
	 * @throws DAOException if anything goes wrong in method
	 */
	public void registerNewBlogUser(BlogUser blogUser) throws DAOException;
	
	/**
	 * Method adds new {@link BlogEntry}.
	 * 
	 * @param blogEntry new {@link BlogEntry}
	 * @throws DAOException if anything goes wrong in method
	 */
	public void addNewBlogEntry(BlogEntry blogEntry) throws DAOException;
	
	/**
	 * Method gets password hash for the accepted nick.
	 * 
	 * @param nick nick
	 * @return password hash
	 * @throws DAOException if anything goes wrong in method
	 */
	public String getPasswordHashByNick(String nick) throws DAOException;
	
	/**
	 * Method gets {@link BlogUser} for the accepted nick.
	 * 
	 * @param nick nick
	 * @return {@link BlogUser}
	 * @throws DAOException if anything goes wrong in method
	 */
	public BlogUser getBlogUserByNick(String nick) throws DAOException;
	
	/**
	 * Method gets all nicks.
	 * 
	 * @return all nicks
	 * @throws DAOException if anything goes wrong in method
	 */ 
	public List<String> getAllNicks() throws DAOException;
	
	/**
	 * Method gets all blog entries for the accepted nick.
	 * 
	 * @param nick nick
	 * @return all blog entries
	 * @throws DAOException if anything goes wrong in method
	 */
	public List<BlogEntry> getBlogEntriesByNick(String nick) throws DAOException;
	
	/**
	 * Method adds new {@link BlogComment}.
	 * 
	 * @param blogComment new {@link BlogComment}
	 * @throws DAOException if anything goes wrong in method
	 */
	public void addNewBlogComment(BlogComment blogComment) throws DAOException;
}