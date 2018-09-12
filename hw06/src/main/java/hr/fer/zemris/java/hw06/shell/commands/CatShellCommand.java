package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
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
 * This class represents shell command cat. Command cat takes one or two arguments. 
 * The first argument is path to some file and is mandatory. The second argument is charset name that 
 * should be used to interpret chars from bytes. If not provided, a default platform charset is used.
 * This command opens given file and writes its content to console.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class CatShellCommand implements ShellCommand {
	
	/**
	 * Environment of the command.
	 */
	private Environment env;

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		this.env = env;
		
		if(arguments == null) {
			env.writeln("Path must be given as first argument.");
			
			return ShellStatus.CONTINUE;
		}
		
		ArgumentParser argumentParser = new ArgumentParser(arguments);
		List<String> parts = argumentParser.parse();
		
		if(parts.size() != 1 && parts.size() != 2) {
			env.writeln("Number of arguments must be 1 or 2.");
			
			return ShellStatus.CONTINUE;
		}
		
		Path path = null;
		try {
			path = Paths.get(parts.get(0));
		} catch(InvalidPathException exc) {
			env.writeln("Argument is not valid path.");
			
			return ShellStatus.CONTINUE;
		}
		
		if(!Files.exists(path)) {
			env.writeln("File does not exist.");
			
			return ShellStatus.CONTINUE;
		}
		
		if(Files.isDirectory(path)) {
			env.writeln("File must not be directory.");
			
			return ShellStatus.CONTINUE;
		}
		
		String charsetName = null;
		if(parts.size() == 2) {
			charsetName = parts.get(1);
			
			if(!Charset.isSupported(charsetName)) {
				env.writeln("Charset " + charsetName + " is not supported.");
				
				return ShellStatus.CONTINUE;
			}
		}
		
		writeFileContent(path, charsetName == null ? Charset.defaultCharset().name() : charsetName);
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * This method opens given file and writes its content to console.
	 * 
	 * @param path path of the given file
	 * @param charsetName charset name that is used to interpret chars from bytes
	 */
	private void writeFileContent(Path path, String charsetName) {
		try(BufferedReader br = new BufferedReader(
				new InputStreamReader(
						new BufferedInputStream(
								new FileInputStream(path.toString())), charsetName))) {
			while(true) {
				String line = br.readLine();
				if(line == null) {
					break;
				}
				
				env.writeln(line);
			}
	  	} catch(IOException exc) {
	  		env.writeln("Error while reading from file.");
	  	}
	}

	@Override
	public String getCommandName() {
		return "cat";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		
		description.add("Command cat takes one or two arguments. ");
		description.add("The first argument is path to some file and is mandatory.");
		description.add("The second argument is charset name that should be used to interpret chars from bytes.");
		description.add("If not provided, a default platform charset is used.");
		description.add("This command opens given file and writes its content to console.");
		
		return Collections.unmodifiableList(description);
	}

}
