package hr.fer.zemris.java.hw03.prob1;

/**
 * Enumeration which represents types of Token.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */

public enum TokenType {
	
	/**
	 * Token of this type represents end of file.
	 */
	EOF, 
	
	/**
	 * Token of this type represents text.
	 */
	WORD,
	
	/**
	 * Token of this type represents number.
	 */
	NUMBER,
	
	/**
	 * Token of this type represents symbol.
	 */
	SYMBOL

}
