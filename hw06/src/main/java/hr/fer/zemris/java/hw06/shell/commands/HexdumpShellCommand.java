package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.crypto.Util;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This class represents shell command hexdump. The hexdump command expects a single argument: file name, 
 * and produces hex-output.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class HexdumpShellCommand implements ShellCommand {

	/**
	 * Size of the buffer.
	 */
	private static final int BUFFER_SIZE = 16;
	
	/**
	 * Number of hex digits per line in the output.
	 */
	private static final int NUM_OF_HEX_DIGITS = 8;
	
	/**
	 * Number of bytes per line in the output.
	 */
	private static final int NUM_OF_BYTES_PER_LINE = 16;
	
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
		
		if(Files.isDirectory(path)) {
			env.writeln("Argument must not be directory.");
			
			return ShellStatus.CONTINUE;
		}
		
		printHexDump(path);
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * Method prints hex dump of the accepted file.
	 * 
	 * @param path file which hex dump is being printed
	 */
	private void printHexDump(Path path) {
		try (InputStream is = Files.newInputStream(path,
				 StandardOpenOption.READ)) {
			
			 byte[] buff = new byte[BUFFER_SIZE];
			 
			 int numOfBytes = 0;
			 while(true) {
				 int r = is.read(buff);
				 if(r<1) break;
				 
				 printOneLine(buff, r, numOfBytes);
				 
				 numOfBytes += NUM_OF_BYTES_PER_LINE;
			}
		} catch(IOException ex) {
			env.writeln("Error while reading from file.");
		}
		
	}

	/**
	 * Method prints hex dump of the one line of the file.
	 * 
	 * @param buff buffer
	 * @param r number of bytes read
	 * @param numOfBytes number of bytes already printed
	 */
	private void printOneLine(byte[] buff, int r, int numOfBytes) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(getFirstPart(numOfBytes));
		sb.append(getSecondPart(buff, r));
		sb.append(getThirdPart(buff, r));
		
		env.writeln(sb.toString());
	}

	/**
	 * Method gets first part of the hex dump. 
	 * 
	 * @param numOfBytes number of bytes already printed
	 * @return first part of the hex dump
	 */
	private String getFirstPart(int numOfBytes) {
		StringBuilder sb = new StringBuilder();
		
		String hexString = Integer.toHexString(numOfBytes);
		int numberOfZerosToAdd = NUM_OF_HEX_DIGITS - hexString.length();
		for(int i = 0; i < numberOfZerosToAdd; i++) {
			hexString = "0" + hexString;
		}
		sb.append(hexString).append(": ");
		
		return sb.toString();
	}

	/**
	 * Method gets second part of the hex dump.
	 * 
	 * @param buff buffer
	 * @param r number of bytes read
	 * @return second part of the hex dump
	 */
	private String getSecondPart(byte[] buff, int r) {
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < r; i++) {
			if(i == NUM_OF_BYTES_PER_LINE / 2) {
				sb.append("|");
			} else {
				sb.append(" ");
			}
			
			sb.append(Util.byteToHex(buff).substring(i, i + 2));
		}
		
		if(r < NUM_OF_BYTES_PER_LINE) {
			for(int i = r; i < NUM_OF_BYTES_PER_LINE; i++) {
				sb.append("   ");
			} 
		}
		
		sb.append(" | ");
		
		return sb.toString();
	}

	/**
	 * Method gets third part of the hex dump.
	 * 
	 * @param buff buffer
	 * @param r number of bytes read
	 * @return third part of the hex dump
	 */
	private String getThirdPart(byte[] buff, int r) {
		StringBuilder sb = new StringBuilder();
		
		String text = new String(buff); 
		char[] data = text.toCharArray();
		for(int i = 0; i < r; i++) {
			if(data[i] < 32 || data[i] > 127) {
				sb.append(".");
			} else {
				sb.append(data[i]);
			}
		}
		
		return sb.toString();
	}

	@Override
	public String getCommandName() {
		return "hexdump";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		
		description.add("The hexdump command expects a single argument: file name, and produces hex-output.");
		
		return Collections.unmodifiableList(description);
	}

}
