package hr.fer.zemris.java.custom.scripting.lexer;

import java.util.Arrays;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * Class represents lexical analyser. It returns Tokens from accepted text.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class SmartScriptLexer {
	
	/**
	 * Constant which represents max number of elements in for loop.
	 */
	private static final int NUM_OF_ELEMENTS_IN_FOR = 4;
	
	/**
	 * Char Array of text which is being analysed.
	 */
	private char[] data;
	
	/**
	 * Current index of data.
	 */
	private int currentIndex;
	
	/**
	 * Last read Token.
	 */
	private Token token;
	
	/**
	 * State of the Lexer.
	 */
	private SmartScriptLexerState state;
	
	/**
	 * Constructor which accepts one argument, text which is analysed.
	 * 
	 * @param text text which is analysed
	 * @throws IllegalArgumentException if argument text is null
	 */
	public SmartScriptLexer(String text) {
		if(text == null) {
			throw new IllegalArgumentException("Argument text must not be null value.");
		}
		
		data = text.toCharArray();
		currentIndex = 0;
		state = SmartScriptLexerState.TEXT;
	}
	
	/**
	 * Getter for the token
	 * 
	 * @return token
	 */
	public Token getToken() {
		return token;
	}
	
	/**
	 * Method returns next Token from text.
	 * 
	 * @return next Token from text
	 */
	public Token nextToken() {
		if(token != null && token.getType() == TokenType.EOF) {
			throw new SmartScriptLexerException("No tokens available.");
		}
		
		if(currentIndex >= data.length) {
			return token = new Token(TokenType.EOF, null);
		}
		
		if(state == SmartScriptLexerState.TEXT) {
			nextTokenText();
		} else if(state == SmartScriptLexerState.NON_EMPTY_TAG) {
			nextTokenNonEmptyTag();
		} else {
			nextTokenEmptyTag();
		}
		
		return getToken();
		
	}
	
	/**
	 * This method is called by method nextToken if the lexer is in the state EMPTY_TAG.
	 * It creates new Token of type ECHO and as its elements it sets read elements.
	 */
	private void nextTokenEmptyTag() {
		Element[] elements = null;
		Element element;
		
		while(true) {
			skipBlanks();
			
			if(currentIndex >= data.length) {
				throw new SmartScriptLexerException(
						"Document can't end with unclosed echo tag.");
			}
			
			if(data[currentIndex] == '$') {
				break;
			}
			
			if(data[currentIndex] == '\r' && data[currentIndex + 1] == '\n') {
				elements = addToArray(elements, new ElementString("\r\n"));
				
				currentIndex += 2;
	
				continue;
			}
			
			if(data[currentIndex] == '"') {
				currentIndex++;
				element = readString();
				currentIndex++;
				elements = addToArray(elements, element);
				
				continue;
			}
			
			if(((data[currentIndex] == '-') && (Character.isDigit(data[currentIndex + 1]))) || 
				 Character.isDigit(data[currentIndex])) {
				element = readNumber();
				elements = addToArray(elements, element);
				
				continue;
			}
			
			if((data[currentIndex] == '+') ||
			   (data[currentIndex] == '-') ||
			   (data[currentIndex] == '*') ||
			   (data[currentIndex] == '/') ||
			   (data[currentIndex] == '^')) {
				element = new ElementOperator(String.valueOf(data[currentIndex]));
				elements = addToArray(elements, element);
				
				currentIndex++;
				
				continue;
			}
				
			
			if(Character.isLetter(data[currentIndex])) {
				element = readVariable();
				elements = addToArray(elements, element);
				
				continue;
			}
			
			if(data[currentIndex] == '@') {
				currentIndex++;
				element = readFunction();
				elements = addToArray(elements, element);
				
				continue;
			}
			
			throw new SmartScriptLexerException("Invalid arguments in echo tag.");
			
		}
		
		if(elements == null) {
			throw new SmartScriptLexerException("Echo tag must not be empty.");
		}
		
		token = new Token(TokenType.ECHO, elements);
 	}
	
	/**
	 * This method is called by method nextToken if the lexer is in the state NON_EMPTY_TAG.
	 * It creates Token of the type TAG_END or Token of the type TAG_FOR. If the created Token
	 * is of the type TAG_END its value is set to null. If the created Token is of the type
	 * TAG_FOR its elements are set to read elements.
	 */
	private void nextTokenNonEmptyTag() {
		if(token.getValue().equals("{$END")) {
			token = new Token(TokenType.TAG_END, null);
			skipBlanks();
			return;
		}
		
		Element[] elements = new Element[NUM_OF_ELEMENTS_IN_FOR];
		
		skipBlanks();
		
		elements[0] = readVariable();
		
		int i;
		for(i = 1; i < NUM_OF_ELEMENTS_IN_FOR; i++) {
			skipBlanks();
			
			if(data[currentIndex] == '$') {
				break;
			}
			
			if(data[currentIndex] == '"') {
				currentIndex++;
				elements[i] = readString();
				currentIndex++;
				
				continue;
			}
			
			if(Character.isLetter(data[currentIndex])) {
				elements[i] = readVariable();
				
				continue;
			} 
			
			if((data[currentIndex] == '-') || Character.isDigit(data[currentIndex])) {
				elements[i] = readNumber();
				
				continue;
			}
				
			throw new SmartScriptLexerException("Invalid arguments in for loop.");
			
		}
		
		if(i < NUM_OF_ELEMENTS_IN_FOR - 1) {
			throw new SmartScriptLexerException("Too few arguments in for loop.");
		}
		
		skipBlanks();
		if(data[currentIndex] != '$') {
			throw new SmartScriptLexerException("Too many arguments in for loop.");
			
		}
		
		token = new Token(TokenType.TAG_FOR, elements);
		
	}
	
	/**
	 * This method is called by method nextToken if the lexer is in the state TEXT.
	 * It creates new Token of type TEXT, its value is set to the read text.
	 */
	private void nextTokenText() {
		String value = "";
		
		if(data[currentIndex] == '{' && data[currentIndex + 1] == '$') {
			currentIndex += 2;
			
			value = "{$";
			
			skipBlanks();
			
			if(currentIndex >= data.length) {
				throw new SmartScriptLexerException("Document can't end with starting tag.");
			}
			
			if(data[currentIndex] == '=') {
				value += '=';
				
				currentIndex++;
			} else if((data[currentIndex] == 'f' || data[currentIndex] == 'F') &&
					  (data[currentIndex + 1] == 'o' || data[currentIndex + 1] == 'O') &&
					  (data[currentIndex + 2] == 'r' || data[currentIndex + 2] == 'R')) {
				value += "FOR";	
				
				currentIndex += 3;
			} else if((data[currentIndex] == 'e' || data[currentIndex] == 'E') &&
					  (data[currentIndex + 1] == 'n' || data[currentIndex + 1] == 'N') &&
					  (data[currentIndex + 2] == 'd' || data[currentIndex + 2] == 'D')) {
				value += "END";
				
				currentIndex += 3;
			} else {
				throw new SmartScriptLexerException("Invalid syntax");
			}
			
		} else if((data[currentIndex] == '$') && (data[currentIndex + 1] == '}')){
			currentIndex += 2;
			
			value = "$}";
		} else {
			while(data[currentIndex] != '{') {
				if(data[currentIndex] == '\\') {
					currentIndex++;
					
					if(currentIndex >= data.length) {
						throw new SmartScriptLexerException("Invalid syntax.");
					}
					
					if(data[currentIndex] == '\\' || data[currentIndex] == '{') {
						value += data[currentIndex];
						
						currentIndex++;
					} else if(data[currentIndex] == 'r' && data[currentIndex + 1] == '\\' &&
							  data[currentIndex + 2] == 'n') {
						value += "r\n";
						
						currentIndex +=3;	
					} else if(data[currentIndex] == 't') {
						value += "\t";
						
						currentIndex++;
					} else {
							throw new SmartScriptLexerException("After \\ can be only: \\, \"");
					}
					
					
				}
				
				value += data[currentIndex];
				
				currentIndex++;
				if(currentIndex >= data.length) {
					break;
				}
			}
		}
		
		token = new Token(TokenType.TEXT, value);
	}
	
	/**
	 * This method skips all blanks in text.
	 */
	private void skipBlanks() {
		while(currentIndex < data.length) {
			char c = data[currentIndex];
			if(c==' ') {
				currentIndex++;
				continue;
			}
			break;
		}
	}
	
	/**
	 * Setter for the state.
	 * 
	 * @param state state
	 */
	public void setState(SmartScriptLexerState state) {
		if(state == null) {
			throw new IllegalArgumentException("Lexer state can not be null value.");
		}
		
		this.state = state;
	}
	
	/**
	 * This method reads variable. It creates new ElementVariable and sets its value to the
	 * read value.
	 * 
	 * @return ElementVariable
	 * @throws SmartScriptLexerException if the variable name is invalid 
	 * SmartScriptLexerException is thrown
	 */
	private ElementVariable readVariable() {
		String value = "";
		
		if(!Character.isLetter(data[currentIndex])) {
			throw new SmartScriptLexerException("Invalid variable name.");
		}
		
		while(Character.isLetter(data[currentIndex]) || 
			  Character.isDigit(data[currentIndex]) ||
			  data[currentIndex] == '_') {
			value += data[currentIndex];
			
			currentIndex++;
		}
		
		if(data[currentIndex] != ' ' && data[currentIndex] != '$') {
		  throw new SmartScriptLexerException("Invalid variable name.");
		}
		
		return new ElementVariable(value);
	}
	
	/**
	 * This method reads String. It creates new ElementString and sets its value to the
	 * read String.
	 * 
	 * @return ElementString
	 */
	private ElementString readString() {
		String value = "";
		
		while(data[currentIndex] != '"') {
			
//			if(data[currentIndex] == '\r' && data[currentIndex + 1] == '\n') {
//				value += "\r\n";
//				
//				currentIndex += 2;
//	
//				continue;
//			}
			
			if(data[currentIndex] == '\\' && data[currentIndex + 1] == '"') {
				value += "\\\"";
				
				currentIndex += 2;
				continue;
			}
			
			if(data[currentIndex] == '\\' && data[currentIndex + 1] == '\\') {
				value += "\\\\";
				
				currentIndex += 2;
				continue;
			}
			
			value += data[currentIndex];
			
			currentIndex++;
		}
	
		return new ElementString(value);
	}
	
	/**
	 * This method reads number. If the next number is decimal number it creates new
	 * ElementConstantDouble and sets its value to the read double number. If the next number is
	 * Integer number method creates new ElementConstant integer and sets its value to the
	 * read number. Method can read negative and positive numbers.
	 * 
	 * @return read number, ElementConstantDouble or ElementConstantInteger
	 * @throws SmartScriptLexerException if the next number is too big, 
	 * SmartScriptLexerException
	 * is thrown
	 */
	private Element readNumber() {
		boolean isDecimal = false;
		boolean isNegative = data[currentIndex] == '-' ? true : false;
		int i = 10;
		
		if(isNegative) {
			currentIndex++;
		}
		
		int temporary = currentIndex;
		while((Character.isDigit(data[temporary])) || data[temporary] == '.') {
			if(data[temporary] == '.') {
				isDecimal = true;
				
				break;
			}
			
			temporary++;
			
			if(temporary >= data.length) {
				throw new SmartScriptLexerException("Document can't end with number.");
			}
		}
		
		if(isDecimal) {
			double value = 0.0;
			
			while(Character.isDigit(data[currentIndex])) {
				if((Double.MAX_VALUE - data[currentIndex]) / i < value) {
					throw new SmartScriptLexerException("Too big number.");
				}
				
				value = value * i + (data[currentIndex] - 48); 
				
				currentIndex++;
			}
			
			currentIndex++;
			
			while(Character.isDigit(data[currentIndex])) {
				value = value + ((double)(data[currentIndex] - 48)) / i;
				
				i *= 10;
				currentIndex++;
			}
			
			
			value = isNegative ? -1 * value : value;
			return new ElementConstantDouble(value);
		} else {
			int value = 0;
			
			while(Character.isDigit(data[currentIndex])) {
				if((Integer.MAX_VALUE - data[currentIndex]) / i < value) {
					throw new SmartScriptLexerException("Too big number.");
				}
				
				value = value * i + (data[currentIndex] - 48); 
				
				currentIndex++;
			}
			
			value = isNegative ? -1 * value : value;
			return new ElementConstantInteger(value);
		}
	}
	
	/**
	 * This method reads next function. It creates new ElementFunction and sets its value
	 * to the read value.
	 * 
	 * @return ElementFunction
	 * @throws SmartScriptLexerException if the function name is invalid, 
	 * SmartScriptLexerException is thrown
	 */
	private ElementFunction readFunction() {
		String value = "";
		
		if(!Character.isLetter(data[currentIndex])) {
			throw new SmartScriptLexerException("Invalid function name.");
		}
		
		while(Character.isLetter(data[currentIndex]) || 
				  Character.isDigit(data[currentIndex]) ||
				  data[currentIndex] == '_') {
				value += data[currentIndex];
				
				currentIndex++;
		}
		
//		if(data[currentIndex] != ' ') {
//			throw new SmartScriptLexerException("Invalid function name.");
//		}
		
		return new ElementFunction(value);
	}
	
	/**
	 * Method adds Element at the end of the Array of Elements.
	 * 
	 * @param elements Array of Elements
	 * @param element Element which is added to Array
	 * @return Array of Elements
	 */
	private Element[] addToArray(Element[] elements, Element element) {
		if(elements == null) {
			elements = new Element[1];
			
		} else {
			elements = Arrays.copyOf(elements, elements.length + 1);
		}
		elements[elements.length - 1] = element;
		
		return elements;
	}
	
	

}
