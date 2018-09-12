package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

@SuppressWarnings("javadoc")
public class SmartScriptLexerTest {

	private static final double DELTA = 1E-2;

	@Test
	public void testNotNull() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		assertNotNull("Token was expected but null was returned.", lexer.nextToken());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNullInput() {
		new SmartScriptLexer(null);
	}
	
	@Test
	public void testEmpty() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		assertEquals("Empty input must generate only EOF token.", TokenType.EOF, lexer.nextToken().getType());
	}
	
	@Test
	public void testGetReturnsLastNext() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		Token token = lexer.nextToken();
		assertEquals("getToken returned different token than nextToken.", token, lexer.getToken());
		assertEquals("getToken returned different token than nextToken.", token, lexer.getToken());
	}
	
	@Test(expected=SmartScriptLexerException.class)
	public void testRadAfterEOF() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		lexer.nextToken();
		lexer.nextToken();
	}
	
	@Test
	public void testTextOneLineWithJumpToNewLine() {
		String document = loader("document3.txt");
		SmartScriptLexer lexer = new SmartScriptLexer(document);
		
		Token token = lexer.nextToken();
		
		assertEquals(TokenType.TEXT, token.getType());
		assertEquals("This is sample text.\r\n", token.getValue());
	}
	
	@Test
	public void testTextOneLine() {
		String document = loader("document27.txt");
		SmartScriptLexer lexer = new SmartScriptLexer(document);
		
		Token token = lexer.nextToken();
		
		assertEquals(TokenType.TEXT, token.getType());
		assertEquals("One line text.", token.getValue());
	}
	
	@Test
	public void testTextThreeLines() {
		String document = loader("document28.txt");
		SmartScriptLexer lexer = new SmartScriptLexer(document);
		
		Token token = lexer.nextToken();
		
		assertEquals(TokenType.TEXT, token.getType());
		assertEquals("First line.\r\n\r\nThird line.", token.getValue());
	}
	
	@Test
	public void testEchoTagOnlyEcho() {
		String document = loader("document17.txt");
		SmartScriptLexer lexer = new SmartScriptLexer(document); 
		
		checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "{$="));
	}
	
	@Test
	public void testEchoTagOnlyOneVariable() {
		String document = loader("document18.txt");
		SmartScriptLexer lexer = new SmartScriptLexer(document); 
		
		Element[] expectedElements = {
				new ElementVariable("i"),
		};
		
		Token[] correctData = {
				new Token(TokenType.TEXT, "{$="),
				new Token(TokenType.ECHO, expectedElements),
				new Token(TokenType.TEXT, "$}")
		};
		
		checkToken(lexer.nextToken(), correctData[0]);
		
		lexer.setState(SmartScriptLexerState.EMPTY_TAG);
		
		Token actualToken = lexer.nextToken();
		Token expectedToken = correctData[1];
		
		Element[] actualElements = (Element[])actualToken.getValue();
				
		assertEquals(expectedToken.getType(), actualToken.getType());
		assertEquals(expectedElements[0].asText(), actualElements[0].asText());
		
		lexer.setState(SmartScriptLexerState.TEXT);
		
		checkToken(lexer.nextToken(), correctData[2]);
	}
	
	@Test(expected=SmartScriptLexerException.class)
	public void testEchoTagInvalidVariableName() {
		String document = loader("document21.txt");
		SmartScriptLexer lexer = new SmartScriptLexer(document); 
		
		checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "{$="));
		
		lexer.setState(SmartScriptLexerState.EMPTY_TAG);
		
		@SuppressWarnings("unused")
		Token token = lexer.nextToken();
	}
	
	@Test(expected=SmartScriptLexerException.class)
	public void testEchoTagTooBigInteger() {
		String document = loader("document22.txt");
		SmartScriptLexer lexer = new SmartScriptLexer(document); 
		
		checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "{$="));
		
		lexer.setState(SmartScriptLexerState.EMPTY_TAG);
		
		@SuppressWarnings("unused")
		Token token = lexer.nextToken();
	}
	
	@Test(expected=SmartScriptLexerException.class)
	public void testEchoTagTooBigNegativeInteger() {
		String document = loader("document25.txt");
		SmartScriptLexer lexer = new SmartScriptLexer(document); 
		
		checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "{$="));
		
		lexer.setState(SmartScriptLexerState.EMPTY_TAG);
		
		@SuppressWarnings("unused")
		Token token = lexer.nextToken();
	}
	
	@Test(expected=SmartScriptLexerException.class)
	public void testEchoTagTooBigDouble() {
		String document = loader("document23.txt");
		SmartScriptLexer lexer = new SmartScriptLexer(document); 
		
		checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "{$="));
		
		lexer.setState(SmartScriptLexerState.EMPTY_TAG);
		
		@SuppressWarnings("unused")
		Token token = lexer.nextToken();
	}
	
	@Test(expected=SmartScriptLexerException.class)
	public void testEchoTagInvalidFunction() {
		String document = loader("document24.txt");
		SmartScriptLexer lexer = new SmartScriptLexer(document); 
		
		checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "{$="));
		
		lexer.setState(SmartScriptLexerState.EMPTY_TAG);
		
		@SuppressWarnings("unused")
		Token token = lexer.nextToken();
	}
	
	@Test
	public void testEchoTagThreeVariables() {
		String document = loader("document19.txt");
		SmartScriptLexer lexer = new SmartScriptLexer(document); 
		
		Element[] expectedElements = {
				new ElementVariable("i"),
				new ElementVariable("variable"),
				new ElementVariable("V_A_R_I_A_B_L_E"),
		};
		
		Token[] correctData = {
				new Token(TokenType.TEXT, "{$="),
				new Token(TokenType.ECHO, expectedElements),
				new Token(TokenType.TEXT, "$}")
		};
		
		checkToken(lexer.nextToken(), correctData[0]);
		
		lexer.setState(SmartScriptLexerState.EMPTY_TAG);
		
		Token actualToken = lexer.nextToken();
		Token expectedToken = correctData[1];
		
		Element[] actualElements = (Element[])actualToken.getValue();
				
		assertEquals(expectedToken.getType(), actualToken.getType());
		assertEquals(expectedElements[0].asText(), actualElements[0].asText());
		assertEquals(expectedElements[1].asText(), actualElements[1].asText());
		assertEquals(expectedElements[2].asText(), actualElements[2].asText());
		
		lexer.setState(SmartScriptLexerState.TEXT);
		
		checkToken(lexer.nextToken(), correctData[2]);
	}
	
	@Test
	public void testEchoTagTwoVariablesOperatorFunctionStringFunction() {
		String document = loader("document20.txt");
		SmartScriptLexer lexer = new SmartScriptLexer(document); 
		
		Element[] expectedElements = {
				new ElementVariable("i"),
				new ElementVariable("i"),
				new ElementOperator("*"),
				new ElementFunction("sin"),
				new ElementString("0.000"),
				new ElementFunction("decfmt")
		};
		
		Token[] correctData = {
				new Token(TokenType.TEXT, "{$="),
				new Token(TokenType.ECHO, expectedElements),
				new Token(TokenType.TEXT, "$}")
		};
		
		checkToken(lexer.nextToken(), correctData[0]);
		
		lexer.setState(SmartScriptLexerState.EMPTY_TAG);
		
		Token actualToken = lexer.nextToken();
		Token expectedToken = correctData[1];
		
		Element[] actualElements = (Element[])actualToken.getValue();
				
		assertEquals(expectedToken.getType(), actualToken.getType());
		assertEquals(expectedElements[0].asText(), actualElements[0].asText());
		assertEquals(expectedElements[1].asText(), actualElements[1].asText());
		assertEquals(expectedElements[2].asText(), actualElements[2].asText());
		assertEquals(expectedElements[3].asText(), actualElements[3].asText());
		assertEquals(expectedElements[4].asText(), actualElements[4].asText());
		assertEquals(expectedElements[5].asText(), actualElements[5].asText());
		
		lexer.setState(SmartScriptLexerState.TEXT);
		
		checkToken(lexer.nextToken(), correctData[2]);
	}
	
	@Test
	public void testForTagOnlyFor() {
		String document = loader("document2.txt");
		SmartScriptLexer lexer = new SmartScriptLexer(document); 
		
		checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "{$FOR"));
	}
	
	@Test
	public void testForTagVariableIntIntInt() {
		String document = loader("document1.txt");
		SmartScriptLexer lexer = new SmartScriptLexer(document);
		
		Element[] expectedElements = {
				new ElementVariable("i"),
				new ElementConstantInteger(1),
				new ElementConstantInteger(10),
				new ElementConstantInteger(1)
		};
		
		Token[] correctData = {
				new Token(TokenType.TEXT, "{$FOR"),
				new Token(TokenType.TAG_FOR, expectedElements),
				new Token(TokenType.TEXT, "$}")
		};
		
		checkToken(lexer.nextToken(), correctData[0]);
		
		lexer.setState(SmartScriptLexerState.NON_EMPTY_TAG);
		
		Token actualToken = lexer.nextToken();
		Token expectedToken = correctData[1];
		
		Element[] actualElements = (Element[])actualToken.getValue();
				
		assertEquals(expectedToken.getType(), actualToken.getType());
		assertEquals(expectedElements[0].asText(), actualElements[0].asText());
		assertEquals(expectedElements[1].asText(), actualElements[1].asText());
		assertEquals(expectedElements[2].asText(), actualElements[2].asText());
		assertEquals(expectedElements[3].asText(), actualElements[3].asText());
		
		lexer.setState(SmartScriptLexerState.TEXT);
		
		checkToken(lexer.nextToken(), correctData[2]);
	}
	
	@Test
	public void testForTagVariableIntIntInt2() {
		String document = loader("document4.txt");
		SmartScriptLexer lexer = new SmartScriptLexer(document);
		
		Element[] expectedElements = {
				new ElementVariable("variable"),
				new ElementConstantInteger(150),
				new ElementConstantInteger(104),
				new ElementConstantInteger(116)
		};
		
		Token[] correctData = {
				new Token(TokenType.TEXT, "{$FOR"),
				new Token(TokenType.TAG_FOR, expectedElements),
				new Token(TokenType.TEXT, "$}")
		};
		
		checkToken(lexer.nextToken(), correctData[0]);
		
		lexer.setState(SmartScriptLexerState.NON_EMPTY_TAG);
		
		Token actualToken = lexer.nextToken();
		Token expectedToken = correctData[1];
		
		Element[] actualElements = (Element[])actualToken.getValue();
					
		assertEquals(expectedToken.getType(), actualToken.getType());
		assertEquals(expectedElements[0].asText(), actualElements[0].asText());
		assertEquals(expectedElements[1].asText(), actualElements[1].asText());
		assertEquals(expectedElements[2].asText(), actualElements[2].asText());
		assertEquals(expectedElements[3].asText(), actualElements[3].asText());
		
		lexer.setState(SmartScriptLexerState.TEXT);
		
		checkToken(lexer.nextToken(), correctData[2]);
	}
	
	@Test
	public void testForTagVariableNegativeIntNegativeDoubleNull() {
		String document = loader("document5.txt");
		SmartScriptLexer lexer = new SmartScriptLexer(document);
		
		Element[] expectedElements = {
				new ElementVariable("var"),
				new ElementConstantInteger(-150),
				new ElementConstantDouble(-104.678),
		};
		
		Token[] correctData = {
				new Token(TokenType.TEXT, "{$FOR"),
				new Token(TokenType.TAG_FOR, expectedElements),
				new Token(TokenType.TEXT, "$}")
		};
		
		checkToken(lexer.nextToken(), correctData[0]);
		
		lexer.setState(SmartScriptLexerState.NON_EMPTY_TAG);
		
		Token actualToken = lexer.nextToken();
		Token expectedToken = correctData[1];
		
		Element[] actualElements = (Element[])actualToken.getValue();
				
		assertEquals(expectedToken.getType(), actualToken.getType());
		assertEquals(expectedElements[0].asText(), actualElements[0].asText());
		assertEquals(expectedElements[1].asText(), actualElements[1].asText());
		assertEquals(Double.parseDouble(expectedElements[2].asText()), 
				     Double.parseDouble(actualElements[2].asText()), DELTA);
		assertNull(actualElements[3]);
		
		lexer.setState(SmartScriptLexerState.TEXT);
		
		checkToken(lexer.nextToken(), correctData[2]);
	}
	
	@Test
	public void testForTagLongNamedVariableNegDoubleNegDoubleDouble() {
		String document = loader("document6.txt");
		SmartScriptLexer lexer = new SmartScriptLexer(document);
		
		Element[] expectedElements = {
				new ElementVariable("VAR_123_var1_2_3var"),
				new ElementConstantDouble(-15.755555),
				new ElementConstantDouble(-104.6789999),
				new ElementConstantDouble(0.99)
		};
		
		Token[] correctData = {
				new Token(TokenType.TEXT, "{$FOR"),
				new Token(TokenType.TAG_FOR, expectedElements),
				new Token(TokenType.TEXT, "$}")
		};
		
		checkToken(lexer.nextToken(), correctData[0]);
		
		lexer.setState(SmartScriptLexerState.NON_EMPTY_TAG);
		
		Token actualToken = lexer.nextToken();
		Token expectedToken = correctData[1];
		
		Element[] actualElements = (Element[])actualToken.getValue();
				
		assertEquals(expectedToken.getType(), actualToken.getType());
		assertEquals(expectedElements[0].asText(), actualElements[0].asText());
		assertEquals(Double.parseDouble(expectedElements[1].asText()), 
			     Double.parseDouble(actualElements[1].asText()), DELTA);
		assertEquals(Double.parseDouble(expectedElements[2].asText()), 
				     Double.parseDouble(actualElements[2].asText()), DELTA);
		assertEquals(Double.parseDouble(expectedElements[3].asText()), 
			     Double.parseDouble(actualElements[3].asText()), DELTA);
		
		lexer.setState(SmartScriptLexerState.TEXT);
		
		checkToken(lexer.nextToken(), correctData[2]);
	}
	
	@Test
	public void testForTagVariableStringIntegerString() {
		String document = loader("document7.txt");
		SmartScriptLexer lexer = new SmartScriptLexer(document);
		
		Element[] expectedElements = {
				new ElementVariable("sco_re"),
				new ElementString("-1"),
				new ElementConstantInteger(10),
				new ElementString("1")
		};
		
		Token[] correctData = {
				new Token(TokenType.TEXT, "{$FOR"),
				new Token(TokenType.TAG_FOR, expectedElements),
				new Token(TokenType.TEXT, "$}")
		};
		
		checkToken(lexer.nextToken(), correctData[0]);
		
		lexer.setState(SmartScriptLexerState.NON_EMPTY_TAG);
		
		Token actualToken = lexer.nextToken();
		Token expectedToken = correctData[1];
		
		Element[] actualElements = (Element[])actualToken.getValue();
		
		assertEquals(expectedToken.getType(), actualToken.getType());
		assertEquals(expectedElements[0].asText(), actualElements[0].asText());
		assertEquals(expectedElements[1].asText(), actualElements[1].asText());
		assertEquals(expectedElements[2].asText(), actualElements[2].asText());
		assertEquals(expectedElements[3].asText(), actualElements[3].asText());
		
		lexer.setState(SmartScriptLexerState.TEXT);
		
		checkToken(lexer.nextToken(), correctData[2]);
	}
	
	@Test
	public void testForTagVariableStringIntegerString2() {
		String document = loader("document8.txt");
		SmartScriptLexer lexer = new SmartScriptLexer(document);
		
		Element[] expectedElements = {
				new ElementVariable("sco_re"),
				new ElementString("-1500.550"),
				new ElementConstantInteger(10),
				new ElementString("0.006")
		};
		
		Token[] correctData = {
				new Token(TokenType.TEXT, "{$FOR"),
				new Token(TokenType.TAG_FOR, expectedElements),
				new Token(TokenType.TEXT, "$}")
		};
		
		checkToken(lexer.nextToken(), correctData[0]);
		
		lexer.setState(SmartScriptLexerState.NON_EMPTY_TAG);
		
		Token actualToken = lexer.nextToken();
		Token expectedToken = correctData[1];
		
		Element[] actualElements = (Element[])actualToken.getValue();
				
		assertEquals(expectedToken.getType(), actualToken.getType());
		assertEquals(expectedElements[0].asText(), actualElements[0].asText());
		assertEquals(expectedElements[1].asText(), actualElements[1].asText());
		assertEquals(expectedElements[2].asText(), actualElements[2].asText());
		assertEquals(expectedElements[3].asText(), actualElements[3].asText());
		
		lexer.setState(SmartScriptLexerState.TEXT);
		
		checkToken(lexer.nextToken(), correctData[2]);
	}
	
	@Test
	public void testForTagVariableStringStringNull() {
		String document = loader("document9.txt");
		SmartScriptLexer lexer = new SmartScriptLexer(document);
		
		Element[] expectedElements = {
				new ElementVariable("sco_re"),
				new ElementString("-1500.550"),
				new ElementString("0.006")
		};
		
		Token[] correctData = {
				new Token(TokenType.TEXT, "{$FOR"),
				new Token(TokenType.TAG_FOR, expectedElements),
				new Token(TokenType.TEXT, "$}")
		};
		
		checkToken(lexer.nextToken(), correctData[0]);
		
		lexer.setState(SmartScriptLexerState.NON_EMPTY_TAG);
		
		Token actualToken = lexer.nextToken();
		Token expectedToken = correctData[1];
		
		Element[] actualElements = (Element[])actualToken.getValue();
				
		assertEquals(expectedToken.getType(), actualToken.getType());
		assertEquals(expectedElements[0].asText(), actualElements[0].asText());
		assertEquals(expectedElements[1].asText(), actualElements[1].asText());
		assertEquals(expectedElements[2].asText(), actualElements[2].asText());
		assertNull(actualElements[3]);
		
		lexer.setState(SmartScriptLexerState.TEXT);
		
		checkToken(lexer.nextToken(), correctData[2]);
	}
	
	@Test
	public void testForTagVariableIntVariable() {
		String document = loader("document10.txt");
		SmartScriptLexer lexer = new SmartScriptLexer(document);
		
		Element[] expectedElements = {
				new ElementVariable("year"),
				new ElementConstantInteger(1),
				new ElementVariable("last_year")
		};
		
		Token[] correctData = {
				new Token(TokenType.TEXT, "{$FOR"),
				new Token(TokenType.TAG_FOR, expectedElements),
				new Token(TokenType.TEXT, "$}")
		};
		
		checkToken(lexer.nextToken(), correctData[0]);
		
		lexer.setState(SmartScriptLexerState.NON_EMPTY_TAG);
		
		Token actualToken = lexer.nextToken();
		Token expectedToken = correctData[1];
		
		Element[] actualElements = (Element[])actualToken.getValue();
				
		assertEquals(expectedToken.getType(), actualToken.getType());
		assertEquals(expectedElements[0].asText(), actualElements[0].asText());
		assertEquals(expectedElements[1].asText(), actualElements[1].asText());
		assertEquals(expectedElements[2].asText(), actualElements[2].asText());
		assertNull(actualElements[3]);
		
		lexer.setState(SmartScriptLexerState.TEXT);
		
		checkToken(lexer.nextToken(), correctData[2]);
	}
	
	@Test(expected=SmartScriptLexerException.class)
	public void testForTagNotVariableAtStart() {
		String document = loader("document11.txt");
		SmartScriptLexer lexer = new SmartScriptLexer(document);
		
		checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "{$FOR"));
		
		lexer.setState(SmartScriptLexerState.NON_EMPTY_TAG);
		
		@SuppressWarnings("unused")
		Token token = lexer.nextToken();
	}
	
	@Test(expected=SmartScriptLexerException.class)
	public void testForTagNotVariableAtStart2() {
		String document = loader("document12.txt");
		SmartScriptLexer lexer = new SmartScriptLexer(document);
		
		checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "{$FOR"));
		
		lexer.setState(SmartScriptLexerState.NON_EMPTY_TAG);
		
		@SuppressWarnings("unused")
		Token token = lexer.nextToken();
	}
	
	@Test(expected=SmartScriptLexerException.class)
	public void testForTagFunctionElementInLoop() {
		String document = loader("document13.txt");
		SmartScriptLexer lexer = new SmartScriptLexer(document);
		
		checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "{$FOR"));
		
		lexer.setState(SmartScriptLexerState.NON_EMPTY_TAG);
		
		@SuppressWarnings("unused")
		Token token = lexer.nextToken();
	}
	
	@Test(expected=SmartScriptLexerException.class)
	public void testForTagTooManyArguments() {
		String document = loader("document14.txt");
		SmartScriptLexer lexer = new SmartScriptLexer(document);
		
		checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "{$FOR"));
		
		lexer.setState(SmartScriptLexerState.NON_EMPTY_TAG);
		
		@SuppressWarnings("unused")
		Token token = lexer.nextToken();
	}
	
	@Test(expected=SmartScriptLexerException.class)
	public void testForTagTooFewArguments() {
		String document = loader("document15.txt");
		SmartScriptLexer lexer = new SmartScriptLexer(document);
		
		checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "{$FOR"));
		
		lexer.setState(SmartScriptLexerState.NON_EMPTY_TAG);
		
		@SuppressWarnings("unused")
		Token token = lexer.nextToken();
	}
	
	@Test(expected=SmartScriptLexerException.class)
	public void testForTagTooManyArguments2() {
		String document = loader("document16.txt");
		SmartScriptLexer lexer = new SmartScriptLexer(document);
		
		checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "{$FOR"));
		
		lexer.setState(SmartScriptLexerState.NON_EMPTY_TAG);
		
		@SuppressWarnings("unused")
		Token token = lexer.nextToken();
	}

	
	private void checkToken(Token actual, Token expected) {
		String msg = "Token are not equal.";
		assertEquals(msg, expected.getType(), actual.getType());
		assertEquals(msg, expected.getValue(), actual.getValue());
	}
	
	private String loader(String filename) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try(java.io.InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename)) {
			byte[] buffer = new byte[1024];
			
			while(true) {
					int read = is.read(buffer);
						if(read<1) break;
							bos.write(buffer, 0, read);
			}
			return new String(bos.toByteArray(), StandardCharsets.UTF_8);
		} catch(IOException ex) {
			return null;
		}
		
	}

}
