package hr.fer.zemris.java.hw03.prob1;

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
	 * Deafult constructor which accepts one parameter, message of exception.
	 * 
	 * @param message message of exception
	 */
	public LexerException(String message) {
		super(message);
	}

}
