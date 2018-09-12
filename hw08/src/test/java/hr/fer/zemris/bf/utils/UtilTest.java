package hr.fer.zemris.bf.utils;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import hr.fer.zemris.bf.model.Node;
import hr.fer.zemris.bf.parser.Parser;

@SuppressWarnings("javadoc")
public class UtilTest {

	@Test
	public void testIndexToByteArrayExampleFromHomework1() {
		byte[] byteArray = Util.indexToByteArray(3, 2);
		
		assertArrayEquals(new byte[] {1, 1}, byteArray);
	}
	
	@Test
	public void testIndexToByteArrayExampleFromHomework2() {
		byte[] byteArray = Util.indexToByteArray(3, 4);
		
		assertArrayEquals(new byte[] {0, 0, 1, 1}, byteArray);
	}
	
	@Test
	public void testIndexToByteArrayExampleFromHomework3() {
		byte[] byteArray = Util.indexToByteArray(3, 6);
		
		assertArrayEquals(new byte[] {0, 0, 0, 0, 1, 1}, byteArray);
	}
	
	@Test
	public void testIndexToByteArrayExampleFromHomework4() {
		byte[] byteArray = Util.indexToByteArray(-2, 32);
		
		assertArrayEquals(new byte[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}, byteArray);
	}
	
	@Test
	public void testIndexToByteArrayExampleFromHomework5() {
		byte[] byteArray = Util.indexToByteArray(-2, 16);
		
		assertArrayEquals(new byte[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}, byteArray);
	}
	
	@Test
	public void testIndexToByteArrayExampleFromHomework6() {
		byte[] byteArray = Util.indexToByteArray(19, 4);
		
		assertArrayEquals(new byte[] {0, 0, 1, 1}, byteArray);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testIndexToByteArrayInvalidLength1() {
		@SuppressWarnings("unused")
		byte[] byteArray = Util.indexToByteArray(3, 0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testIndexToByteArrayInvalidLength2() {
		@SuppressWarnings("unused")
		byte[] byteArray = Util.indexToByteArray(3, -5);
	}
	
	@Test
	public void testIndexToByteArrayNumber0Length1() {
		byte[] byteArray = Util.indexToByteArray(0, 1);
		
		assertArrayEquals(new byte[] {0}, byteArray);
	}
	
	@Test
	public void testIndexToByteArrayNumber1Length1() {
		byte[] byteArray = Util.indexToByteArray(1, 1);
		
		assertArrayEquals(new byte[] {1}, byteArray);
	}
	
	@Test
	public void testIndexToByteArrayNumberMinus1Length1() {
		byte[] byteArray = Util.indexToByteArray(-1, 1);
		
		assertArrayEquals(new byte[] {1}, byteArray);
	}
	
	@Test
	public void testIndexToByteArrayNumberMinus2Length1() {
		byte[] byteArray = Util.indexToByteArray(-2, 1);
		
		assertArrayEquals(new byte[] {0}, byteArray);
	}
	
	@Test
	public void testIndexToByteArrayNumber5Length12() {
		byte[] byteArray = Util.indexToByteArray(5, 12);
		
		assertArrayEquals(new byte[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1}, byteArray);
	}
	
	@Test
	public void testToSumOfMinterms() {
		String dontCares = "NOT A AND B AND NOT D";
		List<String> listOfVariables = new ArrayList<>();
		listOfVariables.add("A");
		listOfVariables.add("B");
		listOfVariables.add("C");
		listOfVariables.add("D");
		
		Parser parser = new Parser(dontCares);
		Node expression = parser.getExpression();
		
		ExpressionEvaluator eval = new ExpressionEvaluator(listOfVariables);
		eval.setValues(new boolean[]{true, true, true, true});
		expression.accept(eval);
		System.out.println();
		System.out.println("RESULT: " + eval.getResult());
		System.out.println();
		
		Set<Integer> dontCareSet = Util.toSumOfMinterms(listOfVariables, expression);
		
		System.out.println(dontCareSet);
	}
}
