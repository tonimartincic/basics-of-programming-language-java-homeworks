package hr.fer.zemris.bf.qmc;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class MaskTest {

	@Test
	public void testFirstConstructor1() {
		Mask mask = new Mask(3, 4, true);
		
		assertTrue(mask.isDontCare());
		assertFalse(mask.isCombined());
		
		assertEquals(0, mask.getValueAt(0));
		assertEquals(0, mask.getValueAt(1));
		assertEquals(1, mask.getValueAt(2));
		assertEquals(1, mask.getValueAt(3));
		
		assertEquals(3, mask.getIndexes().toArray()[0]);
	}
	
	@Test
	public void testFirstConstructor2() {
		Mask mask = new Mask(3, 2, true);
		
		assertTrue(mask.isDontCare());
		assertFalse(mask.isCombined());
		
		assertEquals(1, mask.getValueAt(0));
		assertEquals(1, mask.getValueAt(1));
		
		assertEquals(3, mask.getIndexes().toArray()[0]);
	}
	
	@Test
	public void testFirstConstructor3() {
		Mask mask = new Mask(0, 3, false);
		
		assertFalse(mask.isDontCare());
		assertFalse(mask.isCombined());
		
		assertEquals(0, mask.getValueAt(0));
		assertEquals(0, mask.getValueAt(1));
		assertEquals(0, mask.getValueAt(2));
		
		assertEquals(0, mask.getIndexes().toArray()[0]);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFirstConstructorNegativeIndex() {
		@SuppressWarnings("unused")
		Mask mask = new Mask(2, 0, false);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFirstConstructorNumberOfVariablesLessThanOne() {
		@SuppressWarnings("unused")
		Mask mask = new Mask(-1, 3, false);
	}
	
	@Test
	public void testSecondConstructor1() {
		byte[] values = new byte[]{0, 1, 1, 1};
		
		Set<Integer> indexes = new TreeSet<>();
		indexes.add(7);
		
		boolean dontCare = false;
		
		Mask mask = new Mask(values, indexes, dontCare);
		
		assertFalse(mask.isDontCare());
		assertFalse(mask.isCombined());
		
		assertEquals(0, mask.getValueAt(0));
		assertEquals(1, mask.getValueAt(1));
		assertEquals(1, mask.getValueAt(2));
		assertEquals(1, mask.getValueAt(3));
		
		assertEquals(7, mask.getIndexes().toArray()[0]);
	}
	
	@Test
	public void testSecondConstructor2() {
		byte[] values = new byte[]{0, 2, 1, 1};
		
		Set<Integer> indexes = new TreeSet<>();
		indexes.add(7);
		indexes.add(4);
		
		boolean dontCare = false;
		
		Mask mask = new Mask(values, indexes, dontCare);
		
		assertFalse(mask.isDontCare());
		assertFalse(mask.isCombined());
		
		assertEquals(0, mask.getValueAt(0));
		assertEquals(2, mask.getValueAt(1));
		assertEquals(1, mask.getValueAt(2));
		assertEquals(1, mask.getValueAt(3));
		
		assertEquals(4, mask.getIndexes().toArray()[0]);
		assertEquals(7, mask.getIndexes().toArray()[1]);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSecondConstructorValuesIsNull() {
		byte[] values = null;
		
		Set<Integer> indexes = new TreeSet<>();
		indexes.add(7);
		
		boolean dontCare = true;
		
		@SuppressWarnings("unused")
		Mask mask = new Mask(values, indexes, dontCare);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSecondConstructorIndexesIsNull() {
		byte[] values = new byte[]{0, 1, 1, 1};
		
		Set<Integer> indexes = null;
		
		boolean dontCare = true;
		
		@SuppressWarnings("unused")
		Mask mask = new Mask(values, indexes, dontCare);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSecondConstructorIndexesIsEmpty() {
		byte[] values = new byte[]{0, 1, 1, 1};
		
		Set<Integer> indexes = new TreeSet<>();
		
		boolean dontCare = true;
		
		@SuppressWarnings("unused")
		Mask mask = new Mask(values, indexes, dontCare);
	}
	
	@Test
	public void testHashcodeOfSameMask() {
		byte[] values = new byte[]{0, 1, 1, 1};
		
		Set<Integer> indexes = new TreeSet<>();
		indexes.add(7);
		
		boolean dontCare = false;
		
		Mask mask = new Mask(values, indexes, dontCare);
		
		int hashcode1 = mask.hashCode();
		int hashcode2 = mask.hashCode();
		
		assertEquals(hashcode1, hashcode2);
	}
	

	@Test
	public void testEqualsAreEquals() {
		byte[] values1 = new byte[]{0, 1, 1, 1};
		
		Set<Integer> indexes1 = new TreeSet<>();
		indexes1.add(7);
		
		boolean dontCare1 = false;
		
		Mask mask1 = new Mask(values1, indexes1, dontCare1);
		
		byte[] values2 = new byte[]{0, 1, 1, 1};
		
		Set<Integer> indexes2 = new TreeSet<>();
		indexes2.add(7);
		
		boolean dontCare2 = true;
		
		Mask mask2 = new Mask(values2, indexes2, dontCare2);
		
		assertTrue(mask1.equals(mask2));
	}
	
	@Test
	public void testEqualsAreNotEquals() {
		byte[] values1 = new byte[]{0, 1, 1, 1};
		
		Set<Integer> indexes1 = new TreeSet<>();
		indexes1.add(7);
		
		boolean dontCare1 = false;
		
		Mask mask1 = new Mask(values1, indexes1, dontCare1);
		
		byte[] values2 = new byte[]{0, 1, 1, 2};
		
		Set<Integer> indexes2 = new TreeSet<>();
		indexes2.add(7);
		indexes2.add(6);
		
		boolean dontCare2 = true;
		
		Mask mask2 = new Mask(values2, indexes2, dontCare2);
		
		assertFalse(mask1.equals(mask2));
	}
	
	@Test
	public void testEqualsSameMask() {
		byte[] values1 = new byte[]{0, 1, 1, 1};
		
		Set<Integer> indexes1 = new TreeSet<>();
		indexes1.add(7);
		
		boolean dontCare1 = false;
		
		Mask mask1 = new Mask(values1, indexes1, dontCare1);
		
		assertTrue(mask1.equals(mask1));
	}
	
	@Test
	public void testEqualsOtherIsNull() {
		byte[] values1 = new byte[]{0, 1, 1, 1};
		
		Set<Integer> indexes1 = new TreeSet<>();
		indexes1.add(7);
		
		boolean dontCare1 = false;
		
		Mask mask1 = new Mask(values1, indexes1, dontCare1);
		
		assertFalse(mask1.equals(null));
	}
	
	@Test
	public void testEqualsOtherIsNotInstanceOfMask() {
		byte[] values1 = new byte[]{0, 1, 1, 1};
		
		Set<Integer> indexes1 = new TreeSet<>();
		indexes1.add(7);
		
		boolean dontCare1 = false;
		
		Mask mask1 = new Mask(values1, indexes1, dontCare1);
		
		List<Double> other = new ArrayList<>();
		
		assertFalse(mask1.equals(other));
	}
	
	@Test
	public void testCombined() {
		byte[] values1 = new byte[]{0, 1, 1, 1};
		
		Set<Integer> indexes1 = new TreeSet<>();
		indexes1.add(7);
		
		boolean dontCare1 = false;
		
		Mask mask1 = new Mask(values1, indexes1, dontCare1);
		
		assertFalse(mask1.isCombined());
		
		mask1.setCombined(true);
		
		assertTrue(mask1.isCombined());
	}
	
	@Test
	public void testCountOfOnes() {
		byte[] values1 = new byte[]{0, 1, 1, 1};
		
		Set<Integer> indexes1 = new TreeSet<>();
		indexes1.add(7);
		
		boolean dontCare1 = false;
		
		Mask mask1 = new Mask(values1, indexes1, dontCare1);
		
		assertEquals(3, mask1.countOfOnes());
	}
	
	@Test
	public void testToString1() {
		byte[] values1 = new byte[]{0, 1, 1, 1};
		
		Set<Integer> indexes1 = new TreeSet<>();
		indexes1.add(7);
		
		boolean dontCare1 = false;
		
		Mask mask1 = new Mask(values1, indexes1, dontCare1);
		
		assertEquals("0111 .    [7]", mask1.toString());
		
	}
	
	@Test
	public void testToString2() {
		byte[] values1 = new byte[]{0, 1, 1, 1};
		
		Set<Integer> indexes1 = new TreeSet<>();
		indexes1.add(7);
		
		boolean dontCare1 = true;
		
		Mask mask1 = new Mask(values1, indexes1, dontCare1);
		
		mask1.setCombined(true);
		
		assertEquals("0111 D  * [7]", mask1.toString());
	}
	
	@Test
	public void testToString3() {
		byte[] values1 = new byte[]{0, 1, 2, 2};
		
		Set<Integer> indexes1 = new TreeSet<>();
		indexes1.add(4);
		indexes1.add(5);
		indexes1.add(6);
		indexes1.add(7);
		
		boolean dontCare1 = true;
		
		Mask mask1 = new Mask(values1, indexes1, dontCare1);
		
		mask1.setCombined(true);
		
		assertEquals("01-- D  * [4, 5, 6, 7]", mask1.toString());
	}
	
	@Test
	public void testSize() {
		byte[] values1 = new byte[]{0, 1, 2, 2};
		
		Set<Integer> indexes1 = new TreeSet<>();
		indexes1.add(4);
		indexes1.add(5);
		indexes1.add(6);
		indexes1.add(7);
		
		boolean dontCare1 = true;
		
		Mask mask1 = new Mask(values1, indexes1, dontCare1);
		
		assertEquals(4, mask1.size());
	}

	@Test
	public void testGetValueAtLegalIndex() {
		byte[] values1 = new byte[]{0, 1, 2, 2};
		
		Set<Integer> indexes1 = new TreeSet<>();
		indexes1.add(4);
		indexes1.add(5);
		indexes1.add(6);
		indexes1.add(7);
		
		boolean dontCare1 = true;
		
		Mask mask1 = new Mask(values1, indexes1, dontCare1);
		
		assertEquals(2, mask1.getValueAt(2));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetValueAtIllegalIndex() {
		byte[] values1 = new byte[]{0, 1, 2, 2};
		
		Set<Integer> indexes1 = new TreeSet<>();
		indexes1.add(4);
		indexes1.add(5);
		indexes1.add(6);
		indexes1.add(7);
		
		boolean dontCare1 = true;
		
		Mask mask1 = new Mask(values1, indexes1, dontCare1);
		
		mask1.getValueAt(-1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCombineWithOtherIsNull() {
		byte[] values1 = new byte[]{0, 1, 1, 1};
		
		Set<Integer> indexes1 = new TreeSet<>();
		indexes1.add(7);
		
		boolean dontCare1 = true;
		
		Mask mask1 = new Mask(values1, indexes1, dontCare1);
		
		mask1.combineWith(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCombineWithOtherSizeDifferent() {
		byte[] values1 = new byte[]{0, 1, 1, 1};
		
		Set<Integer> indexes1 = new TreeSet<>();
		indexes1.add(7);
		
		boolean dontCare1 = true;
		
		Mask mask1 = new Mask(values1, indexes1, dontCare1);
		
		byte[] values2 = new byte[]{0, 0, 1, 1, 1};
		
		Set<Integer> indexes2 = new TreeSet<>();
		indexes2.add(7);
		
		boolean dontCare2 = true;
		
		Mask mask2 = new Mask(values2, indexes2, dontCare2);
		
		mask1.combineWith(mask2);
	}
	
	@Test
	public void testCombineWithDontCaresAreNotAtSamePlaces1() {
		byte[] values1 = new byte[]{0, 2, 1, 1};
		
		Set<Integer> indexes1 = new TreeSet<>();
		indexes1.add(3);
		indexes1.add(7);
		
		boolean dontCare1 = true;
		
		Mask mask1 = new Mask(values1, indexes1, dontCare1);
		
		byte[] values2 = new byte[] {0, 1, 1, 2};
		
		Set<Integer> indexes2 = new TreeSet<>();
		indexes2.add(6);
		indexes2.add(7);
		
		boolean dontCare2 = true;
		
		Mask mask2 = new Mask(values2, indexes2, dontCare2);
		
		Optional<Mask> mask3 = mask1.combineWith(mask2);
		
		assertFalse(mask3.isPresent());
	}
	
	@Test
	public void testCombineWithDontCaresAreNotAtSamePlaces2() {
		byte[] values1 = new byte[]{2, 0, 1, 1};
		
		Set<Integer> indexes1 = new TreeSet<>();
		indexes1.add(3);
		indexes1.add(11);
		
		boolean dontCare1 = true;
		
		Mask mask1 = new Mask(values1, indexes1, dontCare1);
		
		byte[] values2 = new byte[] {0, 1, 1, 2};
		
		Set<Integer> indexes2 = new TreeSet<>();
		indexes2.add(6);
		indexes2.add(7);
		
		boolean dontCare2 = true;
		
		Mask mask2 = new Mask(values2, indexes2, dontCare2);
		
		Optional<Mask> mask3 = mask1.combineWith(mask2);
		
		assertFalse(mask3.isPresent());
	}
	
	@Test
	public void testCombineWithNumberOfDifferencesIsNotOne1() {
		byte[] values1 = new byte[]{1, 0, 1, 2};
		
		Set<Integer> indexes1 = new TreeSet<>();
		indexes1.add(10);
		indexes1.add(11);
		
		boolean dontCare1 = true;
		
		Mask mask1 = new Mask(values1, indexes1, dontCare1);
		
		byte[] values2 = new byte[] {0, 1, 1, 2};
		
		Set<Integer> indexes2 = new TreeSet<>();
		indexes2.add(6);
		indexes2.add(7);
		
		boolean dontCare2 = true;
		
		Mask mask2 = new Mask(values2, indexes2, dontCare2);
		
		Optional<Mask> mask3 = mask1.combineWith(mask2);
		
		assertFalse(mask3.isPresent());
	}
	
	@Test
	public void testCombineWithNumberOfDifferencesIsNotOne2() {
		byte[] values1 = new byte[]{0, 0, 0, 2};
		
		Set<Integer> indexes1 = new TreeSet<>();
		indexes1.add(0);
		indexes1.add(1);
		
		boolean dontCare1 = true;
		
		Mask mask1 = new Mask(values1, indexes1, dontCare1);
		
		byte[] values2 = new byte[] {0, 1, 1, 2};
		
		Set<Integer> indexes2 = new TreeSet<>();
		indexes2.add(6);
		indexes2.add(7);
		
		boolean dontCare2 = true;
		
		Mask mask2 = new Mask(values2, indexes2, dontCare2);
		
		Optional<Mask> mask3 = mask1.combineWith(mask2);
		
		assertFalse(mask3.isPresent());
	}
	
	@Test
	public void testCombineWithNumberOfDifferencesIsNotOne3() {
		byte[] values1 = new byte[]{0, 0, 0, 2};
		
		Set<Integer> indexes1 = new TreeSet<>();
		indexes1.add(0);
		indexes1.add(1);
		
		boolean dontCare1 = true;
		
		Mask mask1 = new Mask(values1, indexes1, dontCare1);
		
		byte[] values2 = new byte[] {0, 1, 1, 2};
		
		Set<Integer> indexes2 = new TreeSet<>();
		indexes2.add(6);
		indexes2.add(7);
		
		boolean dontCare2 = true;
		
		Mask mask2 = new Mask(values2, indexes2, dontCare2);
		
		Optional<Mask> mask3 = mask1.combineWith(mask2);
		
		assertFalse(mask3.isPresent());
	}
	
	@Test
	public void testCombineWith1() {
		byte[] values1 = new byte[]{0, 0, 0, 2};
		
		Set<Integer> indexes1 = new TreeSet<>();
		indexes1.add(0);
		indexes1.add(1);
		
		boolean dontCare1 = true;
		
		Mask mask1 = new Mask(values1, indexes1, dontCare1);
		
		byte[] values2 = new byte[] {0, 1, 0, 2};
		
		Set<Integer> indexes2 = new TreeSet<>();
		indexes2.add(4);
		indexes2.add(5);
		
		boolean dontCare2 = true;
		
		Mask mask2 = new Mask(values2, indexes2, dontCare2);
		
		Optional<Mask> mask3 = mask1.combineWith(mask2);
		
		assertTrue(mask3.isPresent());
		
		assertEquals(0, mask3.get().getValueAt(0));
		assertEquals(2, mask3.get().getValueAt(1));
		assertEquals(0, mask3.get().getValueAt(2));
		assertEquals(2, mask3.get().getValueAt(3));
		
		assertEquals(0, mask3.get().getIndexes().toArray()[0]);
		assertEquals(1, mask3.get().getIndexes().toArray()[1]);
		assertEquals(4, mask3.get().getIndexes().toArray()[2]);
		assertEquals(5, mask3.get().getIndexes().toArray()[3]);
	}
	
	@Test
	public void testCombineWith2() {
		byte[] values1 = new byte[]{2, 2, 1, 0};
		
		Set<Integer> indexes1 = new TreeSet<>();
		indexes1.add(2);
		indexes1.add(6);
		indexes1.add(14);
		
		boolean dontCare1 = true;
		
		Mask mask1 = new Mask(values1, indexes1, dontCare1);
		
		byte[] values2 = new byte[] {2, 2, 1, 1};
		
		Set<Integer> indexes2 = new TreeSet<>();
		indexes2.add(3);
		indexes2.add(7);
		indexes2.add(15);
		
		boolean dontCare2 = true;
		
		Mask mask2 = new Mask(values2, indexes2, dontCare2);
		
		Optional<Mask> mask3 = mask1.combineWith(mask2);
		
		assertTrue(mask3.isPresent());
		
		assertEquals(2, mask3.get().getValueAt(0));
		assertEquals(2, mask3.get().getValueAt(1));
		assertEquals(1, mask3.get().getValueAt(2));
		assertEquals(2, mask3.get().getValueAt(3));
		
		assertEquals(2, mask3.get().getIndexes().toArray()[0]);
		assertEquals(3, mask3.get().getIndexes().toArray()[1]);
		assertEquals(6, mask3.get().getIndexes().toArray()[2]);
		assertEquals(7, mask3.get().getIndexes().toArray()[3]);
		assertEquals(14, mask3.get().getIndexes().toArray()[4]);
		assertEquals(15, mask3.get().getIndexes().toArray()[5]);
	}
}
