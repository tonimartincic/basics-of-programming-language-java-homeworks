package hr.fer.zemris.java.custom.collections;

import static org.junit.Assert.*;

import org.junit.Test;

public class ArrayIndexedCollectionTest {
	
	private static final int DEFAULT_INITIAL_CAPACITY = 16;
	private static final int TEST_INITIAL_CAPACITY = 10;
	
	@Test
	public void testSizeEmptyCollection() {
		ArrayIndexedCollection arrayIndexedCollection = new ArrayIndexedCollection();
		
		assertEquals(0, arrayIndexedCollection.size());
	}
	
	@Test
	public void testAddOneElement() {
		ArrayIndexedCollection arrayIndexedCollection = new ArrayIndexedCollection();
		arrayIndexedCollection.add("First element");
		
		assertEquals(1, arrayIndexedCollection.size());
	}
	
	@Test
	public void testAddThreeElements() {
		ArrayIndexedCollection arrayIndexedCollection = new ArrayIndexedCollection();
		arrayIndexedCollection.add("First element");
		arrayIndexedCollection.add("Second element");
		arrayIndexedCollection.add("Third element");
		
		assertEquals(3, arrayIndexedCollection.size());
	}

	@Test
	public void testContainsDoesContain() {
		ArrayIndexedCollection arrayIndexedCollection = new ArrayIndexedCollection();
		arrayIndexedCollection.add("First element");
		arrayIndexedCollection.add("Second element");
		arrayIndexedCollection.add("Third element");
		
		assertEquals(3, arrayIndexedCollection.size());
		assertTrue(arrayIndexedCollection.contains("Second element"));
	}
	
	@Test
	public void testContainsDoesNotContain() {
		ArrayIndexedCollection arrayIndexedCollection = new ArrayIndexedCollection();
		arrayIndexedCollection.add("First element");
		arrayIndexedCollection.add("Second element");
		arrayIndexedCollection.add("Third element");
		
		assertEquals(3, arrayIndexedCollection.size());
		assertFalse(arrayIndexedCollection.contains("Fourth element"));
	}
	
	@Test
	public void testContainsNullValue() {
		ArrayIndexedCollection arrayIndexedCollection = new ArrayIndexedCollection();
		arrayIndexedCollection.add("First element");
		arrayIndexedCollection.add("Second element");
		arrayIndexedCollection.add("Third element");
		
		assertEquals(3, arrayIndexedCollection.size());
		assertFalse(arrayIndexedCollection.contains(null));
	}

	@Test
	public void testRemoveObjectElementExists() {
		ArrayIndexedCollection arrayIndexedCollection = new ArrayIndexedCollection();
		arrayIndexedCollection.add("First element");
		arrayIndexedCollection.add("Second element");
		arrayIndexedCollection.add("Third element");
		
		assertEquals(3, arrayIndexedCollection.size());
		
		arrayIndexedCollection.remove("First element");
		
		assertEquals(2, arrayIndexedCollection.size());
	}
	
	@Test
	public void testRemoveObjectElementDoesNotExists() {
		ArrayIndexedCollection arrayIndexedCollection = new ArrayIndexedCollection();
		arrayIndexedCollection.add("First element");
		arrayIndexedCollection.add("Second element");
		arrayIndexedCollection.add("Third element");
		
		assertEquals(3, arrayIndexedCollection.size());
		
		arrayIndexedCollection.remove("Fifth element");
		
		assertEquals(3, arrayIndexedCollection.size());
	}

	@Test
	public void testClear() {
		ArrayIndexedCollection arrayIndexedCollection = new ArrayIndexedCollection();
		arrayIndexedCollection.add("First element");
		arrayIndexedCollection.add("Second element");
		arrayIndexedCollection.add("Third element");
		
		assertEquals(3, arrayIndexedCollection.size());
		
		arrayIndexedCollection.clear();
		
		assertEquals(0, arrayIndexedCollection.size());
		
	}

	@Test
	public void testArrayIndexedCollectionConstructor() {
		ArrayIndexedCollection arrayIndexedCollection = new ArrayIndexedCollection();
		
		assertEquals(0, arrayIndexedCollection.size());
		assertEquals(DEFAULT_INITIAL_CAPACITY, arrayIndexedCollection.getCapacity());
	}

	@Test
	public void testArrayIndexedCollectionConstructorInt() {
		ArrayIndexedCollection arrayIndexedCollection = new ArrayIndexedCollection(TEST_INITIAL_CAPACITY);
		
		assertEquals(0, arrayIndexedCollection.size());
		assertEquals(TEST_INITIAL_CAPACITY, arrayIndexedCollection.getCapacity());
	}

	@Test
	public void testArrayIndexedCollectionConstructorCollection() {
		ArrayIndexedCollection arrayIndexedCollection1 = new ArrayIndexedCollection();
		arrayIndexedCollection1.add("First element");
		arrayIndexedCollection1.add("Second element");
		arrayIndexedCollection1.add("Third element");
		
		assertEquals(3, arrayIndexedCollection1.size());
		assertEquals(DEFAULT_INITIAL_CAPACITY, arrayIndexedCollection1.getCapacity());
		
		ArrayIndexedCollection arrayIndexedCollection2 = 
				new ArrayIndexedCollection(arrayIndexedCollection1);
		
		assertEquals(3, arrayIndexedCollection2.size());
		assertEquals(DEFAULT_INITIAL_CAPACITY, arrayIndexedCollection2.getCapacity());
	}

	@Test
	public void testArrayIndexedCollectionConstructorCollectionInt() {
		ArrayIndexedCollection arrayIndexedCollection1 = new ArrayIndexedCollection();
		arrayIndexedCollection1.add("First element");
		arrayIndexedCollection1.add("Second element");
		arrayIndexedCollection1.add("Third element");
		
		assertEquals(3, arrayIndexedCollection1.size());
		assertEquals(DEFAULT_INITIAL_CAPACITY, arrayIndexedCollection1.getCapacity());
		
		ArrayIndexedCollection arrayIndexedCollection2 = 
				new ArrayIndexedCollection(arrayIndexedCollection1, TEST_INITIAL_CAPACITY);
		
		assertEquals(3, arrayIndexedCollection2.size());
		assertEquals(TEST_INITIAL_CAPACITY, arrayIndexedCollection2.getCapacity());
		
	}

	@Test
	public void testGet() {
		ArrayIndexedCollection arrayIndexedCollection = new ArrayIndexedCollection();
		arrayIndexedCollection.add("First element");
		arrayIndexedCollection.add("Second element");
		arrayIndexedCollection.add("Third element");
		
		assertEquals("Second element", arrayIndexedCollection.get(1));
		
	}

	@Test
	public void testInsert() {
		ArrayIndexedCollection arrayIndexedCollection = new ArrayIndexedCollection();
		arrayIndexedCollection.add("First element");
		arrayIndexedCollection.add("Second element");
		arrayIndexedCollection.add("Third element");
		
		arrayIndexedCollection.insert("Element between second and third element", 3);
		
		assertEquals(4, arrayIndexedCollection.size());
	}

	@Test
	public void testIndexOfElementExists() {
		ArrayIndexedCollection arrayIndexedCollection = new ArrayIndexedCollection();
		arrayIndexedCollection.add("First element");
		arrayIndexedCollection.add("Second element");
		arrayIndexedCollection.add("Third element");
		
		assertEquals(1, arrayIndexedCollection.indexOf("Second element"));
	}
	
	@Test
	public void testIndexOfElementDoesNotExists() {
		ArrayIndexedCollection arrayIndexedCollection = new ArrayIndexedCollection();
		arrayIndexedCollection.add("First element");
		arrayIndexedCollection.add("Second element");
		arrayIndexedCollection.add("Third element");
		
		assertEquals(-1, arrayIndexedCollection.indexOf("FourthElement"));
	}
	
	@Test
	public void testIndexOfNull() {
		ArrayIndexedCollection arrayIndexedCollection = new ArrayIndexedCollection();
		arrayIndexedCollection.add("First element");
		arrayIndexedCollection.add("Second element");
		arrayIndexedCollection.add("Third element");
		
		assertEquals(-1, arrayIndexedCollection.indexOf(null));
	}

	@Test
	public void testRemoveElementAtIndexThatExists() {
		ArrayIndexedCollection arrayIndexedCollection = new ArrayIndexedCollection();
		arrayIndexedCollection.add("First element");
		arrayIndexedCollection.add("Second element");
		arrayIndexedCollection.add("Third element");
		
		arrayIndexedCollection.remove(1);
		
		assertEquals(2, arrayIndexedCollection.size());
	}

}
