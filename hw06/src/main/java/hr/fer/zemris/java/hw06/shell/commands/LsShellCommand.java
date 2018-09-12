package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This class represents shell command ls. Command ls takes a single argument, directory, 
 * and writes a directory listing.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class LsShellCommand implements ShellCommand {
	
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
		
		File[] children = path.toFile().listFiles();
		for(File file : children) {
			printFileData(file);
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * Method prints data for one file which is accepted as argument of the method.
	 * 
	 * @param file file which data is being printed
	 */
	private void printFileData(File file) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(getFileInformations(file));
		sb.append(getFileSize(file));
		sb.append(getDate(file));
		sb.append(file.getName());
		
		env.writeln(sb.toString());
	}

	/**
	 * Method gets four informations about the file. It gets information is file a directory, 
	 * is file readable, is file writeable and is file executable.
	 * 
	 * @param file file which informations are get
	 * @return file informations
	 */
	private String getFileInformations(File file) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(file.isDirectory() ? "d" : "-");
		sb.append(file.canRead() ? "r" : "-");
		sb.append(file.canWrite() ? "w" : "-");
		sb.append(file.canExecute() ? "e" : "-");
		
		return sb.toString();
	}
	
	/**
	 * Method return size of the accepted file.
	 * 
	 * @param file file which size is returned
	 * @return size of the accepted file
	 */
	private String getFileSize(File file) {
		int size = getSize(file);
		String sizeAsString = String.valueOf(size);
		
		return String.format("%10s", sizeAsString);
	}
	
	/**
	 * Method return size of the all files in accepted file.  
	 * 
	 * @param file accepted file
	 * @return size
	 */
	private static int getSize(File file) {
		int size = 0;
		
		File[] children = file.listFiles();
		
		if(children == null) {
			return 0;
		}
		
		for(File child : children) {
			if(child.isDirectory()) {
				size += getSize(child);
				
				continue;
			} else if(child.isFile()) {
				size += child.length();
			}
		}
		
		return size;
	}
	
	/**
	 * Method for the accepted file gets the date when it is created.
	 * 
	 * @param file accepted file
	 * @return date
	 */
	private String getDate(File file) {
		SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");
		Path path = Paths.get(file.toString());
		BasicFileAttributeView faView = Files.getFileAttributeView(path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
		
		BasicFileAttributes attributes = null;
		try {
			attributes = faView.readAttributes();
		} catch (IOException e) {
			env.writeln(e.getMessage());
		}
		
		FileTime fileTime = attributes.creationTime();
		String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
		
		return formattedDateTime;
	}

	@Override
	public String getCommandName() {
		return "ls";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		
		description.add("Command ls takes a single argument – directory – and writes a directory listing.");
		
		return Collections.unmodifiableList(description);
	}

}
