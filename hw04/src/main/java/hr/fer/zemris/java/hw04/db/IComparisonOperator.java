package hr.fer.zemris.java.hw04.db;

/**
 * This interface declares only one method which accepts two Strings. 
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public interface IComparisonOperator {
	
	/**
	 * Method checks if first String satisfies second String.
	 * 
	 * @param value1 first String
	 * @param value2 second String
	 * @return true if first String satisfies second String, false otherwise
	 */
	public boolean satisfied(String value1, String value2);

}
