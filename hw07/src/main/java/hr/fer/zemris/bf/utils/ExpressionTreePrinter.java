package hr.fer.zemris.bf.utils;

import hr.fer.zemris.bf.model.BinaryOperatorNode;
import hr.fer.zemris.bf.model.ConstantNode;
import hr.fer.zemris.bf.model.Node;
import hr.fer.zemris.bf.model.NodeVisitor;
import hr.fer.zemris.bf.model.UnaryOperatorNode;
import hr.fer.zemris.bf.model.VariableNode;

/**
 * Class represents node visitor which visit tree created from boolean expression. 
 * It prints every visited node to the standard output.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class ExpressionTreePrinter implements NodeVisitor {

	@Override
	public void visit(ConstantNode node) {
		System.out.println(node);
		
	}

	@Override
	public void visit(VariableNode node) {
		System.out.println(node);
	}

	@Override
	public void visit(UnaryOperatorNode node) {
		print(node, 0);
	}

	@Override
	public void visit(BinaryOperatorNode node) {
		print(node, 0);
	}
	
	/**
	 * Recursive method which prints accepted node and all its children.
	 * 
	 * @param node accepted node
	 * @param i number of spaces printed before node
	 */
	private void print(Node node, int i) {
		if(node instanceof BinaryOperatorNode) {
			printSpaces(i);
			System.out.print(((BinaryOperatorNode) node).getName());
			System.out.printf("\n");
			
			for(Node child : ((BinaryOperatorNode) node).getChildren()) {
				print(child, i + 2);
			}
		} else if (node instanceof UnaryOperatorNode) {
			printSpaces(i);
			System.out.print(((UnaryOperatorNode) node).getName());
			System.out.printf("\n");
			
			print(((UnaryOperatorNode) node).getChild(), i + 2);
		} else {
			printSpaces(i);
			System.out.print(node);
			System.out.printf("\n");
		}
	}

	/**
	 * Helping method which prints spaces before the node is printed.
	 * 
	 * @param i number of spaces to print
	 */
	private void printSpaces(int i) {
		for(int j = 0; j < i; j++) {
			System.out.print(" ");
		}
	}
	

}
