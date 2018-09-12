package hr.fer.zemris.bf.utils;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;

import hr.fer.zemris.bf.model.Node;

/**
 * Class contains static methods which are called from node visitors which visit tree representations
 * of boolean expressions.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class Util {
	
	/**
	 * Method accepts variables of boolean expression and instance of {@link Consumer}. For each 
	 * combination of values of accepted variables method accept from accepted consumer is called.
	 * 
	 * @param variables variables of boolean expression
	 * @param consumer instance of {@link Consumer}
	 */
	public static void forEach(List<String> variables, Consumer<boolean[]> consumer) {
		List<boolean[]> allLines = getAllLines(variables);
		
		for(boolean[] line : allLines) {
			consumer.accept(line);
		}
	}

	/**
	 * Method returns all combinations of values for accepted variables.
	 * 
	 * @param variables accepted variables
	 * @return all combinations of values for accepted variables
	 */
	private static List<boolean[]> getAllLines(List<String> variables) {
		List<boolean[]> allLines = new ArrayList<>();
		
		for(int i = 0, n = (int) Math.pow(2, variables.size()); i < n; i++) {
			String line = Integer.toBinaryString(i);
			while(line.length() != variables.size()) {
				line = "0" + line;
			}
			
			char[] data = line.toCharArray();
			boolean[] values = new boolean[data.length];
			for(int j = 0; j < data.length; j++) {
				if(data[j] == '0') {
					values[j] = false;
				} else {
					values[j] = true;
				}
			}
			
			
			allLines.add(values);
		}
		
		return allLines;
	}
	
	/**
	 * Method returns only rows from truth table which result is equal to accepted expression value.
	 * 
	 * @param variables variables of boolean expression
	 * @param expression boolean expression
	 * @param expressionValue value of the result
	 * @return set of rows of the truth table which result is equal to accepted expression value
	 */
	public static Set<boolean[]> filterAssignments(
		List<String> variables, Node expression, boolean expressionValue) {
		
		Set<boolean[]> set = new LinkedHashSet<>();
		List<boolean[]> allLines = getAllLines(variables);
		
		ExpressionEvaluator eval = new ExpressionEvaluator(variables);
		
		for(boolean[] line : allLines) {
			eval.setValues(line);
			expression.accept(eval);
			
			if(eval.getResult() == expressionValue) {
				set.add(line);
			}
		}
		
		return set;
	}
	
	/**
	 * Method converts accepted boolean array to integer number. True in array represents digit 1, and
	 * false in array represents digit 0.
	 * 
	 * @param values accepted boolean array
	 * @return integer number
	 */
	public static int booleanArrayToInt(boolean[] values) {
		String temp = "";
		
		for(int i = 0; i < values.length; i++) {
			if(values[i] == true) {
				temp += "1";
			} else {
				temp += "0";
			}
		}
		
		return Integer.parseInt(temp, 2);
	}
	
	/**
	 * Method returns all minterms for accepted boolean expression.
	 * 
	 * @param variables variables of boolean expression
	 * @param expression accepted expression
	 * @return set of minterms
	 */
	public static Set<Integer> toSumOfMinterms(List<String> variables, Node expression) {
		Set<Integer> set = new TreeSet<>();
		
		Set<boolean[]> setOfMinterms = filterAssignments(variables, expression, true);
		
		for(boolean[] minterm : setOfMinterms) {
			set.add(booleanArrayToInt(minterm));
		}
		
		return set;
	}
	
	/**
	 * Method returns all maxterms for accepted boolean expression.
	 * 
	 * @param variables variables of boolean expression
	 * @param expression accepted expression
	 * @return set of maxterms
	 */
	public static Set<Integer> toProductOfMaxterms(List<String> variables, Node expression) {
		Set<Integer> set = new TreeSet<>();
		
		Set<boolean[]> setOfMaxterms = filterAssignments(variables, expression, false);
		
		for(boolean[] maxterm : setOfMaxterms) {
			set.add(booleanArrayToInt(maxterm));
		}
		
		return set;
	}
	
	/**
	 * Method accepts integer number and converts it into array of binary digits which represent
	 * accepted number.
	 * 
	 * @param x accepted integer number
	 * @param n number of binary digits
	 * @return array of binary digits which represent accepted number
	 * @throws IllegalArgumentException if length of the byte array is less than 1
	 */
	public static byte[] indexToByteArray(int x, int n) {
		if(n < 1) {
			throw new IllegalArgumentException("Number of binary digits can not be less than 0.");
		}
		
		byte[] byteArray = new byte[n];
		
		for(int i = 0; i < n; i++) {
			byteArray[n - 1 - i] = (byte) ((x >> i) & 1);
		}
		
		return byteArray;
	}
}
