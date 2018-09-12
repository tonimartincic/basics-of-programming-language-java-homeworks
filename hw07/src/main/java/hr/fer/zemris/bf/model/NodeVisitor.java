package hr.fer.zemris.bf.model;

/**
 * Class which implements this interface is visitor which visits every node of tree created from
 * boolean expression.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public interface NodeVisitor {
	
	/**
	 * This method is called when the visited node is instance of {@link ConstantNode}.
	 * 
	 * @param node instance of {@link ConstantNode} which is visited
	 */
	void visit(ConstantNode node);

	/**
	 * This method is called when the visited node is instance of {@link VariableNode}.
	 * 
	 * @param node instance of {@link VariableNode} which is visited
	 */
	void visit(VariableNode node);
	
	/**
	 * This method is called when the visited node is instance of {@link UnaryOperatorNode}.
	 * 
	 * @param node instance of {@link UnaryOperatorNode} which is visited
	 */
	void visit(UnaryOperatorNode node);
	
	/**
	 * This method is called when the visited node is instance of {@link BinaryOperatorNode}.
	 * 
	 * @param node instance of {@link BinaryOperatorNode} which is visited
	 */
	void visit(BinaryOperatorNode node);

}
