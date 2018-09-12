package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.Assert.*;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class ObjectMultistackTest {

	@Test
	public void testEmptyMultistack() {
		ObjectMultistack multistack = new ObjectMultistack();
		
		assertTrue(multistack.isEmpty("name"));
	}
	
	@Test
	public void testNonEmptyMultistack() {
		ObjectMultistack multistack = new ObjectMultistack();
		
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		
		assertFalse(multistack.isEmpty("year"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testKeyIsNullValue() {
		ObjectMultistack multistack = new ObjectMultistack();
		
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push(null, year);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testValueWrapperIsNullValue() {
		ObjectMultistack multistack = new ObjectMultistack();
		
		@SuppressWarnings("unused")
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", null);
	}
	
	@Test
	public void testAddNullValue() {
		ObjectMultistack multistack = new ObjectMultistack();
		
		multistack.push("stack1", new ValueWrapper(null));
		
		assertEquals(null, multistack.peek("stack1").getValue());
	}
	
	@Test(expected=IllegalStateException.class)
	public void testPopFromEmptyStack() {
		ObjectMultistack multistack = new ObjectMultistack();
		
		multistack.pop("year");
	}
	
	@Test(expected=IllegalStateException.class)
	public void testPopFromEmptyStack2() {
		ObjectMultistack multistack = new ObjectMultistack();
		
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		
		assertFalse(multistack.isEmpty("year"));
		
		multistack.pop("year");
		
		assertTrue(multistack.isEmpty("year"));
		
		multistack.pop("year");
	}
	
	@Test(expected=IllegalStateException.class)
	public void testPeekEmptyStack() {
		ObjectMultistack multistack = new ObjectMultistack();
	
		multistack.peek("year");
	}
	
	@Test
	public void testAddFourStacks() {
		ObjectMultistack multistack = new ObjectMultistack();
		
		multistack.push("Integeri", new ValueWrapper(1));
		multistack.push("Integeri", new ValueWrapper(2));
		multistack.push("Integeri", new ValueWrapper(3));
		multistack.push("Integeri", new ValueWrapper(4));
		
		multistack.push("Doubleovi", new ValueWrapper(1.0));
		multistack.push("Doubleovi", new ValueWrapper(2.0));
		multistack.push("Doubleovi", new ValueWrapper(3.0));
		multistack.push("Doubleovi", new ValueWrapper(4.0));
		
		multistack.push("Stringovi", new ValueWrapper("Jedan"));
		multistack.push("Stringovi", new ValueWrapper("Dva"));
		multistack.push("Stringovi", new ValueWrapper("Tri"));
		multistack.push("Stringovi", new ValueWrapper("Četiri"));
		
		multistack.push("Miješani", new ValueWrapper(1));
		multistack.push("Miješani", new ValueWrapper(1.0));
		multistack.push("Miješani", new ValueWrapper(null));
		multistack.push("Miješani", new ValueWrapper("Jedan"));
		
		assertFalse(multistack.isEmpty("Integeri"));
		assertFalse(multistack.isEmpty("Doubleovi"));
		assertFalse(multistack.isEmpty("Stringovi"));
		assertFalse(multistack.isEmpty("Miješani"));
		
		assertEquals(4, multistack.pop("Integeri").getValue());
		assertEquals(3, multistack.pop("Integeri").getValue());
		assertEquals(2, multistack.pop("Integeri").getValue());
		assertEquals(1, multistack.pop("Integeri").getValue());
		
		assertTrue(multistack.isEmpty("Integeri"));
		
		assertEquals(4.0, multistack.pop("Doubleovi").getValue());
		assertEquals(3.0, multistack.pop("Doubleovi").getValue());
		assertEquals(2.0, multistack.pop("Doubleovi").getValue());
		assertEquals(1.0, multistack.pop("Doubleovi").getValue());
		
		assertTrue(multistack.isEmpty("Doubleovi"));
		
		assertEquals("Četiri", multistack.pop("Stringovi").getValue());
		assertEquals("Tri", multistack.pop("Stringovi").getValue());
		assertEquals("Dva", multistack.pop("Stringovi").getValue());
		assertEquals("Jedan", multistack.pop("Stringovi").getValue());
		
		assertTrue(multistack.isEmpty("Stringovi"));
		
		assertEquals("Jedan", multistack.pop("Miješani").getValue());
		assertEquals(null, multistack.pop("Miješani").getValue());
		assertEquals(1.0, multistack.pop("Miješani").getValue());
		assertEquals(1, multistack.pop("Miješani").getValue());
		
		assertTrue(multistack.isEmpty("Miješani"));
	}

}
