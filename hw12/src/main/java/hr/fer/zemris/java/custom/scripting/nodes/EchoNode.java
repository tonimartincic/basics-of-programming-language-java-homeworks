package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * A node representing a command which generates some textual output dynamically. 
 * It inherits from Node class. Class EchoNode defines a single additional 
 * read-only Element[] property elements.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class EchoNode extends Node {
	
	/**
	 * Array of Elements of the EchoNode.
	 */
	private Element[] elements;
	
	/**
	 * Constructor which accepts only one argument, Array of Elements.
	 * 
	 * @param elements Array of Elements
	 */
	public EchoNode(Element... elements) {
		this.elements = elements;
	}
	
	@Override
	public void accept(INodeVisitor iNodeVisitor) {
		if(iNodeVisitor == null) {
			throw new IllegalArgumentException("Argument iNodeVisitor can not be null value.");
		}
		
		iNodeVisitor.visitEchoNode(this);
	}
	
	/**
	 * Getter for elements.
	 * 
	 * @return elements
	 */
	public Element[] getElements() {
		return elements;
	}
}
