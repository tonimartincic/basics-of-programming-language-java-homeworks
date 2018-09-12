package hr.fer.zemris.bf.lexer;

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
	private TokenType tokenType;
	
	/**
	 * Value of the Token.
	 */
	private Object tokenValue;
	
	/**
	 * Constructor which accepts two arguments, TokenType type and Object value.
	 * 
	 * @param tokenType type of the Token
	 * @param tokenValue value of the Token
	 */
	public Token(TokenType tokenType, Object tokenValue) {
		super();
		
		this.tokenType = tokenType;
		this.tokenValue = tokenValue;
	}
	
	/**
	 * Getter for the tokenType.
	 * 
	 * @return tokenType
	 */
	public TokenType getTokenType() {
		return tokenType;
	}
	
	/**
	 * Getter for the tokenValue.
	 * 
	 * @return tokenValue
	 */
	public Object getTokenValue() {
		return tokenValue;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Type: ").append(tokenType.toString()).append(", ");
		sb.append("Value: ");
		
		if(tokenType == TokenType.EOF) {
			sb.append("null");
		} else { 
			sb.append(tokenValue.toString()).append(", ");
			sb.append("Value is instance of: ").append(tokenValue.getClass());
		}
		
		return sb.toString();
	}


}
