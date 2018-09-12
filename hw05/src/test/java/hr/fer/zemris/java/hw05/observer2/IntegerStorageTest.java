package hr.fer.zemris.java.hw05.observer2;

import static org.junit.Assert.*;

import org.junit.Test;

import hr.fer.zemris.java.hw05.observer1.IntegerStorage;
import hr.fer.zemris.java.hw05.observer1.SquareValue;

@SuppressWarnings("javadoc")
public class IntegerStorageTest {

	private static final int INITIAL_VALUE = 10;

	@Test
	public void testCreateIntegerStorage() {
		IntegerStorage integerStorage = new IntegerStorage(INITIAL_VALUE);
		
		assertEquals(INITIAL_VALUE, integerStorage.getValue());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAddNullValueAsObserver() {
		IntegerStorage integerStorage = new IntegerStorage(INITIAL_VALUE);
		
		integerStorage.addObserver(null);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testRemoveObserverWhenThereIsNoObservers() {
		IntegerStorage integerStorage = new IntegerStorage(INITIAL_VALUE);
		
		SquareValue squareValue = new SquareValue();
		
		integerStorage.removeObserver(squareValue);
	}

}
