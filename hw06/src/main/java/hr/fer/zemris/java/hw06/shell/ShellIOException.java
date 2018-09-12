package hr.fer.zemris.java.hw06.shell;

/**
 * Class extends {@link RuntimeException} and represents exception which is thrown if methods which read
 * or write {@link ShellEnvironment} fail.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class ShellIOException extends RuntimeException {
	
	/**
	 * Universal version identifier for a Serializable class.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Deafult constructor which accepts one parameter, message of exception.
	 * 
	 * @param message message of exception
	 */
	public ShellIOException(String message) {
		super(message);
	}

}
