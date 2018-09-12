package hr.fer.zemris.java.custom.scripting.elems;

/**
 * This class inherits Element and has single read-only int property: value.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */

public class ElementConstantInteger extends Element {
	
	/**
	 * Value of the ElementConstantInteger.
	 */
	private int value;
	
	/**
	 * Constructor which accepts one argument, value of the ElementConstantInteger.
	 * 
	 * @param value value of the ElementConstantInteger
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}
	
	/**
	 * Getter for the value.
	 * 
	 * @return value
	 */
	public int getValue() {
		return value;
	}
	
	@Override
	public String asText() {
		return String.valueOf(value);
	}
	
}
