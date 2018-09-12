package hr.fer.zemris.bf.model;

/**
 * Class represents constant of the boolean expression. It contains value of the constant.
 * Value is boolean value.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class ConstantNode implements Node {
	
	/**
	 * Value of the constant.
	 */
	private boolean value;
	
	/**
	 * Constructor which accepts one argument, value of the constant.
	 * 
	 * @param value value of the constant
	 */
	public ConstantNode(boolean value) {
		this.value = value;
	}
	
	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}
	
	/**
	 * Getter for the value.
	 * 
	 * @return value
	 */
	public boolean getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return value ? "1" : "0";
	}
}
