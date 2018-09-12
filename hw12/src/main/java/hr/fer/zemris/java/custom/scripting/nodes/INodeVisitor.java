package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Class which implements this interface is the visitor which visits each node of document tree.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public interface INodeVisitor {

	/**
	 * This method is called when the {@link TextNode} is visited.
	 * 
	 * @param node visited {@link TextNode}
	 */
	public void visitTextNode(TextNode node);
	
	/**
	 * This method is called when the {@link ForLoopNode} is visited.
	 * 
	 * @param node visited {@link ForLoopNode}
	 */
	public void visitForLoopNode(ForLoopNode node);
	
	/**
	 * This method is called when the {@link EchoNode} is visited.
	 * 
	 * @param node visited {@link EchoNode}
	 */
	public void visitEchoNode(EchoNode node);
	
	/**
	 * This method is called when the {@link DocumentNode} is visited.
	 * 
	 * @param node visited {@link DocumentNode}
	 */
	public void visitDocumentNode(DocumentNode node);

}
