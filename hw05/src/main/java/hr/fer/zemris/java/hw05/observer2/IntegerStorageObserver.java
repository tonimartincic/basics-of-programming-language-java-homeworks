package hr.fer.zemris.java.hw05.observer2;

/**
 * Class which implements this interface is observer which observes instance of {@link IntegerStorage}.
 * Each time value of the {@link IntegerStorage} is changed, method valueChanged of this class is
 * called.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public interface IntegerStorageObserver {

	/**
	 * Every class which implements this interface must override this method. This method is called
	 * from class {@link IntegerStorage} in which list is this added every time value of 
	 * {@link IntegerStorage} is changed.
	 * 
	 * @param integerStorageChange IntegerStorage which call this method
	 */
	public void valueChanged(IntegerStorageChange integerStorageChange);
	
}
