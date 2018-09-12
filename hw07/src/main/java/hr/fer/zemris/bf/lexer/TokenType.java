package hr.fer.zemris.bf.lexer;

/**
 * Enumeration represents types of tokens of boolean expression.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public enum TokenType {
	
	/**
	 * End of the boolean expression.
	 */
	EOF,
	
	/**
	 * Variable of the boolean expression.
	 */ 
	VARIABLE,
	
	/**
	 * Constant of the boolean expression.
	 */
	CONSTANT,
	
	/**
	 * Operator of the boolean expression.
	 */
	OPERATOR,
	
	/**
	 * Open bracket of the boolean expression.
	 */
	OPEN_BRACKET,
	
	/**
	 * Close bracket of the boolean expression.
	 */
	CLOSED_BRACKET

}
