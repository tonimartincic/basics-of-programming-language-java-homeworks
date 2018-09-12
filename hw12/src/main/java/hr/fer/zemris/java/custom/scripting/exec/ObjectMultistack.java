package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;

/**
 * Class represents map which keys are string and values are instances of {@link MultistackEntry}.
 * Multiple values can be stored for the same key. Last value added for the same key is the first 
 * value that will be returned for that key.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class ObjectMultistack {
	
	/**
	 * Map which key is String and values are instances of {@link MultistackEntry}.
	 */
	Map<String, MultistackEntry> mapOfStacks;
	
	/**
	 * Constructor which accepts no arguments.
	 */
	public ObjectMultistack() {
		mapOfStacks = new HashMap<>();
	}
	
	/**
	 * Method pushes accepted {@link ValueWrapper} to the stack for key given as argument name.
	 * Complexity of the method is O(1).
	 * 
	 * @param name key
	 * @param valueWrapper instance of {@link ValueWrapper} which is pushed to the stack
	 */
	public void push(String name, ValueWrapper valueWrapper) {
		//System.out.println("OM " + name + " " + valueWrapper.getValue());
		
		if(name == null) {
			throw new IllegalArgumentException("Argument name can't be null value");
		}
		
		if(valueWrapper == null) {
			throw new IllegalArgumentException("Argument valueWrapper can't be null value");
		}
		
		if(mapOfStacks.containsKey(name)) {
			MultistackEntry newMultistackEntry = new MultistackEntry(valueWrapper);
			
			newMultistackEntry.next = mapOfStacks.get(name);
			
			mapOfStacks.replace(name, mapOfStacks.get(name), newMultistackEntry);
		} else {
			mapOfStacks.put(name, new MultistackEntry(valueWrapper));
		}
	}
	
	/**
	 * Method pops {@link ValueWrapper} from stack for given key. Argument name is key.
	 * Complexity of the method is O(1).
	 * 
	 * @param name key
	 * @return {@link ValueWrapper} from top of the stack for given key
	 */
	public ValueWrapper pop(String name) {
		if(name == null) {
			throw new IllegalArgumentException("Argument name can't be null value");
		}
		
		if(isEmpty(name)) {
			throw new IllegalStateException("Can't pop from empty stack.");
		}
		
		ValueWrapper valueWrapper = mapOfStacks.get(name).valueWrapper;
		
		mapOfStacks.replace(name, mapOfStacks.get(name), mapOfStacks.get(name).next);
		
		return valueWrapper;
	}
	
	/**
	 * Method peeks stack for the key accepted as argument name.
	 * Complexity of the method is O(1).
	 * 
	 * @param name key 
	 * @return {@link ValueWrapper} from top of the stack for given key
	 */
	public ValueWrapper peek(String name) {
		if(name == null) {
			throw new IllegalArgumentException("Argument name can't be null value");
		}
		
		if(isEmpty(name)) {
			throw new IllegalStateException("Can't peek empty stack.");
		}
		
		return mapOfStacks.get(name).valueWrapper;
	}
	
	/**
	 * Method checks if stack for key name is empty.
	 * 
	 * @param name key
	 * @return true if stack is empty, false otherwise
	 */
	public boolean isEmpty(String name) {
		if(name == null) {
			throw new IllegalArgumentException("Argument name can't be null value");
		}
		
		return mapOfStacks.get(name) == null;
	}

	/**
	 * Class represents one element of the stack. It contains reference to the next element of
	 * the stack and referenc to the {@link ValueWrapper} object.
	 * 
	 * @author Toni Martinčić
	 * @version 1.0
	 */
	private static class MultistackEntry {
		
		/**
		 * Reference to next {@link MultistackEntry} in stack.
		 */
		MultistackEntry next;
		
		/**
		 * Instance of {@link ValueWrapper} which contains value of this {@link MultistackEntry}.
		 */
		ValueWrapper valueWrapper;
		
		/**
		 * Constructor which accepts one argument, instance of {@link ValueWrapper}
		 * 
		 * @param valueWrapper accepted instance of {@link ValueWrapper}
		 */
		public MultistackEntry(ValueWrapper valueWrapper) {
			this.valueWrapper = valueWrapper;
		}
	}

}
