package hr.fer.zemris.bf.lexer;

/**
 * Exception that is thrown from class Lexer. It extends RuntimeException.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class LexerException extends RuntimeException {

	/**
	 * Universal version identifier for a Serializable class.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Empty constructor.
	 */
	public LexerException() {
		super();
	}
	
	/**
	 * Constructor which accepts one parameter, message of exception.
	 * 
	 * @param message message of exception
	 */
	public LexerException(String message) {
		super(message);
	}
	
	/**
	 * Constructor which accepts one parameter, cause of exception.
	 * 
	 * @param cause cause of exception
	 */
	public LexerException(Throwable cause) {
		super(cause);
	}

}
