package hr.fer.zemris.java.hw05.observer2;

/**
 * Instances of SquareValue class write a square of the integer stored in the IntegerStorage to 
 * the standard output. But the stored integer itself is not modified.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class SquareValue implements IntegerStorageObserver {

	@Override
	public void valueChanged(IntegerStorageChange integerStorageChange) {
		if(integerStorageChange == null) {
			throw new IllegalArgumentException(
					"Null value can't be given as IntegerStorageChange.");
		}
		
		System.out.printf("Provided new value: %d, square is %d%n", 
				integerStorageChange.getCurrentValue(), 
				(int) Math.pow(integerStorageChange.getCurrentValue(), 2));
	}
}
