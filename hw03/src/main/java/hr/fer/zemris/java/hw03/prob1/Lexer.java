package hr.fer.zemris.java.hw03.prob1;

/**
 * Class represents lexical analyser. It returns Tokens from accepted text.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */

public class Lexer {
	
	/**
	 * Char Array of text which is being analysed.
	 */
	private char[] data;
	
	/**
	 * Last read Token.
	 */
	private Token token;
	
	/**
	 * Current index of data.
	 */
	private int currentIndex;
	
	/**
	 * State of the Lexer.
	 */
	private LexerState state;
	
	/**
	 * Constructor which accepts one argument, text which is analysed.
	 * 
	 * @param text text which is analysed
	 * @throws IllegalArgumentException if argument text is null
	 */
	public Lexer(String text) {
		if(text == null) {
			throw new IllegalArgumentException("Argument text must not be null value.");
		}
		
		data = text.toCharArray();
		currentIndex = 0;
		state = LexerState.BASIC;
		
	}
	
	/**
	 * Method returns next Token from text.
	 * 
	 * @return next Token from text
	 */
	public Token nextToken() {
		if(token != null && token.getType() == TokenType.EOF) {
			throw new LexerException("No tokens available.");
		}
		
		skipBlanks();
		
		if(currentIndex >= data.length) {
			return token = new Token(TokenType.EOF, null);
		}
		
		if(state == LexerState.BASIC) {
			extractNextTokenBasic();
		} else {
			extractNextTokenExtended();
		}
		
		return getToken();
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
	 * This method is called by method nextToken if the lexer is in the state BASIC.
	 * It creates new Token of type WORD and as its value it sets word which is read.
	 */
	private void extractNextTokenBasic() { 
		if((Character.isLetter(data[currentIndex])) || (data[currentIndex] == '\\')) {
			extractNextWord();
			return;
		}
			
		
		if(Character.isDigit(data[currentIndex])) {
			extractNextNumber();
			return;
		}
		
		extractNextSymbol();
	}
	
	/**
	 * This method extracts next Word by rules from BASIC state of the Lexer.
	 */
	private void extractNextWord() {
		StringBuilder sb = new StringBuilder();
		
		while(currentIndex < data.length) {
			if((!Character.isLetter(data[currentIndex])) &&
			   (data[currentIndex] != '\\')) {
				break;
			}
			
			if(data[currentIndex] == '\\') {
				currentIndex++;
				
				if(currentIndex >= data.length) {
					throw new LexerException("Token must not end with backslash");
				}
				
				if(Character.isLetter(data[currentIndex])) {
					throw new LexerException("After backslash must not be letter.");
				}
			}
			
			sb.append(data[currentIndex]);
			currentIndex++;
		}
		
		String value = new String(sb.toString());

		token = new Token(TokenType.WORD, value);
	}
	
	/**
	 * This method extracts next number from text.
	 * It creates new Token of type NUMBER and as its value it sets read number.
	 * 
	 * @throws LexerException if read number is to big LexerException is thrown
	 */
	private void extractNextNumber() {
		long value = 0;
		int i = 10;
		
		while(currentIndex < data.length) {
			if(!Character.isDigit(data[currentIndex])) {
				break;
			}
			
			if((Long.MAX_VALUE - data[currentIndex]) / i < value) {
				throw new LexerException("Too big number.");
			}
			
			value = value * i + (data[currentIndex] - 48); 
			
			currentIndex++;
		}
		
		token = new Token(TokenType.NUMBER, value);
	}
	
	/**
	 * This method extracts next symbol from text.
	 * It creates new Token of type SYMBOL and as its value it sets read symbol.
	 */
	private void extractNextSymbol() {
		Character value;
		
		value = data[currentIndex];
		currentIndex++;
		
		token = new Token(TokenType.SYMBOL, value);
	}
	
	/**
	 * This method skips all blanks in text.
	 */
	private void skipBlanks() {
		while(currentIndex < data.length) {
			char c = data[currentIndex];
			if(c==' ' || c=='\t' || c=='\r' || c=='\n') {
				currentIndex++;
				continue;
			}
			break;
		}
	}
	
	/**
	 * This method is called by method nextToken if the lexer is in the state EXTENDED.
	 * It reads next symbol or next word.
	 */
	private void extractNextTokenExtended() {
		if(data[currentIndex] == '#') {
			extractNextSymbol();
			return;
		}
		
		extractNextWordExtended();
		
	}
	
	/**
	 * This method extracts next word by rules from EXTENDED state of the Lexer.
	 * It creates new Token of type WORD and as its value it sets word which is read.
	 */
	private void extractNextWordExtended() {
		StringBuilder sb = new StringBuilder();
		
		while(currentIndex < data.length) {
			if(data[currentIndex] == ' ') {
				skipBlanks();
				break;
			}
			
			if(data[currentIndex] == '#' ) {
				break;
			}
			
			sb.append(data[currentIndex]);
			currentIndex++;
		}
		
		String value = new String(sb.toString());
		
		token = new Token(TokenType.WORD, value);
	}
	
	/**
	 * Setter for the state.
	 * 
	 * @param state state
	 */
	public void setState(LexerState state) {
		if(state == null) {
			throw new IllegalArgumentException("Lexer state can not be null value.");
		}
		
		this.state = state;
	}

}
