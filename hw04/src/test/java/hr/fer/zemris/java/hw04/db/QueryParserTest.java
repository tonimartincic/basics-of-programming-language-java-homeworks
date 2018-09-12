package hr.fer.zemris.java.hw04.db;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class QueryParserTest {
	
	@Test
	public void testExampleThreeConditionalExpressions() {
		QueryParser qp1 = new QueryParser(
				" jmbag =   \"0123456789\"  AND   firstName  >=  \"Ana\" AnD lastName LIKE \"*b\"");
		
		assertFalse(qp1.isDirectQuery());
	
		assertEquals(3, qp1.getQuery().size());
		
		List<ConditionalExpression> queryList = qp1.getQuery();
	
		ConditionalExpression conditionalExpression0 = queryList.get(0);
		ConditionalExpression conditionalExpression1 = queryList.get(1);
		ConditionalExpression conditionalExpression2 = queryList.get(2);
		
		assertEquals(FieldValueGetters.JMBAG, conditionalExpression0.getFieldGetter());
		assertEquals(ComparisonOperators.EQUALS, conditionalExpression0.getComparisonOperator());
		assertEquals("0123456789", conditionalExpression0.getStringLiteral());
		
		assertEquals(FieldValueGetters.FIRST_NAME, conditionalExpression1.getFieldGetter());
		assertEquals(ComparisonOperators.GREATER_OR_EQUALS, conditionalExpression1.getComparisonOperator());
		assertEquals("Ana", conditionalExpression1.getStringLiteral());
		
		assertEquals(FieldValueGetters.LAST_NAME, conditionalExpression2.getFieldGetter());
		assertEquals(ComparisonOperators.LIKE, conditionalExpression2.getComparisonOperator());
		assertEquals("*b", conditionalExpression2.getStringLiteral());
	}

	@Test
	public void testExampleFromHomework1() {
		QueryParser qp1 = new QueryParser(" jmbag =\"0123456789\" ");
		assertTrue(qp1.isDirectQuery());
		assertEquals("0123456789", qp1.getQueriedJMBAG());
		assertEquals(1, qp1.getQuery().size());
	}
	
	@Test
	public void testExampleFromHomework2() {
		QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		assertFalse(qp2.isDirectQuery());
		assertEquals(2, qp2.getQuery().size());
	}
	
	@Test
	public void testExampleFromHomework3() {
		QueryParser qp2 = new QueryParser("jmbag = \"0000000003\"");
		assertTrue(qp2.isDirectQuery());
		assertEquals("0000000003", qp2.getQueriedJMBAG());
		assertEquals(1, qp2.getQuery().size());
	}
	
	@Test
	public void testExampleFromHomework4() {
		QueryParser qp2 = new QueryParser("jmbag = \"0000000003\" AND lastName LIKE  \"B*\"");
		assertFalse(qp2.isDirectQuery());
		assertEquals(2, qp2.getQuery().size());
	}
	
	@Test
	public void testExampleFromHomework5() {
		QueryParser qp2 = new QueryParser(" jmbag = \"0000000003\" AND lastName LIKE \"L*\"");
		assertFalse(qp2.isDirectQuery());
		assertEquals(2, qp2.getQuery().size());
	}
	
	@Test
	public void testExampleFromHomework6() {
		QueryParser qp2 = new QueryParser("lastName LIKE \"B*\"");
		assertFalse(qp2.isDirectQuery());
		assertEquals(1, qp2.getQuery().size());
	}
	
	@Test
	public void testExampleFromHomework7() {
		QueryParser qp2 = new QueryParser("lastName LIKE \"Be*\"");
		assertFalse(qp2.isDirectQuery());
		assertEquals(1, qp2.getQuery().size());
	}
	
	@Test(expected=IllegalStateException.class)
	public void testgetQueriedJMBAGfromNonDirectQuery() {
		QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		assertFalse(qp2.isDirectQuery());
		qp2.getQueriedJMBAG(); // would throw!
	}
	
	@Test(expected=QueryParser.QueryParserException.class)
	public void testInvalidFieldGetter() {
		@SuppressWarnings("unused")
		QueryParser qp2 = new QueryParser("jMMMMbag=\"0123456789\" and lastName>\"J\"");
	}
	
	@Test(expected=QueryParser.QueryParserException.class)
	public void testInvalidFieldGetter2() {
		@SuppressWarnings("unused")
		QueryParser qp2 = new QueryParser("oib=\"0123456789\" and lastName>\"J\"");
	}
	
	@Test(expected=QueryParser.QueryParserException.class)
	public void testInvalidFieldGetter3() {
		@SuppressWarnings("unused")
		QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" and Name>\"J\"");
	}
	
	@Test(expected=QueryParser.QueryParserException.class)
	public void testInvalidFieldGetter4() {
		@SuppressWarnings("unused")
		QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" and Name>\"J and   first = \"Ana\"");
	}
	
	@Test(expected=QueryParser.QueryParserException.class)
	public void testInvalidOperator() {
		@SuppressWarnings("unused")
		QueryParser qp2 = new QueryParser("jmbag**\"0123456789\" and lastName>\"J\"");
	}
	
	@Test(expected=QueryParser.QueryParserException.class)
	public void testInvalidOperator2() {
		@SuppressWarnings("unused")
		QueryParser qp2 = new QueryParser("jmbag>=\"0123456789\" and lastName lajk  \"J\"");
	}
	
	@Test(expected=QueryParser.QueryParserException.class)
	public void testInvalidStringLiteral() {
		@SuppressWarnings("unused")
		QueryParser qp2 = new QueryParser("jmbag>  0123456789 and lastName>\"J\"");
	}
}
