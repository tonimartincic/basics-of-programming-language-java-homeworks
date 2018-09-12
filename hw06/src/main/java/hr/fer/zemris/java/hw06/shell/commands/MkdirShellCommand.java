package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This class represents shell command mkdir. The mkdir command takes a single argument: directory name, 
 * and creates the appropriate directory structure. 
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class MkdirShellCommand implements ShellCommand {
	
	/**
	 * Environment of the command.
	 */
	private Environment env;

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		this.env = env;
		
		if(arguments == null) {
			env.writeln("Directory must be given as argument.");
			
			return ShellStatus.CONTINUE;
		}
		
		ArgumentParser argumentParser = new ArgumentParser(arguments);
		List<String> parts = argumentParser.parse();
		
		if(parts.size() != 1) {
			env.writeln("Number of arguments must be 1.");
			
			return ShellStatus.CONTINUE;
		}
		
		Path path = null;
		try {
			path = Paths.get(parts.get(0));
		} catch(InvalidPathException exc) {
			env.writeln("Argument is not valid path.");
			
			return ShellStatus.CONTINUE;
		}
		
		makeDirectory(path);
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * Method create directory.
	 * 
	 * @param path path of the created directory
	 */
	private void makeDirectory(Path path) {
		try {
			Files.createDirectories(path);
		} catch (IOException e) {
			env.writeln("Error while creating directory.");
		}
	}

	@Override
	public String getCommandName() {
		return "mkdir";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		
		description.add("The mkdir command takes a single argument: directory name, and creates the appropriate directory structure.");
		
		return Collections.unmodifiableList(description);
	}

}
