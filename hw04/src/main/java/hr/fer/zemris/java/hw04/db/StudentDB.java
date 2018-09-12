package hr.fer.zemris.java.hw04.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.java.hw04.db.QueryParser.QueryParserException;

/**
 * Class represents DatabaseEmulator. Program reads student records from text file and creates
 * database of StudentRecords. Program accepts queries from standard input.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class StudentDB {

	/**
	 * The method that starts the execution of this program.
	 * 
	 * @param args command line arguments. Unused.
	 */
	public static void main(String[] args) {
		
		printInstructions();
		
		Scanner sc = new Scanner(System.in);
		
		List<String> lines = new ArrayList<>();
		lines = readLines();
		StudentDatabase db = new StudentDatabase(lines);
		
		while(true) {
			System.out.print("> ");
			
			String query = null;
			
			try {
				query = getNextLine(sc);
				
				if(query.equals("exit")){
					System.out.println("Goodbye!");
					
					break;
				}
			} catch(IllegalArgumentException exc) {
				System.out.println(exc.getMessage());
				
				continue;
			} catch(Exception exc) {
				System.out.println("Invalid query.");
				
				continue;
			}
			
			QueryParser parser = null;
			
			try {
				parser = new QueryParser(query);
			} catch(QueryParserException exc) {
				System.out.println(exc.getMessage());
				continue;
			}
			
			List<StudentRecord> studentRecords = new ArrayList<>();
			
			try {
				if(parser.isDirectQuery()) {
					studentRecords.add(db.forJMBAG(parser.getQueriedJMBAG()));
					if(studentRecords.get(0) == null) {
						System.out.println("There is no student with this jmbag.");
						
						continue;
					}
				} else {
					studentRecords.addAll(db.filter(new QueryFilter(parser.getQuery())));
				}
			} catch(Exception exc) {
				System.out.println(exc.getMessage());
				
				continue;
			}
			
			printStudentRecords(studentRecords);
		}
		

	}

	/**
	 * Method prints student records to standard output.
	 * 
	 * @param studentRecords list of student records to print
	 */
	private static void printStudentRecords(List<StudentRecord> studentRecords) {
		if(studentRecords.size() == 0) {
			System.out.println("Records selected: 0");
			System.out.println();
			
			return;
		}
		
		int[] longestFirstAndLastNames = findLongestFirstAndLastNames(studentRecords);
		
		if(studentRecords.size() == 1) {
			System.out.println("Using index for record retrieval.");
		}
		
		printFirstOrLastLineOfTheTable(longestFirstAndLastNames);
		for(StudentRecord studentRecord : studentRecords) {
			printStudentLine(studentRecord, longestFirstAndLastNames);
		}
		printFirstOrLastLineOfTheTable(longestFirstAndLastNames);
		
		System.out.printf("Records selected: %d%n", studentRecords.size());
		System.out.println();
	}

	/**
	 * Method prints one student record to standard output.
	 * 
	 * @param studentRecord
	 * @param longestFirstAndLastNames
	 */
	private static void printStudentLine(StudentRecord studentRecord, 
			int[] longestFirstAndLastNames) {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("| ").append(studentRecord.getJmbag()).append(" | ");
		sb.append(studentRecord.getLastName());
		
		for(int i=0;i < longestFirstAndLastNames[1] - studentRecord.getLastName().length();i++){
			sb.append(" ");
		}
		
		sb.append(" | ");
		
		sb.append(studentRecord.getFirstName());
		
		for(int i=0;i < longestFirstAndLastNames[0] - studentRecord.getFirstName().length();i++){
			sb.append(" ");
		}
		
		sb.append(" | ");
		
		sb.append(studentRecord.getFinalGrade());
		
		sb.append(" |");
		
		System.out.println(sb.toString());
	}

	/**
	 * Method prints first or last line of the table of student records.
	 * 
	 * @param longestFirstAndLastNames array which contains length of longest first and 
	 * last name
	 */
	private static void printFirstOrLastLineOfTheTable(int[] longestFirstAndLastNames) {
		System.out.print("+============+");
		
		for(int i = 0; i < longestFirstAndLastNames[1] + 2; i++) {
			System.out.print("=");
		}
		
		System.out.print("+");
		
		for(int i = 0; i < longestFirstAndLastNames[0] + 2; i++) {
			System.out.print("=");
		}
		
		System.out.printf("+===+%n");
		
	}

	/**
	 * Method finds longest first and last name in accepted list of student records.
	 * 
	 * @param studentRecords list of student records
	 * @return array which contains length of longest first and last name
	 */
	private static int[] findLongestFirstAndLastNames(List<StudentRecord> studentRecords) {
		int longestFirstName = 0;
		int longestLastName = 0;
		
		for(StudentRecord studentRecord : studentRecords) {
			if(longestFirstName < studentRecord.getFirstName().length()) {
				longestFirstName = studentRecord.getFirstName().length();
			}
			
			if(longestLastName < studentRecord.getLastName().length()) {
				longestLastName = studentRecord.getLastName().length();
			}
		}
		
		int[] longestFirstAndLastNames = new int[2];
		longestFirstAndLastNames[0] = longestFirstName;
		longestFirstAndLastNames[1] = longestLastName;
				
		return longestFirstAndLastNames;
	}

	/**
	 * Method reads next line from standard input.
	 * 
	 * @param sc reference to Scanner
	 * @return query if line starts with "query", or "exit" if line starts with "exit"
	 */
	private static String getNextLine(Scanner sc) {
		String line = sc.nextLine();
		
		line = skipBlanks(line);
		
		if(line.startsWith("exit")) {
			return "exit";
		}
		
		if(!line.startsWith("query")) {
			throw new IllegalArgumentException("Input must start with \"query\"");
		}
		
		return line.substring(6);
	}
	
	/**
	 * Method skip whitespaces.
	 * 
	 * @param line String in which whitespaces are skipped
	 * @return line with skipped whitespaces
	 */
	private static String skipBlanks(String line) {
		int currentIndex = 0;
		char[] data = line.toCharArray();	
		
		while(data[currentIndex] == ' ') {
			currentIndex++;
		}
		
		return line.substring(currentIndex);
	}

	/**
	 * Method reads all lines from text file.
	 * 
	 * @return list of all lines
	 */
	private static List<String> readLines() {
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

	/**
	 * Method prints instructions at the start of the program.
	 */
	private static void printInstructions() {
		System.out.println("Welcome to the DatabaseEmulator!");
		System.out.print("Each row contains the data for single student. "); 
		System.out.printf("Example of one row: \"0000000001	Akšamović	Marin	2\"%n");
		
		System.out.println("Emulator supports a single command: query.");
		System.out.println("Structure of query:");
		System.out.println("  query jmbag/firstName/lastName </>/<=/>=/!=/=/LIKE \"some string\" AND query jmbag/firstName/lastName </>/<=/>=/!=/=/LIKE \"some string\" AND ... ");
		
		System.out.println();
		System.out.println("Some examples of queries: (Some of them are invalid)");
		
		System.out.println("  query jmbag=\"0000000003\"");
		System.out.println("  query lastName = \"Blažić\"");
		System.out.println("  query firstName>\"A\" and lastName LIKE \"B*ć\"");
		System.out.println("  query firstName>\"A\" and firstName<\"C\" and lastName LIKE \"B*ć\" and jmbag>\"0000000002\"");
		System.out.print("  query lastName LIKE \"*b*\"");  
		System.out.printf("  <-- invalid%n");
		System.out.println("  query firstNameLIKE\"M*\"AnD lastName  LIKE\"A*\"");
		System.out.println("  query firstName >= \"M\" and lastName <= \"M\" and jmbag LIKE \"000000002*\"");
		
		System.out.println();
		System.out.println("The program is terminated when \"exit\" is entered.");
		
		System.out.println();
	}
}
