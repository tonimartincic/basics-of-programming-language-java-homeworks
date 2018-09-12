package hr.fer.zemris.java.custom.collections;

/**
 * Class Collection represents some general collection of objects. 
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class Collection {
	
	/**
	 * Default constructor.
	 */
	protected Collection() {
		
	}
	
	/**
	 * Method checks is collection empty.
	 * 
	 * @return true if collection contains no objects and false otherwise
	 */
	public boolean isEmpty() {
		return size() == 0;
	}
	
	/**
	 * Method returns the number of currently stored objects in this collections.
	 * 
	 * @return size of collection
	 */
	public int size() {
		return 0;
	}
	
	/**
	 * Method adds the given object into this collection.
	 * 
	 * @param value value that is added into collection
	 */
	public void add(Object value) {
		
	}
	
	/**
	 * Method checks if the collection contains given value. 
	 * 
	 * @param value value for which we are checking is it in collection
	 * @return true if the collection contains given value, false otherwise
	 */
	public boolean contains(Object value) {
		return false;
	}
	
	/**
	 * Method checks if the collection contains given value and removes
	 * one occurrence of it.
	 * 
	 * @param value value that we try remove from collection
	 * @return true if the value was removed from collection, false otherwise
	 */
	public boolean remove(Object value) {
		return false;
	}
	
	/**
	 * Method allocates new array with size equals to the size of this collections, 
	 * fills it with collection content and returns the array.
	 * 
	 * @return array filled with elemnents of collection
	 * @throws UnsupportedOperationException if this method is called on object that is
	 * instance of class Collection it throws UnsupportedOperationException
	*/
	public Object[] toArray() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Method calls processor.process() for each element of this collection.
	 * 
	 * @param processor processor which method process is called for each element
	 */
	public void forEach(Processor processor) {
		
	}
	
	/**
	 * Method adds into itself all elements from given collection. 
	 * This other collection remains unchanged.
	 * 
	 * @param other collection which elements are added into this collection
	 */
	public void addAll(Collection other) {
		class AddElementProcessor extends Processor {
			@Override
			public void process(Object value) {
				add(value);
			}
		}
		
		Processor processor = new AddElementProcessor();
		
		other.forEach(processor);
	}
	
	/**
	 * Removes all elements from this collection.
	 * 
	 */
	public void clear() {
		
	}

}
