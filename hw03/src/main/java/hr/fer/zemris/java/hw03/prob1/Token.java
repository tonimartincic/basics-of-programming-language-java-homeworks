package hr.fer.zemris.java.hw03.prob1;

/**
 * Token is lexical parameter which groups one or more chars from text.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */

public class Token {
	
	/**
	 * Type of the Token.
	 */
	private TokenType type;
	
	/**
	 * Value of the Token.
	 */
	private Object value;
	
	/**
	 * Constructor which accepts two arguments, TokenType type and Object value.
	 * 
	 * @param type type of the Token
	 * @param value value of the Token
	 */
	public Token(TokenType type, Object value) {
		super();
		
		if(type == null) {
			throw new IllegalArgumentException("Token type can not be null.");
		}
		
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Getter for the value.
	 * 
	 * @return value
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * Getter for the type.
	 * 
	 * @return type
	 */
	public TokenType getType() {
		return type;
	}

}
