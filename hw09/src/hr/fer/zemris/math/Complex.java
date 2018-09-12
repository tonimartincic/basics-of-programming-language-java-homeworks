package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents model of the complex number. Complex number is unmodifiable  and in the constructor it gets
 * real and imaginary part. All operations which are called on complex number return new complex number which is the 
 * result of the operation. 
 * 
 * Class contains method which returns module of the complex number, method which returns angle of the complex number,
 * method which multiplies two complex numbers, method which divides two complex numbers, method which adds two
 * complex numbers, method which subs two complex numbers, method which negates complex number, method which 
 * calculates n-th power of the complex number and method which calculates n-th roots of the complex number.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class Complex {
	
	/**
	 * Complex number with both real and imaginary parts set to 0.
	 */
	public static final Complex ZERO = new Complex(0,0);
	
	/**
	 * Complex number which real part is 1 and imaginary part is 0.
	 */
	public static final Complex ONE = new Complex(1,0);
	
	/**
	 * Complex number which real part is -1 and imaginary part is 0.
	 */
	public static final Complex ONE_NEG = new Complex(-1,0);
	
	/**
	 * Complex number which real part is 0 and imaginary part is 1.
	 */
	public static final Complex IM = new Complex(0,1);
	
	/**
	 * Complex number which real part is 0 and imaginary part is -1.
	 */
	public static final Complex IM_NEG = new Complex(0,-1);
	
	/**
	 * Real part of the complex number.
	 */
	private final double re;
	
	/**
	 * Imaginary part of the complex number.
	 */
	private final double im;
	
	/**
	 * Constructor which accepts no arguments and sets both real and imaginary part of the complex number to 0.
	 */
	public Complex() {
		this(ZERO.getRe(), ZERO.getIm());
	}
	
	/**
	 * Constructor which accepts two arguments, real and imaginary parts of the complex number.
	 * 
	 * @param re real part of the complex number
	 * @param im imaginary part of the complex number
	 */
	public Complex(double re, double im) {
		super();
		
		this.re = re;
		this.im = im;
	}
	
	/**
	 * Method calculates and returns module of the complex number.
	 * 
	 * @return module of the complex number
	 */
	public double module() {
		return Math.sqrt(re*re + im*im);
	}
	
	/**
	 * Method calculates and returns angle of the complex number.
	 * 
	 * @return angle of the complex number
	 */
	public double angle() {
		return Math.atan2(im, re);
	}
	 
	/**
	 * Method multiplies this and the accepted complex numbers and creates and returns new complex number which is
	 * result of the operation.
	 * 
	 * @param c accepted complex number
	 * @return new complex number which is result of the multiplying this and the accepted complex numbers.
	 * @throws IllegalArgumentException if accepted complex number is null value
	 */
	public Complex multiply(Complex c) {
		if(c == null) {
			throw new IllegalArgumentException("Other complex number can not be null.");
		}
		
		double newRe = re * c.getRe() - im * c.getIm();
		double newIm = re* c.getIm() + im * c.getRe();
		
		return new Complex(newRe, newIm);
	}

	/**
	 * Method divides this and the accepted complex numbers and creates and returns new complex number which is
	 * result of the operation.
	 * 
	 * @param c accepted complex number
	 * @return new complex number which is result of the dividing this and the accepted complex numbers.
	 * @throws IllegalArgumentException if accepted complex number is null value
	 */
	public Complex divide(Complex c) {
		if(c == null) {
			throw new IllegalArgumentException("Other complex number can not be null.");
		}
		
		double newModule = module() / c.module();
		double newAngle = angle() - c.angle();
		
		return new Complex(newModule * Math.cos(newAngle), newModule * Math.sin(newAngle));
	}
	
	/**
	 * Method adds this and the accepted complex numbers and creates and returns new complex number which is
	 * result of the operation.
	 * 
	 * @param c accepted complex number
	 * @return new complex number which is result of the adding this and the accepted complex numbers.
	 * @throws IllegalArgumentException if accepted complex number is null value
	 */
	public Complex add(Complex c) {
		if(c == null) {
			throw new IllegalArgumentException("Other complex number can not be null.");
		}
		
		return new Complex(re + c.getRe(), im + c.getIm());
	}

	/**
	 * Method subs this and the accepted complex numbers and creates and returns new complex number which is
	 * result of the operation.
	 * 
	 * @param c accepted complex number
	 * @return new complex number which is result of the subtracting this and the accepted complex numbers.
	 * @throws IllegalArgumentException if accepted complex number is null value
	 */
	public Complex sub(Complex c) {
		if(c == null) {
			throw new IllegalArgumentException("Other complex number can not be null.");
		}
		
		return new Complex(re - c.getRe(), im - c.getIm());
	}

	/**
	 * Method creates and returns new complex number which is negated this complex number.
	 * 
	 * @return negated this complex number
	 */
	public Complex negate() {
		return new Complex(-1 * re, -1 * im);
	}
	
	/**
	 * Method calculates and returns n-th power of this complex number.
	 * 
	 * @param n power
	 * @return n-th power of this complex number
	 * @throws IllegalArgumentException if accepted power is less then zero
	 */
	public Complex power(int n) {
		if(n < 0) {
			throw new IllegalArgumentException("Argument n must be zero or positive number.");
		}
		
		double module = Math.pow(module(), n);
		double angle = angle() * n;;
		
		return new Complex(module * Math.cos(angle), module * Math.sin(angle));
	}
	
	/**
	 * Method calculates and returns n-th roots of this complex number.
	 * 
	 * @param n root
	 * @return list of the n-th roots of this complex number
	 */
	public List<Complex> root(int n) {
		if(n < 1) {
			throw new IllegalArgumentException("Argument n must be positive integer.");
		}
		
		List<Complex> roots = new ArrayList<>();
		
		double newModule = Math.pow(module(), 1.0 / n);
		for(int k = 0; k < n; k++) {
			double newAngle = (angle() + 2 * k * Math.PI) / n; 
					
			roots.add(new Complex(newModule * Math.cos(newAngle), newModule * Math.sin(newAngle)));
		}
		
		return roots;
	}
	
	@Override
	public String toString() {
		return String.format("(%f, %f)", re, im);
	}
	
	/**
	 * Getter for the real part of the complex number.
	 * 
	 * @return real part of the complex number
	 */
	public double getRe() {
		return re;
	}
	
	/**
	 * Getter for the imaginary part of the complex number.
	 * 
	 * @return imaginary part of the complex number
	 */
	public double getIm() {
		return im;
	}

}
