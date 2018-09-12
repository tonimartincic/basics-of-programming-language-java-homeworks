package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Enumeration which represents states of SmartScriptLexer. SmartScriptLexer can be in one
 * of three states: TEXT, NON_EMPTY_TAG, EMPTY_TAG.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */

public enum SmartScriptLexerState {
	
	/**
	 * Lexer is in this states when it reads text outside the tags.
	 */
	TEXT,
	
	/**
	 * Lexer is in this state when it reads text in non empty tag.
	 */
	NON_EMPTY_TAG,
	
	/**
	 * Lexer is in this state when it reads text in empty tag.
	 */
	EMPTY_TAG

}
