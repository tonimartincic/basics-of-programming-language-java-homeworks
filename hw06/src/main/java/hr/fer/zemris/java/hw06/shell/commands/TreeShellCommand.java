package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
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
 * This class represents shell command tree. The tree command expects a single argument: directory name and 
 * prints a directory tree.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class TreeShellCommand implements ShellCommand {
	
	/**
	 * Environment of the command.
	 */
	private Environment env;

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		this.env = env;
		
		if(arguments == null) {
			env.writeln("Path must be given as argument.");
			
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
		if(!Files.isDirectory(path)) {
			env.writeln("Argument must be directory.");
			
			return ShellStatus.CONTINUE;
		}
		
		printTree(path.toFile(), 0);
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * Method prints directory tree.
	 * 
	 * @param file accepted directory
	 * @param level directory level
	 */
	private void printTree(File file, int level) {
		if(!file.isDirectory()) {
			return;
		}
		
		File[] children = file.listFiles();
		if(children == null) {
			return;
		}
		
		printSpaces(level);
		
		env.write(file.getName() + "\n");
		
		for(File child : children) {
			if(child.isDirectory()) {
				printTree(child, level + 1);
			} else {
				printSpaces(level + 2);
				
				env.write(child.getName() + "\n");
			}
		}
	}

	/**
	 * Method prints spaces in directory tree.
	 * 
	 * @param level directory level
	 */
	private void printSpaces(int level) {
		for(int i = 0; i < level; i++) {
			env.write("  ");
		}
	}

	@Override
	public String getCommandName() {
		return "tree";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		
		description.add("The tree command expects a single argument: directory name and prints a tree.");
		
		return Collections.unmodifiableList(description);
	}

}
