package hr.fer.zemris.java.hw06.shell;

import java.util.SortedMap;

/**
 * Interface is an abstraction which is be passed to each command. The each
 * implemented command communicates with user only through this interface.
 *
 * @author Toni Martinčić
 * @version 1.0
 */
public interface Environment {

	/**
	 * Method reads one line.
	 * 
	 * @return read line
	 * @throws ShellIOException if reading fails {@link ShellIOException} is thrown
	 */
	String readLine() throws ShellIOException;

	/**
	 * Method writes accepted text.
	 * 
	 * @param text
	 * @throws ShellIOException if writing fails {@link ShellIOException} is thrown
	 */
	void write(String text) throws ShellIOException;

	/**
	 * Method writes accepted text and jumps into new line.
	 * 
	 * @param text
	 * @throws ShellIOException if writing fails {@link ShellIOException} is thrown
	 */
	void writeln(String text) throws ShellIOException;

	/**
	 * Getter for the map of the commands.
	 * 
	 * @return map of the commands
	 */
	SortedMap<String, ShellCommand> commands();

	/**
	 * Getter for the mulitiline symbol,
	 * 
	 * @return multiline symbol
	 */
	Character getMultilineSymbol();

	/**
	 * Setter for the multiline symbol
	 * 
	 * @param symbol multiline symbol
	 */
	void setMultilineSymbol(Character symbol);

	/**
	 * Getter for the prompt symbol
	 * 
	 * @return prompt symbol
	 */
	Character getPromptSymbol();

	/**
	 * Setter for the prompt symbol.
	 * 
	 * @param symbol prompt symbol
	 */
	void setPromptSymbol(Character symbol);

	/**
	 * Getter for the morelines symbol.
	 * 
	 * @return morelines symbol
	 */
	Character getMorelinesSymbol();

	/**
	 * Setter for the morelines symbol
	 * 
	 * @param symbol morelines symbol
	 */
	void setMorelinesSymbol(Character symbol);

}
