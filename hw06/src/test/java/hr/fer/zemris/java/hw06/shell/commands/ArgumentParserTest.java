package hr.fer.zemris.java.hw06.shell.commands;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class ArgumentParserTest {

	@Test
	public void testParseOneString() {
		String input = "Java";
		ArgumentParser argumentParser = new ArgumentParser(input);
		
		List<String> parts = argumentParser.parse();
		assertEquals("Java", parts.get(0));
	}
	
	@Test
	public void testParseTwoStrings() {
		String input = "Java Java";
		ArgumentParser argumentParser = new ArgumentParser(input);
		
		List<String> parts = argumentParser.parse();
		assertEquals("Java", parts.get(0));
		assertEquals("Java", parts.get(1));
	}
	
	@Test
	public void testParseTwoStrings2() {
		String input = " Jeli SeOvoDobroIsparsiralo???";
		ArgumentParser argumentParser = new ArgumentParser(input);
		
		List<String> parts = argumentParser.parse();
		assertEquals("Jeli", parts.get(0));
		assertEquals("SeOvoDobroIsparsiralo???", parts.get(1));
	}
	
	@Test
	public void testParseFiveStrings() {
		String input = "  Java   Java   Java Java      Java";
		ArgumentParser argumentParser = new ArgumentParser(input);
		
		List<String> parts = argumentParser.parse();
		assertEquals("Java", parts.get(0));
		assertEquals("Java", parts.get(1));
		assertEquals("Java", parts.get(2));
		assertEquals("Java", parts.get(3));
		assertEquals("Java", parts.get(4));
	}
	
	@Test
	public void testParseOnePath() {
		String input = "/home/john/info.txt";
		ArgumentParser argumentParser = new ArgumentParser(input);
		
		List<String> parts = argumentParser.parse();
		assertEquals("/home/john/info.txt", parts.get(0));
	}
	
	@Test
	public void testParseTwoPaths() {
		String input = "/home/john/info.txt /home/john/backupFolder";
		ArgumentParser argumentParser = new ArgumentParser(input);
		
		List<String> parts = argumentParser.parse();
		assertEquals("/home/john/info.txt", parts.get(0));
		assertEquals("/home/john/backupFolder", parts.get(1));
	}
	
	@Test
	public void testParseFourPaths() {
		String input = "/home/john/info.txt /home/john/backupFolder C:/home/Toni/pics D:/home/Toni/movies";
		ArgumentParser argumentParser = new ArgumentParser(input);
		
		List<String> parts = argumentParser.parse();
		assertEquals("/home/john/info.txt", parts.get(0));
		assertEquals("/home/john/backupFolder", parts.get(1));
		assertEquals("C:/home/Toni/pics", parts.get(2));
		assertEquals("D:/home/Toni/movies", parts.get(3));
	}
	
	@Test
	public void testParseOnePathWithQuote() {
		String input = "\"C:/Program Files/Program1/info.txt\"";
		ArgumentParser argumentParser = new ArgumentParser(input);
		
		List<String> parts = argumentParser.parse();
		assertEquals("C:/Program Files/Program1/info.txt", parts.get(0));
	}
	
	@Test
	public void testParseTwoPathsWithQuotes() {
		String input = " \"  C:/ Program Files/Program1  /info.txt\"    \"   home/ Toni dir/ pics and movies  \"";
		ArgumentParser argumentParser = new ArgumentParser(input);
		
		List<String> parts = argumentParser.parse();
		assertEquals("  C:/ Program Files/Program1  /info.txt", parts.get(0));
		assertEquals("   home/ Toni dir/ pics and movies  ", parts.get(1));
	}

}
