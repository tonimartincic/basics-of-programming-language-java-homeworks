package hr.fer.zemris.java.hw06.shell;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class CommandParserTest {

	@Test
	public void testOnlyCommand() {
		String input = "OneCommand";
		
		CommandParser commandParser = new CommandParser(input);
		List<String> parts = commandParser.parse();
		
		assertEquals(1, parts.size());
		assertEquals("OneCommand", parts.get(0));
	}
	
	@Test
	public void testCommandAndOneArgument() {
		String input = "Command Argument";
		
		CommandParser commandParser = new CommandParser(input);
		List<String> parts = commandParser.parse();
		
		assertEquals(2, parts.size());
		assertEquals("Command", parts.get(0));
		assertEquals("Argument", parts.get(1));
	}
	
	@Test
	public void testCommandAndFiveArguments() {
		String input = "Command Argument1   Argument2 Argument3  Argument4    Argument5";
		
		CommandParser commandParser = new CommandParser(input);
		List<String> parts = commandParser.parse();
		
		assertEquals(2, parts.size());
		assertEquals("Command", parts.get(0));
		assertEquals("Argument1   Argument2 Argument3  Argument4    Argument5", parts.get(1));
	}
}
