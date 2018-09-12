package hr.fer.zemris.java.hw05.observer1;

import java.util.ArrayList;
import java.util.List;

/**
 * Class stores integer value and has list of {@link IntegerStorageObserver}. Each time the value 
 * change valueChanged method is called for each observer.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class IntegerStorage {

	/**
	 * Value of the integer storage.
	 */
	private int value;
	
	/**
	 * Observers of the integer storage.
	 */
	private List<IntegerStorageObserver> observers; 

	/**
	 * Constructor which accepts one argument, initial value of the integer storage.
	 * 
	 * @param initialValue initial value of the integer storage
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
	}

	/**
	 * Method adds instance of IntegerStorageObserver into the internal list of 
	 * IntegerStorageObservers.
	 * 
	 * @param observer IntegerStorageObserver which is added into the list
	 * @throws IllegalArgumentException if observer is null {@link IllegalArgumentException} is thrown.
	 */
	public void addObserver(IntegerStorageObserver observer) {
		if(observer == null) {
			throw new IllegalArgumentException("Null value given as observer.");
		}
		
		
		
		if(observers == null) {
			observers = new ArrayList<>();
		}
		
		if(observers.contains(observer)) {
			return;
		}
		
		observers.add(observer);
	} 

	/**
	 * Method removes given IntegerStorageObserver from internal list of IntegerStorageObservers.
	 * 
	 * @param observer IntegerStorageObserver which is removed
	 * @throws IllegalStateException if list is empty {@link IllegalStateException} is thrown
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		if(observers == null) {
			throw new IllegalStateException("Integer storage has no observers.");
		}
		
		observers.remove(observer);
	}

	/**
	 * Method removes all observers from list.
	 */
	public void clearObservers() {
		observers = null;
	}

	/**
	 * Getter for the value.
	 * 
	 * @return value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Method sets value to the accepted value if accepted value is not equals to the
	 * current value. Method calls method valueChanged for each observer in the list.
	 * 
	 * @param value new value
	 */
	public void setValue(int value) {
		if (this.value != value) {
			this.value = value;
			
			if (observers != null) {
				List<IntegerStorageObserver> temp = new ArrayList<>(observers);
				
				for (IntegerStorageObserver observer : temp) {
					observer.valueChanged(this);
				}
			}
		}
	}

}
