package hr.fer.zemris.java.hw04.db;

/**
 * Instances of this represent records for each student. Each student record has student's 
 * jmbag, last name, first name and final grade.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class StudentRecord {
	
	/**
	 * Student's jmbag.
	 */
	private String jmbag;
	
	/**
	 * Student's last name.
	 */
	private String lastName;
	
	/**
	 * Student's first name.
	 */
	private String firstName;
	
	/**
	 * Student's final grade.
	 */
	private int finalGrade;
	
	/**
	 * Constructor which accepts student's jmbag, last name, first name and final grade.
	 * 
	 * @param jmbag student's jmbag
	 * @param lastName student's last name
	 * @param firstName student's first name
	 * @param finalGrade student's final grade
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		super();
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}

	/**
	 * Getter for the jmbag.
	 * 
	 * @return jmbag
	 */ 
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Getter for the last name.
	 * 
	 * @return last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Getter for the first name.
	 * 
	 * @return first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Getter for the final grade.
	 * 
	 * @return final grade
	 */
	public int getFinalGrade() {
		return finalGrade;
	}
	
	@Override
	public String toString() {
		return String.format("%s %s %s %d", jmbag, lastName, firstName, finalGrade);
	}
	
}
