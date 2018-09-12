package hr.fer.zemris.java.custom.scripting.elems;

/**
 * This class  inherits Element and has single read-only String property: symbol.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */

public class ElementOperator extends Element {
	
	/**
	 * Symbol of the ElementOperator.
	 */
	private String symbol;
	
	/**
	 * Constructor which accepts one argument, symbol of the ElementOperator.
	 * 
	 * @param symbol symbol of the ElementOperator
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}
	
	/**
	 * Getter for the symbol.
	 * 
	 * @return symbol
	 */
	public String getSymbol() {
		return symbol;
	}
	
	@Override
	public String asText() {
		return symbol;
	}

}
