package hr.fer.zemris.java.gui.calc;

/**
 * Class which implements this interface must override one method, calculate. Method accept one double value and 
 * return second double value.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public interface IInstantOperation {
	
	/**
	 * Method accept one double value and return second double value which is result of the operation.
	 * 
	 * @param value accepted value
	 * @return result of the operation
	 */
	public abstract double calculate(double value);

}
