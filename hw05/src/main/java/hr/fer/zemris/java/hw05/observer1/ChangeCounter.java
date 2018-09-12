package hr.fer.zemris.java.hw05.observer1;

/**
 * Instances of ChangeCounter counts and writes to the standard output the number of times the
 * value stored has been changed since the registration. 
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class ChangeCounter implements IntegerStorageObserver {
	
	/**
	 * Number of times the stored value has been changed.
	 */
	private int numberOfValueChanges;

	@Override
	public void valueChanged(IntegerStorage istorage) {
		if(istorage == null) {
			throw new IllegalArgumentException("Null value can't be given as IntegerStorage.");
		}
		
		numberOfValueChanges++;
		
		System.out.printf("Number of value changes since tracking: %d%n", numberOfValueChanges);
	}

}
