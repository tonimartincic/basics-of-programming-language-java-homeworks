package hr.fer.zemris.java.hw02;

/**
 * Class ComplexNumber represents an unmodifiable complex number. 
 * Both real and imaginary parts are double numbers.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class ComplexNumber {
	
	/**
	 * Real part of complex number
	 */
	private final double real;
	
	/**
	 * Imaginary part of complex number
	 */
	private final double imaginary;
	
	/**
	 * Constructor which accepts two arguments, real and imaginary part of complex number.
	 * 
	 * @param real real part of complex number
	 * @param imaginary imaginary part of complex number
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}
	
	/**
	 * Method which creates complex number from given real part of complex number.
	 * Imaginary part is set to 0.0.
	 * 
	 * @param real real part of complex number
	 * @return created complex number
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0.0);
	}
	
	/**
	 * Method which creates complex number from given imaginary part of complex number.
	 * Real part is set to 0.0
	 * 
	 * @param imaginary imaginary part of complex number
	 * @return created complex number
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0.0, imaginary);
	}
	
	/**
	 * Method creates complex number from given magnitude and angle of complex number.
	 * 
	 * @param magnitude magnitude od complex number
	 * @param angle angle of complex number
	 * @return created complex number
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		return new ComplexNumber(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
	}
	
	/**
	 * Method accepts string represetation of complex number and converts it in complex number.
	 * It can accept string that represents complex number with both real and imaginary part or 
	 * only with imaginary part. It can accept number with only real part.
	 * 
	 * @param s string represetation of complex number
	 * @return complex number
	 * @throws IllegalArgumentException if argument s is null or empty string
	 * IllegalArgumentException is thrown
	 */
	public static ComplexNumber parse(String s) throws IllegalArgumentException {
		if(s.equals(null)) {
			throw new IllegalArgumentException("String must not be null.");
		}
		
		if(s.equals("")) {
			throw new IllegalArgumentException("String must not be empty string.");
		}
		
		String[] parts = s.split("[+-]");
		
		String[] newArray = new String[3];
		int i = 0;
		for(i = 0; i < parts.length; i++) {
			newArray[i] = parts[i];
		}
		for(int j = i; j < newArray.length; j++) {
			newArray[j] = null;
		}
		
		parts = newArray;
		
		boolean realPositive = true;
		boolean imaginaryPositive = true;
		
		if (s.charAt(0) == '-' && !parts[1].contains("i")) {
		    realPositive = false;
		} else if(s.charAt(0) == '-' && parts[1].contains("i")) {
			imaginaryPositive = false;
		} 
		if (imaginaryPositive && s.substring(1).contains("-")) {
			imaginaryPositive = false;
		}
		
		if(s.charAt(0) == '-') {
			parts[0] = parts[1];
			parts[1] = parts[2];
		}
		
		double real = 0.0;
		double imaginary = 0.0;
		
		if(parts[0] != null && parts[1] != null) { //Both real and imaginary parts exist
			real = Double.parseDouble((realPositive ? "" : "-") + parts[0]);
			
			parts[1] = parts[1].substring(0, parts[1].length() - 1);
			imaginary = Double.parseDouble((imaginaryPositive ? "+" : "-") + parts[1]);
		} else if(!parts[0].contains("i")) { //Only real part exists
			real = Double.parseDouble((realPositive ? "" : "-") + parts[0]);
			
			imaginary = 0.0;
		} else { //Only imaginary part exists
			parts[0] = parts[0].substring(0, parts[0].length() - 1);
			imaginary = Double.parseDouble((imaginaryPositive ? "+" : "-") + parts[0]);
		}
		
		return new ComplexNumber(real, imaginary);
	}
	
	/**
	 * Getter for real part of complex number.
	 * 
	 * @return real part of complex number
	 */
	public double getReal() {
		return real;
	}
	
	/**
	 * Getter for imaginary part of complex number.
	 * 
	 * @return imaginary part of complex number
	 */
	public double getImaginary() {
		return imaginary;
	}
	
	/**
	 * Getter for magnitude of complex number.
	 * 
	 * @return magnitude of complex number
	 */
	public double getMagnitude() {
		return Math.sqrt(Math.pow(real, 2.0) + Math.pow(imaginary, 2.0));
	}
	
	/**
	 * Getter for angle of complex number.
	 * 
	 * @return angle of complex number
	 */
	public double getAngle() {
		double angle = Math.atan2(imaginary, real);
		
		return angle > 0.0 ? angle : angle + 2 * Math.PI;
	}
	
	/**
	 * Method adds this and the accepted complex numbers.
	 * 
	 * @param c complex number which is added to this
	 * @return new complex number which is result of adding this and given comlex numbers
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(this.real + c.real, this.imaginary + c.imaginary);
	}
	
	/**
	 * Method substracts this and the accepted complex numbers.
	 * 
	 * @param c complex number which is substracted from this
	 * @return new complex number which is result of substracting this and the accepted 
	 * complex numbers
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(this.real - c.real, this.imaginary - c.imaginary);
	}
	
	/**
	 * Method multiply this and the accepted complex numbers.
	 * 
	 * @param c complex number which is multiplied with this
	 * @return new complex number which is result of multiplying this and accepted
	 * complex numbers
	 */
	public ComplexNumber mul(ComplexNumber c) {
		return new ComplexNumber(this.real * c.real - this.imaginary * c.imaginary,
								 this.real * c.imaginary + this.imaginary * c.real);
	}
	
	/**
	 * Method divides this and the accepted complex numbers.
	 * 
	 * @param c complex number with which is this complex number divided
	 * @return new complex number which is result of dividing this complex number
	 * with accepted complex number
	 */
	public ComplexNumber div(ComplexNumber c) {
		double magnitude1 = this.getMagnitude();
		double angle1 = this.getAngle();
		double magnitude2 = c.getMagnitude();
		double angle2 = c.getAngle();
		
		return fromMagnitudeAndAngle(magnitude1 / magnitude2, angle1 - angle2);
		
	}
	
	/**
	 * Method calculates n-th power of this complex number. 
	 * 
	 * @param n power which is calculated
	 * @return new complex number which is power of this complex number
	 * @throws IllegalArgumentException if n is less than 0 IllegalArgumentException is thrown 
	 */
	public ComplexNumber power(int n) throws IllegalArgumentException {
		if(n < 0) {
			throw new IllegalArgumentException("n must be zero or positive number.");
		}
		
		double magnitude = Math.pow(getMagnitude(), n);
		double angle = getAngle() * n;
		
		while(angle > (2 * Math.PI)) {
			angle -= 2 * Math.PI;
		}
		
		return fromMagnitudeAndAngle(magnitude, angle);
	}
	
	/**
	 * Method finds all n-th roots of this complex number and returns all 
	 * roots as array.
	 * 
	 * @param n root which method finds
	 * @return array of n-th roots of accepted complex number
	 */
	public ComplexNumber[] root(int n) {
		if(n <= 0) {
			throw new IllegalArgumentException("n must be positive number.");
		}
		
		double magnitude = this.getMagnitude();
		double angle = this.getAngle();
		
		ComplexNumber[] roots = new ComplexNumber[n];
		
		for(int k = 0; k < n; k++) {
			roots[k] = fromMagnitudeAndAngle(Math.pow(magnitude, 1.0 / n), 
											 (angle + 2 * k * Math.PI) / n);
		}
		
		return roots;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		if(real != 0.0) {
			sb.append(real);
		}
		
		if(imaginary != 0.0) {
			if(imaginary > 0.0) {
				sb.append('+').append(imaginary).append('i');
			} else {
				sb.append(imaginary).append('i');
			}
		}
		
		return sb.toString();
	}
	
}
