package hr.fer.zemris.java.hw03.prob1;

/**
 * Enumeration which represents states of Lexer. Lexer can be in one of two states; 
 * Basic and Extended. Lexer changes its state when it reads symbol '#'.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */

public enum LexerState {
	
	/**
	 * If the Lexer is in this state it reads letters as words, numbers as numbers and
	 * it reads everything else as symbols.
	 */
	BASIC,
	
	/**
	 * If the Lexer is in this state it reads all signs as one word until it gets to the
	 * symbol '#'. When it gets to the symbol '#' it changes state to BASIC.
	 */
	EXTENDED

}
