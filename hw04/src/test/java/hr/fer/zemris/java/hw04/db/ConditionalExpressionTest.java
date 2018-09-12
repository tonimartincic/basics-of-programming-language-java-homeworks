package hr.fer.zemris.java.hw04.db;

import static org.junit.Assert.*;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class ConditionalExpressionTest {

	@Test
	public void testConditionalExpressionExampleFromHomeworkTrue() {
		ConditionalExpression expr = new ConditionalExpression(
			 FieldValueGetters.LAST_NAME,
			 "Bos*",
			 ComparisonOperators.LIKE
		);
		
		StudentRecord record = new StudentRecord("0036999999", "Bosnić", "Ana", 5);
				
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(
				expr.getFieldGetter().get(record), // returns lastName from given record
				expr.getStringLiteral() // returns "Bos*"
		);
		
		assertTrue(recordSatisfies);
	}
	
	@Test
	public void testConditionalExpressionExampleFromHomeworkFalse() {
		ConditionalExpression expr = new ConditionalExpression(
			 FieldValueGetters.LAST_NAME,
			 "Bos*",
			 ComparisonOperators.LIKE
		);
		
		StudentRecord record = new StudentRecord("0036999999", "Bošnjak", "Ana", 5);
				
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(
				expr.getFieldGetter().get(record), // returns lastName from given record
				expr.getStringLiteral() // returns "Bos*"
		);
		
		assertFalse(recordSatisfies);
	}
	
	@Test
	public void testConditionalExpressionLessTrue() {
		ConditionalExpression expr = new ConditionalExpression(
			 FieldValueGetters.FIRST_NAME,
			 "Jasna",
			 ComparisonOperators.LESS
		);
		
		StudentRecord record = new StudentRecord("0036999999", "Bosnić", "Ana", 5);
				
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(
				expr.getFieldGetter().get(record),
				expr.getStringLiteral() 
		);
		
		assertTrue(recordSatisfies);
	}
	
	@Test
	public void testConditionalExpressionLessFalse() {
		ConditionalExpression expr = new ConditionalExpression(
			 FieldValueGetters.FIRST_NAME,
			 "Jasna",
			 ComparisonOperators.LESS
		);
		
		StudentRecord record = new StudentRecord("0036999999", "Bosnić", "Žana", 5);
				
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(
				expr.getFieldGetter().get(record),
				expr.getStringLiteral() 
		);
		
		assertFalse(recordSatisfies);
	}
	
	@Test
	public void testConditionalExpressionLessOrEqualsTrue() {
		ConditionalExpression expr = new ConditionalExpression(
			 FieldValueGetters.FIRST_NAME,
			 "Jasna",
			 ComparisonOperators.LESS_OR_EQUALS
		);
		
		StudentRecord record = new StudentRecord("0036999999", "Bosnić", "Ana", 5);
				
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(
				expr.getFieldGetter().get(record),
				expr.getStringLiteral() 
		);
		
		assertTrue(recordSatisfies);
	}

}
