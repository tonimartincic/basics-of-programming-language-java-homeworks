package hr.fer.zemris.java.hw04.db;

/**
 * Class represents conditional expression which has  a reference to IFieldValueGetter strategy,
 * a reference to stringliteral and a reference to IComparisonOperator strategy.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class ConditionalExpression {
	
	/**
	 * A reference to IFieldValueGetter strategy.
	 */
	private IFieldValueGetter fieldGetter;
	
	/**
	 * A reference to stringliteral.
	 */
	private String stringLiteral;
	
	/**
	 * A reference to IComparisonOperator strategy.
	 */
	private IComparisonOperator comparisonOperator;
	
	/**
	 * Constructor with three arguments: a reference to IFieldValueGetter strategy, 
	 * a reference to string literal and a reference to IComparisonOperator strategy.
	 * 
	 * @param fieldGetter IFieldValueGetter strategy
	 * @param stringLiteral string literal
	 * @param comparisonOperator IComparisonOperator strategy
	 */
	public ConditionalExpression(IFieldValueGetter fieldGetter, String stringLiteral,
			IComparisonOperator comparisonOperator) {
		super();
		this.fieldGetter = fieldGetter;
		this.stringLiteral = stringLiteral;
		this.comparisonOperator = comparisonOperator;
	}

	/**
	 * Getter for the fieldGetter.
	 * 
	 * @return fieldGetter
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	/**
	 * Getter for the stringLiteral
	 * 
	 * @return stringLiteral
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}

	/**
	 * Getter for the comparisonOperator.
	 * 
	 * @return comparisonOperator
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}
	
}
