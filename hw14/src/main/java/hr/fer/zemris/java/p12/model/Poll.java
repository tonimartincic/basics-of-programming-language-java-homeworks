package hr.fer.zemris.java.p12.model;

/**
 * Class represents one record in table polls.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class Poll {
	
	/**
	 * Poll id.
	 */
	private long id;
	
	/**
	 * Poll title.
	 */
	private String title;
	
	/**
	 * Poll message.
	 */
	private String message;

	/**
	 * Empty constructor which accepts no arguments.
	 */
	public Poll() {
	}
	
	/**
	 * Constructor which accepts three arguments; id, title and message.
	 * 
	 * @param id poll id 
	 * @param title poll title
	 * @param message poll message
	 */
	public Poll(long id, String title, String message) {
		this.id = id;
		this.title = title;
		this.message = message;
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
	 * Setter for the id.
	 * 
	 * @param id poll id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Getter for the title.
	 * 
	 * @return poll title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Setter for the title.
	 * 
	 * @param title poll title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Getter for the message.
	 * 
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Setter for the message.
	 * 
	 * @param message poll message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
