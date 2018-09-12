package hr.fer.zemris.java.hw16.vectors;

/**
 * Class represents vector which dimension is not fixed.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class Vector {
	
	/**
	 * Components of the vector.
	 */
	private double[] values;
	
	/**
	 * Constructor which accepts only one argument, values.
	 * 
	 * @param values values
	 */
	public Vector(double[] values) {
		this.values = values;
	}

	/**
	 * Getter for the values.
	 * 
	 * @return values
	 */
	public double[] getValues() {
		return values;
	}

	/**
	 * Setter for the values.
	 * 
	 * @param values values
	 */
	public void setValues(double[] values) {
		this.values = values;
	}

	/**
	 * Method returns dimension of the vector.
	 * 
	 * @return dimension of the vector
	 */
	public int getDimension() {
		return values.length;
	}
	
	/**
	 * Method returns component of the vector for the accepted index.
	 * 
	 * @param index index
	 * @return component of the vector for the accepted index
	 */
	public double getComponentAtIndex(int index) {
		if(index < 0 || index > values.length - 1) {
			throw new IllegalArgumentException("Illegal index!");
		}
		
		return values[index];
	}
	
	/**
	 * Method returns norm of the vector.
	 * 
	 * @return norm of the vector
	 */
	public double norm() {
		double sumOfSquares = 0.0;
		
		for(int i = 0; i < values.length; i++) {
			sumOfSquares += Math.pow(values[i], 2);
		}
		
		return Math.sqrt(sumOfSquares);
	}
	
	/**
	 * Method gets scalar product of this vector and vector accepted as argument.
	 * 
	 * @param other other vector
	 * @return scalar product of this vector and vector accepted as argument
	 */
	public double dot(Vector other) {
		if(other == null) {
			throw new IllegalArgumentException("Other vector can not be null.");
		}
		
		if(other.getDimension() != this.values.length) {
			throw new IllegalArgumentException("Dimensions of the vectors are not equal!");
		}
		
		double result = 0.0;
		
		for(int i = 0; i < values.length; i++) {
			result += values[i] * other.getComponentAtIndex(i);
		}
		
		return result;
	}
	
	/**
	 * Method returns cos value between this vector and vector accepted as argument.
	 * 
	 * @param other other vector
	 * @return cos value between this vector and vector accepted as argument
	 */
	public double cos(Vector other) {
		return this.dot(other) / (this.norm() * other.norm());
	}
	
}
