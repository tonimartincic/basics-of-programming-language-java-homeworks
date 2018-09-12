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
public class QueryFilterTest {

	@Test
	public void testForJmbagEquals() {
		List<String> lines = new ArrayList<>();
		lines = readLines();
		StudentDatabase db = new StudentDatabase(lines);
		
		QueryParser parser = new QueryParser(" jmbag =\"0000000010\" "); //Dokleja	Luka	3
		
		if(parser.isDirectQuery()) {
			 StudentRecord r = db.forJMBAG(parser.getQueriedJMBAG());
			 assertEquals("0000000010 Dokleja Luka 3", r.toString());
		}
	}
	
	@Test
	public void testJmbagGreaterOrEquals() {
		List<String> lines = new ArrayList<>();
		lines = readLines();
		StudentDatabase db = new StudentDatabase(lines);
		
		QueryParser parser = new QueryParser(" jmbag >= \"0000000061\" ");
		
		if(parser.isDirectQuery()) {
			 @SuppressWarnings("unused")
			StudentRecord r = db.forJMBAG(parser.getQueriedJMBAG());
		} else {
			List<StudentRecord> r = db.filter(new QueryFilter(parser.getQuery()));
			
			assertEquals("0000000061 Vukojević Renato 2", r.get(0).toString());
			assertEquals("0000000062 Zadro Kristijan 3", r.get(1).toString());
			assertEquals("0000000063 Žabčić Željko 4", r.get(2).toString());
		}
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
