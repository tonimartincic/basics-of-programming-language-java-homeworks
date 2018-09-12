package hr.fer.zemris.java.custom.collections;

import static org.junit.Assert.*;

import org.junit.Test;

public class LinkedListIndexedCollectionTest {

	@Test
	public void testSizeEmptyCollection() {
		LinkedListIndexedCollection linkedListIndexedCollection = new LinkedListIndexedCollection();
		
		assertEquals(0, linkedListIndexedCollection.size());
	}

	@Test
	public void testAddOneElement() {
		LinkedListIndexedCollection linkedListIndexedCollection = new LinkedListIndexedCollection();
		linkedListIndexedCollection.add(0);
		
		assertEquals(1, linkedListIndexedCollection.size());
	}
	
	@Test
	public void testAddFiveElements() {
		LinkedListIndexedCollection linkedListIndexedCollection = new LinkedListIndexedCollection();
		linkedListIndexedCollection.add(0);
		linkedListIndexedCollection.add(1);
		linkedListIndexedCollection.add(2);
		linkedListIndexedCollection.add(3);
		linkedListIndexedCollection.add(4);
		
		assertEquals(5, linkedListIndexedCollection.size());
	}

	@Test
	public void testContainsDoesContains() {
		LinkedListIndexedCollection linkedListIndexedCollection = new LinkedListIndexedCollection();
		linkedListIndexedCollection.add(0);
		linkedListIndexedCollection.add(1);
		linkedListIndexedCollection.add(2);
		linkedListIndexedCollection.add(3);
		linkedListIndexedCollection.add(4);
		
		assertTrue(linkedListIndexedCollection.contains(3));
	}
	
	@Test
	public void testContainsDoesNotContains() {
		LinkedListIndexedCollection linkedListIndexedCollection = new LinkedListIndexedCollection();
		linkedListIndexedCollection.add(0);
		linkedListIndexedCollection.add(1);
		linkedListIndexedCollection.add(2);
		linkedListIndexedCollection.add(3);
		linkedListIndexedCollection.add(4);
		
		assertFalse(linkedListIndexedCollection.contains(5));
	}

	@Test
	public void testRemoveObject() {
		LinkedListIndexedCollection linkedListIndexedCollection = new LinkedListIndexedCollection();
		linkedListIndexedCollection.add("First");
		linkedListIndexedCollection.add("Second");
		linkedListIndexedCollection.add("Third");
		linkedListIndexedCollection.add("Fourth");
		linkedListIndexedCollection.add("Fifth");
		
		assertEquals(5, linkedListIndexedCollection.size());
		
		linkedListIndexedCollection.remove("Second");
		
		assertEquals(4, linkedListIndexedCollection.size());
		
		String[] array = {"First", "Third", "Fourth", "Fifth"};
		assertArrayEquals(array, linkedListIndexedCollection.toArray());
		
		linkedListIndexedCollection.remove("Third");
		
		assertEquals(3, linkedListIndexedCollection.size());
		
		String[] array2 = {"First", "Fourth", "Fifth"};
		assertArrayEquals(array2, linkedListIndexedCollection.toArray());
	}

	@Test
	public void testToArray() {
		LinkedListIndexedCollection linkedListIndexedCollection = new LinkedListIndexedCollection();
		linkedListIndexedCollection.add(0);
		linkedListIndexedCollection.add(1);
		linkedListIndexedCollection.add(2);
		linkedListIndexedCollection.add(3);
		linkedListIndexedCollection.add(4);
		
		Integer[] array = {0, 1, 2, 3, 4};
		
		assertArrayEquals(array, linkedListIndexedCollection.toArray());
	}

	@Test
	public void testClear() {
		LinkedListIndexedCollection linkedListIndexedCollection = new LinkedListIndexedCollection();
		linkedListIndexedCollection.add(10);
		linkedListIndexedCollection.add(20);
		linkedListIndexedCollection.add(30);
		linkedListIndexedCollection.add(40);
		linkedListIndexedCollection.add(50);
		linkedListIndexedCollection.add(60);
		
		assertEquals(6, linkedListIndexedCollection.size());
		
		linkedListIndexedCollection.clear();
		
		assertEquals(0, linkedListIndexedCollection.size());
	}

	@Test
	public void testLinkedListIndexedCollectionConstructor() {
		LinkedListIndexedCollection linkedListIndexedCollection = new LinkedListIndexedCollection();
		
		assertEquals(0, linkedListIndexedCollection.size());
	}

	@Test
	public void testLinkedListIndexedCollectionConstructorCollection() {
		LinkedListIndexedCollection linkedListIndexedCollection = new LinkedListIndexedCollection();
		linkedListIndexedCollection.add(10);
		linkedListIndexedCollection.add(20);
		linkedListIndexedCollection.add(30);
		linkedListIndexedCollection.add(40);
		linkedListIndexedCollection.add(50);
		linkedListIndexedCollection.add(60);
		
		assertEquals(6, linkedListIndexedCollection.size());
		
		LinkedListIndexedCollection linkedListIndexedCollection2 = new LinkedListIndexedCollection(linkedListIndexedCollection);
		
		assertEquals(6, linkedListIndexedCollection2.size());
		
		assertArrayEquals(linkedListIndexedCollection.toArray(), linkedListIndexedCollection2.toArray());
	}

	@Test
	public void testGet() {
		LinkedListIndexedCollection linkedListIndexedCollection = new LinkedListIndexedCollection();
		linkedListIndexedCollection.add(10);
		linkedListIndexedCollection.add(20);
		linkedListIndexedCollection.add(30);
		linkedListIndexedCollection.add(40);
		linkedListIndexedCollection.add(50);
		linkedListIndexedCollection.add(60);
		
		assertEquals(40, linkedListIndexedCollection.get(3));
		assertEquals(10, linkedListIndexedCollection.get(0));
		assertEquals(60, linkedListIndexedCollection.get(5));
	}
	
	@Test
	public void testInsertElementInTheEmptyCollection() {
		LinkedListIndexedCollection linkedListIndexedCollection = new LinkedListIndexedCollection();
		
		linkedListIndexedCollection.insert("Element", 0);
		
		assertEquals(1, linkedListIndexedCollection.size());
	}

	@Test
	public void testInsertElementInTheMiddle() {
		LinkedListIndexedCollection linkedListIndexedCollection = new LinkedListIndexedCollection();
		linkedListIndexedCollection.add(10);
		linkedListIndexedCollection.add(20);
		linkedListIndexedCollection.add(30);
		linkedListIndexedCollection.add(40);
		linkedListIndexedCollection.add(50);
		
		linkedListIndexedCollection.insert(35, 3);
		
		assertEquals(6, linkedListIndexedCollection.size());
		
		Integer[] array = {10, 20, 30, 35, 40, 50};
		assertArrayEquals(array, linkedListIndexedCollection.toArray());
		
		linkedListIndexedCollection.insert(38, 4);
		
		assertEquals(7, linkedListIndexedCollection.size());
		
		Integer[] array2 = {10, 20, 30, 35, 38, 40, 50};
		assertArrayEquals(array2, linkedListIndexedCollection.toArray());
	}
	
	@Test
	public void testInsertElementAtTheEnd() {
		LinkedListIndexedCollection linkedListIndexedCollection = new LinkedListIndexedCollection();
		linkedListIndexedCollection.add(10);
		linkedListIndexedCollection.add(20);
		linkedListIndexedCollection.add(30);
		linkedListIndexedCollection.add(40);
		linkedListIndexedCollection.add(50);
		
		linkedListIndexedCollection.insert(55, 5);
		
		assertEquals(6, linkedListIndexedCollection.size());
		
		Integer[] array = {10, 20, 30, 40, 50, 55};
		assertArrayEquals(array, linkedListIndexedCollection.toArray());
		
		linkedListIndexedCollection.insert(60, 6);
		
		assertEquals(7, linkedListIndexedCollection.size());
		
		Integer[] array2 = {10, 20, 30, 40, 50, 55, 60};
		assertArrayEquals(array2, linkedListIndexedCollection.toArray());
	}
	
	@Test
	public void testInsertElementAtTheBeginning() {
		LinkedListIndexedCollection linkedListIndexedCollection = new LinkedListIndexedCollection();
		linkedListIndexedCollection.add(10);
		linkedListIndexedCollection.add(20);
		linkedListIndexedCollection.add(30);
		linkedListIndexedCollection.add(40);
		linkedListIndexedCollection.add(50);
		
		linkedListIndexedCollection.insert(5, 0);
		
		assertEquals(6, linkedListIndexedCollection.size());
		
		Integer[] array = {5, 10, 20, 30, 40, 50};
		assertArrayEquals(array, linkedListIndexedCollection.toArray());
		
		linkedListIndexedCollection.insert(2, 0);
		
		assertEquals(7, linkedListIndexedCollection.size());
		
		Integer[] array2 = {2, 5, 10, 20, 30, 40, 50};
		assertArrayEquals(array2, linkedListIndexedCollection.toArray());
	}

	@Test
	public void testIndexOfElementThatExists() {
		LinkedListIndexedCollection linkedListIndexedCollection = new LinkedListIndexedCollection();
		linkedListIndexedCollection.add(10);
		linkedListIndexedCollection.add(20);
		linkedListIndexedCollection.add(30);
		linkedListIndexedCollection.add(40);
		linkedListIndexedCollection.add(50);
		
		assertEquals(2, linkedListIndexedCollection.indexOf(30));
		
	}
	
	@Test
	public void testIndexOfElementThatDoesNotExists() {
		LinkedListIndexedCollection linkedListIndexedCollection = new LinkedListIndexedCollection();
		linkedListIndexedCollection.add(10);
		linkedListIndexedCollection.add(20);
		linkedListIndexedCollection.add(30);
		linkedListIndexedCollection.add(40);
		linkedListIndexedCollection.add(50);
		
		assertEquals(-1, linkedListIndexedCollection.indexOf(60));
	}

	@Test
	public void testRemoveElementAtIndexInTheMiddle() {
		LinkedListIndexedCollection linkedListIndexedCollection = new LinkedListIndexedCollection();
		linkedListIndexedCollection.add(10);
		linkedListIndexedCollection.add(20);
		linkedListIndexedCollection.add(30);
		linkedListIndexedCollection.add(40);
		linkedListIndexedCollection.add(50);
		
		assertEquals(5, linkedListIndexedCollection.size());
		
		linkedListIndexedCollection.remove(2);
		
		assertEquals(4, linkedListIndexedCollection.size());
		
		Integer[] array = {10, 20, 40, 50};
		assertArrayEquals(array, linkedListIndexedCollection.toArray());
	}
	
	@Test
	public void testRemoveElementAtIndexZero() {
		LinkedListIndexedCollection linkedListIndexedCollection = new LinkedListIndexedCollection();
		linkedListIndexedCollection.add(10);
		linkedListIndexedCollection.add(20);
		linkedListIndexedCollection.add(30);
		linkedListIndexedCollection.add(40);
		linkedListIndexedCollection.add(50);
		
		assertEquals(5, linkedListIndexedCollection.size());
		
		linkedListIndexedCollection.remove(0);
		
		assertEquals(4, linkedListIndexedCollection.size());
		
		Integer[] array = {20, 30, 40, 50};
		assertArrayEquals(array, linkedListIndexedCollection.toArray());
		
		linkedListIndexedCollection.remove(0);
		
		assertEquals(3, linkedListIndexedCollection.size());
		
		Integer[] array2 = {30, 40, 50};
		assertArrayEquals(array2, linkedListIndexedCollection.toArray());
	}
	
	@Test
	public void testRemoveElementAtLastIndex() {
		LinkedListIndexedCollection linkedListIndexedCollection = new LinkedListIndexedCollection();
		linkedListIndexedCollection.add(10);
		linkedListIndexedCollection.add(20);
		linkedListIndexedCollection.add(30);
		linkedListIndexedCollection.add(40);
		linkedListIndexedCollection.add(50);
		
		assertEquals(5, linkedListIndexedCollection.size());
		
		linkedListIndexedCollection.remove(4);
		
		assertEquals(4, linkedListIndexedCollection.size());
		
		Integer[] array = {10, 20, 30, 40};
		assertArrayEquals(array, linkedListIndexedCollection.toArray());
		
		linkedListIndexedCollection.remove(3);
		
		assertEquals(3, linkedListIndexedCollection.size());
		
		Integer[] array2 = {10, 20, 30};
		assertArrayEquals(array2, linkedListIndexedCollection.toArray());
	}

}
