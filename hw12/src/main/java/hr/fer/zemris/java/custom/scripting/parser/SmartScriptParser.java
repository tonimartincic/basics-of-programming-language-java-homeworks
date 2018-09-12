package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexerException;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexerState;
import hr.fer.zemris.java.custom.scripting.lexer.Token;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

/**
 * SmartScriptParser in constructor gets text which it parse into document model.
 * Document model is tree which base Node is DocumentNode. SmartScriptParser uses
 * SmartScriptLexer for getting the Nodes from text.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */

public class SmartScriptParser {
	
	/**
	 * SmartScriptLexer which returns Tokens from given text.
	 */
	private SmartScriptLexer lexer;
	
	/**
	 * Base Node of the document model.
	 */
	private DocumentNode documentNode;
	
	/**
	 * Instance of Object stack which is used for creating document model.
	 */
	private ObjectStack objectStack;
	
	/**
	 * Constructor which accepts documentBody.
	 * 
	 * @param documentBody text that is parsed
	 */
	public SmartScriptParser(String documentBody) {
		try {
			lexer = new SmartScriptLexer(documentBody);
			objectStack = new ObjectStack();
			documentNode = new DocumentNode();
		} catch(IllegalArgumentException exc) {
			throw new SmartScriptParserException("Document body must not be null");
		}
		
		parse();
	}
	
	/**
	 * Getter for the documentNode.
	 * 
	 * @return documentNode
	 */
	public DocumentNode getDocumentNode() {
		return documentNode;
	}
	
	/**
	 * Method parse the documentBody to the document model which is tree and its base Node is
	 * documentNode.
	 */
	private void parse() {
		objectStack.push(documentNode);
		
		Token token = null;
		
		int numberOfForTags = 0;
		int numberOfEndTags = 0;
	
		do {
			try {
				token = lexer.nextToken();
			} catch(SmartScriptLexerException exc) {
				throw new SmartScriptParserException(exc.getMessage());
			}
			
			if(token.getType() == TokenType.EOF) {
				if(numberOfForTags != numberOfEndTags) {
					throw new SmartScriptParserException(
							"Number of for tags must be equal to number of end tags.");
				}
				
				break;
			}
			
			if(token.getType() == TokenType.ECHO) {
				EchoNode echoNode = new EchoNode((Element[])token.getValue());
				
				addToTheLastNode(echoNode);
				
				lexer.setState(SmartScriptLexerState.TEXT);
			}
			
			if(token.getType() == TokenType.TEXT) {
				String text = (String)token.getValue();
				
				switch(text) {
					case "{$FOR" :
						numberOfForTags++;
						
						lexer.setState(SmartScriptLexerState.NON_EMPTY_TAG);
						break;
						
					case "{$=" :
						lexer.setState(SmartScriptLexerState.EMPTY_TAG);
						break;
						
					case "$}" :
						break;
						
					case "{$END" :
						numberOfEndTags++;
						
						lexer.setState(SmartScriptLexerState.NON_EMPTY_TAG);
						break;
						
					default:
						TextNode textNode = new TextNode((String)token.getValue());
						addToTheLastNode(textNode);
						break;
				}
				
			}
			
			if(token.getType() == TokenType.TAG_FOR) {
				Element[] elements = (Element[])token.getValue();
				ForLoopNode forLoopNode = new ForLoopNode((ElementVariable)elements[0], 
														   				   elements[1], 
														   				   elements[2], 
														   				   elements[3]);
				
				addToTheLastNode(forLoopNode);
				objectStack.push(forLoopNode);
				
				lexer.setState(SmartScriptLexerState.TEXT);
			}
			
			if(token.getType() == TokenType.TAG_END) {
				objectStack.pop();
				
				if(objectStack.isEmpty()) {
					throw new SmartScriptParserException("Error in document it contains more {$END$}-s than opened non-empty tags");
				}
				
				lexer.setState(SmartScriptLexerState.TEXT);
			}
			
		} while(true);
	}
	
	/**
	 * Method adds Node to the last Node on the objectStack.
	 * 
	 * @param node node which is added
	 */
	private void addToTheLastNode(Node node) {
		Node lastNodeOnStack = (Node)objectStack.peek();
		lastNodeOnStack.addChildNode(node);
	}

}
