package hr.fer.zemris.java.hw06.shell;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents parser which accepts string which is shell command. It parses accepted command
 * and returns command name and arguments.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class CommandParser {
	
	/**
	 * Array of chars of accepted string which is being parsed.
	 */
	char[] data = null;
	
	/**
	 * Current index of data.
	 */
	int currentIndex;
	
	/**
	 * Flag which contains information if the command from the accepted string is already return or not.
	 * It is set to true if command is already returned, false otherwise.
	 */
	boolean commandReturned;
	
	/**
	 * Constructor which accepts one argument, string which is being parsed.
	 * 
	 * @param input string which is being parsed
	 */
	public CommandParser(String input) {
		data = input.toCharArray();
		currentIndex = 0;
		commandReturned = false; 
	}
	
	/**
	 * Method parse the accepted string. Accepted string represents shell command.
	 * It returns list of strings.
	 * List can contain one or two elements. It contains command name from accepted string
	 * and it can contain arguments of the accepted command as one string if 
	 * they exist.
	 * 
	 * @return list of strings
	 */
	public List<String> parse() {
		List<String> parts = new ArrayList<>();
		
		while(true) {
			String part = getNextPart();
			
			if(part == null) {
				break;
			}
			
			parts.add(part);
		}
		
		return parts;
	}
	
	/**
	 * Method gets next part of the accepted command.
	 * 
	 * @return next part of the command
	 */
	private String getNextPart() {
		skipBlanks();
		if(currentIndex >= data.length) {
			return null;
		}
		
		String nextPart = null;
		
		if(!commandReturned) {
			nextPart = getCommand();
		} else {
			nextPart = getArgument();
		}
		
		return nextPart;
	}
	
	/**
	 * Method returns command name from the command which is being parsed.
	 * 
	 * @return command name
	 */
	private String getCommand() {
		StringBuilder sb = new StringBuilder();
		
		while(currentIndex < data.length && data[currentIndex] != ' ') {
			sb.append(data[currentIndex]);
			
			currentIndex++;
		}
		
		commandReturned = true;
		return sb.toString();
	}

	/**
	 * Method gets all arguments of command which is being parsed as one string.
	 * 
	 * @return arguments of the command
	 */
	private String getArgument() {
		StringBuilder sb = new StringBuilder();
		
		while(currentIndex < data.length) {
			sb.append(data[currentIndex]);
			
			currentIndex++;
		}
		
		return sb.toString();
	}


	/**
	 * This method skips all blanks in text.
	 */
	private void skipBlanks() {
		while(currentIndex < data.length) {
			char c = data[currentIndex];
			if(c==' ') {
				currentIndex++;
				continue;
			}
			break;
		}
	}

}
