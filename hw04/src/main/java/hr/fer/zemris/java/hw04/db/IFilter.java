package hr.fer.zemris.java.hw04.db;

/**
 * This interface declares only one method which accepts StudentRecord and returns true or
 * false. 
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public interface IFilter {
	
	/**
	 * Method accepts StudentRecord. True is returned if the record is accepted, false otherwise.
	 * 
	 * @param record student record
	 * @return true is returned if the record is accepted, false otherwise
	 */
	public boolean accepts(StudentRecord record);
}
