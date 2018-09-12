package hr.fer.zemris.java.hw05.observer1;

/**
 * Instances of DoubleValue class write to the standard output double value of the current value
 * which is stored in subject, but only first n times since its registration with the subject.
 * After writing the double value for the n-th time, the observer automatically de-registers 
 * itself from the subject.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class DoubleValue implements IntegerStorageObserver {
	
	/**
	 * Number of times double value will be printed.
	 */
	private int n;
	
	/**
	 * Number of times double value was already printed.
	 */
	private int numberOfChanges;
	
	/**
	 * Constructor which accepts one argument, number of times double value will be printed.
	 * 
	 * @param n number of times double value will be printed
	 */
	public DoubleValue(int n) {
		if(n < 0) {
			throw new IllegalArgumentException("n can't be less than 0.");
		}
		
		this.n = n;
	}

	@Override
	public void valueChanged(IntegerStorage istorage) {
		if(istorage == null) {
			throw new IllegalArgumentException("Null value can't be given as IntegerStorage.");
		}
		
		System.out.printf("Double value: %d%n", istorage.getValue() * 2);
		
		numberOfChanges++;
		
		if(numberOfChanges < n) {
			return;
		}
		
		istorage.removeObserver(this);
	}

}
