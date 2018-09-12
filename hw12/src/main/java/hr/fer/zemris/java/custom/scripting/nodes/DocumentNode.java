package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * A node representing an entire document. It inherits from Node class.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */

public class DocumentNode extends Node {
	

	@Override
	public void accept(INodeVisitor iNodeVisitor) {
		if(iNodeVisitor == null) {
			throw new IllegalArgumentException("Argument iNodeVisitor can not be null value.");
		}
		
		iNodeVisitor.visitDocumentNode(this);
	}
}
