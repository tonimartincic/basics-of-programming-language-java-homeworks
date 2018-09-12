package hr.fer.zemris.bf.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import hr.fer.zemris.bf.model.BinaryOperatorNode;
import hr.fer.zemris.bf.model.ConstantNode;
import hr.fer.zemris.bf.model.Node;
import hr.fer.zemris.bf.model.NodeVisitor;
import hr.fer.zemris.bf.model.UnaryOperatorNode;
import hr.fer.zemris.bf.model.VariableNode;

/**
 * Class represents node visitor which visit tree created from boolean expression.
 * It evaluates boolean expression represented by visited tree.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class ExpressionEvaluator implements NodeVisitor {
	
	/**
	 * Values of variables.
	 */
	private boolean[] values;
	
	/**
	 * Positions of the variables in the truth table.
	 */
	private Map<String, Integer> positions;
	
	/**
	 * Instance of {@link Stack}.
	 */
	private Stack<Boolean> stack = new Stack<>();
	
	/**
	 * Constructor which accepts one argument, list of variables.
	 * 
	 * @param variables
	 * @throws IllegalArgumentException if arguments are null
	 */
	public ExpressionEvaluator(List<String> variables) {
		super();
		
		if(variables == null) {
			throw new IllegalArgumentException("Null value given as variables.");
		}
		
		positions = new HashMap<>();
		for(int i = 0; i < variables.size(); i++) {
			positions.put(variables.get(i), i);
		}
	}
	
	/**
	 * Method sets values to the accepted values.
	 * 
	 * @param values
	 */
	public void setValues(boolean[] values) {
		start();
		
		this.values = Arrays.copyOf(values, values.length);
	}

	@Override
	public void visit(ConstantNode node) {
		stack.push(node.getValue());
	}

	@Override
	public void visit(VariableNode node) {
		Integer orderNumber = positions.get(node.getName());
		
		if(orderNumber == null) {
			throw new IllegalStateException("There is no variable " + node.getName());
		}
		
		stack.push(values[orderNumber]);
	}

	@Override
	public void visit(UnaryOperatorNode node) {
		Node child = node.getChild();
		
		child.accept(this);
		
		boolean value = stack.pop();
		stack.push(node.getOperator().apply(value));
	}

	@Override
	public void visit(BinaryOperatorNode node) {
		for(Node child : node.getChildren()) {
			child.accept(this);
		}
		
		boolean[] childrenValues = new boolean[node.getChildren().size()];
		for(int i = 0; i < childrenValues.length; i++) {
			childrenValues[i] = stack.pop();
		}
		
		boolean value = node.getOperator().apply(childrenValues[0], childrenValues[1]);
		for(int i = 2; i < childrenValues.length; i++) {
			value = node.getOperator().apply(value, childrenValues[i]);
		}
		
		stack.push(value);
	}
	
	/**
	 * Method clears the stack.
	 */
	public void start() {
		stack.clear();
	}
	
	/**
	 * Method returns the result of the evaluation.
	 * 
	 * @return result of the evaluation
	 */
	public boolean getResult() {
		if(stack.size() != 1) {
			throw new IllegalStateException("Number of values on stack is not 1.");
		}
		
		return stack.peek();
	}

}
