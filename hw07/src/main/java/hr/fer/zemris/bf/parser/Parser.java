package hr.fer.zemris.bf.parser;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.bf.lexer.Lexer;
import hr.fer.zemris.bf.lexer.LexerException;
import hr.fer.zemris.bf.lexer.Token;
import hr.fer.zemris.bf.lexer.TokenType;
import hr.fer.zemris.bf.model.BinaryOperatorNode;
import hr.fer.zemris.bf.model.ConstantNode;
import hr.fer.zemris.bf.model.Node;
import hr.fer.zemris.bf.model.UnaryOperatorNode;
import hr.fer.zemris.bf.model.VariableNode;

/**
 * Parser in constructor gets string expression which represents boolean expression. It creates instance of
 * class {@link Lexer} and uses it to get tokens from boolean expression. From accepted tokens Parser 
 * creates tree.
 * Parser is recursive descent parser which works with grammar:
 * S -> E1
 * E1 -> E2 (OR E2)*
 * E2 -> E3 (XOR E3)*
 * E3 -> E4 (AND E4)*
 * E4 -> NOT E4 | E5
 * E5 -> VAR | KONST | '(' E1 ')'
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class Parser {
	
	/**
	 * Instance of {@link Lexer} which is used to get tokens from boolean expression.
	 */
	private Lexer lexer;
	
	/**
	 * Base node of tree created by parser.
	 */
	private Node expressionNode;
	
	/**
	 * Last token returned by lexer.
	 */
	private Token currentToken;
	
	/**
	 * Constructor which accepts one argument, string expression which represents boolean expression.
	 * Instance of {@link Lexer} is created with expression as argument and then method parse is called.
	 * 
	 * @param expression accepted boolean expression
	 * @throws ParserException if accepted expression is null
	 */
	public Parser(String expression) {
		if(expression == null) {
			throw new ParserException("Expression can not be null.");
		}
		
		lexer = new Lexer(expression);
		
		try {
			expressionNode = parse();
		} catch(LexerException exc) {
			throw new ParserException(exc.getMessage());
		}
		
	}

	/**
	 * Getter for the expression node.
	 * 
	 * @return expression node
	 */
	public Node getExpression() {
		return expressionNode;
	}

	/**
	 * Method parse the accepted boolean expression and creates tree.
	 * 
	 * @return base node of the created tree
	 */
	private Node parse() {
		currentToken = lexer.nextToken();
		
		Node node =  S();
		
		if(currentToken.getTokenType() != TokenType.EOF) {
			throw new ParserException("Expression is not valid");
		}
		
		return node;
	}
	
	/**
	 * Method for nonterminal symbol S of the grammar.
	 * 
	 * @return next node of the tree
	 */
	private Node S() {
		return E1();
	}
	
	/**
	 * Method for nonterminal symbol E1 of the grammar.
	 * 
	 * @return next node of the tree
	 * @throws ParserException if the last token of boolean expression is operator or
	 */
	private Node E1() {
		List<Node> children = new ArrayList<>();
		Node node = null;
		
		while(currentToken.getTokenType() != TokenType.EOF &&
			  currentToken.getTokenType() != TokenType.CLOSED_BRACKET) {
		
			if(currentToken.getTokenType() == TokenType.OPERATOR &&
			   currentToken.getTokenValue().equals("or")) {
				
				currentToken = lexer.nextToken();
				if(currentToken.getTokenType() == TokenType.EOF) {
					throw new ParserException("Unexpected token: " + currentToken);
				}
				
				children.add(E2());
				
			} 
			else if(currentToken.getTokenType() == TokenType.OPERATOR &&
					  !currentToken.getTokenValue().equals("not")) {
				break;
			} 
			else {
				node = E2();
				
				if(currentToken.getTokenType() == TokenType.OPERATOR &&
				   currentToken.getTokenValue().equals("or")) {
					
					children.add(node);
				}
			}
		}
		
		if(!children.isEmpty()) {
			if(!children.contains(node)) {
				children.add(node);
			}
			BinaryOperatorNode binaryOperatorNodeOR = new BinaryOperatorNode("or", children, (t, u) -> (t || u));
			
			return binaryOperatorNodeOR;
		}

		return node;
	}
	
	/**
	 * Method for nonterminal symbol E2 of the grammar.
	 * 
	 * @return next node of the tree
	 * @throws ParserException if the last token of boolean expression is operator xor
	 */
	private Node E2() {
		List<Node> children = new ArrayList<>();
		Node node = null;
		
		while(currentToken.getTokenType() != TokenType.EOF &&
			  currentToken.getTokenType() != TokenType.CLOSED_BRACKET) {
			
			if(currentToken.getTokenType() == TokenType.OPERATOR &&
			   currentToken.getTokenValue().equals("xor")) {
				
			   currentToken = lexer.nextToken();
			   if(currentToken.getTokenType() == TokenType.EOF) {
					throw new ParserException("Unexpected token: " + currentToken);
			   }
			
			   children.add(E3());

			} else if(currentToken.getTokenType() == TokenType.OPERATOR &&
					  !currentToken.getTokenValue().equals("not")) {
				break;
			} else {
				node = E3();
				
				if(currentToken.getTokenType() == TokenType.OPERATOR &&
				   currentToken.getTokenValue().equals("xor")) {
						children.add(node);
				}
			}
		}
		
		if(!children.isEmpty()) {
			if(!children.contains(node)) {
				children.add(node);
			}
			BinaryOperatorNode binaryOperatorNodeXOR = new BinaryOperatorNode("xor", children, (t, u) -> (t ^ u));
			
			return binaryOperatorNodeXOR;
		}

		return node;
	}
	
	/**
	 * Method for nonterminal symbol E3 of the grammar.
	 * 
	 * @return next node of the tree
	 * @throws ParserException if the last token of boolean expression is operator and
	 */
	private Node E3() {
		List<Node> children = new ArrayList<>();
		Node node = null;
		
		while(currentToken.getTokenType() != TokenType.EOF &&
			  currentToken.getTokenType() != TokenType.CLOSED_BRACKET) {
			
			if(currentToken.getTokenType() == TokenType.OPERATOR &&
			   currentToken.getTokenValue().equals("and")) {
				
				currentToken = lexer.nextToken();
				if(currentToken.getTokenType() == TokenType.EOF) {
					throw new ParserException("Unexpected token: " + currentToken);
				}
			
				children.add(E4());
			
			} else if(currentToken.getTokenType() == TokenType.OPERATOR &&
					  !currentToken.getTokenValue().equals("not")) {
				break;
			} else {
				node = E4();
				
				if(currentToken.getTokenType() == TokenType.OPERATOR &&
				   currentToken.getTokenValue().equals("and")) {
						children.add(node);
				}
			}
		}
		
		if(!children.isEmpty()) {
			if(!children.contains(node)) {
				children.add(node);
			}
			BinaryOperatorNode binaryOperatorNodeAND = new BinaryOperatorNode("and", children, (t, u) -> (t && u));
			
			return binaryOperatorNodeAND;
		}

		return node;
	}
	
	/**
	 * Method for nonterminal symbol E4 of the grammar.
	 * 
	 * @return next node of the tree
	 */
	private Node E4() {
		if(currentToken.getTokenValue().equals("not")) {
			currentToken = lexer.nextToken();
			
			UnaryOperatorNode unaryOperatorNodeNot = new UnaryOperatorNode("not", E4(),  t -> !t);
			
			return unaryOperatorNodeNot;
		}
		
		Node node = E5();
		return node;
	}
	
	/**
	 * Method for nonterminal symbol E5 of the grammar.
	 * 
	 * @return next node of the tree
	 * @throws ParserException if boolean expression is not valid
	 */
	private Node E5() {
		switch (currentToken.getTokenType()) {
			case OPEN_BRACKET :	
				currentToken = lexer.nextToken();
				if((currentToken.getTokenType() == TokenType.OPERATOR && !currentToken.getTokenValue().equals("not")) ||
				   (currentToken.getTokenType() == TokenType.CLOSED_BRACKET)) {
					 throw new ParserException("Unexpected token: " + currentToken);
				}
				
				Node node = E1();
				
				if(currentToken.getTokenType() != TokenType.CLOSED_BRACKET) {
					throw new ParserException("Unexpected token: " + currentToken);
				}
				
				currentToken = lexer.nextToken();
				
				if(currentToken.getTokenType() == TokenType.OPERATOR &&
				   currentToken.getTokenValue().equals("not")) {
					throw new ParserException("Unexpected token: " + currentToken);
				}
				
				if(currentToken.getTokenType() == TokenType.VARIABLE || 
					currentToken.getTokenType() == TokenType.CONSTANT ||
				    currentToken.getTokenType() == TokenType.OPEN_BRACKET) {
						throw new ParserException("Unexpected token: " + currentToken);
				}
				
				return node;
				
			case VARIABLE :
				VariableNode variableNode = new VariableNode((String) currentToken.getTokenValue());
				currentToken = lexer.nextToken();
				
				if(currentToken.getTokenType() == TokenType.OPERATOR &&
				   currentToken.getTokenValue().equals("not")) {
					throw new ParserException("Unexpected token: " + currentToken);
				}
				
				if(currentToken.getTokenType() == TokenType.VARIABLE || 
				   currentToken.getTokenType() == TokenType.CONSTANT ||
				   currentToken.getTokenType() == TokenType.OPEN_BRACKET) {
					throw new ParserException("Unexpected token: " + currentToken);
				}
					
				return variableNode;
				
			case CONSTANT :
				ConstantNode constantNode = new ConstantNode((boolean) currentToken.getTokenValue());
				currentToken = lexer.nextToken();
				
				if(currentToken.getTokenType() == TokenType.OPERATOR &&
				   currentToken.getTokenValue().equals("not")) {
						throw new ParserException("Unexpected token: " + currentToken);
				}
				
				if(currentToken.getTokenType() == TokenType.VARIABLE || 
				   currentToken.getTokenType() == TokenType.CONSTANT ||
				   currentToken.getTokenType() == TokenType.OPEN_BRACKET) {
					throw new ParserException("Unexpected token: " + currentToken);
				}
				
				return constantNode;
			
			default:
				throw new ParserException("Expression is not valid");
		}
	}
}
