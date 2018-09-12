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
public class FieldValueGettersTest {

	@Test
	public void testFieldValueGettersFirstName() {
		List<String> lines = new ArrayList<>();
		lines = readLines();
		StudentDatabase studentDatabase = new StudentDatabase(lines);
		
		StudentRecord studentRecord = studentDatabase.forJMBAG("0000000001");
		
		assertNotNull(studentRecord);
		
		assertEquals("Marin", FieldValueGetters.FIRST_NAME.get(studentRecord));
	}
	
	@Test
	public void testFieldValueGettersLastName() {
		List<String> lines = new ArrayList<>();
		lines = readLines();
		StudentDatabase studentDatabase = new StudentDatabase(lines);
		
		StudentRecord studentRecord = studentDatabase.forJMBAG("0000000001");
		
		assertNotNull(studentRecord);
		
		assertEquals("Akšamović", FieldValueGetters.LAST_NAME.get(studentRecord));
	}
	
	@Test
	public void testFieldValueGettersJmbag() {
		List<String> lines = new ArrayList<>();
		lines = readLines();
		StudentDatabase studentDatabase = new StudentDatabase(lines);
		
		StudentRecord studentRecord = studentDatabase.forJMBAG("0000000001");
		
		assertNotNull(studentRecord);
		
		assertEquals("0000000001", FieldValueGetters.JMBAG.get(studentRecord));
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
