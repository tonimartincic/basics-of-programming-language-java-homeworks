package hr.fer.zemris.java.hw02.demo;

import hr.fer.zemris.java.hw02.ComplexNumber;

/**
 * Class tests some of functionality of class ComplexNumber.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class ComplexDemo {
	
	/**
	 * The method which starts the execution of this program.
	 * @param args command line arguments. Unused.
	 */
	public static void main(String[] args) {
		
		ComplexNumber c1 = new ComplexNumber(2, 3);
		ComplexNumber c2 = ComplexNumber.parse("2.5-3i");
		ComplexNumber c3 = c1.add(ComplexNumber.fromMagnitudeAndAngle(2, 1.57)).div(c2).power(3).root(2)[1];
		System.out.println(c3);



	}

}
