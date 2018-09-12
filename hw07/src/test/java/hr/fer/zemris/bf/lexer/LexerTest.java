package hr.fer.zemris.bf.lexer;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class LexerTest {

	@Test(expected=LexerException.class) 
	public void testExpressionIsNull() {
		String expression = null;
		@SuppressWarnings("unused")
		Lexer lexer = new Lexer(expression);
	}
	
	@Test(expected=LexerException.class) 
	public void testInvalidDigit() {
		String expression = "0 0 2";
		Lexer lexer = new Lexer(expression);
		
		List<Token> tokenList = new ArrayList<>();
		
		Token token;
		do {
			token = lexer.nextToken();
			tokenList.add(token);
		} while(token.getTokenType()!=TokenType.EOF);
	}
	
	@Test(expected=LexerException.class) 
	public void testInvalidNumberOfDigits() {
		String expression = "00";
		Lexer lexer = new Lexer(expression);
		
		List<Token> tokenList = new ArrayList<>();
		
		Token token;
		do {
			token = lexer.nextToken();
			tokenList.add(token);
		} while(token.getTokenType()!=TokenType.EOF);
	}
	
	@Test(expected=LexerException.class) 
	public void testInvalidNumberOfDigits2() {
		String expression = "a and 10";
		Lexer lexer = new Lexer(expression);
		
		List<Token> tokenList = new ArrayList<>();
		
		Token token;
		do {
			token = lexer.nextToken();
			tokenList.add(token);
		} while(token.getTokenType()!=TokenType.EOF);
	}
	
	@Test(expected=LexerException.class) 
	public void testInvalidCharacter() {
		String expression = "0 0 #";
		Lexer lexer = new Lexer(expression);
		
		List<Token> tokenList = new ArrayList<>();
		
		Token token;
		do {
			token = lexer.nextToken();
			tokenList.add(token);
		} while(token.getTokenType()!=TokenType.EOF);
	}
	
	@Test(expected=LexerException.class) 
	public void testInvalidCharacter2() {
		String expression = "0 0 var %";
		Lexer lexer = new Lexer(expression);
		
		List<Token> tokenList = new ArrayList<>();
		
		Token token;
		do {
			token = lexer.nextToken();
			tokenList.add(token);
		} while(token.getTokenType()!=TokenType.EOF);
	}
	
	@Test(expected=LexerException.class) 
	public void testInvalidIdentifier() {
		String expression = "A$";
		Lexer lexer = new Lexer(expression);
		
		List<Token> tokenList = new ArrayList<>();
		
		Token token;
		do {
			token = lexer.nextToken();
			tokenList.add(token);
		} while(token.getTokenType()!=TokenType.EOF);
	}
	
	@Test(expected=LexerException.class) 
	public void testInvalidIdentifier2() {
		String expression = "Var-";
		Lexer lexer = new Lexer(expression);
		
		List<Token> tokenList = new ArrayList<>();
		
		Token token;
		do {
			token = lexer.nextToken();
			tokenList.add(token);
		} while(token.getTokenType()!=TokenType.EOF);
	}
	
	@Test 
	public void testComplexExampleWithoutSpaces() {
		String expression = "1and 0:+:true*not 0";
		Lexer lexer = new Lexer(expression);
		
		List<Token> tokenList = new ArrayList<>();
		
		Token token;
		do {
			token = lexer.nextToken();
			tokenList.add(token);
		} while(token.getTokenType()!=TokenType.EOF);
		
		assertEquals(9, tokenList.size());
		
		assertEquals(TokenType.CONSTANT, tokenList.get(0).getTokenType());
		assertEquals(true, tokenList.get(0).getTokenValue());
		
		assertEquals(TokenType.OPERATOR, tokenList.get(1).getTokenType());
		assertEquals("and", tokenList.get(1).getTokenValue());
		
		assertEquals(TokenType.CONSTANT, tokenList.get(2).getTokenType());
		assertEquals(false, tokenList.get(2).getTokenValue());
		
		assertEquals(TokenType.OPERATOR, tokenList.get(3).getTokenType());
		assertEquals("xor", tokenList.get(3).getTokenValue());
		
		assertEquals(TokenType.CONSTANT, tokenList.get(4).getTokenType());
		assertEquals(true, tokenList.get(4).getTokenValue());
		
		assertEquals(TokenType.OPERATOR, tokenList.get(5).getTokenType());
		assertEquals("and", tokenList.get(5).getTokenValue());
		
		assertEquals(TokenType.OPERATOR, tokenList.get(6).getTokenType());
		assertEquals("not", tokenList.get(6).getTokenValue());
		
		assertEquals(TokenType.CONSTANT, tokenList.get(7).getTokenType());
		assertEquals(false, tokenList.get(7).getTokenValue());
		
		assertEquals(TokenType.EOF, tokenList.get(8).getTokenType());
		assertEquals(null, tokenList.get(8).getTokenValue());
	}
	
	@Test 
	public void testComplexExampleWithoutSpaces2() {
		String expression = "1and 0:+:true*not 0+(var_123*TRUE)";
		Lexer lexer = new Lexer(expression);
		
		List<Token> tokenList = new ArrayList<>();
		
		Token token;
		do {
			token = lexer.nextToken();
			tokenList.add(token);
		} while(token.getTokenType()!=TokenType.EOF);
		
		assertEquals(15, tokenList.size());
		
		assertEquals(TokenType.CONSTANT, tokenList.get(0).getTokenType());
		assertEquals(true, tokenList.get(0).getTokenValue());
		
		assertEquals(TokenType.OPERATOR, tokenList.get(1).getTokenType());
		assertEquals("and", tokenList.get(1).getTokenValue());
		
		assertEquals(TokenType.CONSTANT, tokenList.get(2).getTokenType());
		assertEquals(false, tokenList.get(2).getTokenValue());
		
		assertEquals(TokenType.OPERATOR, tokenList.get(3).getTokenType());
		assertEquals("xor", tokenList.get(3).getTokenValue());
		
		assertEquals(TokenType.CONSTANT, tokenList.get(4).getTokenType());
		assertEquals(true, tokenList.get(4).getTokenValue());
		
		assertEquals(TokenType.OPERATOR, tokenList.get(5).getTokenType());
		assertEquals("and", tokenList.get(5).getTokenValue());
		
		assertEquals(TokenType.OPERATOR, tokenList.get(6).getTokenType());
		assertEquals("not", tokenList.get(6).getTokenValue());
		
		assertEquals(TokenType.CONSTANT, tokenList.get(7).getTokenType());
		assertEquals(false, tokenList.get(7).getTokenValue());
		
		assertEquals(TokenType.OPERATOR, tokenList.get(8).getTokenType());
		assertEquals("or", tokenList.get(8).getTokenValue());
		
		assertEquals(TokenType.OPEN_BRACKET, tokenList.get(9).getTokenType());
		assertEquals('(', tokenList.get(9).getTokenValue());
		
		assertEquals(TokenType.VARIABLE, tokenList.get(10).getTokenType());
		assertEquals("VAR_123", tokenList.get(10).getTokenValue());
		
		assertEquals(TokenType.OPERATOR, tokenList.get(11).getTokenType());
		assertEquals("and", tokenList.get(11).getTokenValue());
		
		assertEquals(TokenType.CONSTANT, tokenList.get(12).getTokenType());
		assertEquals(true, tokenList.get(12).getTokenValue());
		
		assertEquals(TokenType.CLOSED_BRACKET, tokenList.get(13).getTokenType());
		assertEquals(')', tokenList.get(13).getTokenValue());
		
		assertEquals(TokenType.EOF, tokenList.get(14).getTokenType());
		assertEquals(null, tokenList.get(14).getTokenValue());
	}
	
	@Test
	public void testFirstFromIzrazi1() {
		String expression = "0";
		Lexer lexer = new Lexer(expression);
		
		List<Token> tokenList = new ArrayList<>();
		
		Token token;
		do {
			token = lexer.nextToken();
			tokenList.add(token);
		} while(token.getTokenType()!=TokenType.EOF);
		
		assertEquals(2, tokenList.size());
		
		assertEquals(TokenType.CONSTANT, tokenList.get(0).getTokenType());
		assertEquals(false, tokenList.get(0).getTokenValue());
		
		assertEquals(TokenType.EOF, tokenList.get(1).getTokenType());
		assertEquals(null, tokenList.get(1).getTokenValue());
	}
	
	@Test
	public void testSecondFromIzrazi1() {
		String expression = "tRue";
		Lexer lexer = new Lexer(expression);
		
		List<Token> tokenList = new ArrayList<>();
		
		Token token;
		do {
			token = lexer.nextToken();
			tokenList.add(token);
		} while(token.getTokenType()!=TokenType.EOF);
		
		assertEquals(2, tokenList.size());
		
		assertEquals(TokenType.CONSTANT, tokenList.get(0).getTokenType());
		assertEquals(true, tokenList.get(0).getTokenValue());
		
		assertEquals(TokenType.EOF, tokenList.get(1).getTokenType());
		assertEquals(null, tokenList.get(1).getTokenValue());
	}
	
	@Test
	public void testThirdFromIzrazi1() {
		String expression = "Not a";
		Lexer lexer = new Lexer(expression);
		
		List<Token> tokenList = new ArrayList<>();
		
		Token token;
		do {
			token = lexer.nextToken();
			tokenList.add(token);
		} while(token.getTokenType()!=TokenType.EOF);
		
		assertEquals(3, tokenList.size());
		
		assertEquals(TokenType.OPERATOR, tokenList.get(0).getTokenType());
		assertEquals("not", tokenList.get(0).getTokenValue());
		
		assertEquals(TokenType.VARIABLE, tokenList.get(1).getTokenType());
		assertEquals("A", tokenList.get(1).getTokenValue());
		
		assertEquals(TokenType.EOF, tokenList.get(2).getTokenType());
		assertEquals(null, tokenList.get(2).getTokenValue());
	}
	
	@Test
	public void testFourthFromIzrazi1() {
		String expression = "A aNd b";
		Lexer lexer = new Lexer(expression);
		
		List<Token> tokenList = new ArrayList<>();
		
		Token token;
		do {
			token = lexer.nextToken();
			tokenList.add(token);
		} while(token.getTokenType()!=TokenType.EOF);
		
		assertEquals(4, tokenList.size());
		
		assertEquals(TokenType.VARIABLE, tokenList.get(0).getTokenType());
		assertEquals("A", tokenList.get(0).getTokenValue());
		
		assertEquals(TokenType.OPERATOR, tokenList.get(1).getTokenType());
		assertEquals("and", tokenList.get(1).getTokenValue());
		
		assertEquals(TokenType.VARIABLE, tokenList.get(2).getTokenType());
		assertEquals("B", tokenList.get(2).getTokenValue());
		
		assertEquals(TokenType.EOF, tokenList.get(3).getTokenType());
		assertEquals(null, tokenList.get(3).getTokenValue());
	}
	
	@Test
	public void testFifthFromIzrazi1() {
		String expression = "a or b";
		Lexer lexer = new Lexer(expression);
		
		List<Token> tokenList = new ArrayList<>();
		
		Token token;
		do {
			token = lexer.nextToken();
			tokenList.add(token);
		} while(token.getTokenType()!=TokenType.EOF);
		
		assertEquals(4, tokenList.size());
		
		assertEquals(TokenType.VARIABLE, tokenList.get(0).getTokenType());
		assertEquals("A", tokenList.get(0).getTokenValue());
		
		assertEquals(TokenType.OPERATOR, tokenList.get(1).getTokenType());
		assertEquals("or", tokenList.get(1).getTokenValue());
		
		assertEquals(TokenType.VARIABLE, tokenList.get(2).getTokenType());
		assertEquals("B", tokenList.get(2).getTokenValue());
		
		assertEquals(TokenType.EOF, tokenList.get(3).getTokenType());
		assertEquals(null, tokenList.get(3).getTokenValue());
	}
	
	@Test
	public void testSixthFromIzrazi1() {
		String expression = "a xoR b";
		Lexer lexer = new Lexer(expression);
		
		List<Token> tokenList = new ArrayList<>();
		
		Token token;
		do {
			token = lexer.nextToken();
			tokenList.add(token);
		} while(token.getTokenType()!=TokenType.EOF);
		
		assertEquals(4, tokenList.size());
		
		assertEquals(TokenType.VARIABLE, tokenList.get(0).getTokenType());
		assertEquals("A", tokenList.get(0).getTokenValue());
		
		assertEquals(TokenType.OPERATOR, tokenList.get(1).getTokenType());
		assertEquals("xor", tokenList.get(1).getTokenValue());
		
		assertEquals(TokenType.VARIABLE, tokenList.get(2).getTokenType());
		assertEquals("B", tokenList.get(2).getTokenValue());
		
		assertEquals(TokenType.EOF, tokenList.get(3).getTokenType());
		assertEquals(null, tokenList.get(3).getTokenValue());
	}
	
	@Test
	public void testSeventhFromIzrazi1() {
		String expression = "A and b * c";
		Lexer lexer = new Lexer(expression);
		
		List<Token> tokenList = new ArrayList<>();
		
		Token token;
		do {
			token = lexer.nextToken();
			tokenList.add(token);
		} while(token.getTokenType()!=TokenType.EOF);
		
		assertEquals(6, tokenList.size());
		
		assertEquals(TokenType.VARIABLE, tokenList.get(0).getTokenType());
		assertEquals("A", tokenList.get(0).getTokenValue());
		
		assertEquals(TokenType.OPERATOR, tokenList.get(1).getTokenType());
		assertEquals("and", tokenList.get(1).getTokenValue());
		
		assertEquals(TokenType.VARIABLE, tokenList.get(2).getTokenType());
		assertEquals("B", tokenList.get(2).getTokenValue());
		
		assertEquals(TokenType.OPERATOR, tokenList.get(3).getTokenType());
		assertEquals("and", tokenList.get(3).getTokenValue());
		
		assertEquals(TokenType.VARIABLE, tokenList.get(4).getTokenType());
		assertEquals("C", tokenList.get(4).getTokenValue());
		
		assertEquals(TokenType.EOF, tokenList.get(5).getTokenType());
		assertEquals(null, tokenList.get(5).getTokenValue());
	}
	
	@Test
	public void testEighthFromIzrazi1() {
		String expression = "a or b or c";
		Lexer lexer = new Lexer(expression);
		
		List<Token> tokenList = new ArrayList<>();
		
		Token token;
		do {
			token = lexer.nextToken();
			tokenList.add(token);
		} while(token.getTokenType()!=TokenType.EOF);
		
		assertEquals(6, tokenList.size());
		
		assertEquals(TokenType.VARIABLE, tokenList.get(0).getTokenType());
		assertEquals("A", tokenList.get(0).getTokenValue());
		
		assertEquals(TokenType.OPERATOR, tokenList.get(1).getTokenType());
		assertEquals("or", tokenList.get(1).getTokenValue());
		
		assertEquals(TokenType.VARIABLE, tokenList.get(2).getTokenType());
		assertEquals("B", tokenList.get(2).getTokenValue());
		
		assertEquals(TokenType.OPERATOR, tokenList.get(3).getTokenType());
		assertEquals("or", tokenList.get(3).getTokenValue());
		
		assertEquals(TokenType.VARIABLE, tokenList.get(4).getTokenType());
		assertEquals("C", tokenList.get(4).getTokenValue());
		
		assertEquals(TokenType.EOF, tokenList.get(5).getTokenType());
		assertEquals(null, tokenList.get(5).getTokenValue());
	}
	
	@Test
	public void testNinthFromIzrazi1() {
		String expression = "a xor b :+: c";
		Lexer lexer = new Lexer(expression);
		
		List<Token> tokenList = new ArrayList<>();
		
		Token token;
		do {
			token = lexer.nextToken();
			tokenList.add(token);
		} while(token.getTokenType()!=TokenType.EOF);
		
		assertEquals(6, tokenList.size());
		
		assertEquals(TokenType.VARIABLE, tokenList.get(0).getTokenType());
		assertEquals("A", tokenList.get(0).getTokenValue());
		
		assertEquals(TokenType.OPERATOR, tokenList.get(1).getTokenType());
		assertEquals("xor", tokenList.get(1).getTokenValue());
		
		assertEquals(TokenType.VARIABLE, tokenList.get(2).getTokenType());
		assertEquals("B", tokenList.get(2).getTokenValue());
		
		assertEquals(TokenType.OPERATOR, tokenList.get(3).getTokenType());
		assertEquals("xor", tokenList.get(3).getTokenValue());
		
		assertEquals(TokenType.VARIABLE, tokenList.get(4).getTokenType());
		assertEquals("C", tokenList.get(4).getTokenValue());
		
		assertEquals(TokenType.EOF, tokenList.get(5).getTokenType());
		assertEquals(null, tokenList.get(5).getTokenValue());
	}
	
	@Test
	public void testTenthFromIzrazi1() {
		String expression = "not not a";
		Lexer lexer = new Lexer(expression);
		
		List<Token> tokenList = new ArrayList<>();
		
		Token token;
		do {
			token = lexer.nextToken();
			tokenList.add(token);
		} while(token.getTokenType()!=TokenType.EOF);
		
		assertEquals(4, tokenList.size());
		
		assertEquals(TokenType.OPERATOR, tokenList.get(0).getTokenType());
		assertEquals("not", tokenList.get(0).getTokenValue());
		
		assertEquals(TokenType.OPERATOR, tokenList.get(1).getTokenType());
		assertEquals("not", tokenList.get(1).getTokenValue());
		
		assertEquals(TokenType.VARIABLE, tokenList.get(2).getTokenType());
		assertEquals("A", tokenList.get(2).getTokenValue());
		
		assertEquals(TokenType.EOF, tokenList.get(3).getTokenType());
		assertEquals(null, tokenList.get(3).getTokenValue());
	}
	
	@Test
	public void testEleventhFromIzrazi1() {
		String expression = "a or b xor c and d";
		Lexer lexer = new Lexer(expression);
		
		List<Token> tokenList = new ArrayList<>();
		
		Token token;
		do {
			token = lexer.nextToken();
			tokenList.add(token);
		} while(token.getTokenType()!=TokenType.EOF);
		
		assertEquals(8, tokenList.size());
		
		assertEquals(TokenType.VARIABLE, tokenList.get(0).getTokenType());
		assertEquals("A", tokenList.get(0).getTokenValue());
		
		assertEquals(TokenType.OPERATOR, tokenList.get(1).getTokenType());
		assertEquals("or", tokenList.get(1).getTokenValue());
		
		assertEquals(TokenType.VARIABLE, tokenList.get(2).getTokenType());
		assertEquals("B", tokenList.get(2).getTokenValue());
		
		assertEquals(TokenType.OPERATOR, tokenList.get(3).getTokenType());
		assertEquals("xor", tokenList.get(3).getTokenValue());
		
		assertEquals(TokenType.VARIABLE, tokenList.get(4).getTokenType());
		assertEquals("C", tokenList.get(4).getTokenValue());
		
		assertEquals(TokenType.OPERATOR, tokenList.get(5).getTokenType());
		assertEquals("and", tokenList.get(5).getTokenValue());
		
		assertEquals(TokenType.VARIABLE, tokenList.get(6).getTokenType());
		assertEquals("D", tokenList.get(6).getTokenValue());
		
		assertEquals(TokenType.EOF, tokenList.get(7).getTokenType());
		assertEquals(null, tokenList.get(7).getTokenValue());
	}
	
	@Test
	public void testTwelfthFromIzrazi1() {
		String expression = "a or b xor c or d";
		Lexer lexer = new Lexer(expression);
		
		List<Token> tokenList = new ArrayList<>();
		
		Token token;
		do {
			token = lexer.nextToken();
			tokenList.add(token);
		} while(token.getTokenType()!=TokenType.EOF);
		
		assertEquals(8, tokenList.size());
		
		assertEquals(TokenType.VARIABLE, tokenList.get(0).getTokenType());
		assertEquals("A", tokenList.get(0).getTokenValue());
		
		assertEquals(TokenType.OPERATOR, tokenList.get(1).getTokenType());
		assertEquals("or", tokenList.get(1).getTokenValue());
		
		assertEquals(TokenType.VARIABLE, tokenList.get(2).getTokenType());
		assertEquals("B", tokenList.get(2).getTokenValue());
		
		assertEquals(TokenType.OPERATOR, tokenList.get(3).getTokenType());
		assertEquals("xor", tokenList.get(3).getTokenValue());
		
		assertEquals(TokenType.VARIABLE, tokenList.get(4).getTokenType());
		assertEquals("C", tokenList.get(4).getTokenValue());
		
		assertEquals(TokenType.OPERATOR, tokenList.get(5).getTokenType());
		assertEquals("or", tokenList.get(5).getTokenValue());
		
		assertEquals(TokenType.VARIABLE, tokenList.get(6).getTokenType());
		assertEquals("D", tokenList.get(6).getTokenValue());
		
		assertEquals(TokenType.EOF, tokenList.get(7).getTokenType());
		assertEquals(null, tokenList.get(7).getTokenValue());
	}
	
	@Test
	public void testThirteenthFromIzrazi1() {
		String expression = "a xor b or c xor d";
		Lexer lexer = new Lexer(expression);
		
		List<Token> tokenList = new ArrayList<>();
		
		Token token;
		do {
			token = lexer.nextToken();
			tokenList.add(token);
		} while(token.getTokenType()!=TokenType.EOF);
		
		assertEquals(8, tokenList.size());
		
		assertEquals(TokenType.VARIABLE, tokenList.get(0).getTokenType());
		assertEquals("A", tokenList.get(0).getTokenValue());
		
		assertEquals(TokenType.OPERATOR, tokenList.get(1).getTokenType());
		assertEquals("xor", tokenList.get(1).getTokenValue());
		
		assertEquals(TokenType.VARIABLE, tokenList.get(2).getTokenType());
		assertEquals("B", tokenList.get(2).getTokenValue());
		
		assertEquals(TokenType.OPERATOR, tokenList.get(3).getTokenType());
		assertEquals("or", tokenList.get(3).getTokenValue());
		
		assertEquals(TokenType.VARIABLE, tokenList.get(4).getTokenType());
		assertEquals("C", tokenList.get(4).getTokenValue());
		
		assertEquals(TokenType.OPERATOR, tokenList.get(5).getTokenType());
		assertEquals("xor", tokenList.get(5).getTokenValue());
		
		assertEquals(TokenType.VARIABLE, tokenList.get(6).getTokenType());
		assertEquals("D", tokenList.get(6).getTokenValue());
		
		assertEquals(TokenType.EOF, tokenList.get(7).getTokenType());
		assertEquals(null, tokenList.get(7).getTokenValue());
	}
	
	@Test
	public void testFourteenthFromIzrazi1() {
		String expression = "(a + b) xor (c or d)";
		Lexer lexer = new Lexer(expression);
		
		List<Token> tokenList = new ArrayList<>();
		
		Token token;
		do {
			token = lexer.nextToken();
			tokenList.add(token);
		} while(token.getTokenType()!=TokenType.EOF);
		
		assertEquals(12, tokenList.size());
		
		assertEquals(TokenType.OPEN_BRACKET, tokenList.get(0).getTokenType());
		assertEquals('(', tokenList.get(0).getTokenValue());
		
		assertEquals(TokenType.VARIABLE, tokenList.get(1).getTokenType());
		assertEquals("A", tokenList.get(1).getTokenValue());
		
		assertEquals(TokenType.OPERATOR, tokenList.get(2).getTokenType());
		assertEquals("or", tokenList.get(2).getTokenValue());
		
		assertEquals(TokenType.VARIABLE, tokenList.get(3).getTokenType());
		assertEquals("B", tokenList.get(3).getTokenValue());
		
		assertEquals(TokenType.CLOSED_BRACKET, tokenList.get(4).getTokenType());
		assertEquals(')', tokenList.get(4).getTokenValue());
		
		assertEquals(TokenType.OPERATOR, tokenList.get(5).getTokenType());
		assertEquals("xor", tokenList.get(5).getTokenValue());
		
		assertEquals(TokenType.OPEN_BRACKET, tokenList.get(6).getTokenType());
		assertEquals('(', tokenList.get(6).getTokenValue());
		
		assertEquals(TokenType.VARIABLE, tokenList.get(7).getTokenType());
		assertEquals("C", tokenList.get(7).getTokenValue());
		
		assertEquals(TokenType.OPERATOR, tokenList.get(8).getTokenType());
		assertEquals("or", tokenList.get(8).getTokenValue());
		
		assertEquals(TokenType.VARIABLE, tokenList.get(9).getTokenType());
		assertEquals("D", tokenList.get(9).getTokenValue());
		
		assertEquals(TokenType.CLOSED_BRACKET, tokenList.get(10).getTokenType());
		assertEquals(')', tokenList.get(10).getTokenValue());
		
		assertEquals(TokenType.EOF, tokenList.get(11).getTokenType());
		assertEquals(null, tokenList.get(11).getTokenValue());
	}
	
	@Test
	public void testFifteenthFromIzrazi1() {
		String expression = "(d or b) xor not (a or c)";
		Lexer lexer = new Lexer(expression);
		
		List<Token> tokenList = new ArrayList<>();
		
		Token token;
		do {
			token = lexer.nextToken();
			tokenList.add(token);
		} while(token.getTokenType()!=TokenType.EOF);
		
		assertEquals(13, tokenList.size());
		
		assertEquals(TokenType.OPEN_BRACKET, tokenList.get(0).getTokenType());
		assertEquals('(', tokenList.get(0).getTokenValue());
		
		assertEquals(TokenType.VARIABLE, tokenList.get(1).getTokenType());
		assertEquals("D", tokenList.get(1).getTokenValue());
		
		assertEquals(TokenType.OPERATOR, tokenList.get(2).getTokenType());
		assertEquals("or", tokenList.get(2).getTokenValue());
		
		assertEquals(TokenType.VARIABLE, tokenList.get(3).getTokenType());
		assertEquals("B", tokenList.get(3).getTokenValue());
		
		assertEquals(TokenType.CLOSED_BRACKET, tokenList.get(4).getTokenType());
		assertEquals(')', tokenList.get(4).getTokenValue());
		
		assertEquals(TokenType.OPERATOR, tokenList.get(5).getTokenType());
		assertEquals("xor", tokenList.get(5).getTokenValue());
		
		assertEquals(TokenType.OPERATOR, tokenList.get(6).getTokenType());
		assertEquals("not", tokenList.get(6).getTokenValue());
		
		assertEquals(TokenType.OPEN_BRACKET, tokenList.get(7).getTokenType());
		assertEquals('(', tokenList.get(7).getTokenValue());
		
		assertEquals(TokenType.VARIABLE, tokenList.get(8).getTokenType());
		assertEquals("A", tokenList.get(8).getTokenValue());
		
		assertEquals(TokenType.OPERATOR, tokenList.get(9).getTokenType());
		assertEquals("or", tokenList.get(9).getTokenValue());
		
		assertEquals(TokenType.VARIABLE, tokenList.get(10).getTokenType());
		assertEquals("C", tokenList.get(10).getTokenValue());
		
		assertEquals(TokenType.CLOSED_BRACKET, tokenList.get(11).getTokenType());
		assertEquals(')', tokenList.get(11).getTokenValue());
		
		assertEquals(TokenType.EOF, tokenList.get(12).getTokenType());
		assertEquals(null, tokenList.get(12).getTokenValue());
	}
	
	@Test
	public void testSixteenthFromIzrazi1() {
		String expression = "(c or d) mor not (a or b)";
		Lexer lexer = new Lexer(expression);
		
		List<Token> tokenList = new ArrayList<>();
		
		Token token;
		do {
			token = lexer.nextToken();
			tokenList.add(token);
		} while(token.getTokenType()!=TokenType.EOF);
		
		assertEquals(13, tokenList.size());
		
		assertEquals(TokenType.OPEN_BRACKET, tokenList.get(0).getTokenType());
		assertEquals('(', tokenList.get(0).getTokenValue());
		
		assertEquals(TokenType.VARIABLE, tokenList.get(1).getTokenType());
		assertEquals("C", tokenList.get(1).getTokenValue());
		
		assertEquals(TokenType.OPERATOR, tokenList.get(2).getTokenType());
		assertEquals("or", tokenList.get(2).getTokenValue());
		
		assertEquals(TokenType.VARIABLE, tokenList.get(3).getTokenType());
		assertEquals("D", tokenList.get(3).getTokenValue());
		
		assertEquals(TokenType.CLOSED_BRACKET, tokenList.get(4).getTokenType());
		assertEquals(')', tokenList.get(4).getTokenValue());
		
		assertEquals(TokenType.VARIABLE, tokenList.get(5).getTokenType());
		assertEquals("MOR", tokenList.get(5).getTokenValue());
		
		assertEquals(TokenType.OPERATOR, tokenList.get(6).getTokenType());
		assertEquals("not", tokenList.get(6).getTokenValue());
		
		assertEquals(TokenType.OPEN_BRACKET, tokenList.get(7).getTokenType());
		assertEquals('(', tokenList.get(7).getTokenValue());
		
		assertEquals(TokenType.VARIABLE, tokenList.get(8).getTokenType());
		assertEquals("A", tokenList.get(8).getTokenValue());
		
		assertEquals(TokenType.OPERATOR, tokenList.get(9).getTokenType());
		assertEquals("or", tokenList.get(9).getTokenValue());
		
		assertEquals(TokenType.VARIABLE, tokenList.get(10).getTokenType());
		assertEquals("B", tokenList.get(10).getTokenValue());
		
		assertEquals(TokenType.CLOSED_BRACKET, tokenList.get(11).getTokenType());
		assertEquals(')', tokenList.get(11).getTokenValue());
		
		assertEquals(TokenType.EOF, tokenList.get(12).getTokenType());
		assertEquals(null, tokenList.get(12).getTokenValue());
	}
	
	@Test
	public void testSeventeenthFromIzrazi1() {
		String expression = "not a not b";
		Lexer lexer = new Lexer(expression);
		
		List<Token> tokenList = new ArrayList<>();
		
		Token token;
		do {
			token = lexer.nextToken();
			tokenList.add(token);
		} while(token.getTokenType()!=TokenType.EOF);
		
		assertEquals(5, tokenList.size());
		
		assertEquals(TokenType.OPERATOR, tokenList.get(0).getTokenType());
		assertEquals("not", tokenList.get(0).getTokenValue());
		
		assertEquals(TokenType.VARIABLE, tokenList.get(1).getTokenType());
		assertEquals("A", tokenList.get(1).getTokenValue());
		
		assertEquals(TokenType.OPERATOR, tokenList.get(2).getTokenType());
		assertEquals("not", tokenList.get(2).getTokenValue());
		
		assertEquals(TokenType.VARIABLE, tokenList.get(3).getTokenType());
		assertEquals("B", tokenList.get(3).getTokenValue());
		
		assertEquals(TokenType.EOF, tokenList.get(4).getTokenType());
		assertEquals(null, tokenList.get(4).getTokenValue());
	}
	
	@Test
	public void testEighteenthFromIzrazi1() {
		String expression = "a and (b or";
		Lexer lexer = new Lexer(expression);
		
		List<Token> tokenList = new ArrayList<>();
		
		Token token;
		do {
			token = lexer.nextToken();
			tokenList.add(token);
		} while(token.getTokenType()!=TokenType.EOF);
		
		assertEquals(6, tokenList.size());
		
		assertEquals(TokenType.VARIABLE, tokenList.get(0).getTokenType());
		assertEquals("A", tokenList.get(0).getTokenValue());
		
		assertEquals(TokenType.OPERATOR, tokenList.get(1).getTokenType());
		assertEquals("and", tokenList.get(1).getTokenValue());
		
		assertEquals(TokenType.OPEN_BRACKET, tokenList.get(2).getTokenType());
		assertEquals('(', tokenList.get(2).getTokenValue());
		
		assertEquals(TokenType.VARIABLE, tokenList.get(3).getTokenType());
		assertEquals("B", tokenList.get(3).getTokenValue());
		
		assertEquals(TokenType.OPERATOR, tokenList.get(4).getTokenType());
		assertEquals("or", tokenList.get(4).getTokenValue());
		
		assertEquals(TokenType.EOF, tokenList.get(5).getTokenType());
		assertEquals(null, tokenList.get(5).getTokenValue());
	}
	
	@Test
	public void testNineteenthFromIzrazi1() {
		String expression = "a and (b or c";
		Lexer lexer = new Lexer(expression);
		
		List<Token> tokenList = new ArrayList<>();
		
		Token token;
		do {
			token = lexer.nextToken();
			tokenList.add(token);
		} while(token.getTokenType()!=TokenType.EOF);
		
		assertEquals(7, tokenList.size());
		
		assertEquals(TokenType.VARIABLE, tokenList.get(0).getTokenType());
		assertEquals("A", tokenList.get(0).getTokenValue());
		
		assertEquals(TokenType.OPERATOR, tokenList.get(1).getTokenType());
		assertEquals("and", tokenList.get(1).getTokenValue());
		
		assertEquals(TokenType.OPEN_BRACKET, tokenList.get(2).getTokenType());
		assertEquals('(', tokenList.get(2).getTokenValue());
		
		assertEquals(TokenType.VARIABLE, tokenList.get(3).getTokenType());
		assertEquals("B", tokenList.get(3).getTokenValue());
		
		assertEquals(TokenType.OPERATOR, tokenList.get(4).getTokenType());
		assertEquals("or", tokenList.get(4).getTokenValue());
		
		assertEquals(TokenType.VARIABLE, tokenList.get(5).getTokenType());
		assertEquals("C", tokenList.get(5).getTokenValue());
		
		assertEquals(TokenType.EOF, tokenList.get(6).getTokenType());
		assertEquals(null, tokenList.get(6).getTokenValue());
	}
	
	@Test(expected=LexerException.class)
	public void testTwentyethFromIzrazi1() {
		String expression = "a and 10";
		Lexer lexer = new Lexer(expression);
		
		List<Token> tokenList = new ArrayList<>();
		
		Token token;
		do {
			token = lexer.nextToken();
			tokenList.add(token);
		} while(token.getTokenType()!=TokenType.EOF);
	}
}
