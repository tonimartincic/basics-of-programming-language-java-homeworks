package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents parser which parse accepted string which represents arguments of the command.
 * It returns list of the arguments. Each argument is one string.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class ArgumentParser {
	
	/**
	 * Array of chars of accepted string which is being parsed.
	 */
	char[] data = null;
	
	/**
	 * Current index of data.
	 */
	int currentIndex;
	
	/**
	 * Constructor which accepts one argument, string which is being parsed.
	 * 
	 * @param input string which is being parsed
	 */
	public ArgumentParser(String input) {
		data = input.toCharArray();
		currentIndex = 0;
	}

	/**
	 * Method parse the accepted string. String contains all argument.
	 * Method returns list of strings in which every string is one argument.
	 * 
	 * @return list of arguments of the command
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
	 * Method returns next argument of the command.
	 * 
	 * @return next argument
	 */
	private String getNextPart() {
		skipBlanks();
		if(currentIndex >= data.length) {
			return null;
		}
		
		String nextPart = null;
		
		if(data[currentIndex] == '"') {
			nextPart = getNextPartQuoteMode();
		} else {
			nextPart = getNextPartBasic();
		}
		
		return nextPart;
	}
	
	/**
	 * This method returns next argument which can contain whitespaces in his name.
	 * 
	 * @return next argument
	 */
	private String getNextPartQuoteMode() {
		currentIndex++;
		
		StringBuilder sb = new StringBuilder();
		
		while(currentIndex < data.length && data[currentIndex] != '"') {
			if(data[currentIndex] == '\\' && data[currentIndex + 1] == '\\') {
				sb.append("\\");
				
				currentIndex += 2;
				continue;
			}
			
			if(data[currentIndex] == '\\' && data[currentIndex + 1] == '"') {
				sb.append("\"");
				
				currentIndex += 2;
				continue;
			}
			
			sb.append(data[currentIndex]);
			
			currentIndex++;
		}
		
		currentIndex++;
		
		return sb.toString();
	}
	
	/**
	 * This method returns next argument which can not contain whitespaces in his name.
	 * 
	 * @return next argument
	 */
	private String getNextPartBasic() {
		StringBuilder sb = new StringBuilder();
		
		while(currentIndex < data.length && data[currentIndex] != ' ') {
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
