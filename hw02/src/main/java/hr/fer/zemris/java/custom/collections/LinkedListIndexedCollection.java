package hr.fer.zemris.java.custom.collections;

/**
 * Class represents linked list-backed collection of objects.
 * Duplicate elements are allowed, storage of null references is not allowed.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */

public class LinkedListIndexedCollection extends Collection {
	
	/**
	 * Class represents ListNode with pointers to previous and next list node and 
	 * additional reference for value storage.
	 * 
	 * @author Toni Martinčić
	 * @version 1.0
	 */
	
	 private static class ListNode {
		 
		 /**
		  * Pointer to previous list node.
		  */
		 ListNode previousNode;
		 
		 /**
		  * Pointer to next list node.
		  */
		 ListNode nextNode;
		 
		 /**
		  * Value of the node.
		  */
		 Object value;
	 }
	 
	 /**
	  * Number of elements int the collection.
	  */
	 private int size;
	 
	 /**
	  * Pointer to first list node.
	  */
	 private ListNode first;
	 
	 /**
	  * Pointer to last list node.
	  */
	 private ListNode last;
	 
	 
	 /**
	  * Default constructor. Creates an empty collection.
	  */
	 public LinkedListIndexedCollection() {
		 this(null);
	 }
	 
	 /**
	  * Constructor which accepts one argument,  reference to some other
	  * Collection which elements are copied into this newly constructed collection.
	  * 
	  * @param other Collection which elements are copied into this newly constructed collection
	  */
	 public LinkedListIndexedCollection(Collection other) {
		 first = null;
		 last = null;
		 size = 0;
		 
		 if(other == null) {
			 return;
		 }
		 
		 addAll(other);
	 }
	 
	 @Override
	 public int size() {
		 return size;
	 }
	 
	 @Override 
	 public boolean contains(Object value) {
		 if(value == null) {
			 return false;
		 }
			
		 return indexOf(value) != -1;
	 }
	 
	 @Override
	 public void forEach(Processor processor) {
		 ListNode temporary = first;
		 while(temporary != null) {
			 processor.process(temporary.value);
			 temporary = temporary.nextNode;
		 }
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
	 public void add(Object value) throws IllegalArgumentException {
		 if(value == null) {
			 throw new IllegalArgumentException("Value must not be null.");
		 }
		 
		 ListNode newNode = new ListNode();
		 newNode.value = value;
		 
		 if(first == null) {
			 first = newNode;
			 last = newNode;
			 newNode.previousNode = null;
			 newNode.nextNode = null;
		 } else {
			 newNode.previousNode = last;
			 last.nextNode = newNode;
			 last = newNode;
			 newNode.nextNode = null;
		 }
		 
		 size++;
	 }
	 
	 /**
	  * Returns the object that is stored in linked list at position index. 
	  * Valid indexes are 0 to size-1.
	  * 
	  * @param index index of object which is returned
	  * @return object that is stored in linked list at position index
	  * @throws IndexOutOfBoundsException if index is less than 0 ir bigger than
	  * size of collection - 1 IndexOutOfBoundsException is thrown
	  */
	 public Object get(int index) throws IndexOutOfBoundsException {
		 if((index < 0) || (index > size - 1)) {
			 throw new IndexOutOfBoundsException("Valid indexes are 0 to size-1.");
		 }
		 
		 ListNode temporary;
		 int i;
		 
		 if(index < size / 2) {
			 temporary = first;
			 i = 0;
			 while(i < index) {
				 temporary = temporary.nextNode;
				 i++;
			}
		 } else {
			 temporary = last;
			 i = size - 1;
			 while(i > index) {
				 temporary = temporary.previousNode;
				 i--;
			 }
			 
		 }
		 
		 return temporary.value;
	 }
	 
	 @Override
	 public void clear() {
		 first = null;
		 last = null;
		 size = 0;
	 }
	 
	 /**
	  * Inserts, does not overwrite, the given value at the given position in linked-list.
	  * Elements starting from this position are shifted one position. 
	  * The legal positions are 0 to size.
	  * 
	  * @param value value which is inserted in collection
	  * @param position position at which is value inserted in collection 
	  * @throws IllegalArgumentException if position is less than 0 or bigger than size of
	  * collection IllegalArgumentException is thrown
	  */
	 public void insert(Object value, int position) throws IllegalArgumentException {
		 if((position < 0) || (position > size)) {
			 throw new IllegalArgumentException("The legal positions are 0 to size.");
		 }
		 
		 if(value == null) {
			 throw new IllegalArgumentException("Value must not be null.");
		 }
		 
		 ListNode temporary = first;
		 
		 int i = position;
		 while(i > 0) {
			 temporary = temporary.nextNode;
			 i--;
		 }
		 
		 ListNode newNode = new ListNode();
		 newNode.value = value;
		 
		 if(position == 0) {
			 newNode.previousNode = null;
			 newNode.nextNode = first;
			 first = newNode;
		 } else if(position == size) {
			 newNode.previousNode = last;
			 newNode.nextNode = null;
			 last.nextNode = newNode;
			 last = newNode;
		 } else {
			 newNode.previousNode = temporary.previousNode;
			 newNode.nextNode = temporary;
			 
			 temporary.previousNode.nextNode = newNode;
			 temporary.previousNode = newNode;
		 }
		 
		 size++;
	 }
	 
	 /**
	  * Searches the collection and returns the index of the first occurrence of the given value 
	  * or -1 if the value is not found.
	  * 
	  * @param value value which is searched in collection
	  * @return index of the first occurrence of the given value or -1 if the value is not found
	  */
	 public int indexOf(Object value) {
		 if(value == null) {
			 return -1;
		 }
		 
		 ListNode temporary = first;
		 int index = 0;
		 
		 while(temporary != null) {
			 if(temporary.value.equals(value)) {
				 return index;
			 }
			 temporary = temporary.nextNode;
			 index++;
		 }
		 
		 return -1;
	 }
	 
	 /**
	  * Removes element at specified index from collection. 
	  * Element that was previously at location index+1 after this operation is on 
	  * location index, etc. Legal indexes are 0 to size-1.
	  * 
	  * @param index position at which is element removed from collection
	  * @throws IllegalArgumentException if index is less than 0 or bigger than size of
	  * collection -1 IllegalArgumentException is thrown
	  */
	 public void remove(int index) throws IllegalArgumentException {
		 if((index < 0) || (index > size - 1)) {
			throw new IllegalArgumentException("Legal indexes are 0 to size-1.");
		 }
		 
		 ListNode temporary = first;
		 
		 while(index > 0) {
			 temporary = temporary.nextNode;
			 index--;
		 }
		 
		 if(temporary.previousNode != null) {
			 temporary.previousNode.nextNode = temporary.nextNode;
		 } else {
			 first = temporary.nextNode;
		 }
		 
		 if(temporary.nextNode != null) {
			 temporary.nextNode.previousNode = temporary.previousNode;
		 } else {
			 last = temporary.previousNode;
		 }
		 
		 size--;
	 }
	 
	 @Override
	 public Object[] toArray() {
		 int numOfElements = size();
		 Object[] array = new Object[numOfElements];
		
		 ListNode temporary = first;
		 int i = 0;
		 
		 while(temporary != null) {
			 array[i] = temporary.value;
			 temporary = temporary.nextNode;
			 i++;
		 }
		 
		 return array;
	 }
	 

}
