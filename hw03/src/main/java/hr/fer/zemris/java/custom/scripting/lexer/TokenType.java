package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Enumeration which represents types of Token.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */

public enum TokenType {
	
	/**
	 * Token of this type represents text.
	 */
	TEXT,
	
	/**
	 * Token of this type represents tag for.
	 */
	TAG_FOR,
	
	/**
	 * Token of this type represents tag end.
	 */
	TAG_END,
	
	/**
	 * Token of this type represents end of file.
	 */
	EOF,
	
	/**
	 * Token of this type represents empty tag.
	 */
	ECHO

}
