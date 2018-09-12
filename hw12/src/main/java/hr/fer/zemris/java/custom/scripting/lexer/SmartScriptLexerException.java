package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Exception that is thrown from class SmartScriptLexer. It extends RuntimeException.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */

public class SmartScriptLexerException extends RuntimeException {
	
	
	/**
	 * Universal version identifier for a Serializable class.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Deafult constructor which accepts one parameter, message of exception.
	 * 
	 * @param message message of exception
	 */
	public SmartScriptLexerException(String message) {
		super(message);
	}

}
