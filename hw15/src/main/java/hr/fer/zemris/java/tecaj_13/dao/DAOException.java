package hr.fer.zemris.java.tecaj_13.dao;

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
}