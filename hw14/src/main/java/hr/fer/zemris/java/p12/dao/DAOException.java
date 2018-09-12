package hr.fer.zemris.java.p12.dao;

import java.io.Serializable;

/**
 * Class extends {@link RuntimeException} and represents exception which is thrown from methods of the
 * interface {@link DAO}.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class DAOException extends RuntimeException {

	/**
	 * Universal version identifier for a {@link Serializable} class.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor which accepts no arguments and calls constructor of the {@link RuntimeException}.
	 */
	public DAOException() {
	}

	/**
	 * Constructor which accepts four arguments; message, cause, enableSuppression, writableStackTrace.
	 * 
	 * @param message exception message
	 * @param cause
	 * @param enableSuppression is suppression enabled
	 * @param writableStackTrace is stack trace writeable
	 */
	public DAOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Constructor which accepts two arguments, message and cause of the exception.
	 * 
	 * @param message message of the exception
	 * @param cause cause of the exception
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor which accepts one argument, message of the exception.
	 * 
	 * @param message message of the exception
	 */
	public DAOException(String message) {
		super(message);
	}

	/**
	 * Constructor which accepts one argument, cause of the exception.
	 * 
	 * @param cause cause of the exception
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}
}