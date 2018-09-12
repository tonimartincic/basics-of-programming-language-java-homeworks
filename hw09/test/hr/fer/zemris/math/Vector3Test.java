package hr.fer.zemris.math;

import static org.junit.Assert.*;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class Vector3Test {

	private static final double DELTA = 1E-6;

	@Test
	public void testNorm() {
		Vector3 vector = new Vector3(4.0, 4.0, 2.0);
		
		assertEquals(6.0, vector.norm(), DELTA);
	}
	
	@Test
	public void testNormalized() {
		Vector3 vector = new Vector3(4.0, 4.0, 2.0);
		
		assertEquals(6.0, vector.norm(), DELTA);
		
		Vector3 normalizedVector = vector.normalized();
		
		assertArrayEquals(new double[]{4.0 / 6.0, 4.0 / 6.0, 2.0 / 6.0}, 
				          normalizedVector.toArray(), 
				          DELTA);
	}
	
	@Test
	public void testAdd() {
		Vector3 vector1 = new Vector3(4.0, 4.0, 2.0);
		Vector3 vector2 = new Vector3(3.0, 5.0, 7.0);
		
		Vector3 vector3 = vector1.add(vector2);
		
		assertArrayEquals(new double[]{7.0, 9.0, 9.0}, vector3.toArray(), DELTA);
	}

	@Test
	public void testSub() {
		Vector3 vector1 = new Vector3(4.0, 4.0, 2.0);
		Vector3 vector2 = new Vector3(3.0, 5.0, 7.0);
		
		Vector3 vector3 = vector1.sub(vector2);
		
		assertArrayEquals(new double[]{1.0, -1.0, -5.0}, vector3.toArray(), DELTA);
	}
	
	@Test
	public void testDot() {
		Vector3 vector1 = new Vector3(4.0, 4.0, 2.0);
		Vector3 vector2 = new Vector3(3.0, 5.0, 7.0);
		
		assertEquals(46.0, vector1.dot(vector2), DELTA);
	}
	
	@Test
	public void testCross() {
		Vector3 vector1 = new Vector3(4.0, 4.0, 2.0);
		Vector3 vector2 = new Vector3(3.0, 5.0, 7.0);
		
		Vector3 vector3 = vector1.cross(vector2);
		
		assertArrayEquals(new double[]{18.0, -22.0, 8.0}, vector3.toArray(), DELTA);
	}
	
	@Test
	public void testScale() {
		Vector3 vector1 = new Vector3(4.0, 4.0, 2.0);
		
		Vector3 vector2 = vector1.scale(-2);
		
		assertArrayEquals(new double[]{-8.0, -8.0, -4.0}, vector2.toArray(), DELTA);
	}
	
	@Test
	public void testCosAngle() {
		Vector3 vector1 = new Vector3(4.0, 4.0, 2.0);
		Vector3 vector2 = new Vector3(3.0, 5.0, 7.0);
		
		double cosAngle = vector1.cosAngle(vector2);
		
		assertEquals(0.84152599, cosAngle, DELTA);
	}
}
