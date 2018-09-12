package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This class represents shell command charsets. Command charsets takes no arguments and lists
 * names of supported charsets for your Java platform. A single charset name is written per line. 
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class CharsetsShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments != null) {
			env.writeln("Number of arguments must be 0.");
			
			return ShellStatus.CONTINUE;
		}
		
		Charset.availableCharsets().keySet().forEach(charsetName -> System.out.println(charsetName));
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "charsets";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		
		description.add("Command charsets takes no arguments and lists names of supported charsets for your Java platform.");
		description.add("A single charset name is written per line. ");
		
		return Collections.unmodifiableList(description);
	}
	
}
