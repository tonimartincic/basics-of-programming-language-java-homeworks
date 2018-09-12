package hr.fer.zemris.java.hw04.db;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

@SuppressWarnings("javadoc")
public class StudentDatabaseTest {
	
	@Test
	public void testForJmbagJmbagExists() {
		List<String> lines = new ArrayList<>();
		lines = readLines();
		StudentDatabase studentDatabase = new StudentDatabase(lines);
		
		StudentRecord studentRecord = studentDatabase.forJMBAG("0000000001");
		
		assertNotNull(studentRecord);
		assertEquals("0000000001", studentRecord.getJmbag());
		assertEquals("Akšamović", studentRecord.getLastName());
		assertEquals("Marin", studentRecord.getFirstName());
		assertEquals(2, studentRecord.getFinalGrade());
	}
	
	@Test
	public void testForJmbagTwoLastNames() {
		List<String> lines = new ArrayList<>();
		lines = readLines();
		StudentDatabase studentDatabase = new StudentDatabase(lines);
		
		StudentRecord studentRecord = studentDatabase.forJMBAG("0000000015");
	
		assertNotNull(studentRecord);
		assertEquals("0000000015", studentRecord.getJmbag());
		assertEquals("Glavinić Pecotić", studentRecord.getLastName());
		assertEquals("Kristijan", studentRecord.getFirstName());
		assertEquals(4, studentRecord.getFinalGrade());
	}
	
	@Test
	public void testFilterAlwaysTrue() {
		List<String> lines = new ArrayList<>();
		lines = readLines();
		StudentDatabase studentDatabase = new StudentDatabase(lines);
		
		int numberOfStudents = lines.size();
		
		List<StudentRecord> filteredList = studentDatabase.filter(
				studentRecord -> true);
		
		assertEquals(numberOfStudents, filteredList.size());
	}
	
	@Test
	public void testFilterOnlyFinalGrades5() {
		List<String> lines = new ArrayList<>();
		lines = readLines();
		StudentDatabase studentDatabase = new StudentDatabase(lines);
		
		List<StudentRecord> filteredList = studentDatabase.filter(
				studentRecord -> {
					return studentRecord.getFinalGrade() == 5;
				});
		
		for(StudentRecord studentRecord : filteredList) {
			assertEquals(5, studentRecord.getFinalGrade());
		}
	}
	
	@Test
	public void testFilterOnlyFinalGrades2() {
		List<String> lines = new ArrayList<>();
		lines = readLines();
		StudentDatabase studentDatabase = new StudentDatabase(lines);
		
		List<StudentRecord> filteredList = studentDatabase.filter(
				studentRecord -> {
					return studentRecord.getFinalGrade() == 2;
				});
		
		for(StudentRecord studentRecord : filteredList) {
			assertEquals(2, studentRecord.getFinalGrade());
		}
	}
	
	@Test
	public void testFilterOnlyFinalGrades6() {
		List<String> lines = new ArrayList<>();
		lines = readLines();
		StudentDatabase studentDatabase = new StudentDatabase(lines);
		
		List<StudentRecord> filteredList = studentDatabase.filter(
				studentRecord -> {
					return studentRecord.getFinalGrade() == 6;
				});
		
		for(StudentRecord studentRecord : filteredList) {
			assertEquals(6, studentRecord.getFinalGrade());
		}
	}
	
	@Test
	public void testFilterOnlyFirstNameMarin() {
		List<String> lines = new ArrayList<>();
		lines = readLines();
		StudentDatabase studentDatabase = new StudentDatabase(lines);
		
		List<StudentRecord> filteredList = studentDatabase.filter(
				studentRecord -> {
					return studentRecord.getFirstName().equals("Marin");
				});
		
		for(StudentRecord studentRecord : filteredList) {
			assertEquals("Marin", studentRecord.getFirstName());
		}
	}
	
	@Test
	public void testFilterOnlyStudentsWithTwoLastNames() {
		List<String> lines = new ArrayList<>();
		lines = readLines();
		StudentDatabase studentDatabase = new StudentDatabase(lines);
		
		List<StudentRecord> filteredList = studentDatabase.filter(
				studentRecord -> {
					return studentRecord.getLastName().contains(" ");
				});
		
		for(StudentRecord studentRecord : filteredList) {
			assertTrue(studentRecord.getLastName().contains(" "));
		}
	}
	
	@Test
	public void testFilterAlwaysFalse() {
		List<String> lines = new ArrayList<>();
		lines = readLines();
		StudentDatabase studentDatabase = new StudentDatabase(lines);
		
		List<StudentRecord> filteredList = studentDatabase.filter(
				studentRecord -> false);

		assertEquals(0, filteredList.size());
	}

	private List<String> readLines() {
		List<String> lines = new ArrayList<>();
		
		try {
			lines = Files.readAllLines(
					 Paths.get("src/test/resources/database.txt"),
					 StandardCharsets.UTF_8
			);
		} catch (IOException e) {
			System.err.println(e);
		}	
		
		return lines;
	}

}
