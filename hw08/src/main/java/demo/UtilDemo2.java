package demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.bf.model.Node;
import hr.fer.zemris.bf.parser.Parser;
import hr.fer.zemris.bf.utils.Util;
import hr.fer.zemris.bf.utils.VariablesGetter;

@SuppressWarnings("javadoc")
public class UtilDemo2 {

	public static void main(String[] args) {
		
		Node expression = new Parser("A and b or C").getExpression();
		//Node expression = new Parser("(A and b or C) xor (A * (B xor D))").getExpression();
		
		VariablesGetter getter = new VariablesGetter();
		expression.accept(getter);
		
		List<String> variables = getter.getVariables();
		System.out.println("Mintermi f("+variables+"): " + Util.toSumOfMinterms(variables, expression));
		
		List<String> variables2 = new ArrayList<>(variables);
		Collections.reverse(variables2);
		System.out.println("Mintermi f("+variables2+"): " + Util.toSumOfMinterms(variables2, expression));
	}
	
}
