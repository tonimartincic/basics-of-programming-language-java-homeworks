package hr.fer.zemris.java.custom.scripting.elems;

/**
 * This class  inherits Element and has single read-only String property: value.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */

public class ElementString extends Element {
	
	/**
	 * Value of the double ElementString.
	 */
	private String value;
	
	/**
	 * Constructor which accepts one argument, value of the double ElementString.
	 * 
	 * @param value value of the double ElementString
	 */
	public ElementString(String value) {
		this.value = value;
	}
	
	/**
	 * Getter for the value.
	 * 
	 * @return value
	 */
	public String getValue() {
		return value;
	}
	
	@Override
	public String asText() {
		return value;
	}

}
