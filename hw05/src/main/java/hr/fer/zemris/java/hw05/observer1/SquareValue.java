package hr.fer.zemris.java.hw05.observer1;

/**
 * Instances of SquareValue class write a square of the integer stored in the IntegerStorage to 
 * the standard output. But the stored integer itself is not modified.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class SquareValue implements IntegerStorageObserver {

	@Override
	public void valueChanged(IntegerStorage istorage) {
		if(istorage == null) {
			throw new IllegalArgumentException("Null value can't be given as IntegerStorage.");
		}
		
		System.out.printf("Provided new value: %d, square is %d%n", 
				istorage.getValue(), (int)Math.pow(istorage.getValue(), 2));
	}

}
