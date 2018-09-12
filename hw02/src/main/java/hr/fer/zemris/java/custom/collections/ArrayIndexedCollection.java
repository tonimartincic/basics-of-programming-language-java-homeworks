package hr.fer.zemris.java.custom.collections;

/**
 * Class represents resizable array-backed collection of objects.
 * Duplicate elements are allowed, storage of null references is not allowed.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class ArrayIndexedCollection extends Collection {
	
	/**
	 * Capacity of collection if it is created by default constructor
	 */
	private static final int DEFAULT_INITIAL_CAPACITY = 16;
	
	/**
	 * Minimum legal capacity of collection
	 */
	private static final int MINIMUM_INITIAL_CAPACITY = 1;
	
	/**
	 * Current size of collection
	 */
	private int size = 0;
	
	/**
	 * Current capacity of allocated array of object references
	 */
	private int capacity = 0;
	
	/**
	 * An array of object references which length is determined by capacity variable
	 */
	Object[] elements = new Object[capacity];
	
	/**
	 * Default constructor. Initial capacity of collection is set to default initial capacity
	 * which is 16.
	 */
	public ArrayIndexedCollection() {
		this(DEFAULT_INITIAL_CAPACITY);
	}
	
	/**
	 * Constructor which accepts only one parameter, initial capacity.
	 * If initial capacity is less than 1, an IllegalArgumentException is thrown.
	 * 
	 * @param initialCapacity capacity of created collection
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		this(null, initialCapacity);
	}
	
	/**
	 * Constructor which accepts only one parameter, reference to some other Collection 
	 * which elements are copied into this newly constructed collection. 
	 * 
	 * @param other collection which elements are copied into this collection
	 */
	public ArrayIndexedCollection(Collection other) {
		this(other, DEFAULT_INITIAL_CAPACITY);
	}
	
	/**
	 * Constructor which accepts two parameters, initial capacity and reference to some 
	 * other Collection  which elements are copied into this newly constructed collection.
	 * 
	 * @param other collection which elements are copied into this collection
	 * @param initialCapacity capacity of created collection
	 * @throws IllegalArgumentException if argument initial capacity is less than 1
	 * IllegalArgumentException is thrown
	 */
	public ArrayIndexedCollection(Collection other, int initialCapacity) throws IllegalArgumentException {
		if(initialCapacity < MINIMUM_INITIAL_CAPACITY) {
			throw new IllegalArgumentException("Initial capacity can't be less than 1.");
		}
		
		elements = new Object[initialCapacity];
		capacity = initialCapacity;
		
		if(other == null) {
			return;
		}
		
		addAll(other);
	}
	
	/**
	 * Getter for capacity.
	 * 
	 * @return capacity of collection
	 */
	public int getCapacity() {
		return capacity;
	}
	
	@Override
	public int size() {
		return size;
	}
	
	@Override
	public void add(Object value) throws IllegalArgumentException {
		if(value == null) {
			throw new IllegalArgumentException("Null value can't be added into collection.");
		}
		
		if(size == capacity) {
			Object[] newArray = new Object[2 * capacity];
			
			for(int i = 0; i < size; i++) {
				newArray[i] = elements[i];
			}
			
			elements = newArray;
			capacity  *= 2;
		}
		
		elements[size] = value;
		size++;
	}
	
	@Override
	public boolean contains(Object value) {
		if(value == null) {
			return false;
		}
		
		return indexOf(value) != -1;
	}
	
	@Override
	public boolean remove(Object value) {
		if(value == null) {
			return false;
		}
		
		int index = indexOf(value);
		if(index == -1) {
			return false;
		}
		
		remove(index);
		return true;
	}
	
	@Override
	public void forEach(Processor processor) {
		for(int i = 0; i < size; i++) {
			processor.process(elements[i]);
		}
	}
	
	/**
	  * Method returns the object that is stored in backing array at position index. 
	  * Valid indexes are 0 to size-1.
	  * 
	  * @param index index of positon from which object is returned
	  * @return object at position index
	  * @throws IndexOutOfBoundsException if index is less than 0 or bigger than size of
	  * collection - 1 IndexOutOfBoundsException is thrown
	  */
	public Object get(int index) throws IndexOutOfBoundsException {
		if((index < 0) || (index > size - 1)) {
			throw new IndexOutOfBoundsException("Valid indexes are 0 to size-1");
		}
		
		return elements[index];
	}
	
	@Override
	public void clear() {
		for(int i = 0; i < size; i++) {
			elements[i] = null;
		}
		
		size = 0;
	}
	
	/**
	  * Method inserts,does not overwrite the given value at the given position in array.
	  * The legal positions are 0 to size.
	  * 
	  * @param value value which is inserted into collection
	  * @param position positon at which value is inserted
	  * @throws IllegalArgumentException if argument position is less than 0 or bigger than size
	  * of collection IllegalArgumentException is thrown
	  */
	public void insert(Object value, int position) throws IllegalArgumentException {
		if((position < 0) || (position > size)) {
			throw new IllegalArgumentException("The legal positions are 0 to size.");
		}
		
		if(value == null) {
			throw new IllegalArgumentException("Null value can't be added into collection.");
		}
		
		if(size == capacity) {
			Object[] newArray = new Object[2 * capacity];
			
			for(int i = 0; i < size; i++) {
				newArray[i] = elements[i];
			}
			
			elements = newArray;
			capacity  *= 2;
		}
		
		for(int i = size; i > position; i--) {
			elements[i] = elements[i - 1];
		}
		
		elements[position] = value;
		size++;
	}
	
	/**
	 * Method searches the collection and returns the index of the first occurrence 
	 * of the given value or -1 if the value is not found. 
	 * 
	 * @param value value which method searches in collection
	 * @return the index of the first occurrence of the given value or 
	 * -1 if the value is not found
	 */
	public int indexOf(Object value) {
		if(value == null) {
			return -1;
		}
		
		for(int i = 0; i < size; i++) {
			if(elements[i].equals(value)) {
				return i;
			}
		}
		
		return -1;
	}
	
	/**
	 * Method removes element at specified index from collection. 
	 * Element that was previously at location index+1 after this operation is on 
	 * location index, etc. Legal indexes are 0 to size-1.
	 * 
	 * @param index index of element which method removes from collection
	 * @throws IndexOutOfBoundsException if index is less than one or
	 * bigger than size of collection IndexOutOfBoundsException is thrown
	 */
	public void remove(int index) throws IndexOutOfBoundsException{
		if((index < 0) || (index > size - 1)) {
			throw new IndexOutOfBoundsException("Legal indexes are 0 to size-1.");
		}
		
		for(int i = index; i <= size -2; i++) {
			elements[i] = elements[i + 1];
		}
		
		size--;
	}
	
	@Override
	public Object[] toArray() {
		int numOfElements = size();
		Object[] array = new Object[numOfElements];
		
		for(int i = 0; i < size; i++) {
			array[i] = elements[i];
		}
		
		return array;
	}
	
}
