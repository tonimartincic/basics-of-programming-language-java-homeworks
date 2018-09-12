package hr.fer.zemris.java.hw06.shell;

import java.util.List;

/**
 * Interface contain all the methods which every shell command must have.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public interface ShellCommand {
	
	/**
	 * Method executes the command.
	 * 
	 * @param env environment of the command
	 * @param arguments arguments of the command
	 * @return {@link ShellStatus}
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	
	/**
	 * Getter for the command name.
	 * 
	 * @return command name
	 */
	String getCommandName();
	
	/**
	 * Method returns description of the command.
	 * 
	 * @return description of the command
	 */
	List<String> getCommandDescription(); 

}
