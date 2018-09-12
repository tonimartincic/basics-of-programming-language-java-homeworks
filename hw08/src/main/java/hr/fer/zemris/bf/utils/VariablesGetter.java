package hr.fer.zemris.bf.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hr.fer.zemris.bf.model.BinaryOperatorNode;
import hr.fer.zemris.bf.model.ConstantNode;
import hr.fer.zemris.bf.model.Node;
import hr.fer.zemris.bf.model.NodeVisitor;
import hr.fer.zemris.bf.model.UnaryOperatorNode;
import hr.fer.zemris.bf.model.VariableNode;

/**
 * Class represents node visitor which visit tree created from boolean expression.
 * It gets all variables from tree.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class VariablesGetter implements NodeVisitor {
	
	/**
	 * Variables.
	 */
	private List<String> variables;
	
	/**
	 * Helping set which is used to check if the variable is already added in list.
	 */
	private Set<String> set;
	
	/**
	 * Constructor which accepts no arguments. It instantiates list and set of variables.
	 * 
	 */
	public VariablesGetter() {
		super();
		
		variables = new ArrayList<>();
		set = new HashSet<>();
	}
	
	/**
	 * Getter for the list of variables. Variables are returned sorted.
	 * 
	 * @return variables
	 */
	public List<String> getVariables() {
		Collections.sort(variables);
		
		return variables;
	}

	@Override
	public void visit(ConstantNode node) {
		return;
	}

	@Override
	public void visit(VariableNode node) {
		if(set.add(node.getName())) {
			variables.add(node.getName());
		}
	}

	@Override
	public void visit(UnaryOperatorNode node) {
		addVariables(node);
	}

	@Override
	public void visit(BinaryOperatorNode node) {
		addVariables(node);
	}
	
	/**
	 * Recursive method which adds all children of accepted node which are variables into the
	 * list of variables.
	 * 
	 * @param node accepted node
	 */
	private void addVariables(Node node) {
		if(node instanceof BinaryOperatorNode) {
			for(Node child : ((BinaryOperatorNode) node).getChildren()) {
				addVariables(child);
			}
		} else if (node instanceof UnaryOperatorNode) {
			addVariables(((UnaryOperatorNode) node).getChild());
		} else if(node instanceof VariableNode){
			if(set.add(((VariableNode) node).getName())) {
				variables.add(((VariableNode) node).getName());
			}
		}
	}

}
