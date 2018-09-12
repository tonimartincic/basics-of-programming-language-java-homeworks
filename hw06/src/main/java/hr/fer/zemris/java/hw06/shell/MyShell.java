package hr.fer.zemris.java.hw06.shell;

import java.util.List;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.CatShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CharsetsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HexdumpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.LsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.SymbolShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.TreeShellCommand;

/**
 * Class represents a command-line program. Program accepts commands from user and execute them.
 * Supported commands are: charsets, cat, ls, tree, copy, mkdir, hexdump, exit, symbol and help.
 * Their description can be retrieved by command help.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class MyShell {

	/**
	 * Default morelines symbol which is used if command is multi-line.
	 */
	private static final char DEFAULT_MORELINES_SYMBOL = '\\';
	
	/**
	 * Default multiline symbol which is printed if command is multi-line.
	 */
	private static final char DEFAULT_MULTILINE_SYMBOL = '|';
	
	/**
	 * Default prompt symbol.
	 */
	private static final char DEFAULT_PROMPT_SYMBOL = '>';
	
	/**
	 * Instance of {@link ShellEnvironment} which is passed to each command of the shell.
	 */
	private static ShellEnvironment shellEnvironment;

	/**
	 * Main method from which program starts.
	 * 
	 * @param args arguments of the command line, unused
	 */
	public static void main(String[] args) {
		SortedMap<String, ShellCommand> commands = new TreeMap<>();
		putCommandsIntoMap(commands);
		
		Scanner sc = new Scanner(System.in);
		
		shellEnvironment = new ShellEnvironment(
				DEFAULT_PROMPT_SYMBOL, DEFAULT_MORELINES_SYMBOL, DEFAULT_MULTILINE_SYMBOL, commands, sc); 
		
		shellEnvironment.writeln("Welcome to MyShell v 1.0");
		while(true) {
			  shellEnvironment.write(shellEnvironment.getPromptSymbol() + " ");
			  String input = readLineOrLines();
			  
			  CommandParser commandParser = new CommandParser(input);
			  List<String> parts = commandParser.parse();
			  
			  if(parts.size() != 1 && parts.size() != 2) {
				  shellEnvironment.writeln("Input must be one command or command and arguments.");
				  
				  continue;
			  }
			  
			  String commandName = extractCommandName(parts); 
			  if(!shellEnvironment.commands().containsKey(commandName)) {
				  shellEnvironment.writeln("Command " + commandName + " does not exist.");
				  
				  continue;
			  }
			  
			  String arguments = extractArguments(parts);
			  ShellCommand command = commands.get(commandName);
			  
			  ShellStatus status = command.executeCommand(shellEnvironment, arguments);
			  if(status == ShellStatus.TERMINATE) {
				  shellEnvironment.writeln("Goodbye!");
				  
				  break;
			  }
		} 
		


	}

	/**
	 * Method reads command. Command can be one-line or multi-line command.
	 * It returns the read command as one string.
	 * 
	 * @return command
	 */
	private static String readLineOrLines() {
		StringBuilder sb = new StringBuilder();
		
		while(true) {
			String line = shellEnvironment.readLine();
			if(line == null) {
				break;
			}
			
			line.trim();
			if(line.isEmpty()) {
				break;
			}
			
			if(line.charAt(line.length() - 1) != shellEnvironment.getMorelinesSymbol()) {
				sb.append(line);
				
				break;
			}
			
			
			sb.append(line.substring(0, line.length() - 1));
			shellEnvironment.write(shellEnvironment.getMultilineSymbol() + " ");
		}
		
		return sb.toString();
	}

	/**
	 * Method accepts list of the parts of the command and returns command name.
	 * 
	 * @param parts list of the parts of the command
	 * @return commandName
	 */
	private static String extractCommandName(List<String> parts) {
		return parts.get(0);
	}

	/**
	 * Method accepts list of the parts of the command and returns arguments of the command.
	 * 
	 * @param parts list of the parts of the command
	 * @return arguments of the command
	 */
	private static String extractArguments(List<String> parts) {
		if(parts.size() == 1) {
			return null;
		}
		
		return parts.get(1);
	}

	/**
	 * Method puts shell commands into the map.
	 * 
	 * @param commands shell commands
	 */
	private static void putCommandsIntoMap(SortedMap<String, ShellCommand> commands) {
		commands.put("exit", new ExitShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("cat", new CatShellCommand());
		commands.put("charsets", new CharsetsShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("help", new HelpShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("symbol", new SymbolShellCommand());
		commands.put("tree", new TreeShellCommand());
	}

}
