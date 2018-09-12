package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This class represents shell command copy. The copy command expects two arguments: source file name and 
 * destination file name. Is destination file exists, user is asked is it allowed to overwrite it. 
 * Copy command works only with files. If the second argument is directory, original file is copied into 
 * that directory using the original file name. 
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class CopyShellCommand implements ShellCommand {
	
	/**
	 * Size of the buffer.
	 */
	private static final int BUFFER_SIZE = 4096;
	
	/**
	 * Environmet of the command.
	 */
	private Environment env;

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		this.env = env;
		
		if(arguments == null) {
			env.writeln("Paths must be given as argument.");
			
			return ShellStatus.CONTINUE;
		}
		
		ArgumentParser argumentParser = new ArgumentParser(arguments);
		List<String> parts = argumentParser.parse();
		
		if(parts.size() != 2) {
			env.writeln("Number of arguments must be 2.");
			
			return ShellStatus.CONTINUE;
		}
		
		Path sourcePath = null;
		Path destinationPath = null;
		try {
			sourcePath = Paths.get(parts.get(0));
			destinationPath = Paths.get(parts.get(1));
		} catch(InvalidPathException exc) {
			env.writeln("One of the arguments is not valid path.");
			
			return ShellStatus.CONTINUE;
		}
		
		if(!Files.exists(sourcePath)) {
			env.writeln("Source folder does not exist.");
			
			return ShellStatus.CONTINUE;
		}
		
		if(Files.isDirectory(sourcePath)) {
			env.writeln("Source file must not be directory.");
			
			return ShellStatus.CONTINUE;
		}
		
		if(Files.isDirectory(destinationPath)) {
			copyFileIntoDirectory(sourcePath, destinationPath);
		
			return ShellStatus.CONTINUE;
		}
		
		if(Files.exists(destinationPath)) {
			if(!canOverwrite()) {
				return ShellStatus.CONTINUE;
			}
		}
		
		copyFile(sourcePath, destinationPath);
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * This method is called when destination file is the directory. Then the name of the new file will
	 * be equal to the source file.
	 * 
	 * @param sourcePath
	 * @param destinationPath
	 */
	private void copyFileIntoDirectory(Path sourcePath, Path destinationPath) {
		String sourcePathName = sourcePath.getFileName().toString();
		String destinationPathAsString = destinationPath.toString();
		
		destinationPathAsString += "/" + sourcePathName;
		
		destinationPath = Paths.get(destinationPathAsString);
		
		copyFile(sourcePath, destinationPath);
	}
	
	/**
	 * Method creates destination file which is copy of source file.
	 * 
	 * @param sourcePath path of the source file
	 * @param destinationPath path of the destination file
	 */
	private void copyFile(Path sourcePath, Path destinationPath) {
		try (InputStream is = Files.newInputStream(sourcePath, StandardOpenOption.READ);
				 OutputStream os = new BufferedOutputStream(new FileOutputStream(destinationPath.toString()));) {
				byte[] buff = new byte[BUFFER_SIZE];
				
				while(true) {
					int r = is.read(buff);
					
					if(r<1) break;
					
					os.write(buff, 0, r);
				}
				
		} catch(IOException exc) {
			env.writeln("Error while copying files.");
		}
	}
	
	/**
	 * This method asks user if it is legal to overwrite the existing file.
	 * 
	 * @return true if it is legal to overwrite the existing file, false otherwise
	 */
	private boolean canOverwrite() {
		env.writeln("Destination file already exists. Do you want overwrite it? "
					+ "write \"YES\" if you want.");
	
		String line = env.readLine();
		
		return line.equals("YES");
	}

	@Override
	public String getCommandName() {
		return "copy";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		
		description.add("The copy command expects two arguments: source file name and destination file name.");
		description.add("If destination file exists, user says is it allowed to overwrite it.");
		description.add("Copy command work only with files (no directories).");
		description.add("If the second argument is directory, original file is copied into that directory using the original file name.");
		
		return Collections.unmodifiableList(description);
	}

}
