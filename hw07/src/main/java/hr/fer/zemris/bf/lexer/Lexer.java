package hr.fer.zemris.bf.lexer;

/**
 * Class represents lexical analyzer. It returns Tokens from accepted text which is boolean
 * expression. Each returned token is one part of boolean expression. Token can be operator, variable,
 * constant or opened or closed bracket.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class Lexer {
	
	/**
	 * Char data of accepted text.
	 */
	private char[] data;
	
	/**
	 * Current index of data.
	 */
	private int currentIndex;
	
	/**
	 * Constructor which accepts one argument, boolean expression as string which will be analyzed.
	 * Boolean expression is converted into char array.
	 * 
	 * @param expression boolean expression as string
	 */
	public Lexer(String expression) {
		if(expression == null) {
			throw new LexerException("Expression can not be null value.");
		}
		
		data = expression.toCharArray();
		currentIndex = 0;
	}
	
	/**
	 * Method returns next token from boolean expression.
	 * 
	 * @return next token from boolean expression
	 * @throws LexerException if expression is not valid boolean expression
	 */
	public Token nextToken() {
		skipBlanks();
		
		if(currentIndex >= data.length) {
			return new Token(TokenType.EOF, null);
		}
		
		if(Character.isLetter(data[currentIndex])) {
			return nextIdentifier();
		}
		
		if(Character.isDigit(data[currentIndex])) {
			return nextNumericalSeries();
		}
		
		if(isCurrentDataBracket()) {
			return nextBracket();
		}
		
		if(isCurrentDataOperator()) {
			return nextOperator();
		}
		
		throw new LexerException("Expression is not valid.");
	}
	
	/**
	 * Method returns next identifier from boolean expression. Identifier can be operator, variable or
	 * constant.
	 * 
	 * @return next identifier from boolean expression
	 */
	private Token nextIdentifier() {
		String identifier = getIdentifier();
		
		if(isOperatorAsString(identifier)) {
			return new Token(TokenType.OPERATOR, identifier.toLowerCase());
		}
		
		if(isConstantAsString(identifier)) {
			return new Token(TokenType.CONSTANT, identifier.toLowerCase().equals("true") ? true : false);
		}
		
		return new Token(TokenType.VARIABLE, identifier.toUpperCase());
	}
	
	/**
	 * Helping method which gets next identifier.
	 * 
	 * @return next identifier
	 */
	private String getIdentifier() {
		StringBuilder sb = new StringBuilder();
		
		while((currentIndex < data.length) && 
			  (Character.isLetterOrDigit(data[currentIndex]) || data[currentIndex] == '_')) {
			sb.append(data[currentIndex]);
			
			currentIndex++;
		}
		
		return sb.toString().toLowerCase();
	}
	
	/**
	 * Method checks is accepted string one of the operators. Operators are or, and, xor and not.
	 * 
	 * @param identifier string which is checked
	 * @return true if accepted string is operator, false otherwise
	 */
	private boolean isOperatorAsString(String identifier) {
		return identifier.toLowerCase().equals("and") || identifier.toLowerCase().equals("xor") ||
			   identifier.toLowerCase().equals("or") || identifier.toLowerCase().equals("not");
	}
	
	/**
	 * Method checks is accepted string one of the constants. Constants are true and false.
	 * 
	 * @param identifier accepted string 
	 * @return true if accepted string is one of the constants, false otherwise
	 */
	private boolean isConstantAsString(String identifier) {
		return identifier.toLowerCase().equals("true") || identifier.toLowerCase().equals("false");
	}

	/**
	 * Method returns next numerical series from accepted expression. Valid digits are 0 and 1.
	 * Valid length of numerical series is 1.
	 * 
	 * @return next numerical series
	 * @throws LexerException if length of numerical series is not 1 and if there is some other digit that
	 * is not 0 or 1.
	 */
	private Token nextNumericalSeries() {
		if(data[currentIndex] != '0' && data[currentIndex] != '1') {
			throw new LexerException("Only 0 and 1 are accepted as numerical values.");
		}
		
		if(currentIndex + 1 < data.length && Character.isDigit(data[currentIndex + 1])) {
			throw new LexerException("Numerical series must be length 1.");
		}
		
		if(data[currentIndex] == '0') {
			currentIndex++;
			
			return new Token(TokenType.CONSTANT, false);
		} else {
			currentIndex++;
			
			return new Token(TokenType.CONSTANT, true);
		}
	}

	/**
	 * Method returns next bracket from boolean expression.
	 * 
	 * @return next bracket from boolean expression
	 */
	private Token nextBracket() {
		if(data[currentIndex] == '(') {
			currentIndex++;
			
			return new Token(TokenType.OPEN_BRACKET, '(');
		} else {
			currentIndex++;
			
			return new Token(TokenType.CLOSED_BRACKET, ')');
		}
	}

	/**
	 * Method returns next operator from boolean expression.
	 * 
	 * @return next operator from boolean expression
	 */
	private Token nextOperator() {
		String operator = null;
		
		switch(data[currentIndex]) {
			case '*' :
				operator = "and";
				currentIndex++;
				
				break;
				
			case '+' :
				operator = "or";
				currentIndex++;
			
				break;
				
			case '!' :
				operator = "not";
				currentIndex++;
				
				break;
				
			case ':' :
				operator = "xor";
				currentIndex += 3;
				
				break;
		}
		
		return new Token(TokenType.OPERATOR, operator);
	}

	/**
	 * Method checks if current data is bracket.
	 * 
	 * @return true if current data is bracket, false otherwise
	 */
	private boolean isCurrentDataBracket() {
		return data[currentIndex] == '(' || data[currentIndex] == ')';
	}

	/**
	 * Method checks if current data is operator.
	 * 
	 * @return true if current data is operator, false otherwise
	 */
	private boolean isCurrentDataOperator() {
		boolean isOperator = false;
		
		if(data[currentIndex] == '*' || data[currentIndex] == '+' || data[currentIndex] == '!') {
			isOperator = true;
		}
		
		if(data[currentIndex] == ':') {
			if(currentIndex + 2 >= data.length) {
				throw new LexerException("Expression is invalid");
			}
			
			if(data[currentIndex + 1] == '+' && data[currentIndex + 2] == ':') {
				isOperator = true;
			}
		}
		
		return isOperator;
	}

	/**
	 * Method skips all blanks in boolean expression.
	 */
	private void skipBlanks() {
		while(currentIndex < data.length) {
			char c = data[currentIndex];
			
			if(c==' ' || c=='\t' || c=='\r' || c=='\n') {
				currentIndex++;
				continue;
			}
			
			break;
		}
	} 

}
