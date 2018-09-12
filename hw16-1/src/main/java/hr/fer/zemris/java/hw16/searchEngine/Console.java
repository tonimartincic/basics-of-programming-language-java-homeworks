package hr.fer.zemris.java.hw16.searchEngine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import hr.fer.zemris.java.hw16.ResultRecord;
import hr.fer.zemris.java.hw16.Vocabulary;

/**
 * Class represents command line application which is search engine. It gets from the user path to the 
 * directory as argument. It searches files in accepted directory.
 * 
 * Application has four commands, query, results, type and exit. 
 * Command query does searching with keywords accepted as arguments and prints the results.
 * Command results prints the current results.
 * Command type prints content of the result which index is accepted as argument.
 * Command exit terminates the application.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class Console {
	
	/**
	 * Name of the path to the file with stop words.
	 */
	private static final String STOP_WORDS_PATH = "src/main/resources/stopRijeci.txt";
	
	/**
	 * Instance of the {@link Vocabulary}.
	 */
	private static Vocabulary vocabulary;
	
	/**
	 * Set of queries.
	 */
	private static Set<String> queries = new HashSet<>();
	
	/**
	 * Set of {@link ResultRecord} which are current results.
	 */
	private static Set<ResultRecord> currentResults = new TreeSet<>();
	
	static {
		queries.add("query");
		queries.add("type");
		queries.add("results");
		queries.add("exit");
	}
	
	/**
	 * Main method from which program starts. As argument it gets path to the directory in which files
	 * are searched.
	 * 
	 * @param args path to the directory in which files are searched
	 */
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("Kao argument je potrebno predati stazu do direktorija s člancima.");
			return;
		}
		
		String articlesPathAsString = args[0];
		Path articlesPath;
		try {
			articlesPath = Paths.get(articlesPathAsString);
		} catch(InvalidPathException exc) {
			System.out.println("Staza do direktorija s člancima nije zadana ispravno.");
			return;
		}
		
		if(!Files.isDirectory(articlesPath)) {
			System.out.println("Očekivana je staza do direktorija!");
			return;
		}
		
		try {
			vocabulary = new Vocabulary(articlesPathAsString, STOP_WORDS_PATH);
			
			System.out.printf("Veličina rječnika je %d riječi.%n", vocabulary.vocabularySize());
		} catch(Exception exc) {
			System.out.println(exc.getMessage());
			return;
		}

		Scanner sc = new Scanner(System.in);
		getCommands(sc);
		sc.close();
	}

	/**
	 * Method gets in while loop commands from the user and executes them.
	 * 
	 * @param sc instance of {@link Scanner}
	 */
	private static void getCommands(Scanner sc) {
		while(true) {
			System.out.println();
			System.out.print("Enter command > ");
			
			String inputLine = sc.nextLine();
			if(inputLine == null || inputLine.isEmpty()) {
				continue;
			}
		
			String[] parts = inputLine.split("\\s+");
			String p0 = parts[0].toLowerCase();
			if(!p0.equals("query") && !p0.equals("type") && !p0.equals("results") && !p0.equals("exit")) {
				System.out.println("Nepoznata naredba. Podržane naredbe su: query, results, type, exit.");
				continue;
			}
			
			if(p0.equals("exit")) {
				if(parts.length != 1) {
					System.out.println("Naredba 'exit' ne očekuje argumente!");
					continue;
				}
				
				System.out.println("Završen rad.");
				break;
			}
			
			if(p0.equals("results")) {
				doResultsCommand(parts);
				continue;
			}
			
			if(p0.equals("type")) {
				doTypeCommand(parts);
				continue;
			}
			
			doQueryCommand(parts);
		}
	}

	/**
	 * Method executes the result command.
	 * 
	 * @param parts user input split by whitespaces
	 */
	private static void doResultsCommand(String[] parts) {
		if(parts.length != 1) {
			System.out.println("Naredba 'results' ne očekuje argumente!");
			return;
		}
		
		if(currentResults.isEmpty()) {
			System.out.println("Trenutno nema rezultata.");
			return;
		}
		
		printResults();
	}
	
	/**
	 * Method executes the type command.
	 * 
	 * @param parts user input split by whitespaces
	 */
	private static void doTypeCommand(String[] parts) {
		if(parts.length != 2) {
			System.out.println("Index rezultata je potrebno predati kao argument!");
			return;
		}
		
		int index;
		try {
			index = Integer.parseInt(parts[1]);
		} catch(NumberFormatException exc) {
			System.out.println("Kao index je potrebno predati cijeli broj!");
			return;
		}
		
		if(currentResults.isEmpty()) {
			System.out.println("Trenutno nema rezultata.");
			return;
		}
		
		if(index < 0 || index > currentResults.size() - 1) {
			System.out.println("Ne postoji rezultat s predanim indeksom!");
			return;
		}
		
		try {
			printDocumentByIndex(index);
		} catch(Exception exc) {
			System.out.println(exc.getMessage());
			
			exc.printStackTrace();
		}
	}
	
	/**
	 * Method executes the query command.
	 * 
	 * @param parts user input split by whitespaces
	 */
	private static void doQueryCommand(String[] parts) {
		if(parts.length == 1) {
			System.out.println("Nisu navedene riječi za upit, potrebna barem jedna riječ!");
			return;
		}
		
		Map<String, Integer> words = new HashMap<>();
		for(int i = 1; i < parts.length; i++) {
			String word = parts[i].toLowerCase();
			
			if(vocabulary.contains(word)) {
				words.merge(word, 1, Integer::sum);
			}
		}
		
		System.out.println("Query is: " + words.keySet());
		
		currentResults = vocabulary.getResultsForWords(words);
		printResults();
	}

	/**
	 * Method prints the current results to the standard output.
	 */
	private static void printResults() {
		if(currentResults.isEmpty()) {
			System.out.println("Nije pronađen niti jedan rezultat.");
			return;
		}
		
		if(currentResults.size() == 10) {
			System.out.println("Najboljih 10 rezultata:");
		} else {
			System.out.printf("Najboljih %d rezultata: (nije pronađeno više)%n", currentResults.size());
		}
		int i = 0;
		for(ResultRecord resultRecord : currentResults) {
			System.out.printf("[%2d] %s%n", i, resultRecord);
			
			i++;
		}
	}
	
	/**
	 * Method prints content of the document which index from the results is accepted as argument.
	 * 
	 * @param index index of the document
	 * @throws Exception if file is not found or there is error while reading from the file
	 */
	private static void printDocumentByIndex(int index) throws Exception {
		Path path = (currentResults.toArray(new ResultRecord[currentResults.size()]))[index].getPath();
		
		int lineLength = "Dokument: ".length() + path.toString().length() + 3;
		
		printLine(lineLength);
		System.out.println("Dokument: " + path.toString());
		printLine(lineLength);
		
		try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		       System.out.println(line);
		    }
		} catch (FileNotFoundException e) {
			throw new Exception("Rezultatna datoteka nije pronađena.");
		} catch (IOException e) {
			throw new Exception("Pogreška tijekom čitanja iz rezultatne datoteke.");
		}
		
		printLine(lineLength);
		System.out.println();
	}

	/**
	 * Method prints line(------) to the standard output and the length of the line is accepted as argument.
	 * 
	 * @param lineLength length of the line
	 */
	private static void printLine(int lineLength) {
		for(int i = 0; i < lineLength; i++) {
			System.out.print("-");
		}
		System.out.printf("%n");
	}
	
}
