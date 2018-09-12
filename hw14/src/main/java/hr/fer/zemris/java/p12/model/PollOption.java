package hr.fer.zemris.java.p12.model;

/**
 * Class represents one record in table polloptions.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class PollOption {
	
	/**
	 * Poll option id.
	 */
	private long id;
	
	/**
	 * Poll option title.
	 */
	private String optionTitle;
	
	/**
	 * Poll option link.
	 */
	private String optionLink;
	
	/**
	 * Poll id of the poll option.
	 */
	private long pollID;
	
	/**
	 * Poll option votes count.
	 */
	private long votesCount;
	
	/**
	 * Empty constructor which accepts no arguments.
	 */
	public PollOption() {
	}

	/**
	 * Constructor which accepts five arguments; id, optionTitle, optionLink, pollID and votesCount.
	 * 
	 * @param id poll option id
	 * @param optionTitle poll option title
	 * @param optionLink poll option link
	 * @param pollID poll id
	 * @param votesCount poll option votes count
	 */
	public PollOption(long id, String optionTitle, String optionLink, long pollID, long votesCount) {
		this.id = id;
		this.optionTitle = optionTitle;
		this.optionLink = optionLink;
		this.pollID = pollID;
		this.votesCount = votesCount;
	}

	/**
	 * Getter for the id.
	 * 
	 * @return id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Setter for the id
	 * 
	 * @param id poll option id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Getter for the option title.
	 * 
	 * @return poll option title
	 */
	public String getOptionTitle() {
		return optionTitle;
	}

	/**
	 * Setter for the poll option title
	 * 
	 * @param optionTitle poll option title
	 */
	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}

	/**
	 * Getter for the poll option link.
	 * 
	 * @return poll option link
	 */
	public String getOptionLink() {
		return optionLink;
	}

	/**
	 * Setter for the poll option link.
	 * 
	 * @param optionLink poll option link
	 */
	public void setOptionLink(String optionLink) {
		this.optionLink = optionLink;
	}

	/**
	 * Getter for the poll id.
	 * 
	 * @return poll id
	 */
	public long getPollID() {
		return pollID;
	}

	/**
	 * Setter for the poll id.
	 * 
	 * @param pollID poll id
	 */
	public void setPollID(long pollID) {
		this.pollID = pollID;
	}

	/**
	 * Getter for the votes count.
	 * 
	 * @return poll option votes count
	 */
	public long getVotesCount() {
		return votesCount;
	}

	/**
	 * Setter for the votes count.
	 * 
	 * @param votesCount poll option votes count
	 */
	public void setVotesCount(long votesCount) {
		this.votesCount = votesCount;
	}
}
