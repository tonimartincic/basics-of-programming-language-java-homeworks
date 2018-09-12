package hr.fer.zemris.java.hw05.observer2;

/**
 * Class has informations about change of the value of integer storage. It has reference to the
 * {@link IntegerStorage} and value of the integer storage before and after change.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class IntegerStorageChange {
	
	/**
	 * Integer storage.
	 */
	private IntegerStorage integerStorage;
	
	/**
	 * Value before change.
	 */
	private int valueBeforeChange;
	
	/**
	 * Current value.
	 */
	private int currentValue;
	
	/**
	 * Constructor which accepts three arguments; integer storage, value before change and
	 * current value.
	 * 
	 * @param integerStorage integer storage
	 * @param valueBeforeChange value before change
	 * @param currentValue current value
	 * @throws IllegalArgumentException if integer storage is null {@link IllegalArgumentException}
	 * is thrown
	 */
	public IntegerStorageChange(
			IntegerStorage integerStorage, int valueBeforeChange, int currentValue) {
		super();
		
		if(integerStorage == null) {
			throw new IllegalArgumentException("Null value can't be given as IntegerStorage.");
		}
		
		this.integerStorage = integerStorage;
		this.valueBeforeChange = valueBeforeChange;
		this.currentValue = currentValue;
	}

	/**
	 * Getter for the integer storage.
	 * 
	 * @return integer storage
	 */
	public IntegerStorage getIntegerStorage() {
		return integerStorage;
	}
	
	/**
	 * Getter for the value before change.
	 * 
	 * @return value before change
	 */
	public int getValueBeforeChange() {
		return valueBeforeChange;
	}
	
	/**
	 * Getter for the current value.
	 * 
	 * @return current value
	 */
	public int getCurrentValue() {
		return currentValue;
	}
	
}
