package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * 
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class ExitShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments != null) {
			env.writeln("Number of arguments must be 0.");
			
			return ShellStatus.CONTINUE;
		}
		
		return ShellStatus.TERMINATE;
	}

	@Override
	public String getCommandName() {
		return "exit";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		
		description.add("Shell terminates on this command.");
		
		return Collections.unmodifiableList(description);
	}

}
