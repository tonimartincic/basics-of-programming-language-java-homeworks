package hr.fer.zemris.java.hw01;

import static org.junit.Assert.*;

import org.junit.Test;

public class FactorialTest {

	@Test
	public void testFactorialOfZero() {
		long factorial = Factorial.countFactorial(0);
		assertEquals(1, factorial);
		
	}
	
	@Test
	public void testFactorialOfOne() {
		long factorial = Factorial.countFactorial(1);
		assertEquals(1, factorial);
	}
	
	@Test
	public void testFactorialOfTwenty() {
		long factorial = Factorial.countFactorial(20);
		assertEquals(2432902008176640000l, factorial);
		
	}
	
	@Test
	public void testFactorialOfFive() {
		long factorial = Factorial.countFactorial(5);
		assertEquals(120, factorial);
	}

}
