package hr.fer.zemris.java.hw06.shell;

import java.util.Collections;
import java.util.Scanner;
import java.util.SortedMap;

/**
 * Class implemets interface {@link Environment}. Instance of this class is sent to each
 * shell command. Each shell command communicates with user only through methods of this class.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class ShellEnvironment implements Environment {
	
	/**
	 * Prompt symbol.
	 */
	private char promptSymbol;
	
	/**
	 * Morelines symbol.
	 */
	private char morelinesSymbol;
	
	/**
	 * Multilines symbol.
	 */
	private char multilineSymbol;
	
	/**
	 * Map of shell commands.
	 */
	private SortedMap<String, ShellCommand> commands;
	
	/**
	 * Scanner.
	 */
	private Scanner sc;
	
	/**
	 * Constructor which accepts four arguments: prompt symbol, morelines symbol, multilines symbol and
	 * map of shell commands.
	 * 
	 * @param promptSymbol prompt symbol
	 * @param morelinesSymbol morelines symbol
	 * @param multilineSymbol multilines symbol
	 * @param commands shell commands
	 * @param sc scanner
	 */
	public ShellEnvironment(char promptSymbol, char morelinesSymbol, char multilineSymbol,
			SortedMap<String, ShellCommand> commands, Scanner sc) {
		super();
		this.promptSymbol = promptSymbol;
		this.morelinesSymbol = morelinesSymbol;
		this.multilineSymbol = multilineSymbol;
		this.commands = commands;
		this.sc = sc;
	}

	@Override
	public String readLine() throws ShellIOException {
		String line = null;
		
		if(sc.hasNextLine()) {
			line = sc.nextLine();
		}
	
		return line;
	}

	@Override
	public void write(String text) throws ShellIOException {
		System.out.print(text);
	}

	@Override
	public void writeln(String text) throws ShellIOException {
		System.out.println(text);
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		return Collections.unmodifiableSortedMap(commands);
	}

	@Override
	public Character getMultilineSymbol() {
		return multilineSymbol;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		multilineSymbol = symbol;
	}

	@Override
	public Character getPromptSymbol() {
		return promptSymbol;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		promptSymbol = symbol;
	}

	@Override
	public Character getMorelinesSymbol() {
		return morelinesSymbol;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		morelinesSymbol = symbol;
	}

}
