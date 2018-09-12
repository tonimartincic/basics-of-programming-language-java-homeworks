package hr.fer.zemris.java.custom.scripting.parser;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

@SuppressWarnings("javadoc")
public class SmartScriptParserTest {

	@Test
	public void testNotNull() {
		SmartScriptParser parser = new SmartScriptParser("");
		DocumentNode documentNode = parser.getDocumentNode();
		
		assertNotNull("Token was expected but null was returned.", documentNode);
	}
	
	@Test(expected=SmartScriptParserException.class)
	public void testNullInput() {
		new SmartScriptParser(null);
	}
	
	@Test
	public void testParseOnlyEcho() {
		String document = loader("document17.txt");
		SmartScriptParser parser = new SmartScriptParser(document);
		DocumentNode documentNode = parser.getDocumentNode();
		
		assertEquals(0, documentNode.numberOfChildren());
	}
	
	@Test
	public void testParseStringWithBackslash() {
		String document = loader("document33.txt");
		SmartScriptParser parser = new SmartScriptParser(document);
		DocumentNode documentNode = parser.getDocumentNode();
		
		assertEquals(1, documentNode.numberOfChildren());
		
		TextNode textNode = (TextNode) documentNode.getChild(0);
		assertEquals("Some \\ test X", textNode.getText());
		
	}
	
	@Test
	public void testParseOneLineTextAndOnlyFisrtLineOfForLoop() {
		String document = loader("document29.txt");
		SmartScriptParser parser = new SmartScriptParser(document);
		DocumentNode documentNode = parser.getDocumentNode();
		
		assertEquals(2, documentNode.numberOfChildren());
		assertEquals(0, documentNode.getChild(0).numberOfChildren());
		assertEquals(0, documentNode.getChild(1).numberOfChildren());
		
		TextNode textNode = (TextNode) documentNode.getChild(0);
		ForLoopNode forLoopNode = (ForLoopNode) documentNode.getChild(1);
		
		ElementVariable elementVariable = forLoopNode.getVariable();
		Element element1 = forLoopNode.getStartExpression();
		Element element2 = forLoopNode.getEndExpression();
		Element element3 = forLoopNode.getStepExpression();
		
		assertEquals("This is sample text.\r\n", textNode.getText());
		assertEquals("i", elementVariable.asText());
		assertEquals("1", element1.asText());
		assertEquals("10", element2.asText());
		assertEquals("1", element3.asText());
	}
	
	@Test
	public void testParseOneLineTextAndForLoop() {
		String document = loader("document30.txt");
		SmartScriptParser parser = new SmartScriptParser(document);
		DocumentNode documentNode = parser.getDocumentNode();
		
		assertEquals(2, documentNode.numberOfChildren());
		assertEquals(0, documentNode.getChild(0).numberOfChildren());
		assertEquals(3, documentNode.getChild(1).numberOfChildren());
		
		TextNode textNode1 = (TextNode) documentNode.getChild(0);
		ForLoopNode forLoopNode = (ForLoopNode) documentNode.getChild(1);
		
		ElementVariable elementVariable = forLoopNode.getVariable();
		Element element1 = forLoopNode.getStartExpression();
		Element element2 = forLoopNode.getEndExpression();
		Element element3 = forLoopNode.getStepExpression();
		
		assertEquals("This is sample text.\r\n", textNode1.getText());
		assertEquals("i", elementVariable.asText());
		assertEquals("1", element1.asText());
		assertEquals("10", element2.asText());
		assertEquals("1", element3.asText());
		
		TextNode textNode2 = (TextNode) forLoopNode.getChild(0);
		
		assertEquals("\r\n This is ", textNode2.getText());
		
		EchoNode echoNode = (EchoNode) forLoopNode.getChild(1);
		
		assertEquals("i", echoNode.getElements()[0].asText());
		
		TextNode textNode3 = (TextNode) forLoopNode.getChild(2);
		
		assertEquals("-th time this message is generated.\r\n", textNode3.getText());
		
	}
	
	@Test
	public void testParseFullExampleFromHomework() {
		String document = loader("document26.txt");
		SmartScriptParser parser = new SmartScriptParser(document);
		DocumentNode documentNode = parser.getDocumentNode();
		
		assertEquals(4, documentNode.numberOfChildren());
		
		TextNode textNode1 = (TextNode) documentNode.getChild(0);
		ForLoopNode forLoopNode = (ForLoopNode) documentNode.getChild(1);
		
		assertEquals(0, textNode1.numberOfChildren());
		assertEquals(3, forLoopNode.numberOfChildren());
		
		ElementVariable elementVariable = forLoopNode.getVariable();
		Element element1 = forLoopNode.getStartExpression();
		Element element2 = forLoopNode.getEndExpression();
		Element element3 = forLoopNode.getStepExpression();
		
		assertEquals("This is sample text.\r\n", textNode1.getText());
		assertEquals("i", elementVariable.asText());
		assertEquals("1", element1.asText());
		assertEquals("10", element2.asText());
		assertEquals("1", element3.asText());
		
		TextNode textNode2 = (TextNode) forLoopNode.getChild(0);
		
		assertEquals("\r\n This is ", textNode2.getText());
		
		EchoNode echoNode = (EchoNode) forLoopNode.getChild(1);
		
		assertEquals("i", echoNode.getElements()[0].asText());
		
		TextNode textNode3 = (TextNode) forLoopNode.getChild(2);
		
		assertEquals("-th time this message is generated.\r\n", textNode3.getText());
		
		TextNode textNode4 = (TextNode) documentNode.getChild(2);
		
		assertEquals("\r\n", textNode4.getText());
		
		ForLoopNode forLoopNode2 = (ForLoopNode) documentNode.getChild(3);
		
		assertEquals(5, forLoopNode2.numberOfChildren());
		
		elementVariable = forLoopNode2.getVariable();
		element1 = forLoopNode2.getStartExpression();
		element2 = forLoopNode2.getEndExpression();
		element3 = forLoopNode2.getStepExpression();
		
		assertEquals("i", elementVariable.asText());
		assertEquals("0", element1.asText());
		assertEquals("10", element2.asText());
		assertEquals("2", element3.asText());
		
		TextNode textNode5 = (TextNode) forLoopNode2.getChild(0);
		
		assertEquals("\r\n sin(", textNode5.getText());
		
		EchoNode echoNode2 = (EchoNode) forLoopNode2.getChild(1);
		
		assertEquals("i", echoNode2.getElements()[0].asText());
		
		TextNode textNode6 = (TextNode) forLoopNode2.getChild(2);
		
		assertEquals("^2) = ", textNode6.getText());
		
		EchoNode echoNode3 = (EchoNode) forLoopNode2.getChild(3);
		
		assertEquals("i", echoNode3.getElements()[0].asText());
		assertEquals("i", echoNode3.getElements()[1].asText());
		assertEquals("*", echoNode3.getElements()[2].asText());
		assertEquals("sin", echoNode3.getElements()[3].asText());
		assertEquals("0.000", echoNode3.getElements()[4].asText());
		assertEquals("decfmt", echoNode3.getElements()[5].asText());
		
		TextNode textNode7 = (TextNode) forLoopNode2.getChild(4);
		
		assertEquals("\r\n", textNode7.getText());
		
	}
	
	@Test
	public void testParseOnlyForTag() {
		String document = loader("document2.txt");
		SmartScriptParser parser = new SmartScriptParser(document);
		DocumentNode documentNode = parser.getDocumentNode();
		
		assertEquals(0, documentNode.numberOfChildren());
	}
	
	@Test
	public void testParseForTagVariableIntIntInt() {
		String document = loader("document1.txt");
		SmartScriptParser parser = new SmartScriptParser(document);
		DocumentNode documentNode = parser.getDocumentNode();
		
		assertEquals(1, documentNode.numberOfChildren());
		
		assertEquals(0, documentNode.getChild(0).numberOfChildren());
	}
	
	@Test
	public void testParseEchoTagTwoVariablesOperatorFunctionStringFunction() {
		String document = loader("document20.txt");
		SmartScriptParser parser = new SmartScriptParser(document);
		DocumentNode documentNode = parser.getDocumentNode();
		
		assertEquals(1, documentNode.numberOfChildren());
		
		assertEquals(0, documentNode.getChild(0).numberOfChildren());
	}
	
	@Test
	public void testParseTwoIdenticalTrees() {
		String docBody = loader("document26.txt");
		SmartScriptParser parser = new SmartScriptParser(docBody);
		
		DocumentNode document = parser.getDocumentNode();
		
		String originalDocumentBody = DocumentNode.createOriginalDocumentBody(document);
		
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		
		DocumentNode document2 = parser2.getDocumentNode();
		// now document and document2 should be structurally identical trees
		
		assertEquals(document.numberOfChildren(), document2.numberOfChildren());
		
		for(int i = 0; i < document.numberOfChildren(); i++) {
			assertEquals(document.getChild(i).numberOfChildren(),
					     document2.getChild(i).numberOfChildren());
		}
	}
	
	@Test
	public void testParseDocumentStringAndTag() {
		String docBody = loader("document31.txt");
		
		SmartScriptParser parser = new SmartScriptParser(docBody);
		
		DocumentNode document = parser.getDocumentNode();
		
		String originalDocumentBody = DocumentNode.createOriginalDocumentBody(document);
		
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		
		DocumentNode document2 = parser2.getDocumentNode();
		// now document and document2 should be structurally identical trees
		
		assertEquals(document.numberOfChildren(), document2.numberOfChildren());
		
		for(int i = 0; i < document.numberOfChildren(); i++) {
			assertEquals(document.getChild(i).numberOfChildren(),
					     document2.getChild(i).numberOfChildren());
		}
	}
	
	@Test
	public void testParseDocumentStringAndTag2() {
		String docBody = loader("document32.txt");
		SmartScriptParser parser = new SmartScriptParser(docBody);
		
		DocumentNode document = parser.getDocumentNode();
		
		String originalDocumentBody = DocumentNode.createOriginalDocumentBody(document);
		
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		
		DocumentNode document2 = parser2.getDocumentNode();
		// now document and document2 should be structurally identical trees
		
		assertEquals(document.numberOfChildren(), document2.numberOfChildren());
		
		for(int i = 0; i < document.numberOfChildren(); i++) {
			assertEquals(document.getChild(i).numberOfChildren(),
					     document2.getChild(i).numberOfChildren());
		}
	}
	
	@Test
	public void testParseLongExample() {
		String docBody = loader("document35.txt");
		SmartScriptParser parser = new SmartScriptParser(docBody);
		
		DocumentNode document = parser.getDocumentNode();
		
		String originalDocumentBody = DocumentNode.createOriginalDocumentBody(document);
		
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		
		DocumentNode document2 = parser2.getDocumentNode();
		// now document and document2 should be structurally identical trees
		
		assertEquals(document.numberOfChildren(), document2.numberOfChildren());
		
		for(int i = 0; i < document.numberOfChildren(); i++) {
			assertEquals(document.getChild(i).numberOfChildren(),
					     document2.getChild(i).numberOfChildren());
		}
		
	}
	
	@Test
	public void testParseForLoopInForLoop() {
		String docBody = loader("document36.txt");
		SmartScriptParser parser = new SmartScriptParser(docBody);
		
		DocumentNode document = parser.getDocumentNode();
		
		String originalDocumentBody = DocumentNode.createOriginalDocumentBody(document);
		
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		
		DocumentNode document2 = parser2.getDocumentNode();
		// now document and document2 should be structurally identical trees
		
		assertEquals(document.numberOfChildren(), document2.numberOfChildren());
		
		for(int i = 0; i < document.numberOfChildren(); i++) {
			assertEquals(document.getChild(i).numberOfChildren(),
					     document2.getChild(i).numberOfChildren());
		}
		
	}
	
	@Test(expected=SmartScriptParserException.class)
	public void testParseTooMuchEndTags() {
		String docBody = loader("document39.txt");
		@SuppressWarnings("unused")
		SmartScriptParser parser = new SmartScriptParser(docBody);
	}
	
	
	@Test
	public void testParseForLoopInForLoop2() {
		String docBody = loader("document37.txt");
		SmartScriptParser parser = new SmartScriptParser(docBody);
		
		DocumentNode document = parser.getDocumentNode();
		
		String originalDocumentBody = DocumentNode.createOriginalDocumentBody(document);
		
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		
		DocumentNode document2 = parser2.getDocumentNode();
		// now document and document2 should be structurally identical trees
		
		assertEquals(document.numberOfChildren(), document2.numberOfChildren());
		
		for(int i = 0; i < document.numberOfChildren(); i++) {
			assertEquals(document.getChild(i).numberOfChildren(),
					     document2.getChild(i).numberOfChildren());
		}
		
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
