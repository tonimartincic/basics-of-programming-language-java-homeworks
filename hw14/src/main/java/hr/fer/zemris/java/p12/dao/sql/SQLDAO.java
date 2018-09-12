package hr.fer.zemris.java.p12.dao.sql;

import hr.fer.zemris.java.p12.dao.DAO;
import hr.fer.zemris.java.p12.dao.DAOException;
import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.PollOption;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Class implements {@link DAO} and provides access to an underlying database.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class SQLDAO implements DAO {

	/**
	 * Sql command for creating the table polls.
	 */
	private static final String CREATE_POLLS_COMMAND = 
			"CREATE TABLE Polls(" +
				"id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY," +
				"title VARCHAR(150) NOT NULL," +
				"message CLOB(2048) NOT NULL" +
			")";

	/**
	 * Sql command for creating the table polloptions.
	 */
	private static final String CREATE_POLLOPTIONS_COMMAND = 
			"CREATE TABLE PollOptions(" +
				"id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY," +
				"optionTitle VARCHAR(100) NOT NULL," +
				"optionLink VARCHAR(150) NOT NULL," +
				"pollID BIGINT," +
				"votesCount BIGINT," +
				"FOREIGN KEY (pollID) REFERENCES Polls(id)" +
			")";

	@Override
	public void createPollsTableIfNeeded() throws DAOException {
		createTableIfNeeded("POLLS", CREATE_POLLS_COMMAND);
	}

	@Override
	public void createPollOptionsTableIfNeeded() throws DAOException {
		createTableIfNeeded("POLLOPTIONS", CREATE_POLLOPTIONS_COMMAND);
	}
	
	/**
	 * Method creates table if table which name is accepted as argument does not exist.
	 *  
	 * @param tableName name of the table
	 * @param command sql command for creating the table
	 */
	private void createTableIfNeeded(String tableName, String command) {
		Connection con = SQLConnectionProvider.getConnection();
		
		DatabaseMetaData dbmd = null;
		try {
			dbmd = con.getMetaData();
			
			ResultSet rs = dbmd.getTables(null, null, tableName, null);
			
			if (!rs.next()) {
				PreparedStatement pst = null;
				
				pst = con.prepareStatement(command);
				pst.executeUpdate();
			}
		} catch (SQLException e) {
			throw new DAOException("Error while accesing the database.");
		}
	}

	@Override
	public void fillTablesIfNeeded() throws DAOException {
		Connection con = SQLConnectionProvider.getConnection();
		
		try {
			PreparedStatement pst = con.prepareStatement("SELECT * FROM POLLS");
			
			ResultSet rs = pst.executeQuery();
			if(!rs.next()) {
				fillPollsTable(con);
			}
		} catch (SQLException e) {
			throw new DAOException("Error while accesing the database.");
		}
	}

	/**
	 * Method fills polls table with data.
	 * 
	 * @param con {@link Connection}
	 */
	private void fillPollsTable(Connection con) {
		try {
			PreparedStatement pst1 = con.prepareStatement("INSERT INTO POLLS (title, message) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
			pst1.setString(1, "Glasanje za omiljeni bend");
			pst1.setString(2, "Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste glasali!");
			
			pst1.executeUpdate();
			
			ResultSet rset1 = pst1.getGeneratedKeys();
			rset1.next();
			long pollID1 = rset1.getLong(1);
			
			pst1.close();
			rset1.close();
			
			insertBands(con, pollID1);
			
			PreparedStatement pst2 = con.prepareStatement("INSERT INTO POLLS (title, message) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
			pst2.setString(1, "Glasanje za omiljeno pivo");
			pst2.setString(2, "Od sljedećih piva, koji Vam je najdraža? Kliknite na link kako biste glasali!");
			
			pst2.executeUpdate();
			
			ResultSet rset2 = pst2.getGeneratedKeys();
			rset2.next();
			long pollID2 = rset2.getLong(1);
			
			pst2.close();
			rset2.close();
		
			insertBeers(con, pollID2);
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}	
	}
	
	/**
	 * Method inserts bands into polloptions table.
	 *  
	 * @param con {@link Connection}
	 * @param pollID id of the bands poll
	 */
	private void insertBands(Connection con, long pollID) {
		insertIntoPollOptionsTable(con, "The Beatles","https://www.youtube.com/watch?v=z9ypq6_5bsg", pollID, 0);
		insertIntoPollOptionsTable(con, "The Platters","https://www.youtube.com/watch?v=H2di83WAOhU", pollID, 0);
		insertIntoPollOptionsTable(con, "The Beach Boys","https://www.youtube.com/watch?v=2s4slliAtQU", pollID, 0);
		insertIntoPollOptionsTable(con, "The Four Seasons","https://www.youtube.com/watch?v=y8yvnqHmFds", pollID, 0);
		insertIntoPollOptionsTable(con, "The Marcels","https://www.youtube.com/watch?v=qoi3TH59ZEs", pollID, 0);
		insertIntoPollOptionsTable(con, "The Everly Brothers","https://www.youtube.com/watch?v=tbU3zdAgiX8", pollID, 0);
		insertIntoPollOptionsTable(con, "The Mamas And The Papas","https://www.youtube.com/watch?v=N-aK6JnyFmk", pollID, 0);
	}
	
	/**
	 * Method inserts beers into polloptions table.
	 * 
	 * @param con {@link Connection}
	 * @param pollID id of the beers poll
	 */
	private void insertBeers(Connection con, long pollID) {
		insertIntoPollOptionsTable(con, "Ožujsko","https://nutristo.com/photo/show/1063", pollID, 0);
		insertIntoPollOptionsTable(con, "Pan","http://www.pan.com.hr/media/1163/boca.png", pollID, 0);
		insertIntoPollOptionsTable(con, "Karlovačko","https://www.konzum.hr/klik/images/products/031/03130207_1l.jpg?1468316892", pollID, 0);
		insertIntoPollOptionsTable(con, "Zmajsko pale ale","https://www.konzum.hr/klik/images/products/901/90183384_1l.jpg?1452901739", pollID, 0);
		insertIntoPollOptionsTable(con, "O'hara's stout","https://boozedancing.files.wordpress.com/2012/11/oharas1.jpg", pollID, 0);
		insertIntoPollOptionsTable(con, "Duvel","http://www.duvelmoortgat.be/sites/default/files/duvel_packshot_1.png", pollID, 0);
		insertIntoPollOptionsTable(con, "Paulaner","https://www.paulaner.com/sites/default/files/images/produkte/hwb_glas.png", pollID, 0);
		insertIntoPollOptionsTable(con, "Erdinger","https://upload.wikimedia.org/wikipedia/commons/thumb/e/eb/Erdinger-bottle-glass_RMO.jpg/200px-Erdinger-bottle-glass_RMO.jpg", pollID, 0);
		insertIntoPollOptionsTable(con, "Guinness","https://blogs.haverford.edu/celticfringe/files/2017/02/guinness1.jpg", pollID, 0);
		insertIntoPollOptionsTable(con, "Gulden draak","https://www.vansteenberge.com/wp-content/uploads/2014/11/Gulden-Draak-toog.jpg", pollID, 0);
		insertIntoPollOptionsTable(con, "Chimay blue","http://s3.amazonaws.com/beertourprod/beers/pictures/000/000/069/original/Chimay-Bleue_-Blue_beer_900.jpg?1461521525", pollID, 0);
		insertIntoPollOptionsTable(con, "Benediktiner","https://beerapprentice.files.wordpress.com/2013/09/dscn3662.jpg", pollID, 0);
		insertIntoPollOptionsTable(con, "Augustiner","https://www.beerhawk.co.uk/media/catalog/product/cache/1/image/9df78eab33525d08d6e5fb8d27136e95/b/h/bh77_1.jpg", pollID, 0);
		insertIntoPollOptionsTable(con, "Bornem","http://cdn.shopify.com/s/files/1/0289/0324/products/bornem-dubbel-belgian-beer_1.jpeg", pollID, 0);
		insertIntoPollOptionsTable(con, "Velebitsko svijetlo","http://www.pivovara-licanka.hr/images/svijetlo-velebitsko.png", pollID, 0);
	}

	/**
	 * Method inserts poll option into polloptions table.
	 * 
	 * @param con {@link Connection}
	 * @param optionTitle option title
	 * @param optionLink option link
	 * @param pollID poll id 
	 * @param votesCount votes count
	 */
	private void insertIntoPollOptionsTable(Connection con, String optionTitle, String optionLink, long pollID, long votesCount) {
		try {
			PreparedStatement pst = con.prepareStatement("INSERT INTO POLLOPTIONS (optionTitle, optionLink, pollID, votesCount) VALUES (?, ?, ?, ?)");
			
			pst.setString(1, optionTitle);
			pst.setString(2, optionLink);
			pst.setLong(3, pollID);
			pst.setLong(4, votesCount);
			
			pst.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Error while accesing the database.");
		}
	}
	
	@Override
	public List<Poll> getBasicListOfPolls() throws DAOException {
		List<Poll> polls = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		
		try {
			pst = con.prepareStatement("select id, title from Polls order by id");
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while(rs!=null && rs.next()) {
						Poll poll = new Poll();
						poll.setId(rs.getLong(1));
						poll.setTitle(rs.getString(2));
						polls.add(poll);
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Error while getting list of polls.", ex);
		}
		
		return polls;
	}

	@Override
	public Poll getPoll(long id) throws DAOException {
		Poll poll = null;
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		
		try {
			pst = con.prepareStatement("select id, title, message from Polls where id=?");
			pst.setLong(1, Long.valueOf(id));
			try {
				ResultSet rs = pst.executeQuery();
				
				try {
					if(rs!=null && rs.next()) {
						poll = new Poll();
						poll.setId(rs.getLong(1));
						poll.setTitle(rs.getString(2));
						poll.setMessage(rs.getString(3));
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Error while getting poll.", ex);
		}
		
		return poll;
	}

	@Override
	public List<PollOption> getListOfPollOptions(long pollID, String sortBy) throws DAOException {
		List<PollOption> pollOptions = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		
		try {
			pst = con.prepareStatement("select id, optionTitle, optionLink, pollID, votesCount from PollOptions where pollID=? order by " + sortBy + " desc");
			pst.setLong(1, pollID);
		
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while(rs!=null && rs.next()) {
						PollOption pollOption = new PollOption();
						pollOption.setId(rs.getLong(1));
						pollOption.setOptionTitle(rs.getString(2));
						pollOption.setOptionLink(rs.getString(3));
						pollOption.setPollID(rs.getLong(4));
						pollOption.setVotesCount(rs.getLong(5));
						
						pollOptions.add(pollOption);
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException(ex.getMessage());
		}
		
		return pollOptions;
	}

	@Override
	public void addVote(long pollID, long pollOptionID) throws DAOException {
		Connection con = SQLConnectionProvider.getConnection();
		
		PreparedStatement pst = null;
		
		try {
			pst = con.prepareStatement("update polloptions set votesCount = votesCount + 1 where id=? and pollID=?");
			pst.setLong(1, pollOptionID);
			pst.setLong(2, pollID);
			try {
				pst.executeUpdate();
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Error while updating poll.", ex);
		}
	}

	@Override
	public List<PollOption> getOptionsWithMostVotes(long pollID) throws DAOException {
		List<PollOption> pollOptions = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		
		try {
			pst = con.prepareStatement(
					"select id, optionTitle, optionLink, pollID, votesCount from PollOptions where pollID=? and votesCount = (select max(votesCount) from polloptions where pollID=?)");
			pst.setLong(1, pollID);
			pst.setLong(2, pollID);
		
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while(rs!=null && rs.next()) {
						PollOption pollOption = new PollOption();
						pollOption.setId(rs.getLong(1));
						pollOption.setOptionTitle(rs.getString(2));
						pollOption.setOptionLink(rs.getString(3));
						pollOption.setPollID(rs.getLong(4));
						pollOption.setVotesCount(rs.getLong(5));
						
						pollOptions.add(pollOption);
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException(ex.getMessage());
		}
		
		return pollOptions;
	}
}