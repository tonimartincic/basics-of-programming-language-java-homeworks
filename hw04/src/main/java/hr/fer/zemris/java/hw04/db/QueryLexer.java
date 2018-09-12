package hr.fer.zemris.java.hw04.db;

/**
 * Class represents lexical analyser. It returns conditional expressions from accepted query.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class QueryLexer {
	
	/**
	 * Current conditional expression.
	 */
	private ConditionalExpression conditionalExpression;
	
	/**
	 * Char Array of query which is being analysed.
	 */
	private char[] data;
	
	/**
	 * Current index of data.
	 */
	private int currentIndex;
	
	/**
	 * Boolean which contains information if current conditional expression is first.
	 */
	boolean isFirstConditionalExpression = true;
	
	
	/**
	 * Constructor which accepts query as string.
	 * 
	 * @param queryString
	 */
	public QueryLexer(String queryString) {
		data = queryString.toCharArray();
		currentIndex = 0;
	}
	
	/**
	 * Getter for the current conditional expression.
	 * 
	 * @return current conditional expression
	 */
	public ConditionalExpression getConditionalExpression() {
		return conditionalExpression;
	}
	
	
	/**
	 * Method returns next conditional expression if it exists. If it doesn't exist it returns
	 * null.
	 * 
	 * @return next conditional expression
	 * @throws QueryLexerException if the query is invalid QueryLexerException is thrown
	 */
	public ConditionalExpression nextConditionalExpression() {
		skipBlanks();
		
		if(currentIndex >= data.length) {
			return null;
		}
		
		skipANDifExists();
		
		IFieldValueGetter fieldGetter;
		IComparisonOperator comparisonOperator;
		String stringLiteral;
		
		try {
			skipBlanks();
			
			fieldGetter = extractFieldValueGetter();
			comparisonOperator = extractComparisonOperator();
			stringLiteral = extractStringLiteral();
		} catch(IndexOutOfBoundsException exc) {
			throw new QueryLexerException("Invalid query.");
		} catch(Exception exc) {
			throw new QueryLexerException(exc.getMessage());
		}
		
		isFirstConditionalExpression = false;
		
		return new ConditionalExpression(fieldGetter, stringLiteral, comparisonOperator);
	}

	/**
	 * Method extracts IFieldValueGetter of ConditionalExpression.
	 * 
	 * @return IFieldValueGetter
	 */
	private IFieldValueGetter extractFieldValueGetter() {
		String value = "";
		
		while(Character.isLetter(data[currentIndex])) {
			value += data[currentIndex];
			
			currentIndex++;
			
			if(value.equals("jmbag")) {
				return FieldValueGetters.JMBAG;
			}
			
			if(value.equals("lastName")) {
				return FieldValueGetters.LAST_NAME;
			}
			
			if(value.equals("firstName")) {
				return FieldValueGetters.FIRST_NAME;
			}
			
			if(currentIndex >= data.length) {
				throw new QueryLexerException("Invalid query.");
			}
		}
		
		throw new QueryLexerException("Invalid FieldValueGetter.");
	}
	
	/**
	 * Method extracts IComparisonOperator of ConditionalExpression.
	 * 
	 * @return IComparisonOperator
	 */
	private IComparisonOperator extractComparisonOperator() {
		skipBlanks();
		
		if(data[currentIndex] == '>' && data[currentIndex + 1] != '=') {
			currentIndex++;
			
			return ComparisonOperators.GREATER;
		} else if(data[currentIndex] == '<' && data[currentIndex + 1] != '=') {
			currentIndex++;
			
			return ComparisonOperators.LESS;
		} else if(data[currentIndex] == '>' && data[currentIndex + 1] == '=') {
			currentIndex += 2;
			
			return ComparisonOperators.GREATER_OR_EQUALS;
		} else if(data[currentIndex] == '<' && data[currentIndex + 1] == '=') {
			currentIndex += 2;
			
			return ComparisonOperators.LESS_OR_EQUALS;
		} else if(data[currentIndex] == '=') {
			currentIndex++;
			
			return ComparisonOperators.EQUALS;
		} else if(data[currentIndex] == '!' && data[currentIndex + 1] == '=') {
			currentIndex += 2;
			
			return ComparisonOperators.NOT_EQUALS;
		} else if(data[currentIndex] == 'L' &&
				  data[currentIndex + 1] == 'I' &&
				  data[currentIndex + 2] == 'K' &&
				  data[currentIndex + 3] == 'E') {
			currentIndex += 4;
			
			return ComparisonOperators.LIKE;
		} else {
			throw new QueryLexerException("Invalid operator.");
		}
	}
	
	/**
	 * Method extracts string literal of ConditionalExpression.
	 * 
	 * @return string literal
	 */
	private String extractStringLiteral() {
		skipBlanks();
		
		if(data[currentIndex] != '"') {
			throw new QueryLexerException("Invalid String literal.");
		}
		
		currentIndex++; //skip "
		
		String stringLiteral = "";
		
		while(data[currentIndex] != '"') {
			stringLiteral += data[currentIndex];
			
			currentIndex++;
			
			if(currentIndex >= data.length) {
				throw new QueryLexerException("Invalid query.");
			}
		}
		
		currentIndex++; //skip "
		
		return stringLiteral;
	}
	
	/**
	 * Method skips whitespaces.
	 */
	private void skipBlanks() {
		while(currentIndex < data.length) {
			char c = data[currentIndex];
			if(c==' ') {
				currentIndex++;
				continue;
			}
			break;
		}
	}
	
	/**
	 * If there is AND operator this method skips it.
	 */
	private void skipANDifExists() {
		if((data[currentIndex] == 'a' || data[currentIndex] == 'A') &&
		   (data[currentIndex + 1] == 'n' || data[currentIndex + 1] == 'N') &&
		   (data[currentIndex + 2] == 'd' || data[currentIndex + 2] == 'D')) {
			if(isFirstConditionalExpression) {
				throw new QueryLexerException("\"And\" can't be at start.");
			}
			
			currentIndex += 3;
		}
	}

	/**
	 * This class represents Exception which is thrown from QueryLexer if the query is invalid.
	 * 
	 * @author Toni Martinčić
	 * @version 1.0
	 */
	public class QueryLexerException extends RuntimeException {
		
		/**
		 * Universal version identifier for a Serializable class.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Deafult constructor which accepts one parameter, message of exception.
		 * 
		 * @param message message of exception
		 */
		public QueryLexerException(String message) {
			super(message);
		}

	}

}
