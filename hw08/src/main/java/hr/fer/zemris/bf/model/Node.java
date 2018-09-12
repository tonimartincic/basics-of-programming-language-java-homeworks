package hr.fer.zemris.bf.model;

/**
 * Class represents one node of tree of boolean expression.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public interface Node {
	
	/**
	 * Method is called from node and that node accepts {@link NodeVisitor}.
	 * 
	 * @param visitor instance of {@link NodeVisitor}
	 */
	void accept(NodeVisitor visitor);

}
