package hr.fer.zemris.java.hw04.db;

import static org.junit.Assert.*;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class ComparisonOperatorsTest {

	@Test
	public void testComparisonOperatorLess() {
		IComparisonOperator oper = ComparisonOperators.LESS;
		
		assertTrue(oper.satisfied("Ana", "Jasna"));
		
		assertFalse(oper.satisfied("Ana", "Ana"));
	}
	
	@Test
	public void testComparisonOperatorLessOrEquals() {
		IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;
		
		assertTrue(oper.satisfied("Ana", "Jasna"));
		assertTrue(oper.satisfied("Ana", "Ana"));
		
		assertFalse(oper.satisfied("Jasna", "Ana"));
	}
	
	@Test
	public void testComparisonOperatorGreater() {
		IComparisonOperator oper = ComparisonOperators.GREATER;
		
		assertTrue(oper.satisfied("Jasna", "Ana"));
		
		assertFalse(oper.satisfied("Ana", "Ana"));
	}
	
	@Test
	public void testComparisonOperatorGreaterOrEquals() {
		IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
		
		assertTrue(oper.satisfied("Jasna", "Ana"));
		assertTrue(oper.satisfied("Ana", "Ana"));
		
		assertFalse(oper.satisfied("Ana", "Jasna"));
	}
	
	@Test
	public void testComparisonNotEquals() {
		IComparisonOperator oper = ComparisonOperators.NOT_EQUALS;
		
		assertTrue(oper.satisfied("Jasna", "Ana"));
		
		assertFalse(oper.satisfied("Ana", "Ana"));
	}
	
	@Test
	public void testComparisonEquals() {
		IComparisonOperator oper = ComparisonOperators.EQUALS;
		
		assertFalse(oper.satisfied("Jasna", "Ana"));
		
		assertTrue(oper.satisfied("Ana", "Ana"));
	}
	
	@Test
	public void testComparisonLikeFirstExampleFromHomework() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		
		assertFalse(oper.satisfied("Zagreb", "Aba*"));
	
	}
	
	@Test
	public void testComparisonLikeSecondExampleFromHomework() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
	
		assertFalse(oper.satisfied("AAA", "AA*AA"));
	}
	
	@Test
	public void testComparisonLikeThirdExampleFromHomework() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
	
		assertTrue(oper.satisfied("AAAA", "AA*AA"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testComparisonLikeTwoWildcards() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
	
		assertTrue(oper.satisfied("AAAA", "AA*A*A"));
	}
	
	@Test
	public void testComparisonLikeNoWildcardsTrue() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
	
		assertTrue(oper.satisfied("Zagreb", "Zagreb"));
	}
	
	@Test
	public void testComparisonLikeNoWildcardsFalse() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
	
		assertFalse(oper.satisfied("Zagreb", "Nije Zagreb"));
	}
	
	@Test
	public void testComparisonLikeWildcardAtTheBeginningTrue() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
	
		assertTrue(oper.satisfied("Bjelovar i Zagreb", "* i Zagreb"));
	}
	
	@Test
	public void testComparisonLikeWildcardAtTheBeginningFalse() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
	
		assertFalse(oper.satisfied("Bjelovar, Zagreb", "* i Zagreb"));
	}
	
	@Test
	public void testComparisonLikeWildcardAtTheEndTrue() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
	
		assertTrue(oper.satisfied("Zagreb je velik grad", "Zagreb *"));
	}
	
	@Test
	public void testComparisonLikeWildcardAtTheEndFalse() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
	
		assertFalse(oper.satisfied("Zagreb", "Zagreb *"));
	}
	
	@Test
	public void testComparisonLikeWildcardInTheMiddleTrue() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
	
		assertTrue(oper.satisfied("123 Riječ 123", "123 * 123"));
	}
	
	@Test
	public void testComparisonLikeWildcardInTheMiddleFalse() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
	
		assertFalse(oper.satisfied("111", "11*11"));
	}

}
