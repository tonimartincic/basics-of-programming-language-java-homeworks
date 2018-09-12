package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This class represents shell command help. If started with no arguments, it lists names of all supported 
 * commands. If started with single argument, it prints name and the description of selected command.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class HelpShellCommand implements ShellCommand {
	
	/**
	 * Environment of the command.
	 */
	private Environment env;

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		this.env = env;
		
		if(arguments == null) {
			printAllCommands();
			
			return ShellStatus.CONTINUE;
		}
		
		ArgumentParser argumentParser = new ArgumentParser(arguments);
		List<String> parts = argumentParser.parse();
		
		if(parts.size() != 1) {
			env.writeln("Number of arguments must be 0 or 1.");
			
			return ShellStatus.CONTINUE;
		}
		
		if(!env.commands().containsKey(parts.get(0))) {
			env.writeln("Argument is not valid command.");
			
			return ShellStatus.CONTINUE;
		}
		
		writeCommandDescription(parts.get(0));
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * Method prints names of all commands.
	 */
	private void printAllCommands() {
		SortedMap<String, ShellCommand> commands = env.commands();
		
		commands.keySet().forEach(command -> System.out.println(command));
	}
	
	/**
	 * Method prints name and the description of the accepted command.
	 * 
	 * @param commandName
	 */
	private void writeCommandDescription(String commandName) {
		env.writeln(commandName);
		
		List<String> description = env.commands().get(commandName).getCommandDescription();
		
		for(String line : description) {
			env.writeln(line);
		}
	}


	@Override
	public String getCommandName() {
		return "help";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		
		description.add("If started with no arguments, it lists names of all supported commands.");
		description.add("If started with single argument, it prints name and the description of selected command.");
		
		return Collections.unmodifiableList(description);
	}

}
