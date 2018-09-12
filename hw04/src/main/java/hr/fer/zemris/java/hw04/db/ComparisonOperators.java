package hr.fer.zemris.java.hw04.db;

/**
 * Class contains comparison operators which compare two strings.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class ComparisonOperators {

	/**
	 * Operator returns true if first String is less than second String.
	 */
	public static final IComparisonOperator LESS = ( (s1, s2) -> {
		return s1.compareTo(s2) < 0;
	});
	
	/**
	 * Operator returns true if first String is less or equal to second String.
	 */
	public static final IComparisonOperator LESS_OR_EQUALS = ( (s1, s2) -> {
		return s1.compareTo(s2) <= 0;
	});
	
	/**
	 * Operator returns true if first String is greater than second String.
	 */
	public static final IComparisonOperator GREATER = ( (s1, s2) -> {
		return s1.compareTo(s2) > 0;
	});
	
	/**
	 * Operator returns true if first String is greater or equal to second String.
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS = ( (s1, s2) -> {
		return s1.compareTo(s2) >= 0;
	});
	
	/**
	 * Operator returns true if first String is equal to second String.
	 */
	public static final IComparisonOperator EQUALS = ( (s1, s2) -> {
		return s1.compareTo(s2) == 0;
	});
	
	/**
	 * Operator returns true if first String is not equal to second String.
	 */
	public static final IComparisonOperator NOT_EQUALS = ( (s1, s2) -> {
		return s1.compareTo(s2) != 0;
	});
	
	/**
	 * In like operator, first argument is String to be checked,
	 * and the second argument is pattern to be checked.
	 */
	public static final IComparisonOperator LIKE = new IComparisonOperatorLike();
	
	/**
	 * Class implements IComparisonOperator and overrides 
	 * method satisfied(String value1, String value2). Method accepts String to be checked
	 * as value1 and pattern as value2.
	 * 
	 * @author Toni Martinčić
	 * @version 1.0
	 */
	public static class IComparisonOperatorLike implements IComparisonOperator {
		
		@Override
		public boolean satisfied(String value1, String value2) {
			if(value2.indexOf("*") != value2.lastIndexOf("*")) {
				throw new IllegalArgumentException("Wildcard * can occur at most once.");
			}
			
			if(value2.indexOf("*") == -1 && !value1.equals(value2)) {
				return false;
			} 
			
			if(value2.indexOf("*") == 0) {
				String end = value2.substring(1, value2.length());
				
				if(!value1.endsWith(end)) {
					return false;
				}
			} else if(value2.indexOf("*") == value2.length() - 1) {
				String start = value2.substring(0, value2.length() - 1);
				
				if(!value1.startsWith(start)) {
					return false;
				}
			} else if(value2.indexOf("*") > 0 && value2.indexOf("*") < value2.length() - 1){
				String start = value2.substring(0, value2.indexOf("*"));
				String end = value2.substring(value2.indexOf("*") + 1, value2.length());
				
				if((!value1.startsWith(start)) ||
				   (!value1.endsWith(end)) ||
				   (value1.length() < start.length() + end.length())) {
					return false;
				}
			}
			
			return true;
		}
		
	}

}
