package hr.fer.zemris.bf.model;

import java.util.List;
import java.util.function.BinaryOperator;

/**
 * Class represents binary operator of the boolean expression. It contains name of the binary operator
 * and all its operands. Binary operators are or, xor, and. They can have two or more operands.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class BinaryOperatorNode implements Node {
	
	/**
	 * Name of the binary operator.
	 */
	private String name;
	
	/**
	 * Operands of the binary operator.
	 */
	private List<Node> children;
	
	/**
	 * Instance of {@link BinaryOperator} which represents operation of binary operator.
	 */
	private BinaryOperator<Boolean> operator;
	
	/**
	 * Constructor which accepts three arguments, name, operands and operation of the binary operator.
	 * {@link IllegalArgumentException} is thrown if argument are invalid.
	 * 
	 * @param name name of the binary operator
	 * @param children operands of the binary operator
	 * @param operator operation of the binary operator
	 */
	public BinaryOperatorNode(String name, List<Node> children, BinaryOperator<Boolean> operator) {
		checkArguments(name, children, operator); //throws IllegalArgumentException if arguments are invalid
		
		this.name = name;
		this.children = children;
		this.operator = operator;
	}
	
	/**
	 * Method checks if arguments are valid.
	 * 
	 * @param name name of the binary operator
	 * @param children operands of the binary operator
	 * @param operator operation of the binary operator
	 * @throws IllegalArgumentException if name is null or empty string, children are null or children number 
	 * is less than 2, operator is null
	 */
	private void checkArguments(String name, List<Node> children, BinaryOperator<Boolean> operator) {
		if(name == null) {
			throw new IllegalArgumentException("Name can not be null.");
		}
		
		if(name.equals("")) {
			throw new IllegalArgumentException("Name can not be empty string.");
		}
		
		if(children == null) {
			throw new IllegalArgumentException("List of children nodes can not be null.");
		}
		
		if(children.size() < 2) {
			throw new IllegalArgumentException("Number of children nodes must be at least 2.");
		}
		
		if(operator == null) {
			throw new IllegalArgumentException("Operator can not be null.");
		}
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
	
	/**
	 * Getter for the children
	 * 
	 * @return children
	 */
	public List<Node> getChildren() {
		return children;
	}
	
	/**
	 * Getter for the operator
	 * 
	 * @return operator
	 */
	public BinaryOperator<Boolean> getOperator() {
		return operator;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
