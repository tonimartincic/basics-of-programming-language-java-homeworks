package hr.fer.zemris.java.fractals;

import static org.junit.Assert.*;

import org.junit.Test;

import hr.fer.zemris.math.Complex;

@SuppressWarnings("javadoc")
public class NewtonTest {

	private static final double DELTA = 1E-6;

	@Test
	public void testParse1() {
		String line = "1";
		
		Complex complex = Newton.parseStringToComplex(line);
		
		assertEquals(1, complex.getRe(), DELTA);
		assertEquals(0, complex.getIm(), DELTA);
	}
	
	@Test
	public void testParse2() {
		String line = "-1 + i0";
		
		Complex complex = Newton.parseStringToComplex(line);
		
		assertEquals(-1, complex.getRe(), DELTA);
		assertEquals(0, complex.getIm(), DELTA);
	}
	
	@Test
	public void testParse3() {
		String line = "i";
		
		Complex complex = Newton.parseStringToComplex(line);
		
		assertEquals(0, complex.getRe(), DELTA);
		assertEquals(1, complex.getIm(), DELTA);
	}
	
	@Test
	public void testParse4() {
		String line = "0 - i1";
		
		Complex complex = Newton.parseStringToComplex(line);
		
		assertEquals(0, complex.getRe(), DELTA);
		assertEquals(-1, complex.getIm(), DELTA);
	}
	
	@Test
	public void testParse5() {
		String line = "0";
		
		Complex complex = Newton.parseStringToComplex(line);
		
		assertEquals(0, complex.getRe(), DELTA);
		assertEquals(0, complex.getIm(), DELTA);
	}
	
	@Test
	public void testParse6() {
		String line = "i0";
		
		Complex complex = Newton.parseStringToComplex(line);
		
		assertEquals(0, complex.getRe(), DELTA);
		assertEquals(0, complex.getIm(), DELTA);
	}
	
	@Test
	public void testParse7() {
		String line = "0 + i0";
		
		Complex complex = Newton.parseStringToComplex(line);
		
		assertEquals(0, complex.getRe(), DELTA);
		assertEquals(0, complex.getIm(), DELTA);
	}
	
	@Test
	public void testParse8() {
		String line = "0 - i0";
		
		Complex complex = Newton.parseStringToComplex(line);
		
		assertEquals(0, complex.getRe(), DELTA);
		assertEquals(0, complex.getIm(), DELTA);
	}
	
	@Test
	public void testParse9() {
		String line = "-i";
		
		Complex complex = Newton.parseStringToComplex(line);
		
		assertEquals(0, complex.getRe(), DELTA);
		assertEquals(-1, complex.getIm(), DELTA);
	}

}
