package hr.fer.zemris.java.hw04.collections;

import static org.junit.Assert.*;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

import hr.fer.zemris.java.hw04.collections.SimpleHashtable.TableEntry;

@SuppressWarnings("javadoc")
public class SimpleHashtableTest {

	@Test
	public void testSizeOfEmptySimpleHashTable() {
		SimpleHashtable<String, Integer> simpleHashtable = new SimpleHashtable<>();
		
		assertEquals(0, simpleHashtable.size());
	}
	
	@Test
	public void testSizeOfSimpleHashTableWithOneElement() {
		SimpleHashtable<String, Integer> simpleHashtable = new SimpleHashtable<>();
		
		simpleHashtable.put("Ankica", 5);
		
		assertEquals(1, simpleHashtable.size());
	}
	
	@Test
	public void testSizeOfSimpleHashTableWithThreeElements() {
		SimpleHashtable<String, Integer> simpleHashtable = new SimpleHashtable<>();
		
		simpleHashtable.put("Ankica", 5);
		simpleHashtable.put("Jasenka", 4);
		simpleHashtable.put("Jesenka", 4);
		
		assertEquals(3, simpleHashtable.size());
	}
	
	@Test
	public void testIsEmptyEmptySimpleHashTable() {
		SimpleHashtable<String, Integer> simpleHashtable = new SimpleHashtable<>();
		
		assertEquals(true, simpleHashtable.isEmpty());
	}
	
	@Test
	public void testIsEmptyNonEmptySimpleHashTable() {
		SimpleHashtable<String, Integer> simpleHashtable = new SimpleHashtable<>();
		
		simpleHashtable.put("Ankica", 5);
		
		assertEquals(false, simpleHashtable.isEmpty());
	}
	
	@Test
	public void testCalculateCapacityOne() {
		SimpleHashtable<String, Integer> simpleHashtable = new SimpleHashtable<>(1);
		
		assertEquals(1, simpleHashtable.calculateCapacity(1));
	}
	
	@Test
	public void testCalculateCapacityTwo() {
		SimpleHashtable<String, Integer> simpleHashtable = new SimpleHashtable<>(2);
		
		assertEquals(2, simpleHashtable.calculateCapacity(2));
	}
	
	@Test
	public void testCalculateCapacityThirty() {
		SimpleHashtable<String, Integer> simpleHashtable = new SimpleHashtable<>(30);
		
		assertEquals(32, simpleHashtable.calculateCapacity(30));
	}
	
	@Test
	public void testCalculateCapacitySixtyFour() {
		SimpleHashtable<String, Integer> simpleHashtable = new SimpleHashtable<>(64);
		
		assertEquals(64, simpleHashtable.calculateCapacity(64));
	}
	
	@Test 
	public void testHashcodeOfSameObjects() {
		SimpleHashtable<String, Integer> simpleHashtable = new SimpleHashtable<>(64);;
		
		String string1 = new String("Some string");
		String string2 = new String("Some string");
		
		int hashcode1 = simpleHashtable.calculateHash(string1);
		int hashcode2 = simpleHashtable.calculateHash(string2);
		
		assertEquals(hashcode1, hashcode2);
	}
	
	@Test
	public void testToStringOneElement() {
		SimpleHashtable<String, Integer> simpleHashtable = new SimpleHashtable<>();
		
		simpleHashtable.put("Ankica", 5);
		
		String string = simpleHashtable.toString();
				
		assertEquals("[Ankica=5]", string);
	}
	
	@Test
	public void testPutOneElement() {
		SimpleHashtable<String, Integer> simpleHashtable = new SimpleHashtable<>();
		
		simpleHashtable.put("Ankica", 5);
				
		assertEquals(1, simpleHashtable.size());
		
		Integer num = simpleHashtable.get("Ankica");
		int num1 = num - 5;
		
		assertEquals(0, num1);
		
	}
	
	@Test
	public void testPutValueIsNullValue() {
		SimpleHashtable<String, Integer> simpleHashtable = new SimpleHashtable<>();
		
		simpleHashtable.put("Ankica", null);
				
		assertEquals(1, simpleHashtable.size());
		
		Integer num = simpleHashtable.get("Ankica");
		
		assertNull(num);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSetNegativeCapacity() {
		@SuppressWarnings("unused")
		SimpleHashtable<String, Integer> simpleHashtable = new SimpleHashtable<>(-1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testPutKeyIsNullValue() {
		SimpleHashtable<String, Integer> simpleHashtable = new SimpleHashtable<>();
		
		simpleHashtable.put(null, 5);
	}
	
	@Test
	public void testPutFiveElements() {
		SimpleHashtable<String, Integer> simpleHashtable = new SimpleHashtable<>();
		
		simpleHashtable.put("Ankica", 5);
		simpleHashtable.put("Jasenka", 4);
		simpleHashtable.put("Jesenka", 2);
		simpleHashtable.put("Ivana", 3);
		simpleHashtable.put("Ana", 4);
				
		assertEquals(5, simpleHashtable.size());
		
		Integer[] values = new Integer[5];
		values[0] = simpleHashtable.get("Ankica");
		values[1] = simpleHashtable.get("Jasenka");
		values[2] = simpleHashtable.get("Jesenka");
		values[3] = simpleHashtable.get("Ivana");
		values[4] = simpleHashtable.get("Ana");
		
		assertArrayEquals(new Integer[]{5, 4, 2, 3, 4}, values);
	}
	
	@Test
	public void testPutSixElementsTwoTimesSameKey() {
		SimpleHashtable<String, Integer> simpleHashtable = new SimpleHashtable<>();
		
		simpleHashtable.put("Ankica", 5);
		simpleHashtable.put("Jasenka", 4);
		simpleHashtable.put("Jesenka", 2);
		simpleHashtable.put("Ivana", 3);
		simpleHashtable.put("Ana", 4);
		simpleHashtable.put("Ankica", 1);
				
		assertEquals(5, simpleHashtable.size());
		
		Integer[] values = new Integer[5];
		values[0] = simpleHashtable.get("Ankica");
		values[1] = simpleHashtable.get("Jasenka");
		values[2] = simpleHashtable.get("Jesenka");
		values[3] = simpleHashtable.get("Ivana");
		values[4] = simpleHashtable.get("Ana");
		
		assertArrayEquals(new Integer[]{1, 4, 2, 3, 4}, values);
	}
	
	@Test 
	public void testContainsKeyDoesContain() {
		SimpleHashtable<String, Integer> simpleHashtable = new SimpleHashtable<>();
		
		simpleHashtable.put("Ankica", 5);
		simpleHashtable.put("Jasenka", 4);
		simpleHashtable.put("Jesenka", 2);
		simpleHashtable.put("Ivana", 3);
		simpleHashtable.put("Ana", 4);
		simpleHashtable.put("Ankica", 1);
		
		assertTrue(simpleHashtable.containsKey("Ivana"));
	}
	
	@Test 
	public void testContainsKeyDoesNotContain() {
		SimpleHashtable<String, Integer> simpleHashtable = new SimpleHashtable<>();
		
		simpleHashtable.put("Ankica", 5);
		simpleHashtable.put("Jasenka", 4);
		simpleHashtable.put("Jesenka", 2);
		simpleHashtable.put("Ivana", 3);
		simpleHashtable.put("Ana", 4);
		simpleHashtable.put("Ankica", 1);
		
		assertFalse(simpleHashtable.containsKey("Iva"));
	}
	
	@Test 
	public void testContainsValueDoesContain() {
		SimpleHashtable<String, Integer> simpleHashtable = new SimpleHashtable<>();
		
		simpleHashtable.put("Ankica", 5);
		simpleHashtable.put("Jasenka", 4);
		simpleHashtable.put("Jesenka", 2);
		simpleHashtable.put("Ivana", 3);
		simpleHashtable.put("Ana", 4);
		simpleHashtable.put("Ankica", 1);
		
		assertTrue(simpleHashtable.containsValue(2));
	}
	
	@Test 
	public void testContainsValueDoesNotContain() {
		SimpleHashtable<String, Integer> simpleHashtable = new SimpleHashtable<>();
		
		simpleHashtable.put("Ankica", 5);
		simpleHashtable.put("Jasenka", 4);
		simpleHashtable.put("Jesenka", 1);
		simpleHashtable.put("Ivana", 3);
		simpleHashtable.put("Ana", 4);
		simpleHashtable.put("Ankica", 1);
		
		assertFalse(simpleHashtable.containsValue(2));
	}
	
	@Test 
	public void testRemoveKeyIsInTable() {
		SimpleHashtable<String, Integer> simpleHashtable = new SimpleHashtable<>();
		
		simpleHashtable.put("Ankica", 5);
		simpleHashtable.put("Jasenka", 4);
		simpleHashtable.put("Jesenka", 1);
		simpleHashtable.put("Ivana", 3);
		simpleHashtable.put("Ana", 4);
		simpleHashtable.put("Ankica", 1);
		
		assertEquals(5, simpleHashtable.size());
		
		simpleHashtable.remove("Ankica");
		
		assertEquals(4, simpleHashtable.size());
		
		simpleHashtable.remove("Jasenka");
		
		assertEquals(3, simpleHashtable.size());
		
		simpleHashtable.remove("Ana");
		
		assertEquals(2, simpleHashtable.size());
		
		simpleHashtable.remove("Jesenka");
		
		assertEquals(1, simpleHashtable.size());
		
		String string = simpleHashtable.toString();
		
		assertEquals("[Ivana=3]", string);
	}
	
	@Test 
	public void testRemoveKeyIsNotInTable() {
		SimpleHashtable<String, Integer> simpleHashtable = new SimpleHashtable<>();
		
		simpleHashtable.put("Ankica", 5);
		simpleHashtable.put("Jasenka", 4);
		simpleHashtable.put("Jesenka", 1);
		simpleHashtable.put("Ivana", 3);
		simpleHashtable.put("Ana", 4);
		simpleHashtable.put("Ankica", 1);
		
		assertEquals(5, simpleHashtable.size());
		
		simpleHashtable.remove("Iva");
		
		assertEquals(5, simpleHashtable.size());
	}
	
	@Test
	public void testGetKeyIsNull() {
		SimpleHashtable<String, Integer> simpleHashtable = new SimpleHashtable<>();
		
		simpleHashtable.put("Ankica", 5);
		simpleHashtable.put("Jasenka", 4);
		simpleHashtable.put("Jesenka", 1);
		simpleHashtable.put("Ivana", 3);
		simpleHashtable.put("Ana", 4);
		simpleHashtable.put("Ankica", 1);
		
		Integer value = simpleHashtable.get(null);
		
		assertNull(value);
	}
	
	@Test
	public void testContainsKeyIsNullValue() {
		SimpleHashtable<String, Integer> simpleHashtable = new SimpleHashtable<>();
		
		simpleHashtable.put("Ankica", 5);
		simpleHashtable.put("Jasenka", 4);
		simpleHashtable.put("Jesenka", 1);
		simpleHashtable.put("Ivana", 3);
		simpleHashtable.put("Ana", 4);
		simpleHashtable.put("Ankica", 1);
		
		assertFalse(simpleHashtable.containsKey(null));
	}
	
	@Test
	public void testContainsValueContainsNullValue() {
		SimpleHashtable<String, Integer> simpleHashtable = new SimpleHashtable<>();
		
		simpleHashtable.put("Ankica", 5);
		simpleHashtable.put("Jasenka", 4);
		simpleHashtable.put("Jesenka", 1);
		simpleHashtable.put("Ivana", 3);
		simpleHashtable.put("Ana", 4);
		simpleHashtable.put("Ankica", 1);
		simpleHashtable.put("Ankica", null);
				
		assertEquals(5, simpleHashtable.size());
		
		assertTrue(simpleHashtable.containsValue(null));
	}
	
	@Test
	public void testContainsValueDoesNotContainNullValue() {
		SimpleHashtable<String, Integer> simpleHashtable = new SimpleHashtable<>();
		
		simpleHashtable.put("Ankica", 5);
		simpleHashtable.put("Jasenka", 4);
		simpleHashtable.put("Jesenka", 1);
		simpleHashtable.put("Ivana", 3);
		simpleHashtable.put("Ana", 4);
		simpleHashtable.put("Ankica", 1);
				
		assertEquals(5, simpleHashtable.size());
		
		assertFalse(simpleHashtable.containsValue(null));
	}
	
	@Test
	public void testClear() {
		SimpleHashtable<String, Integer> simpleHashtable = new SimpleHashtable<>();
		
		simpleHashtable.put("Ankica", 5);
		simpleHashtable.put("Jasenka", 4);
		simpleHashtable.put("Jesenka", 1);
		simpleHashtable.put("Ivana", 3);
		simpleHashtable.put("Ana", 4);
		simpleHashtable.put("Ankica", 1);
				
		assertEquals(5, simpleHashtable.size());
		
		simpleHashtable.clear();
		
		assertEquals(0, simpleHashtable.size());
	}
	
	@Test
	public void testIteratorOneElement() {
		SimpleHashtable<String, Integer> simpleHashtable = new SimpleHashtable<>();
		
		simpleHashtable.put("Ankica", 5);
		
		for(@SuppressWarnings("unused") TableEntry<String, Integer> tableEntry : simpleHashtable) {
		}
	}
	
	@Test
	public void testIteratorZeroElements() {
		SimpleHashtable<String, Integer> simpleHashtable = new SimpleHashtable<>();
		
		for(@SuppressWarnings("unused") TableEntry<String, Integer> tableEntry : simpleHashtable) {
		}
	}
	
	@Test
	public void testIteratorFiveElements() {
		SimpleHashtable<String, Integer> simpleHashtable = new SimpleHashtable<>();
		
		simpleHashtable.put("Ankica", 5);
		simpleHashtable.put("Jasenka", 4);
		simpleHashtable.put("Jesenka", 1);
		simpleHashtable.put("Ivana", 3);
		simpleHashtable.put("Ana", 4);
		simpleHashtable.put("Ankica", 1);
		
		for(@SuppressWarnings("unused") TableEntry<String, Integer> tableEntry : simpleHashtable) {
		}
	}
	
	@Test
	public void testAddTwentyElementsEnlargeTable() {
		SimpleHashtable<String, Integer> simpleHashtable = new SimpleHashtable<>(1);
		
		simpleHashtable.put("Ankica", 5);
		simpleHashtable.put("Jasenka", 4);
		simpleHashtable.put("Jesenka", 1);
		simpleHashtable.put("Ivana", 3);
		simpleHashtable.put("Ana", 4);
		
		simpleHashtable.put("Marko", 5);
		simpleHashtable.put("Luka", 4);
		simpleHashtable.put("Ivan", 1);
		simpleHashtable.put("Petar", 3);
		simpleHashtable.put("Ante", 4);
		
		simpleHashtable.put("Martin", 5);
		simpleHashtable.put("Toni", 4);
		simpleHashtable.put("Filip", 1);
		simpleHashtable.put("Mihovil", 3);
		simpleHashtable.put("Matko", 4);
		
		simpleHashtable.put("Bruno", 5);
		simpleHashtable.put("Goran", 4);
		simpleHashtable.put("Zoran", 1);
		simpleHashtable.put("Jasna", 3);
		simpleHashtable.put("Anamarija", 4);
		
		for(TableEntry<String, Integer> tableEntry : simpleHashtable) {
			assertTrue(simpleHashtable.containsKey(tableEntry.getKey()));
		}
	}
	
	@Test
	public void testAddFourtyElementsEnlargeTable() {
		SimpleHashtable<String, Integer> simpleHashtable = new SimpleHashtable<>(1);
		
		simpleHashtable.put("Ankica", 5);
		simpleHashtable.put("Jasenka", 4);
		simpleHashtable.put("Jesenka", 1);
		simpleHashtable.put("Ivana", 3);
		simpleHashtable.put("Ana", 4);
		
		simpleHashtable.put("Marko", 5);
		simpleHashtable.put("Luka", 4);
		simpleHashtable.put("Ivan", 1);
		simpleHashtable.put("Petar", 3);
		simpleHashtable.put("Ante", 4);
		
		simpleHashtable.put("Martin", 5);
		simpleHashtable.put("Toni", 4);
		simpleHashtable.put("Filip", 1);
		simpleHashtable.put("Mihovil", 3);
		simpleHashtable.put("Matko", 4);
		
		simpleHashtable.put("Bruno", 5);
		simpleHashtable.put("Goran", 4);
		simpleHashtable.put("Zoran", 1);
		simpleHashtable.put("Jasna", 3);
		simpleHashtable.put("Anamarija", 4);
		
		simpleHashtable.put("Ankica2", 5);
		simpleHashtable.put("Jasenka2", 4);
		simpleHashtable.put("Jesenka2", 1);
		simpleHashtable.put("Ivana2", 3);
		simpleHashtable.put("Ana2", 4);
		
		simpleHashtable.put("Marko2", 5);
		simpleHashtable.put("Luka2", 4);
		simpleHashtable.put("Ivan2", 1);
		simpleHashtable.put("Petar2", 3);
		simpleHashtable.put("Ante2", 4);
		
		simpleHashtable.put("Martin2", 5);
		simpleHashtable.put("Toni2", 4);
		simpleHashtable.put("Filip2", 1);
		simpleHashtable.put("Mihovil2", 3);
		simpleHashtable.put("Matko2", 4);
		
		simpleHashtable.put("Bruno2", 5);
		simpleHashtable.put("Goran2", 4);
		simpleHashtable.put("Zoran2", 1);
		simpleHashtable.put("Jasna2", 3);
		simpleHashtable.put("Anamarija2", 4);
		
		for(TableEntry<String, Integer> tableEntry : simpleHashtable) {
			assertTrue(simpleHashtable.containsKey(tableEntry.getKey()));
		}
	}
	
	@Test
	public void testIteratorTwentyElements() {
		SimpleHashtable<String, Integer> simpleHashtable = new SimpleHashtable<>();
		
		simpleHashtable.put("Ankica", 5);
		simpleHashtable.put("Jasenka", 4);
		simpleHashtable.put("Jesenka", 1);
		simpleHashtable.put("Ivana", 3);
		simpleHashtable.put("Ana", 4);
		
		simpleHashtable.put("Marko", 5);
		simpleHashtable.put("Luka", 4);
		simpleHashtable.put("Ivan", 1);
		simpleHashtable.put("Petar", 3);
		simpleHashtable.put("Ante", 4);
		
		simpleHashtable.put("Martin", 5);
		simpleHashtable.put("Toni", 4);
		simpleHashtable.put("Filip", 1);
		simpleHashtable.put("Mihovil", 3);
		simpleHashtable.put("Matko", 4);
		
		simpleHashtable.put("Bruno", 5);
		simpleHashtable.put("Goran", 4);
		simpleHashtable.put("Zoran", 1);
		simpleHashtable.put("Jasna", 3);
		simpleHashtable.put("Anamarija", 4);
		
		int numberOfIterations = 0;
		for(@SuppressWarnings("unused") TableEntry<String, Integer> tableEntry : simpleHashtable) {
			numberOfIterations++; 
		}
		
		assertEquals(20, numberOfIterations);
	}
	
	@Test(expected=NoSuchElementException.class)
	public void testIteratorNoMoreElements() {
		SimpleHashtable<String, Integer> simpleHashtable = new SimpleHashtable<>();
		
		simpleHashtable.put("Ankica", 5);
		simpleHashtable.put("Jasenka", 4);
		simpleHashtable.put("Jesenka", 1);
		simpleHashtable.put("Ivana", 3);
		simpleHashtable.put("Ana", 4);
		simpleHashtable.put("Ankica", 1);
		
		@SuppressWarnings("rawtypes")
		Iterator it = simpleHashtable.iterator();
		
		while(true) {
			it.next();
		}
	}
	
	@Test(expected=IllegalStateException.class)
	public void testIteratorRemoveSameElementTwice() {
		SimpleHashtable<String, Integer> simpleHashtable = new SimpleHashtable<>();
		
		simpleHashtable.put("Ankica", 5);
		simpleHashtable.put("Jasenka", 4);
		simpleHashtable.put("Jesenka", 1);
		simpleHashtable.put("Ivana", 3);
		simpleHashtable.put("Ana", 4);
		simpleHashtable.put("Ankica", 1);
		
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = simpleHashtable.iterator();
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
			if(pair.getKey().equals("Ivana")) {
					iter.remove();
					iter.remove();
			}
		}
	}
	
	@Test
	public void testIteratorRemove() {
		SimpleHashtable<String, Integer> simpleHashtable = new SimpleHashtable<>();
		
		simpleHashtable.put("Ankica", 5);
		simpleHashtable.put("Jasenka", 4);
		simpleHashtable.put("Jesenka", 1);
		simpleHashtable.put("Ivana", 3);
		simpleHashtable.put("Ana", 4);
		simpleHashtable.put("Ankica", 1);
		
		assertEquals(5, simpleHashtable.size());
		assertTrue(simpleHashtable.containsKey("Ivana"));
		
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = simpleHashtable.iterator();
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
			if(pair.getKey().equals("Ivana")) {
					iter.remove();
			}
		}
		
		assertEquals(4, simpleHashtable.size());
		assertFalse(simpleHashtable.containsKey("Ivana"));
	}
	
	@Test
	public void testRemoveTwentyElements() {
		SimpleHashtable<String, Integer> simpleHashtable = new SimpleHashtable<>(2);
		
		simpleHashtable.put("Ankica", 5);
		simpleHashtable.put("Jasenka", 4);
		simpleHashtable.put("Jesenka", 1);
		simpleHashtable.put("Ivana", 3);
		simpleHashtable.put("Ana", 4);

		simpleHashtable.put("Marko", 5);
		simpleHashtable.put("Luka", 4);
		simpleHashtable.put("Ivan", 1);
		simpleHashtable.put("Petar", 3);
		simpleHashtable.put("Ante", 4);
		
		simpleHashtable.put("Martin", 5);
		simpleHashtable.put("Toni", 4);
		simpleHashtable.put("Filip", 1);
		simpleHashtable.put("Mihovil", 3);
		simpleHashtable.put("Matko", 4);
		
		simpleHashtable.put("Bruno", 5);
		simpleHashtable.put("Goran", 4);
		simpleHashtable.put("Zoran", 1);
		simpleHashtable.put("Jasna", 3);
		simpleHashtable.put("Anamarija", 4);
		
		int size = 20;
		
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = simpleHashtable.iterator();
		while(iter.hasNext()) {
			assertEquals(size, simpleHashtable.size());
			@SuppressWarnings("unused")
			SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
			iter.remove();
			
			size--;
			assertEquals(size, simpleHashtable.size());
		}
	}
	
	@Test(expected=ConcurrentModificationException.class)
	public void testIteratorRemoveOutsideIteratorWhileIterating() {
		SimpleHashtable<String, Integer> simpleHashtable = new SimpleHashtable<>();
		
		simpleHashtable.put("Ankica", 5);
		simpleHashtable.put("Jasenka", 4);
		simpleHashtable.put("Jesenka", 1);
		simpleHashtable.put("Ivana", 3);
		simpleHashtable.put("Ana", 4);
		simpleHashtable.put("Ankica", 1);
		
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = simpleHashtable.iterator();
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
			if(pair.getKey().equals("Ivana")) {
				simpleHashtable.remove("Ivana");
			}
		}
	}
	
	@Test(expected=ConcurrentModificationException.class)
	public void testIteratorPutOutsideIteratorWhileIterating() {
		SimpleHashtable<String, Integer> simpleHashtable = new SimpleHashtable<>(1);
		
		simpleHashtable.put("Ankica", 5);
		simpleHashtable.put("Jasenka", 4);
		simpleHashtable.put("Jesenka", 1);
		simpleHashtable.put("Ivana", 3);
		simpleHashtable.put("Ana", 4);
		simpleHashtable.put("Ankica", 1);
		
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = simpleHashtable.iterator();
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
			if(pair.getKey().equals("Ivana")) {
				simpleHashtable.put("Lucija", 3);
			}
		}
	}
}
