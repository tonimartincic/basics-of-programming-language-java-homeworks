package hr.fer.zemris.java.hw04.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw04.db.QueryLexer.QueryLexerException;

/**
 * Class represents parser which parse accepted query to list of conditional expressions.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class QueryParser {
	
	/**
	 * Instance of QueryLexer.
	 */
	private QueryLexer queryLexer;
	
	/**
	 * List of conditional expressions. 
	 */
	private List<ConditionalExpression> queryList = new ArrayList<>();
	
	/**
	 * Constructor which accepts query as string. It creates instance of QueryLexer and call
	 * method parse().
	 * 
	 * @param queryString
	 */
	public QueryParser(String queryString) {
		if(queryString.equals("") || queryString == null) {
			throw new QueryParserException("Invalid query.");
		}
		
		queryLexer = new QueryLexer(queryString);
		
		parse();
	}
	
	/**
	 * Method accepts all conditional expressions from queryLexer and adds them to the list of
	 * conditional expressions
	 */
	private void parse() {
		ConditionalExpression conditionalExpression = null;
		
		while(true) {
			try {
				conditionalExpression = queryLexer.nextConditionalExpression();
			} catch(QueryLexerException exc) {
				throw new QueryParserException(exc.getMessage());
			}
			
			if(conditionalExpression == null) {
				break;
			}
			
			queryList.add(conditionalExpression);
		}
	}
	
	/**
	 * Methd checks if the accepted query is direct query.
	 * 
	 * @return true if the query is direct query, false otherwise
	 */
	boolean isDirectQuery() {
		if(queryList.size() != 1) {
			return false;
		}
		
		ConditionalExpression conditionalExpression = queryList.get(0);
		
		if(conditionalExpression.getFieldGetter() != FieldValueGetters.JMBAG  ||
		   conditionalExpression.getComparisonOperator() != ComparisonOperators.EQUALS) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Method gets jmbag from direct query.
	 * 
	 * @return jmbag
	 * @throws IllegalStateException if accepted query is not direct query
	 * IllegalStateException is thrown
	 */
	String getQueriedJMBAG() {
		if(!isDirectQuery()) {
			throw new IllegalStateException("Query is not direct one");
		}
		
		ConditionalExpression conditionalExpression = queryList.get(0);
		
		return conditionalExpression.getStringLiteral();
	}
	
	
    /**
     * Method gets list of the conditional expressions.
     * 
     * @return list of the conditional expressions
     */
	List<ConditionalExpression> getQuery() {
		return queryList;
	}
	
	/**
	 * This class represents Exception which is thrown from QueryParser if the query is invalid.
	 * 
	 * @author Toni Martinčić
	 * @version 1.0
	 */
	public class QueryParserException extends RuntimeException {
		
		/**
		 * Universal version identifier for a Serializable class.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Deafult constructor which accepts one parameter, message of exception.
		 * 
		 * @param message message of exception
		 */
		public QueryParserException(String message) {
			super(message);
		}

	}

}
