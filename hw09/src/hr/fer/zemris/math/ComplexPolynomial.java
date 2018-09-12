package hr.fer.zemris.math;

/**
 * Class is model of the polynomial of the complex numbers. 
 * Polynomial is like: f(z) = zn*z^n + ... + z1*z + z0 where zn, ... , z0 are coefficients of powers. Coefficients
 * of the polynomial are accepted in constructor.
 * All coefficients are complex numbers and the argument z is the complex number. Order of the polynomial is n.
 * 
 * Class contains method which multiplies this polynomial with accepted polynomial, method which derives this 
 * polynomial and the method which calculates value of this polynomial for the accepted complex number.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class ComplexPolynomial {
	
	/**
	 * Coefficients of the polynomial which are accepted in the constructor.
	 */
	private Complex[] factors;
	
	/**
	 * Constructor which accepts array of coefficients of polynomial.
	 * 
	 * @param factors array of coefficients of polynomial
	 * @throws IllegalArgumentException if the argument factors is the null value
	 */
	public ComplexPolynomial(Complex ... factors) {
		if(factors == null) {
			throw new IllegalArgumentException("Argument factors can not be null.");
		}
		
		this.factors = factors;
	}
	
	/**
	 * Method returns order of the polynomial.
	 * 
	 * @return order of the polynomial
	 */
	public short order() {
		return (short) (factors.length - 1);
	}
	
	/**
	 * Method multiplies this poliynomial and the accepted polynomial and returns new polynomial which is result
	 * of the operation.
	 * 
	 * @param p accepted polynomial
	 * @return new polynomial which is result of the multiplying this and accepted polynomials
	 * @throws IllegalArgumentException if the accepted polynomial is the null value
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		if(p == null) {
			throw new IllegalArgumentException("Accepted polynomial can not be null value.");
		}
		
		Complex[] newFactors = new Complex[this.order() + p.order() + 1];
		
		for(int i = 0; i < factors.length; i++) {
			for(int j = 0; j < p.order() + 1; j++) {
				if(newFactors[i + j] == null) {
					newFactors[i + j] = factors[i].multiply(p.getFactorAt(j)); 
					
					continue;
				}
				
				newFactors[i + j] = newFactors[i + j].add(factors[i].multiply(p.getFactorAt(j)));
			}
		}
		
		return new ComplexPolynomial(newFactors);
	}
	
	/**
	 * Method derives this polynomial and returns new polynomial which is result of the operation.
	 * 
	 * @return new polynomial which is result of the deriving this polynomial
	 */
	public ComplexPolynomial derive() {
		Complex[] newFactors = new Complex[factors.length - 1];
		
		for(int i = 1; i < factors.length; i++) {
			newFactors[i - 1] = factors[i].multiply(new Complex(i, 0));
		}
		
		return new ComplexPolynomial(newFactors);
	}
	
	/**
	 * Method calculates and returns value of the polynomial for the accepted argument z. Calculated value is the 
	 * new complex number.
	 * 
	 * @param z accepted complex number
	 * @return value of the polynomial for the accepted argument z
	 * @throws IllegalArgumentException if the argument z is the null value
	 */
	public Complex apply(Complex z) {
		if(z == null) {
			throw new IllegalArgumentException("Argument z can not be null.");
		}
		
		Complex result = factors[0];
		for(int i = 1; i < factors.length; i++) {
			result = result.add(factors[i].multiply(z.power(i)));
		}
		
		return result;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for(int i = factors.length - 1; i >= 0; i--) {
			if(i != factors.length - 1) {
				sb.append("+");
			}
			
			sb.append(factors[i]);
			sb.append("z^").append(i);
		}
		
		return sb.toString();
	}
	
	/**
	 * Getter for the factor on the accepted index.
	 * 
	 * @param index index of factor
	 * @return factor on the accepted index
	 */
	public Complex getFactorAt(int index) {
		if(index < 0 || index > factors.length - 1) {
			throw new IllegalArgumentException("Index is invalid.");
		}
		return factors[index];
	}

}
