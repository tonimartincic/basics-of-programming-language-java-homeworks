package hr.fer.zemris.java.hw04.db;

/**
 * This interface declares only one method which accepts StudentRecord.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public interface IFieldValueGetter {
	
	/**
	 * Method accepts Student record and returns some atribute of the student.
	 * 
	 * @param record student record
	 * @return atribute of the student
	 */
	public String get(StudentRecord record);

}
