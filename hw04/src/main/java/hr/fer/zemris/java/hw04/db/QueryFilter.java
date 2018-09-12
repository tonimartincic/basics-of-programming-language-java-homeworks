package hr.fer.zemris.java.hw04.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Class implements IFilter and filters student records by list of the conditional expressions.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class QueryFilter implements IFilter{
	
	/**
	 * List of the conditional expressions.
	 */
	List<ConditionalExpression> conditionalExpressions;
	
	/**
	 * Constructor which accepts list of the conditional expressions.
	 * 
	 * @param conditionalExpressions list of the conditional expressions
	 */
	public QueryFilter(List<ConditionalExpression> conditionalExpressions) {
		this.conditionalExpressions = new ArrayList<>();
		
		this.conditionalExpressions.addAll(conditionalExpressions);
	}

	@Override
	public boolean accepts(StudentRecord record) {
		for(ConditionalExpression conditionalExpression : conditionalExpressions) {
			if(!conditionalExpression.getComparisonOperator().satisfied(
					conditionalExpression.getFieldGetter().get(record),
					conditionalExpression.getStringLiteral())) {
				return false;
			}
		}
		
		return true;
	}
	

}
