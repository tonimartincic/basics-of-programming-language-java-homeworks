package hr.fer.zemris.java.hw04.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw04.collections.SimpleHashtable;

/**
 * This class represents database of student records. Each student record is instance of
 * class StudentRecord.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class StudentDatabase {
	
	/**
	 * List of student records.
	 */
	private List<StudentRecord> studentRecords;
	
	/**
	 * Hash table of student records.
	 */
	private SimpleHashtable<String, StudentRecord> index;

	/**
	 * Constructor which accepts one argument, list of student records.
	 * 
	 * @param studentRecords list of student records
	 */
	public StudentDatabase(List<String> studentRecords) {
		super();
		
		this.studentRecords = new ArrayList<>();
		this.index = new SimpleHashtable<>();
		
		createInternalList(studentRecords);
	}
	
	/**
	 * Method creates internal list and hash map of student records from accepted
	 * list of student records. In map key is
	 * jmbag and value is student record.
	 * 
	 * @param studentRecords list of student records
	 */
	private void createInternalList(List<String> studentRecords) {
		for(String studentRecordString : studentRecords) {
			String[] parts = studentRecordString.split("\\s+");
			
			String jmbag = parts[0];
			int finalGrade = Integer.parseInt(parts[parts.length - 1]);
			String firstName = parts[parts.length - 2];
			String lastName;
			
			if(parts.length == 4) {
				lastName = parts[1];
			} else {
				lastName = parts[1] + " " + parts[2]; 
			}
			
			StudentRecord studentRecord = new StudentRecord(jmbag, lastName, firstName, finalGrade);
			
			this.studentRecords.add(studentRecord);
			this.index.put(jmbag, studentRecord);
		}
		
	}

	/**
	 * Method gets student record from hash table which jmbag is equal to accepted jmbag.
	 * If there is no such student record, method returns null.
	 * 
	 * @param jmbag jmbag of student
	 * @return StudentRecord of student with this jmbag if student with this record exists,
	 * null otherwise 
	 */
	public StudentRecord forJMBAG(String jmbag){
		return index.get(jmbag);
	}
	
	/**
	 * Method returns filtered list of student records. Filter is accepted as argument as
	 * instance of IFilter.
	 * 
	 * @param filter filter
	 * @return filtered list of student records
	 */
	public List<StudentRecord> filter(IFilter filter){
		List<StudentRecord> temporaryList = new ArrayList<>();
		
		for(StudentRecord studentRecord : studentRecords) {
			if(filter.accepts(studentRecord)) {
				temporaryList.add(studentRecord);
			}
		}
		
		return temporaryList;
	}

}
