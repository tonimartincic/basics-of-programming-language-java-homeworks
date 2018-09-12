package hr.fer.zemris.java.gui.calc;

/**
 * Class which implements this interface must override one method, calculate. Method accepts two double values and
 * returns third double value.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public interface IOperation {
	
	/**
	 * Method accepts two double values and returns third double value which is result of the operation.
	 * 
	 * @param value1 first accepted value
	 * @param value2 second accepted value
	 * @return result of the operation
	 */
	public abstract double calculate(double value1, double value2);

}
