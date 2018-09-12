package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * A node representing a piece of textual data. It inherits from Node class.
 * It defines single additional read-only String property text.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */

public class TextNode extends Node {
	
	/**
	 * Text of the TextNode.
	 */
	private String text;
	
	/**
	 * Constructor which accepts one argument, text of the TextNode.
	 * 
	 * @param text text of the TextNode
	 */
	public TextNode(String text) {
		this.text = text;
	}
	
	@Override
	public void accept(INodeVisitor iNodeVisitor) {
		if(iNodeVisitor == null) {
			throw new IllegalArgumentException("Argument iNodeVisitor can not be null value.");
		}
		
		iNodeVisitor.visitTextNode(this);
	}
	
	/**
	 * Getter for the text
	 * 
	 * @return text
	 */
	public String getText() {
		return text;
	}

}
