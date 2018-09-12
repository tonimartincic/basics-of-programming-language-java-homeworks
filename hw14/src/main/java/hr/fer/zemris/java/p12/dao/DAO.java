package hr.fer.zemris.java.p12.dao;

import java.util.List;

import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * Interface that provides access to an underlying database or any other persistence storage.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public interface DAO {

	/**
	 * Method gets poll from the poll table for the accepted id.
	 * 
	 * @param id poll id
	 * @return poll from the poll table for the accepted id
	 * @throws DAOException if anything goes wrong in method
	 */
	public Poll getPoll(long id) throws DAOException;
	
	/**
	 * Method returns ids and titles of all polls from the polls table.
	 * 
	 * @return ids and titles of all polls from the polls table
	 * @throws DAOException if anything goes wrong in method
	 */
	public List<Poll> getBasicListOfPolls() throws DAOException;
	
	/**
	 * Method gets list of all poll options for the poll which id is accepted as argument.
	 * 
	 * @param pollID id of poll which poll options are returned
	 * @param sortBy poll options attribute which is used for sorting poll options
	 * @return list of all poll options for the poll which id is accepted as argument
	 * @throws DAOException if anything goes wrong in method
	 */
	public List<PollOption> getListOfPollOptions(long pollID, String sortBy) throws DAOException;
	
	/**
	 * Method adds one vote to the poll option. Poll option id and poll id are accepted as arguments
	 * of the method.
	 * 
	 * @param pollID poll id
	 * @param pollOptionID poll option id
	 * @throws DAOException if anything goes wrong in method
	 */
	public void addVote(long pollID, long pollOptionID) throws DAOException;
	
	/**
	 * Method gets all poll options with the most votes from the poll which id is accepted as argument.
	 * 
	 * @param pollID poll id
	 * @return all poll options with the most votes from the poll which id is accepted as argument.
	 * @throws DAOException if anything goes wrong in method
	 */
	public List<PollOption> getOptionsWithMostVotes(long pollID) throws DAOException;
	
	/**
	 * Method creates polls table if there is not polls table.
	 * 
	 * @throws DAOException if anything goes wrong in method
	 */
	public void createPollsTableIfNeeded() throws DAOException;
	
	/**
	 * Method creates poll options table if there is not polls table.
	 * 
	 * @throws DAOException if anything goes wrong in method
	 */
	public void createPollOptionsTableIfNeeded() throws DAOException;
	
	/**
	 * Method fills tables with data if the polls table is empty.
	 * 
	 * @throws DAOException if anything goes wrong in method
	 */
	public void fillTablesIfNeeded() throws DAOException;
}