package hr.fer.zemris.java.hw04.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class represents hash table that contains elements in form: key, value.
 * Key can't be null, value can be null.
 * 
 * @author Toni Martinčić
 *
 * @param <K> type of the key
 * @param <V> type of the value
 * @version 1.0
 */

public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K,V>> {
	
	/**
	 * Deafault size of the hash table.
	 */
	private static final int DEFAULT_HASHTABLE_SIZE = 16;
	
	/**
	 * Array of elements of hash table.
	 */
	private TableEntry<K,V>[] table;
	
	/**
	 * Number of elements in hash table.
	 */
	private int size;
	
	/**
	 * Number of modifications made on hash table.
	 */
	private int modificationCount;
	
	/**
	 * Number of full slots of hash table.
	 */
	private int numberOfFullSlots;
	
	/**
	 * Default constructor. It sets capacity of hash table to 16.
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable() {
		table = (TableEntry<K, V>[]) new TableEntry<?, ?>[DEFAULT_HASHTABLE_SIZE];
	}
	
	/**
	 * Constructor which accepts one argument, capacity of hash table.
	 * 
	 * @param capacity capacity of hash table
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		capacity = calculateCapacity(capacity);
		
		table = (TableEntry<K, V>[]) new TableEntry<?, ?>[capacity];
	}
	
	/**
	 * If the key already exists in the table, method change value for that key to accepted
	 * value. If key don't exist in the table, method adds new element in the table.
	 * 
	 * @param key
	 * @param value
	 * @throws IllegalArgumentException if the key is null IllegalArgumentException
	 * is thrown
	 */
	public void put(K key, V value) {
		if(key == null) {
			throw new IllegalArgumentException("Key must not be null.");
		}
		
		int hash = calculateHash(key);
		
		if(table[hash] == null){
			table[hash] = new TableEntry<>(key, value, null);
			size++;
			modificationCount++;
			
			numberOfFullSlots++;
			if(numberOfFullSlots / (double)table.length > 0.75) {
				enlargeTable();
			}
			
			return;
		} 
		
		TableEntry<K, V> tableEntry = table[hash];
		while(tableEntry.getKey().equals(key) != true && tableEntry.next != null){
			tableEntry = tableEntry.next;
		}
		
		if(tableEntry.getKey().equals(key)){
			tableEntry.setValue(value);
			
			return;
		} 
	    
		tableEntry.next = new TableEntry<K, V>(key, value, null);
		size++;
		modificationCount++;
	}
	
	/**
	 * Method gets value for accepted key. If key is null or key doesn't exist in table it
	 * returns null.
	 * 
	 * @param key 
	 * @return value
	 */
	public V get(Object key) {
		if(key == null) {
			return null;
		}
		
		int hash = calculateHash(key);
		
		TableEntry<K, V> tableEntry = table[hash];
		while(tableEntry != null && tableEntry.getKey().equals(key) != true){
			tableEntry = tableEntry.next;
		}
		
		if(tableEntry == null) {
			return null;
	    }
	
		return tableEntry.getValue();
	}
	
	/**
	 * Method returns number of elements in the hash table.
	 * 
	 * @return number of elements
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Method checks if hash table contains accepted key.
	 * 
	 * @param key
	 * @return true if hash table contains accepted key, false otherwise
	 */
	public boolean containsKey(Object key) {
		if(key == null) {
			return false;
		}
		
		boolean contains = false;
		int hash = calculateHash(key);
		
		TableEntry<K, V> tableEntry = table[hash];
		while(tableEntry != null){
			if(tableEntry.getKey().equals(key)){
				contains = true;
				break;
			}
			tableEntry = tableEntry.next;
		}
		
		return contains;
	}
	
	/**
	 * Method checks if hash table contains accepted value.
	 * 
	 * @param value
	 * @return true if hash table contains value, false otherwise
	 */
	public boolean containsValue(Object value) {
		boolean contains = false;
		
		for(TableEntry<K, V> tableEntry : this.table){
			if(tableEntry != null){
				TableEntry<K, V> temp = tableEntry;
				
				while(temp != null){
					if(temp.getValue() == value){
						contains = true;
						break;
					}
					
					temp = temp.next;
				}
				
				if(contains == true){
					break;
				}
			}
			
		}
		
		return contains;
	}
	
	/**
	 * Method removes element which key is equals to accepted key from hash table.
	 * @param key 
	 */
	public void remove(Object key) {
		if(key == null) {
			return;
		}
		
		int hash = calculateHash(key);
		
		TableEntry<K, V> tableEntry = table[hash];
		TableEntry<K, V> previousTableEntry = null;
		
		while(tableEntry != null && tableEntry.getKey().equals(key) != true){
			previousTableEntry = tableEntry;
			tableEntry = tableEntry.next;
		}
		
		if(tableEntry == null) {
			return;
		}
		
		if(previousTableEntry == null){
			table[hash] = table[hash].next;
		} else {
			previousTableEntry.next = tableEntry.next;
		}
		
		size--;
		modificationCount++;
	}
	
	/**
	 *Method checks if hash table is empty. 
	 *
	 * @return true if hash table is empty, false otherwise
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * Method removes all elements from hash table.
	 */
	public void clear() {
		for(@SuppressWarnings("unused") TableEntry<K, V> tableEntry : table) {
			tableEntry = null;
		}
		
		size = 0;
	}
	
	@Override
	public String toString() {
		boolean first = true;
		StringBuilder sb = new StringBuilder();
		
		sb.append("[");
		
		for(TableEntry<K, V> tableEntry : table) {
			TableEntry<K, V> temp = tableEntry;
			
			while(temp != null){
				
				if(first) {
					sb.append(temp.toString());
					
					first = false;
					temp = temp.next;
					
					continue;
				}
				
				sb.append(", ").append(temp.toString());
				
				temp = temp.next;
			}
		}
		
		sb.append("]");
		
		return sb.toString();
	}

	/**
	 * Method calculates capacity of hash table. Capacity is first power of number 2 which is
	 * greater than accepted capacity.
	 * 
	 * @param size
	 * @return capacity of hash table
	 */
	public int calculateCapacity(int size) {
		if(size < 1) {
			throw new IllegalArgumentException("Size of the SimpleHashtable must be greater than 1.");
		}
		int newSize = 1;
		
	    while(newSize < size) {
	    	newSize *= 2;
	    }
		return newSize;
	}
	
	/**
	 * Method calculates hash value of the accepted key
	 * 
	 * @param key
	 * @return hash value
	 */
	public int calculateHash(Object key) {
		return Math.abs(key.hashCode()) % this.table.length;
	}
	
	/**
	 * Method prints hash table on the standard output.
	 */
	public void printHashTable() {
		System.out.println("***********");
		
		for(int i = 0; i < table.length; i++) {
			TableEntry<K, V> tableEntry = table[i];
			
			while(tableEntry != null) {
				System.out.print(tableEntry);
				tableEntry = tableEntry.next;
			}
			System.out.printf("\n");
			
		}
		
		System.out.println("***********");
	}
	
	/**
	 * Method doubles the capacity of the hash table.
	 */
	private void enlargeTable() {
		@SuppressWarnings("unchecked")
		TableEntry<K, V>[] entriesToMove = (TableEntry<K, V>[]) new TableEntry<?, ?>[size];
		int numOfEntriesToMove = 0;
		
		for(int i = table.length - 1; i >= 0; i--) {
			TableEntry<K, V> tableEntry = table[i];
			
			while(tableEntry != null) {
				if(calculateHash(tableEntry.getKey()) != 
				   Math.abs(tableEntry.getKey().hashCode()) % (this.table.length * 2)) {
					
					entriesToMove[numOfEntriesToMove] = tableEntry;
					numOfEntriesToMove++;
					
					this.remove(tableEntry.getKey());
				}
				
				tableEntry = tableEntry.next;
			}
		}
		
		table = Arrays.copyOf(table, table.length * 2);
		
		for(int i = 0; i < numOfEntriesToMove; i++) {
			put(entriesToMove[i].getKey(), entriesToMove[i].getValue());
		}
	}
	
	public Iterator<SimpleHashtable.TableEntry<K,V>> iterator() { 
		return new IteratorImpl();
	}
	
	/**
	 * 
	 * @author Toni
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K,V>> {
		
		/**
		 * Number of the current slot of the hash table.
		 */
		private int slotNumber;
		
		/**
		 * Number of the current node in the hash table slot.
		 */
		private int nodeNumber;
		
		/**
		 * If this variable is true, removing elemment is allowed, otherwise it isn't.
		 */
		private boolean canRemove;
		
		/**
		 * Current TableEntry.
		 */
		TableEntry<K, V> currentTableEntry;
		
		/**
		 * Number of modifications on hash table at start of iterating. 
		 */
		private int modificationCountAtStart;
		
		/**
		 * Constructor which accepts no arguments.
		 */
		public IteratorImpl() {
			canRemove = false;
			currentTableEntry = null;
			modificationCountAtStart = modificationCount;
		}
		
		public boolean hasNext() { 
			if(modificationCountAtStart != modificationCount) {
				throw new ConcurrentModificationException("Collection was modified.");
			}
			
			while(slotNumber < table.length) {
				
				TableEntry<K, V> tableEntry = table[slotNumber];
				
				for(int i = 0; i < nodeNumber; i++) {
					tableEntry = tableEntry.next;
				}
				
				if(tableEntry != null) {
					return true;
				}
				
				slotNumber++;
				nodeNumber = 0;
			}
			
			return false;
		}
		
		@SuppressWarnings("rawtypes")
		public SimpleHashtable.TableEntry next() { 
			if(modificationCountAtStart != modificationCount) {
				throw new ConcurrentModificationException("Collection was modified.");
			}
			
			if(!hasNext()) {
				throw new NoSuchElementException("No more elements.");
			}
			
			TableEntry<K, V> tableEntry = null;
			
			while(slotNumber < table.length) {
				tableEntry = table[slotNumber];
				
				for(int i = 0; i < nodeNumber; i++) {
					tableEntry = tableEntry.next;
				}
				
				if(tableEntry == null) {
					slotNumber++;
					nodeNumber = 0;
					
					continue;
				}
				
				nodeNumber++;
				break;
			}
			
			canRemove = true;
			currentTableEntry = tableEntry;
			
			return tableEntry;
		}
		
		public void remove() {
			if(modificationCountAtStart != modificationCount) {
				throw new ConcurrentModificationException("Collection was modified.");
			}
			
			if(!canRemove) {
				throw new IllegalStateException(
						"This method can be called only once per call to next().");
			}
			
			if(currentTableEntry.next == null) {
				slotNumber++;
				nodeNumber = 0;
			} 
			
			SimpleHashtable.this.remove(currentTableEntry.getKey());
			modificationCountAtStart++;
			
			canRemove = false;
		}
	}
	
	/**
	 * Class represents one element in SimpleHashTable. It contains key and value.
	 * 
	 * @author Toni Martinčić
	 *
	 * @param <K> type of the key of the TableEntry
	 * @param <V> type of the value of the TableEntry
	 * @version 1.0
	 */
	public static class TableEntry<K,V> {
		
		/**
		 * Key of the TableEntry.
		 */
		private K key;
		
		/**
		 * Value of the TableEntry.
		 */
		private V value;
		
		/**
		 * Reference to the next TableEntry.
		 */
		private TableEntry<K, V> next;
		
		/**
		 * Constructor which accepts three arguments; key of the TableEntry,
		 * value of the TableEntry and the reference to the next TableEntry.
		 * 
		 * @param key key of the TableEntry
		 * @param value value of the TableEntry
		 * @param next reference to the next TableEntry
		 */
		public TableEntry(K key, V value, TableEntry<K, V> next){
        	this.key = key;
        	this.value = value;
        	this.next = next;
        }
		
		/**
		 * Getter for the value.
		 * 
		 * @return value.
		 */
		public V getValue() {
			return value;
		}
		
		/**
		 * Setter for the value.
		 * 
		 * @param value vale
		 */
		public void setValue(V value) {
			this.value = value;
		}
		
		/**
		 * Getter for the key.
		 * 
		 * @return key
		 */
		public K getKey() {
			return key;
		}
		
		@Override
		public String toString() {
			return key + "=" + value;
		}
	
	}

}
