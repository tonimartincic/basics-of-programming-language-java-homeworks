package demo;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import hr.fer.zemris.bf.model.Node;
import hr.fer.zemris.bf.parser.Parser;
import hr.fer.zemris.bf.qmc.Minimizer;
import hr.fer.zemris.bf.utils.Util;

/**
 * Program is console application which gets from user boolean functions and writes minimal forms of the
 * accepted functions to the standard output.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class QMC {
	
	/**
	 * Main method from which program starts.
	 * 
	 * @param args command line arguments, unused
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			System.out.print("> ");
			
			String line = sc.nextLine();
			if(line.equals("quit")) {
				break;
			}
			
			String[] parts = null;
			try {
				parts = getParts(line);
			} catch(IllegalArgumentException exc) {
				System.out.println(exc.getMessage());
				
				continue;
			}
			
			String variables = parts[0];
			String minterms = parts[1];
			String dontCares = null;
			if(parts.length == 3) {
				dontCares = parts[2];
			}
			
			String[] var = variables.split(",");
			List<String> listOfVariables = Arrays.asList(var);
			
			Set<Integer> mintermSet = null;
			Set<Integer> dontCareSet = null;
			
			try {
				mintermSet = getMintermSet(listOfVariables, minterms);
				if(parts.length == 3) {
					dontCareSet = getDontCareSet(listOfVariables, dontCares);
				}
			} catch(IllegalArgumentException exc) {
				System.out.println(exc.getMessage());
				
				continue;
			}
			
			if(dontCareSet == null) {
				dontCareSet = new HashSet<>();
			} else {
				boolean hasOverlap = false;
				for(Integer minterm : dontCareSet) {
					if(mintermSet.contains(minterm)) {
						hasOverlap = true;
						
						break;
					}
				}
				
				if(hasOverlap) {
					System.out.println("Pogreška: skup minterma i don't careova nije disjunktan.");
					
					continue;
				}
			}
			
			Minimizer minimizer = new Minimizer(mintermSet, dontCareSet, listOfVariables);
			List<String> minimalForms = minimizer.getMinimalFormsAsString();
			
			for(int i = 1;  i <= minimalForms.size(); i++) {
				System.out.printf("%d. %s%n", i, minimalForms.get(i - 1));
			}
		}
		
		sc.close();
	}

	/**
	 * Method returns set of the minterms of the function.
	 * 
	 * @param listOfVariables list of the variables of the function
	 * @param minterms minterms of the function as string
	 * @return set of the minterms of the function
	 */ 
	private static Set<Integer> getMintermSet(List<String> listOfVariables, String minterms) {
		Set<Integer> mintermSet = new TreeSet<>();
		
		if(minterms.startsWith("[")) {
			mintermSet = getSet(minterms);
		} else {
			Parser parser = new Parser(minterms);
			Node expression = parser.getExpression();
			
			mintermSet = Util.toSumOfMinterms(listOfVariables, expression);
		}
		
		return mintermSet;
	}

	/**
	 * Method returns set of the don't cares of the function.
	 * 
	 * @param listOfVariables list of the variables of the function
	 * @param dontCares don't cares of the function
	 * @return set of the don't cares of the function
	 */ 
	private static Set<Integer> getDontCareSet(List<String> listOfVariables, String dontCares) {
		Set<Integer> dontCareSet = new TreeSet<>();
		
		if(dontCares.startsWith("[")) {
			dontCareSet = getSet(dontCares);
		} else {
			Parser parser = new Parser(dontCares);
			Node expression = parser.getExpression();
			
			dontCareSet = Util.toSumOfMinterms(listOfVariables, expression);
		}
		
		return dontCareSet;
	}
	
	/**
	 * Method gets set of the minterms or don't cares from string.
	 * 
	 * @param string string of the minterms of don't cares
	 * @return set of the minterms or don't cares
	 */
	private static Set<Integer> getSet(String string) {
		char[] data = string.toCharArray();
		
		StringBuilder sb = new StringBuilder();
		
		int i = 1;
		while(data[i] != ']') {
			if(Character.isDigit(data[i]) || data[i] == ',') {
				sb.append(data[i]);
			} else if(data[i] != ' ') {
				throw new IllegalArgumentException("Pogreška: funkcija nije ispravno zadana. ");
			}
			
			i++;
			if(i >= data.length) {
				throw new IllegalArgumentException("Pogreška: funkcija nije ispravno zadana. Nema zatvorene zagrade.");
			}
		}
		
		Set<Integer> set = new TreeSet<>();
		String setAsString = sb.toString();
		String[] parts = setAsString.split(",");
		for(int j = 0; j < parts.length; j++) {
			set.add(Integer.parseInt(parts[j]));
		}
		
		return set;
	}

	/**
	 * Method gets parts from the input string.
	 * 
	 * @param line input string
	 * @return parts of the input string
	 */
	private static String[] getParts(String line) {
		int positionOfEquals = line.indexOf('=');
		if(positionOfEquals == -1) {
			throw new IllegalArgumentException("Pogreška: funkcija nije ispravno zadana. Nema znaka jednakosti.");
		}
		
		String function = line.substring(0, positionOfEquals).trim();
		String variables = getVariables(function);
	
		int positionOfOptionalSign = line.indexOf('|');
		String minterms = null;
		String dontCares = null;
		if(positionOfOptionalSign == -1) {
			minterms = line.substring(positionOfEquals + 1, line.length()).trim();
		} else {
			minterms = line.substring(positionOfEquals + 1, positionOfOptionalSign).trim();
			dontCares = line.substring(positionOfOptionalSign + 1).trim();
		}

		String[] parts = null;
		if(dontCares != null) {
			parts = new String[3];
		} else {
			parts = new String[2];
		}
		
		parts[0] = variables;
		parts[1] = minterms;
		if(dontCares != null) {
			parts[2] = dontCares;
		}
		
		return parts;
	}

	/**
	 * Method gets variables from the function.
	 * 
	 * @param function accepted function
	 * @return variables from the function
	 */
	private static String getVariables(String function) {
		function.trim();
		char[] data = function.toCharArray();
		
		if(!Character.isLetter(data[0])) {
			throw new IllegalArgumentException("Pogreška: funkcija nije ispravno zadana. Funkcija ne pocinje slovom.");
		}
		
		int i = 0;
		while(data[i] != '(') {
			i++;
			if(i >= data.length || (!Character.isLetterOrDigit(data[i]) && data[i] != '(')) {
				throw new IllegalArgumentException("Pogreška: funkcija nije ispravno zadana. Nema otvorene zagrade.");
			}
		}
		i++;
		
		StringBuilder sb = new StringBuilder();
		while(data[i] != ')') {
			if(Character.isLetterOrDigit(data[i]) || data[i] == ',') {
				sb.append(data[i]);
			} else if(data[i] != ' ') {
				throw new IllegalArgumentException("Pogreška: funkcija nije ispravno zadana. ");
			}
			
			i++;
			if(i >= data.length) {
				throw new IllegalArgumentException("Pogreška: funkcija nije ispravno zadana. Nema zatvorene zagrade.");
			}
		}
		
		return sb.toString();
	}
}
