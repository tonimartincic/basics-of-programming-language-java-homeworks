package hr.fer.zemris.math;

/**
 * Class is model of the polynomial of the complex numbers. 
 * Polynomial is like: f(z) = (z-z1)*(z-z2)*...*(z-zn) where z1, ..., zn are roots of the polynomial. Roots of the 
 * polynomial are accepted in the constructor. 
 * Order of the polynomial is n. Parameter z is the complex number.
 * 
 * Class contains method apply which calculates value of the polynomial for the accepted argument z, method 
 * indexOfClosestRootFor which returns index of the closest root for the accepted complex number and the method
 * toComplexPolynomial which converts this {@link ComplexRootedPolynomial} into the {@link ComplexPolynomial}. 
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class ComplexRootedPolynomial {
	
	/**
	 * Roots of the polynomial which are accepted int the constructor.
	 */
	private Complex[] roots;
	
	/**
	 * Constructor which accepts one argument, array of the roots of the polynomial.
	 * 
	 * @param roots array of the roots of the polynomial
	 * @throws IllegalArgumentException if the argument roots is the null value
	 */
	public ComplexRootedPolynomial(Complex ... roots) {
		if(roots == null) {
			throw new IllegalArgumentException("Argument roots can not be null.");
		}
		
		this.roots = roots;
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
		
		Complex result = z.sub(roots[0]);
		for(int i = 1; i < roots.length; i++) {
			result = result.multiply(z.sub(roots[i]));
		}
		
		return result;
	}
	
	/**
	 * Method converts this {@link ComplexRootedPolynomial} into the {@link ComplexPolynomial}.
	 * 
	 * @return this {@link ComplexRootedPolynomial} as {@link ComplexPolynomial}
	 */
	public ComplexPolynomial toComplexPolynom() {
		Complex[] firstFactors = new Complex[2];
		firstFactors[0] = roots[0].negate();
		firstFactors[1] = new Complex(1, 0);
		ComplexPolynomial result = new ComplexPolynomial(firstFactors);
				
		for(int i = 1; i < roots.length; i++) {
			Complex[] tempFactors = new Complex[2];
			tempFactors[0] = roots[i].negate();
			tempFactors[1] = new Complex(1, 0);
			ComplexPolynomial temp = new ComplexPolynomial(tempFactors);
			
			result = result.multiply(temp);
		}
		
		return result;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < roots.length; i++) {
			if(i != 0) {
				sb.append("*");
			}
			
			sb.append("(z-").append(roots[i]).append(")");
		}
		
		return sb.toString();
	}
	
	/**
	 * Method returns index of the closest root for the accepted complex number from the array of the roots if the 
	 * closest distance is less than accepted argument treshold. If the distance is the greater than accepted argument
	 * treshold, -1 is returned as index. 
	 * 
	 * @param z accepted complex number 
	 * @param treshold accepted treshold
	 * @return index of the closest root if the distance between closest root and the accepted complex number is less
	 * than accepted argument treshold, -1 otherwise
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		if(z == null) {
			throw new IllegalArgumentException("Argument z can not be null.");
		}
		
		if(treshold <= 0.0) {
			throw new IllegalArgumentException("Argument treshold must be greater then 0.");
		}
		
		double min = z.sub(roots[0]).module();
		int indexOfClosestRoot = 0;
		for(int i = 1; i < roots.length; i++) {
			if(z.sub(roots[i]).module() < min) {
				min = z.sub(roots[i]).module();
				
				indexOfClosestRoot = i;
			}
		}
		
		return min < treshold ? indexOfClosestRoot : -1;
	}
	
	/**
	 * Getter for the root on the accepted index.
	 * 
	 * @param index index of root
	 * @return root on the accepted index
	 */
	public Complex getRootAt(int index) {
		if(index < 0 || index > roots.length - 1) {
			throw new IllegalArgumentException("Invalid index");
		}
		
		return roots[index];
	}
}
