package hr.fer.zemris.java.custom.scripting.elems;

/**
 * This class inherits Element, and has a single read-only1 String property: name.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */

public class ElementVariable extends Element {
	
	/**
	 * Name of the ElementVariable.
	 */
	private String name;
	
	/**
	 * Constructor which accepts one argument, name of the ElementVariable.
	 * 
	 * @param name name of the ElementVariable
	 */
	public ElementVariable(String name) {
		this.name = name;
	}
	
	/**
	 * Getter for the name.
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	@Override
	public String asText() {
		return name;
	}

}
