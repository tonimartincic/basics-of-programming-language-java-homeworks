package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This class represents shell command symbol. It can be called with 1 or 2 arguments.
 * First argument must be name of the symbol. If there is no second argument then command prints
 * the symbol for the accepted symbol name. If there is second argument, it must symbol which will be new 
 * symbol for accepted symbol name. 
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class SymbolShellCommand implements ShellCommand {
	
	/**
	 * Environment of the command.
	 */
	private Environment env;

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		this.env = env;
		
		if(arguments == null) {
			env.writeln("Argument must not be null.");
			
			return ShellStatus.CONTINUE;
		}
		
		ArgumentParser argumentParser = new ArgumentParser(arguments);
		List<String> parts = argumentParser.parse();
		
		if(parts.size() != 1 && parts.size() != 2) {
			env.writeln("Number of arguments must be 1 or 2.");
			
			return ShellStatus.CONTINUE;
		}
		
		if(!parts.get(0).equals("PROMPT") && 
		   !parts.get(0).equals("MORELINES") &&
		   !parts.get(0).equals("MULTILINE")) {
			env.writeln("Invalid name of the symbol.");
			
			return ShellStatus.CONTINUE;
		}
		
		if(parts.size() == 1) {
			printSymbol(parts.get(0));
		} else {
			if(parts.get(1).length() != 1) {
				env.writeln("Length of the symbol must be 1.");
				
				return ShellStatus.CONTINUE;
			}
			
			changeSymbol(parts.get(0), parts.get(1));
		}
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * Method prints symbol for the accepted symbol name.
	 * 
	 * @param symbolName symbol name of the symbol which is printed
	 */
	private void printSymbol(String symbolName) {
		char symbol = 0;
		
		switch(symbolName) {
		case "PROMPT" :
			symbol = env.getPromptSymbol();
			break;
			
		case "MORELINES" :
			symbol = env.getMorelinesSymbol();
			break;
			
		case "MULTILINE" :
			symbol = env.getMultilineSymbol();
		}
		
		env.writeln("Symbol for " + symbolName + " is '" + symbol + "'.");
	}

	/**
	 * Method changes symbol for accepted symbol name. It sets symbol to newSymbol.
	 * 
	 * @param symbolName name of the symbol which is changed
	 * @param newSymbol new symbol
	 */
	private void changeSymbol(String symbolName, String newSymbol) {
		char symbol = newSymbol.toCharArray()[0];
		char oldSymbol = 0;
		
		switch(symbolName) {
		case "PROMPT" :
			if(symbol == env.getPromptSymbol()) {
				env.writeln("New symbol is equal to current symbol.");
			} else {
				oldSymbol = env.getPromptSymbol();
				env.setPromptSymbol(symbol);
			}
			
			break;
			
		case "MORELINES" :
			if(symbol == env.getMorelinesSymbol()) {
				env.writeln("New symbol is equal to current symbol.");
			} else {
				oldSymbol = env.getMorelinesSymbol();
				env.setMorelinesSymbol(symbol);
			}
			
			break;
			
		case "MULTILINE" :
			if(symbol == env.getMultilineSymbol()) {
				env.writeln("New symbol is equal to current symbol.");
			} else {
				oldSymbol = env.getMultilineSymbol();
				env.setMultilineSymbol(symbol);
			}
		}
		
		env.writeln("Symbol for " + symbolName + " changed from '" + oldSymbol + "' to '" + symbol + "'.");
		
	}	
	

	@Override
	public String getCommandName() {
		return "symbol";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		
		description.add("Command can be called with one or two arguments.");
		description.add("First argument can be PROMPTSYMBOL, MORELINESSYMBOL and MULTILINESYMBOL.");
		description.add("If it is called with one argument it prints that symbol.");
		description.add("Command symbol can be used to change PROMPTSYMBOL, MORELINESSYMBOL or"
				+ " MULTILINESYMBOL when it is called with two arguments.");
		description.add("Second argument is new symbol");
		
		return Collections.unmodifiableList(description);
	}

}
