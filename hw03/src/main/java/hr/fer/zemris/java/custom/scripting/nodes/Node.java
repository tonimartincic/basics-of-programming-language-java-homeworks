package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * This class is base class for all graph nodes.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */

public class Node {
	
	/**
	 * Instance of ArrayIndexedCollection class which represents an internally 
	 * managed collection of children.
	 */
	ArrayIndexedCollection arrayIndexedCollection;
	
	/**
	 * Method adds given child to an internally managed collection of children.
	 * 
	 * @param child child which is added into collection.
	 */
	public void addChildNode(Node child) {
		if(arrayIndexedCollection == null) {
			arrayIndexedCollection = new ArrayIndexedCollection();
		}
		
		arrayIndexedCollection.add(child);
		
	}
	
	/**
	 * Method returns a number of (direct) children of Node.
	 * 
	 * @return number of (direct) children
	 */
	public int numberOfChildren() {
		if(arrayIndexedCollection == null) {
			return 0;
		}
		
		return arrayIndexedCollection.size();
	}
	
	/**
	 * Method returns selected child.
	 * 
	 * @param index index of the child which is returned
	 * @return child at given index
	 */
	public Node getChild(int index) {
		return (Node)arrayIndexedCollection.get(index);
	}

}
