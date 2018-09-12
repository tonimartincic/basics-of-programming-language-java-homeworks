package hr.fer.zemris.java.custom.scripting.elems;

/**
 * This class inherits Element and has single read-only double property: value.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */

public class ElementConstantDouble extends Element {
	
	/**
	 * Value of the double ElementConstantDouble.
	 */
	private double value;
	
	/**
	 * Constructor which accepts one argument, value of the ElementConstantDouble.
	 * 
	 * @param value value of the double ElementConstantDouble
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}
	
	/**
	 * Getter for the value.
	 * 
	 * @return value
	 */
	public double getValue() {
		return value;
	}
	
	@Override
	public String asText() {
		return String.valueOf(value);
	}
	
}
