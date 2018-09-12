package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Exception that is thrown from class SmartScriptParser. It extends RuntimeException.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */

public class SmartScriptParserException extends RuntimeException {
	
	
	/**
	 * Universal version identifier for a Serializable class.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Deafult constructor which accepts one parameter, message of exception.
	 * 
	 * @param message message of exception
	 */
	public SmartScriptParserException(String message) {
		super(message);
	}

}
