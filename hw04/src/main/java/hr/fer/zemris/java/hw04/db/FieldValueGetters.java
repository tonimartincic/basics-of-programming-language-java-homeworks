package hr.fer.zemris.java.hw04.db;

/**
 * Class contains getters for student's atributes.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class FieldValueGetters {
	
	/**
	 * Getter for student's first name.
	 */
	public static final IFieldValueGetter FIRST_NAME = ( (studentRecord) -> {
		return studentRecord.getFirstName();
	});
	
	/**
	 * Getter for student's last name.
	 */
	public static final IFieldValueGetter LAST_NAME = ( (studentRecord) -> {
		return studentRecord.getLastName();
	});
	
	/**
	 * Getter for student's jmbag.
	 */
	public static final IFieldValueGetter JMBAG = ( (studentRecord) -> {
		return studentRecord.getJmbag();
	});

}
