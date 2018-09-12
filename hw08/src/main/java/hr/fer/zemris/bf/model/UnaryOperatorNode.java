package hr.fer.zemris.bf.model;

import java.util.function.UnaryOperator;

/**
 * Class represents unary operator of the boolean expression. It contains name of the unary operator
 * and its operand. Only unary operator is operator not.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class UnaryOperatorNode implements Node {
	
	/**
	 * Name of the unary operator.
	 */
	private String name;
	
	/**
	 * Operand of the unary operator.
	 */
	private Node child;
	
	/**
	 * Operation of the unary operator.
	 */
	private UnaryOperator<Boolean> operator;

	/**
	 * Constructor which accepts three arguments, name, operand and operation of the unary operator.
	 * {@link IllegalArgumentException} is thrown if argument are invalid.
	 * 
	 * @param name name of the unary operator
	 * @param child operand of the unary operator
	 * @param operator operation of the unary operator
	 */
	public UnaryOperatorNode(String name, Node child, UnaryOperator<Boolean> operator) {
		checkArguments(name, child, operator); //throws IllegalArgumentException if arguments are invalid
		
		this.name = name;
		this.child = child;
		this.operator = operator;
	}
	
	/**
	 * Method checks if arguments are valid.
	 * 
	 * @param name name of the unary operator
	 * @param child operand of the unary operator
	 * @param operator operation of the unary operator
	 * @throws IllegalArgumentException if name is null or empty string, child is null, operand is null
	 */
	private void checkArguments(String name, Node child, UnaryOperator<Boolean> operator) {
		if(name == null) {
			throw new IllegalArgumentException("Name can not be null.");
		}
		
		if(name.equals("")) {
			throw new IllegalArgumentException("Name can not be empty string.");
		}
		
		if(child == null) {
			throw new IllegalArgumentException("Child node can not be null.");
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
	 * Getter for the name
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Getter for the child
	 * 
	 * @return child
	 */
	public Node getChild() {
		return child;
	}
	
	/**
	 * Getter for the operator
	 * 
	 * @return operator
	 */
	public UnaryOperator<Boolean> getOperator() {
		return operator;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
