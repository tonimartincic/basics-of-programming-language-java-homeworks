package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.Assert.*;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class ValueWrapperTest {

	@Test
	public void testExampleFromHomework1() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		
		v1.add(v2.getValue()); 
		
		assertEquals(0, v1.getValue());
		assertEquals(null, v2.getValue());
	}
	
	@Test
	public void testExampleFromHomework2() {
		ValueWrapper v3 = new ValueWrapper("1.2E1");
		ValueWrapper v4 = new ValueWrapper(Integer.valueOf(1));
		
		v3.add(v4.getValue()); 
		
		assertEquals(13.0, v3.getValue());
		assertEquals(1, v4.getValue());
	}
	
	@Test
	public void testExampleFromHomework3() {
		ValueWrapper v5 = new ValueWrapper("12");
		ValueWrapper v6 = new ValueWrapper(Integer.valueOf(1));
		
		v5.add(v6.getValue()); 
		
		assertEquals(13, v5.getValue());
		assertEquals(1, v6.getValue());
	}
	
	@Test(expected=RuntimeException.class)
	public void testExampleFromHomework4() {
		ValueWrapper v7 = new ValueWrapper("Ankica");
		ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));
		
		v7.add(v8.getValue()); 
	}
	
	@Test
	public void testSubNullNull() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		
		v1.subtract(v2.getValue()); 
		
		assertEquals(0, v1.getValue());
		assertEquals(null, v2.getValue());
	}
	
	@Test
	public void testSubDoubleInteger() {
		ValueWrapper v3 = new ValueWrapper("1.2E1");
		ValueWrapper v4 = new ValueWrapper(Integer.valueOf(1));
		
		v3.subtract(v4.getValue()); 
		
		assertEquals(11.0, v3.getValue());
		assertEquals(1, v4.getValue());
	}
	
	@Test
	public void testSubIntegerInteger() {
		ValueWrapper v5 = new ValueWrapper("12");
		ValueWrapper v6 = new ValueWrapper(Integer.valueOf(1));
		
		v5.subtract(v6.getValue()); 
		
		assertEquals(11, v5.getValue());
		assertEquals(1, v6.getValue());
	}
	
	@Test(expected=RuntimeException.class)
	public void testSubInvalidStringInteger() {
		ValueWrapper v7 = new ValueWrapper("Ankica");
		ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));
		
		v7.subtract(v8.getValue()); 
	}
	
	@Test
	public void testMulNullNull() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		
		v1.multiply(v2.getValue()); 
		
		assertEquals(0, v1.getValue());
		assertEquals(null, v2.getValue());
	}
	
	@Test
	public void testMulDoubleInteger() {
		ValueWrapper v3 = new ValueWrapper("1.2E1");
		ValueWrapper v4 = new ValueWrapper(Integer.valueOf(1));
		
		v3.multiply(v4.getValue()); 
		
		assertEquals(12.0, v3.getValue());
		assertEquals(1, v4.getValue());
	}
	
	@Test
	public void testMulIntegerInteger() {
		ValueWrapper v5 = new ValueWrapper("12");
		ValueWrapper v6 = new ValueWrapper(Integer.valueOf(1));
		
		v5.multiply(v6.getValue()); 
		
		assertEquals(12, v5.getValue());
		assertEquals(1, v6.getValue());
	}
	
	@Test(expected=RuntimeException.class)
	public void testMulInvalidStringInteger() {
		ValueWrapper v7 = new ValueWrapper("Ankica");
		ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));
		
		v7.multiply(v8.getValue()); 
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDivNullNull() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		
		v1.divide(v2.getValue()); 
	}
	
	@Test
	public void testDivDoubleInteger() {
		ValueWrapper v3 = new ValueWrapper("1.2E1");
		ValueWrapper v4 = new ValueWrapper(Integer.valueOf(1));
		
		v3.divide(v4.getValue()); 
		
		assertEquals(12.0, v3.getValue());
		assertEquals(1, v4.getValue());
	}
	
	@Test
	public void testDivIntegerInteger() {
		ValueWrapper v5 = new ValueWrapper("12");
		ValueWrapper v6 = new ValueWrapper(Integer.valueOf(1));
		
		v5.divide(v6.getValue()); 
		
		assertEquals(12, v5.getValue());
		assertEquals(1, v6.getValue());
	}
	
	@Test(expected=RuntimeException.class)
	public void testDivInvalidStringInteger() {
		ValueWrapper v7 = new ValueWrapper("Ankica");
		ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));
		
		v7.divide(v8.getValue()); 
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDivWithZero() {
		ValueWrapper v7 = new ValueWrapper(100);
		ValueWrapper v8 = new ValueWrapper(0);
		
		v7.divide(v8.getValue()); 
	}
	
	public void testDivNullWithDoubleAsString() {
		ValueWrapper v7 = new ValueWrapper(null);
		ValueWrapper v8 = new ValueWrapper("300.500e5");
		
		v7.divide(v8.getValue()); 
		
		assertEquals(0, v7.getValue());
		assertEquals(null, v8.getValue());
	}
	
	@Test
	public void testCompareNullNull() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		
		assertEquals(0, v1.numCompare(v2.getValue()));
	}
	
	@Test
	public void testCompareDoubleInteger() {
		ValueWrapper v3 = new ValueWrapper("1.2E1");
		ValueWrapper v4 = new ValueWrapper(Integer.valueOf(1));
		
		assertEquals("1.2E1", v3.getValue());
		assertEquals(1, v4.getValue());
		
		assertTrue(v3.numCompare(v4.getValue()) > 0);
	}
	
	@Test
	public void testCompareIntegerInteger() {
		ValueWrapper v5 = new ValueWrapper("12");
		ValueWrapper v6 = new ValueWrapper(Integer.valueOf(1));
		
		assertEquals("12", v5.getValue());
		assertEquals(1, v6.getValue());
		
		assertTrue(v5.numCompare(v6.getValue()) > 0);
	}
	
	@Test(expected=RuntimeException.class)
	public void testCompareInvalidStringInteger() {
		ValueWrapper v7 = new ValueWrapper("Ankica");
		ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));
		
		v7.numCompare(v8.getValue()); 
	}
	
	@Test
	public void testCompareIntegerInteger2() {
		ValueWrapper v5 = new ValueWrapper(9);
		ValueWrapper v6 = new ValueWrapper(111);
		
		assertEquals(9, v5.getValue());
		assertEquals(111, v6.getValue());
		
		assertTrue(v5.numCompare(v6.getValue()) < 0);
	}
	
	@Test
	public void testCompareIntegerInteger3() {
		ValueWrapper v5 = new ValueWrapper(-8);
		ValueWrapper v6 = new ValueWrapper(-8);
		
		assertEquals(-8, v5.getValue());
		assertEquals(-8, v6.getValue());
		
		assertTrue(v5.numCompare(v6.getValue()) == 0);
	}
	
	@Test
	public void testCompareIntegerInteger4() {
		ValueWrapper v5 = new ValueWrapper(-8);
		ValueWrapper v6 = new ValueWrapper(-100);
		
		assertEquals(-8, v5.getValue());
		assertEquals(-100, v6.getValue());
		
		assertTrue(v5.numCompare(v6.getValue()) > 0);
	}
	
	@Test
	public void testCompareDoubleDouble() {
		ValueWrapper v5 = new ValueWrapper(-8.0);
		ValueWrapper v6 = new ValueWrapper(-100.0);
		
		assertEquals(-8.0, v5.getValue());
		assertEquals(-100.0, v6.getValue());
		
		assertTrue(v5.numCompare(v6.getValue()) > 0);
	}
	
	@Test
	public void testCompareDoubleDouble2() {
		ValueWrapper v5 = new ValueWrapper(-8.0);
		ValueWrapper v6 = new ValueWrapper(-7.0);
		
		assertEquals(-8.0, v5.getValue());
		assertEquals(-7.0, v6.getValue());
		
		assertTrue(v5.numCompare(v6.getValue()) < 0);
	}
	
	@Test
	public void testCompareDoubleDouble3() {
		ValueWrapper v5 = new ValueWrapper(500.0);
		ValueWrapper v6 = new ValueWrapper(500.0);
		
		assertEquals(500.0, v5.getValue());
		assertEquals(500.0, v6.getValue());
		
		assertTrue(v5.numCompare(v6.getValue()) == 0);
	}
	
	@Test
	public void testCompareDoubleDouble4() {
		ValueWrapper v5 = new ValueWrapper(10E1);
		ValueWrapper v6 = new ValueWrapper(99.0);
		
		assertEquals(10E1, v5.getValue());
		assertEquals(99.0, v6.getValue());
		
		assertTrue(v5.numCompare(v6.getValue()) > 0);
	}
	
	@Test
	public void testCompareDoubleDouble5() {
		ValueWrapper v5 = new ValueWrapper(10E1);
		ValueWrapper v6 = new ValueWrapper(5e3);
		
		assertEquals(10E1, v5.getValue());
		assertEquals(5e3, v6.getValue());
		
		assertTrue(v5.numCompare(v6.getValue()) < 0);
	}
	
	@Test
	public void testCompareDoubleAsStringDoubleAsString() {
		ValueWrapper v5 = new ValueWrapper(10E1);
		ValueWrapper v6 = new ValueWrapper(99.0);
		
		assertEquals(10E1, v5.getValue());
		assertEquals(99.0, v6.getValue());
		
		assertTrue(v5.numCompare(v6.getValue()) > 0);
	}
	
	@Test
	public void testCompareDoubleAsStringDoubleAsString1() {
		ValueWrapper v5 = new ValueWrapper("10E1");
		ValueWrapper v6 = new ValueWrapper("5e3");
		
		assertEquals("10E1", v5.getValue());
		assertEquals("5e3", v6.getValue());
		
		assertTrue(v5.numCompare(v6.getValue()) < 0);
	}
	
	@Test
	public void testCompareDoubleAsStringDoubleAsString2() {
		ValueWrapper v5 = new ValueWrapper("10E1");
		ValueWrapper v6 = new ValueWrapper("-99.99");
		
		assertEquals("10E1", v5.getValue());
		assertEquals("-99.99", v6.getValue());
		
		assertTrue(v5.numCompare(v6.getValue()) > 0);
	}
	
	@Test
	public void testCompareDoubleAsStringDoubleAsString3() {
		ValueWrapper v5 = new ValueWrapper("10.50E1");
		ValueWrapper v6 = new ValueWrapper("-99.99");
		
		assertEquals("10.50E1", v5.getValue());
		assertEquals("-99.99", v6.getValue());
		
		assertTrue(v5.numCompare(v6.getValue()) > 0);
	}
	
	@Test
	public void testCompareIntegerDouble() {
		ValueWrapper v5 = new ValueWrapper(10);
		ValueWrapper v6 = new ValueWrapper(1E1);
		
		assertEquals(10, v5.getValue());
		assertEquals(1E1, v6.getValue());
		
		assertTrue(v5.numCompare(v6.getValue()) == 0);
	}
	
	@Test
	public void testCompareIntegerDouble2() {
		ValueWrapper v5 = new ValueWrapper(10);
		ValueWrapper v6 = new ValueWrapper(5.0);
		
		assertEquals(10, v5.getValue());
		assertEquals(5.0, v6.getValue());
		
		assertTrue(v5.numCompare(v6.getValue()) > 0);
	}
	
	@Test
	public void testCompareDoubleInteger2() {
		ValueWrapper v5 = new ValueWrapper(10.0);
		ValueWrapper v6 = new ValueWrapper(5);
		
		assertEquals(10.0, v5.getValue());
		assertEquals(5, v6.getValue());
		
		assertTrue(v5.numCompare(v6.getValue()) > 0);
	}
	
	@Test
	public void testCompareDoubleAsStringIntegerAsString() {
		ValueWrapper v5 = new ValueWrapper("2e2");
		ValueWrapper v6 = new ValueWrapper("199");
		
		assertEquals("2e2", v5.getValue());
		assertEquals("199", v6.getValue());
		
		assertTrue(v5.numCompare(v6.getValue()) > 0);
	}
	
	@Test
	public void testCompareDoubleAsStringNull() {
		ValueWrapper v5 = new ValueWrapper("0.0");
		ValueWrapper v6 = new ValueWrapper(null);
		
		assertEquals("0.0", v5.getValue());
		assertEquals(null, v6.getValue());
		
		assertTrue(v5.numCompare(v6.getValue()) == 0);
	}
}
