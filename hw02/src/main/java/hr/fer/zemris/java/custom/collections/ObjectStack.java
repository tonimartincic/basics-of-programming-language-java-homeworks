package hr.fer.zemris.java.custom.collections;

/**
 * Class represents stack-like collection.
 * Duplicate elements are allowed, storage of null references is not allowed.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class ObjectStack {
	
	/**
	 * Private instance of ArrayIndexedCollection which is used for actual element storage. 
	 */
	private ArrayIndexedCollection arrayIndexedCollection;
	
	/**
	 * Default constructor. Creates instance of ArrayIndexedCollection.
	 */
	public ObjectStack() {
		arrayIndexedCollection = new ArrayIndexedCollection();
	}
	
	/**
	 * Method checks is collection empty. 
	 * 
	 * @return true if collection is empty, false otherwise
	 */
	public boolean isEmpty() {
		return arrayIndexedCollection.isEmpty();
	}
	
	/**
	 * Method returns the number of currently stored objects in this collections.
	 * 
	 * @return size of the collection
	 */
	public int size() {
		return arrayIndexedCollection.size();
	}
	
	/**
	 * Method adds element on the top of the stack.
	 * 
	 * @param value value of the element which is added
	 */
	public void push(Object value) {
		try {
			arrayIndexedCollection.add(value);
		} catch(IllegalArgumentException exc) {
			System.err.println("Null value can't be pushed on the stack.");
		}
	}
	
	/**
	 * Method gets element from the top of the stack.
	 * At least one element must be on the stack.
	 * 
	 * @return element from the top of the stack
	 * @throws EmptyStackException if stack is the empty EmptyStackException is thrown
	 */
	public Object pop() throws EmptyStackException {
		if(isEmpty()) {
			throw new EmptyStackException("Can't pop element from empty stack.");
		}
		
		Object element = arrayIndexedCollection.get(arrayIndexedCollection.size() - 1);
		arrayIndexedCollection.remove(arrayIndexedCollection.size() - 1);
		
		return element;
	}
	
	/**
	 * Method returns last element placed on stack but does not delete it from stack.
	 * 
	 * @return element from the top of the stack
	 * @throws EmptyStackException if the stack is empty EmptyStackException is thrown
	 */
	public Object peek() throws EmptyStackException {
		if(isEmpty()) {
			throw new EmptyStackException("Can't peek empty stack.");
		}
		
		return arrayIndexedCollection.get(arrayIndexedCollection.size() - 1);
	}
	
	/**
	 * Method  removes all elements from stack.
	 */
	public void clear() {
		arrayIndexedCollection.clear();
	}

}
