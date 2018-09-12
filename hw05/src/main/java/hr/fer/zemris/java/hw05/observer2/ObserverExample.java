package hr.fer.zemris.java.hw05.observer2;

/**
 * This class test functionality of {@link IntegerStorage} and {@link IntegerStorageObserver}.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class ObserverExample {
	
	/**
	 * Method from which program starts.
	 * 
	 * @param args arguments of main method, unused
	 */
	public static void main(String[] args) {
		IntegerStorage istorage = new IntegerStorage(20);
		
		IntegerStorageObserver observer = new SquareValue();
		istorage.addObserver(observer);
		
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue(1));
		istorage.addObserver(new DoubleValue(2));
		istorage.addObserver(new DoubleValue(2));

		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);
		
		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);
	}

}
