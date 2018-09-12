package hr.fer.zemris.java.custom.collections;

/**
 * If the stack is empty when method pop or peek is called, 
 * the method should throw EmptyStackException. 
 * 
 * @author Toni Martinčić
 * @version 1.0
 */

public class EmptyStackException extends RuntimeException {
	
	/**
	 * Universal version identifier for a Serializable class.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Deafult constructor which accepts one parameter, message of exception.
	 * 
	 * @param message message of exception
	 */
	public EmptyStackException(String message) {
		super(message);
	}

}
