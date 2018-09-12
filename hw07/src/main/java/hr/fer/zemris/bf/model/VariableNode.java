package hr.fer.zemris.bf.model;

/**
 * Class represents variable of the boolean expression. It contains name of the variable.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class VariableNode implements Node {
	
	/**
	 * Name of the variable.
	 */
	private String name;
	
	/**
	 * Constructor which accepts one argument, name of the variable.
	 * 
	 * @param name name of the variable
	 * @throws IllegalArgumentException if the name is null or empty string
	 */
	public VariableNode(String name) {
		if(name == null) {
			throw new IllegalArgumentException("Name can not be null.");
		}
		
		if(name.equals("")) {
			throw new IllegalArgumentException("Name can not be empty string.");
		}
		
		this.name = name;
	}
	
	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
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
	public String toString() {
		return name;
	}

}
