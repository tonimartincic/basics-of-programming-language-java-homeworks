package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.*;


/**
 * A node representing a single for-loop construct. It inherits from Node class.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */

public class ForLoopNode extends Node {
	
	/**
	 * Variable of the ForLoopNode.
	 */
	private ElementVariable variable;
	
	/**
	 * Start expression of the ForLoopNode. It can be Element of type variable, number or string.
	 */
	private Element startExpression;
	
	/**
	 * End expression of the ForLoopNode. It can be Element of type variable, number or string.
	 */
	private Element endExpression;
	
	/**
	 * Step Expression of the ForLoopNode. It can be Element of type variable, number or string.
	 */
	private Element stepExpression;
	
	/**
	 * Constructor which accepts four arguments; ElementVariable variable,
	 * Element startExpression, Element endExpression and Element stepExpression.
	 * 
	 * @param variable variable of the ForLoopNode
	 * @param startExpression startExpression of the ForLoopNode
	 * @param endExpression endExpression of the ForLoopNode
	 * @param stepExpression stepExpression of the ForLoopNode
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		super();
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}
	
	@Override
	public void accept(INodeVisitor iNodeVisitor) {
		if(iNodeVisitor == null) {
			throw new IllegalArgumentException("Argument iNodeVisitor can not be null value.");
		}
		
		iNodeVisitor.visitForLoopNode(this);
	}
	
	/**
	 * Getter for the variable.
	 * 
	 * @return variable
	 */
	public ElementVariable getVariable() {
		return variable;
	}
	
	/**
	 * Getter for the startExpression.
	 * 
	 * @return startExpression
	 */
	public Element getStartExpression() {
		return startExpression;
	}
	
	/**
	 * Getter for the endExpression.
	 * 
	 * @return endExpression
	 */
	public Element getEndExpression() {
		return endExpression;
	}
	
	/**
	 * Getter for the stepExpression
	 * 
	 * @return stepExpression
	 */
	public Element getStepExpression() {
		return stepExpression;
	}
	
}
