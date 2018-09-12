package hr.fer.zemris.java.custom.scripting.elems;

/**
 * This class inherits Element and has single read-only String property: name. 
 * 
 * @author Toni Martinčić
 * @version 1.0
 */

public class ElementFunction extends Element {
	
	/**
	 * Name of the ElementFunction.
	 */
	private String name;
	
	/**
	 * Constructor which accepts one argument, name of the ElementFunction.
	 * 
	 * @param name name of the ElementFunction
	 */
	public ElementFunction(String name) {
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
