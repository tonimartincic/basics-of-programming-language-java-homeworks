package hr.fer.zemris.math;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class ComplexTest {
	
	private static final double DELTA = 1E-6;

	@Test
	public void testModule() {
		Complex complex = new Complex(3.0, 4.0);
		
		assertEquals(5.0, complex.module(), DELTA);
	}
	
	@Test
	public void testAngle() {
		Complex complex = new Complex(3.0, 4.0);
		
		assertEquals(0.927295, complex.angle(), DELTA);
	}
	
	@Test
	public void testMultiply() {
		Complex complex1 = new Complex(3.0, 4.0);
		Complex complex2 = new Complex(2.0, -3.0);
		
		Complex complex3 = complex1.multiply(complex2);
		
		assertEquals(18.0,  complex3.getRe(), DELTA);
		assertEquals(-1.0, complex3.getIm(), DELTA);
	}
	
	@Test
	public void testDivide() {
		Complex complex1 = new Complex(3.0, 4.0);
		Complex complex2 = new Complex(2.0, -3.0);
		
		Complex complex3 = complex1.divide(complex2);
		
		assertEquals(-0.46153846,  complex3.getRe(), DELTA);
		assertEquals(1.30769230, complex3.getIm(), DELTA);
	}
	
	@Test
	public void testAdd() {
		Complex complex1 = new Complex(3.0, 4.0);
		Complex complex2 = new Complex(2.0, -3.0);
		
		Complex complex3 = complex1.add(complex2);
		
		assertEquals(5.0,  complex3.getRe(), DELTA);
		assertEquals(1.0, complex3.getIm(), DELTA);
	}
	
	@Test
	public void testSub() {
		Complex complex1 = new Complex(3.0, 4.0);
		Complex complex2 = new Complex(2.0, -3.0);
		
		Complex complex3 = complex1.sub(complex2);
		
		assertEquals(1.0,  complex3.getRe(), DELTA);
		assertEquals(7.0, complex3.getIm(), DELTA);
	}
	
	@Test
	public void testNegate() {
		Complex complex1 = new Complex(3.0, 4.0);
		
		Complex complex2 = complex1.negate();
		
		assertEquals(-3.0, complex2.getRe(), DELTA);
		assertEquals(-4.0, complex2.getIm(), DELTA);
	}
	
	@Test
	public void testPower() {
		Complex complex = new Complex(3.0, 4.0);
		int n = 5;
		
		Complex complex2 = complex.power(n);
		
		assertEquals(-237.0, complex2.getRe(), DELTA);
		assertEquals(-3116.0, complex2.getIm(), DELTA);
	}
	
	@Test
	public void testRoot() {
		Complex complex = new Complex(3.0, 4.0);
		int n = 5;
		
		List<Complex> roots = complex.root(n);
		
		assertEquals(1.3560696, roots.get(0).getRe(), DELTA);
		assertEquals(0.25441901, roots.get(0).getIm(), DELTA);
		
		assertEquals(0.17708171, roots.get(1).getRe(), DELTA);
		assertEquals(1.36831867, roots.get(1).getIm(), DELTA);
		
		assertEquals(-1.246627, roots.get(2).getRe(), DELTA);
		assertEquals(0.59124844, roots.get(2).getIm(), DELTA);
		
		assertEquals(-0.94753965, roots.get(3).getRe(), DELTA);
		assertEquals(-1.00290704, roots.get(3).getIm(), DELTA);
		
		assertEquals(0.66101542, roots.get(4).getRe(), DELTA);
		assertEquals(-1.2110790, roots.get(4).getIm(), DELTA);
	}
}
