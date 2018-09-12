package hr.fer.zemris.bf.parser;

/**
 * Exception that is thrown from class {@link Parser}. It extends RuntimeException.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class ParserException extends RuntimeException {

	/**
	 * Universal version identifier for a Serializable class.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Empty constructor.
	 */
	public ParserException() {
		super();
	}
	
	/**
	 * Constructor which accepts one parameter, message of exception.
	 * 
	 * @param message message of exception
	 */
	public ParserException(String message) {
		super(message);
	}
	
	/**
	 * Constructor which accepts one parameter, cause of exception.
	 * 
	 * @param cause cause of exception
	 */
	public ParserException(Throwable cause) {
		super(cause);
	}

}
