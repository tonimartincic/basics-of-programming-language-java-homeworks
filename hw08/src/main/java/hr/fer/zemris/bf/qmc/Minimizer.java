package hr.fer.zemris.bf.qmc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import hr.fer.zemris.bf.model.BinaryOperatorNode;
import hr.fer.zemris.bf.model.ConstantNode;
import hr.fer.zemris.bf.model.Node;
import hr.fer.zemris.bf.model.UnaryOperatorNode;
import hr.fer.zemris.bf.model.VariableNode;

/**
 * Class represents implementation of the Quine–McCluskey algorithm which is used for minimization of boolean functions.
 * It gets in constructor set of minterms and set of don't cares and list of variables of function then it minimize
 * the function. User can get the minimal forms as expression tress and as string.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class Minimizer {
	
	/**
	 * Logger which logs informations about minimization.
	 */
	private static final Logger LOG = Logger.getLogger("hr.fer.zemris.bf.qmc");
	
	/**
	 * Set of minterms of the function.
	 */
	private Set<Integer> mintermSet;
	
	/**
	 * Set of don't cares of the function.
	 */
	private Set<Integer> dontCareSet;
	
	/**
	 * List of variables of the function.
	 */
	private List<String> variables;
	
	/**
	 * Set of primary implicants of the function.
	 */
	private Set<Mask> primCover;
	
	/**
	 * List of minimal forms of the function.
	 */
	List<Set<Mask>> minimalForms;
	
	/**
	 * Constructor which accepts three arguments, set of the minterms of the function, set of the don't cares of
	 * the function and the list of the variables of the function. Constructor starts the minimization of the 
	 * functions. 
	 * 
	 * @param mintermSet set of the minterms of the function
	 * @param dontCareSet set of the don't cares of the function
	 * @param variables list of the variables of the function
	 */
	public Minimizer(Set<Integer> mintermSet, Set<Integer> dontCareSet, List<String> variables) {
		checkNonOverlapping(mintermSet, dontCareSet);
		checkMintermIndexes(mintermSet, dontCareSet, variables);
		
		this.mintermSet = mintermSet;
		this.dontCareSet = dontCareSet;
		this.variables = variables;
		
		primCover = findPrimaryImplicants();
		minimalForms = chooseMinimalCover(primCover);
	}

	/**
	 * Method checks if there is overlapping in the set of the minterms of the function and in the set of the
	 * don't cares of the function.
	 * 
	 * @param mintermSet set of the minterms of the function
	 * @param dontCareSet set of the don't cares of the function
	 * @throws IllegalArgumentException if there overlapping in the set of the minterms of the function and in the set
	 * of the don't cares of the function
	 */
	private void checkNonOverlapping(Set<Integer> mintermSet, Set<Integer> dontCareSet) {
		for(Integer minterm : mintermSet) {
			if(dontCareSet.contains(minterm)) {
				throw new IllegalArgumentException("Minterm set and don't care set can not have same elements.");
			}
		}
	}
	
	/**
	 * Method checks if the indexes in the set of the minterms of the function and in the set of don't cares of the
	 * function are legal. 
	 * 
	 * @param mintermSet set of the minterms of the function
	 * @param dontCareSet set of don't cares of the function
	 * @param variables list of the variables of the function
	 * @throws IllegalArgumentException if the indexes in the set of the minterms of the function and in the set of 
	 * don't cares of the function are illegal
	 */
	private void checkMintermIndexes(Set<Integer> mintermSet, Set<Integer> dontCareSet, List<String> variables) {
		int highestIndex = ((int) Math.pow(2, variables.size())) - 1;
		for(Integer minterm : mintermSet) {
			if(minterm > highestIndex) {
				throw new IllegalArgumentException("Indexes must be valid for given variables.");
			}
		}
		for(Integer minterm : dontCareSet) {
			if(minterm > highestIndex) {
				throw new IllegalArgumentException("Indexes must be valid for given variables.");
			}
		}
	}
	
	/**
	 * Method returns set of the primary implicants of the function.
	 * 
	 * @return set of the primary implicants
	 */
	private Set<Mask> findPrimaryImplicants() {
		Set<Mask> primaryImplicants = new LinkedHashSet<>();
		
		List<Set<Mask>> column = createFirstColumn();
		while(true) {
			@SuppressWarnings("unchecked")
			Set<Mask>[] nextColumnArray = (Set<Mask>[]) new Set<?>[column.size() - 1];
			boolean wasNextColumn = false;
	
			Set<Mask> newPrimaryImplicants = new LinkedHashSet<>();
			for(int i = 0; i < column.size() - 1; i++) {
				Set<Mask> thisGroup = column.get(i);
				Set<Mask> nextGroup = column.get(i + 1);
				
				if((thisGroup == null && nextGroup != null) || 
				   (thisGroup != null && nextGroup == null)) {
					addPrimaryFromOneOfTheGroups(thisGroup, nextGroup, newPrimaryImplicants);
				}
				
				if(thisGroup == null || nextGroup == null) {
					continue;
				}
				
				for(Mask thisMask : thisGroup) {
					for(Mask nextMask : nextGroup) {
						Optional<Mask> mask = thisMask.combineWith(nextMask);
						if(mask.isPresent()) {
							wasNextColumn = true;
							
							addToNextColumn(nextColumnArray, thisMask, nextMask, mask);
						}
					}
				}
				
				addPrimaryFromGroup(thisGroup, newPrimaryImplicants);
				
				if(i == column.size() - 2) {
					addPrimaryFromGroup(nextGroup, newPrimaryImplicants);
				}
			}
			
			if(LOG.isLoggable(Level.FINER)) {
				logCurrentColumn(column);
			}
			
			if(!newPrimaryImplicants.isEmpty()) {
				primaryImplicants.addAll(newPrimaryImplicants);
				
				for(Mask mask : newPrimaryImplicants) {
					LOG.log(Level.FINEST, "Pronašao primarni implikant: " + mask.toString());
				}
				
				LOG.log(Level.FINEST, "");
			}
			
			if(!wasNextColumn) {
				break;
			}
			
			column = Arrays.asList(nextColumnArray);
		}
		
		addAllFromLastColumn(primaryImplicants, column);
		
		if(LOG.isLoggable(Level.FINE)) {
			logAllPrimaryImplicants(primaryImplicants);
		}
		
		return primaryImplicants;
	}
	
	/**
	 * Method adds primary implicants from one of the accepted groups if there are primary implicants to the
	 * set of the primary implicants.
	 * 
	 * @param thisGroup
	 * @param nextGroup
	 * @param newPrimaryImplicants
	 */
	private void addPrimaryFromOneOfTheGroups(Set<Mask> thisGroup, Set<Mask> nextGroup, Set<Mask> newPrimaryImplicants) {
		if(thisGroup == null && nextGroup != null) {
			addPrimaryFromGroup(nextGroup, newPrimaryImplicants);
		}
		
		if(thisGroup != null && nextGroup == null) {
			addPrimaryFromGroup(thisGroup, newPrimaryImplicants);
		}
		
	}
	
	/**
	 * Method adds primary implicants from accepted group into the set of the primary implicants.
	 * 
	 * @param group accepted group
	 * @param newPrimaryImplicants set of the primary implicants
	 */
	private void addPrimaryFromGroup(Set<Mask> group, Set<Mask> newPrimaryImplicants) {
		for(Mask mask : group) {
			if(!mask.isCombined() && !mask.isDontCare()) {
				newPrimaryImplicants.add(mask);
			}
		}
	}

	/**
	 * Method logs the current column.
	 * 
	 * @param column current column
	 */
	private void logCurrentColumn(List<Set<Mask>> column) {
		LOG.log(Level.FINER, "Stupac tablice:");
		LOG.log(Level.FINER, "=================================");
		
		boolean first = true;
		for(Set<Mask> group : column) {
			if(group == null) {
				continue;
			}
			
			if(first) {
				for(Mask mask : group) {
					LOG.log(Level.FINER, mask.toString());
				}
				
				first = false;
				continue;
			}
			
			LOG.log(Level.FINER, "----------------------------");
			for(Mask mask : group) {
				LOG.log(Level.FINER, mask.toString());
			}
		}
		LOG.log(Level.FINER, "");
	}
	
	/**
	 * Method logs all primary implicants. 
	 * 
	 * @param primaryImplicants all primary implicants
	 */
	private void logAllPrimaryImplicants(Set<Mask> primaryImplicants) {
		LOG.log(Level.FINE, "");
		LOG.log(Level.FINE, "Svi primarni implikanti:");
		
		for(Mask mask : primaryImplicants) {
			LOG.log(Level.FINE, mask.toString());
		}
		
	}

	/**
	 * Method adds mask to the next column.
	 * 
	 * @param nextColumnArray next column as array
	 * @param thisMask first mask
	 * @param nextMask second mask
	 * @param mask result mask
	 */ 
	private void addToNextColumn(Set<Mask>[] nextColumnArray, Mask thisMask, Mask nextMask, Optional<Mask> mask) {
		thisMask.setCombined(true);
		nextMask.setCombined(true);
		
		Mask newMask = mask.get();
		int countOfOnes = newMask.countOfOnes();
		
		if(nextColumnArray[countOfOnes] == null) {
			nextColumnArray[countOfOnes] = new LinkedHashSet<>();
		}	
		
		nextColumnArray[countOfOnes].add(newMask);
	}

	/**
	 * Method adds all primary implicants from last column into the set of the primary implicants.
	 *  
	 * @param primaryImplicants set of the primary implicants
	 * @param column last column
	 */
	private void addAllFromLastColumn(Set<Mask> primaryImplicants, List<Set<Mask>> column) {
		boolean added = false;
		for(Set<Mask> group : column) {
			if(group == null) {
				continue;
			}
			
			for(Mask mask : group) {
				if(mask.isDontCare()) {
					continue;
				}
				
				LOG.log(Level.FINEST, "Pronašao primarni implikant: " + mask.toString());
				
				added = true;
				
				primaryImplicants.add(mask);
			}
		}
		
		if(added) {
			LOG.log(Level.FINEST, "");
		}
	}

	/**
	 * Method creates the first column.
	 * 
	 * @return first column
	 */
	private List<Set<Mask>> createFirstColumn() {
		@SuppressWarnings("unchecked")
		Set<Mask>[] firstColumnArray = (Set<Mask>[]) new Set<?>[variables.size() + 1];
		
		for(Integer minterm : mintermSet) {
			addToFirstColumn(firstColumnArray, minterm, false);	
		}	
		
		for(Integer minterm : dontCareSet) {
			addToFirstColumn(firstColumnArray, minterm, true);
		}
		
		return Arrays.asList(firstColumnArray);
	}

	/**
	 * Method adds primary implicant to the first column. 
	 * 
	 * @param firstColumnArray first column as array
	 * @param minterm minterm
	 * @param isDontCare information is the minterm don't care
	 */
	private void addToFirstColumn(Set<Mask>[] firstColumnArray, Integer minterm, boolean isDontCare) {
		Mask mask = new Mask(minterm, variables.size(), isDontCare);
			
		int countOfOnes = mask.countOfOnes();
		if(firstColumnArray[countOfOnes] == null) {
			firstColumnArray[countOfOnes] = new LinkedHashSet<>();
		} 
		
		firstColumnArray[countOfOnes].add(mask);
	}

	/**
	 * Method chooses the minimal cover of the function.
	 * 
	 * @param primCover primary cover of the function
	 * @return minimal cover of the function
	 */
	private List<Set<Mask>> chooseMinimalCover(Set<Mask> primCover) {
		Mask[] implicants = primCover.toArray(new Mask[primCover.size()]);
		Integer[] minterms = mintermSet.toArray(new Integer[mintermSet.size()]);
		
		Map<Integer,Integer> mintermToColumnMap = new HashMap<>();
		for(int i = 0; i < minterms.length; i++) {
			Integer index = minterms[i];
			mintermToColumnMap.put(index, i);
		}
	
		boolean[][] table = buildCoverTable(implicants, minterms, mintermToColumnMap);
		
		boolean[] coveredMinterms = new boolean[minterms.length];
		
		Set<Mask> importantSet = selectImportantPrimaryImplicants(implicants, mintermToColumnMap, table, coveredMinterms);
		
		if(LOG.isLoggable(Level.FINE)) {
			LOG.log(Level.FINE, "");
			LOG.log(Level.FINE, "Bitni primarni implikanti su:");
			for(Mask mask : importantSet) {
				LOG.log(Level.FINE, mask.toString());
			}
		}
		
		List<Set<Mask>> minimalForms = new ArrayList<>();
		
		boolean allCovered = true;
		for(int i = 0; i < coveredMinterms.length; i++) {
			if(coveredMinterms[i] == false) {
				allCovered = false;
				
				break;
			}
		}
		
		if(allCovered) {
			minimalForms.add(importantSet);
			
			if(LOG.isLoggable(Level.FINE)) {
				LOG.log(Level.FINE, "");
				LOG.log(Level.FINE, "Minimalni oblici funkcije su:");
				LOG.log(Level.FINE, minimalForms.get(0).toString());
			}
			
			return minimalForms;
		}
		
		List<Set<BitSet>> pFunction = buildPFunction(table, coveredMinterms);
		
		Set<BitSet> minset = findMinimalSet(pFunction);
		
		for(BitSet bs : minset) {
			Set<Mask> set = new LinkedHashSet<>(importantSet);
			bs.stream().forEach(i -> set.add(implicants[i]));
			minimalForms.add(set);
		}
		
		if(LOG.isLoggable(Level.FINE)) {
			LOG.log(Level.FINE, "");
			LOG.log(Level.FINE, "Minimalni oblici funkcije su:");
			for(Set<Mask> minimalForm : minimalForms) {
				LOG.log(Level.FINE, minimalForm.toString());
			}
		}
		
		return minimalForms;
	}

	/**
	 * Method finds the minimal set of the function.
	 * 
	 * @param pFunction p function of accepted function
	 * @return minimal set of the function
	 */
	private Set<BitSet> findMinimalSet(List<Set<BitSet>> pFunction) {
		Set<BitSet> sum = pFunction.get(0);
		for(int i = 1; i < pFunction.size(); i++) {	
			Set<BitSet> resultSum = new LinkedHashSet<>();
			
			BitSet[] firstArray = sum.toArray(new BitSet[sum.size()]);
			BitSet[] secondArray = pFunction.get(i).toArray(new BitSet[pFunction.get(i).size()]);
			for(int j = 0, n = sum.size(); j < n; j++) {
				for(int k = 0, m = pFunction.get(i).size(); k < m; k++) {
					BitSet resultBitSet = (BitSet) firstArray[j].clone();
					resultBitSet.or(secondArray[k]);

					resultSum.add(resultBitSet);
				}
			}
			
			sum = resultSum;
		}
		
		if(LOG.isLoggable(Level.FINER)) {
			LOG.log(Level.FINER, "Nakon prevorbe p-funkcije u sumu produkata:");
			LOG.log(Level.FINER, sum.toString());
			LOG.log(Level.FINER, "");
		}
		
		int min = primCover.size();
		for(BitSet product : sum) {
			int num = product.cardinality();
			if(num < min) {
				min = num;
			}
		}
		
		Set<BitSet> minset = new LinkedHashSet<>();
		for(BitSet product : sum) {
			if(product.cardinality() == min) {
				minset.add(product);
			}
		}
	
		if(LOG.isLoggable(Level.FINER)) {
			LOG.log(Level.FINER, " Minimalna pokrivanja još trebaju:");
			LOG.log(Level.FINER, minset.toString());
		}
		
		return minset;
	}

	/**
	 * Method returns p function for the accepted function.
	 * 
	 * @param table table of primary implicants and minterms
	 * @param coveredMinterms covered minterms
	 * @return p function
	 */
	private List<Set<BitSet>> buildPFunction(boolean[][] table, boolean[] coveredMinterms) {
		List<Set<BitSet>> pFunction = new ArrayList<>();
		
		for(int j = 0; j < coveredMinterms.length; j++) {
			if(!coveredMinterms[j]) {
				Set<BitSet> sum = new LinkedHashSet<>();
				
				for(int i = 0; i < table.length; i++) {
					if(table[i][j]) {
						BitSet minterm = new BitSet(primCover.size());
						minterm.set(i);
						
						sum.add(minterm);
					}
				}
				
				pFunction.add(sum);
			}
		}
		
		if(LOG.isLoggable(Level.FINER)) {
			LOG.log(Level.FINER, "");
			LOG.log(Level.FINER, "p funkcija je: ");
			LOG.log(Level.FINER, pFunction.toString());
			LOG.log(Level.FINER, "");
		}
		
		return pFunction;
	}

	/**
	 * Method returns important primary implicants of the function.
	 * 
	 * @param implicants implicants of the function
	 * @param mintermToColumnMap map which maps every minterm to one column of the table
	 * @param table table of primary implicants and minterms
	 * @param coveredMinterms covered minterms
	 * @return important primary implicants of the function
	 */
	private Set<Mask> selectImportantPrimaryImplicants(
			Mask[] implicants, Map<Integer, Integer> mintermToColumnMap, boolean[][] table, boolean[] coveredMinterms) {
		Set<Mask> importantSet = new LinkedHashSet<>();
		
		for(int j = 0; j < mintermToColumnMap.size(); j++) {
			int numOfImplicants = 0;
			Mask importantImplicant = null;
			for(int i = 0; i < implicants.length; i++) {
				if(table[i][j]) {
					numOfImplicants++;
					importantImplicant = implicants[i];
				}
			}
			
			if(numOfImplicants == 1) {
				importantSet.add(importantImplicant);
			}
		}
		

		for(Mask mask : importantSet) {
			for(Integer minterm : mask.getIndexes()) {
				if(!mintermToColumnMap.containsKey(minterm)) {
					continue;
				}
				coveredMinterms[mintermToColumnMap.get(minterm)] = true;
			}
		}
	
		return importantSet;
	}

	/**
	 * Method returns cover table.
	 * 
	 * @param implicants implicants of the function
	 * @param minterms minterms of the function
	 * @param mintermToColumnMap map which maps every minterm to one column of the table
	 * @return cover table
	 */
	private boolean[][] buildCoverTable(Mask[] implicants, Integer[] minterms, Map<Integer, Integer> mintermToColumnMap) {
		boolean[][] table = new boolean[implicants.length][minterms.length];
		
		for(int i = 0; i < implicants.length; i++) {
			for(int j = 0; j < minterms.length; j++) {
				if(implicants[i].getIndexes().contains(minterms[j])) {
					table[i][mintermToColumnMap.get(minterms[j])] = true;
				}
			}
		}
		
		return table;
	}
	
	/**
	 * Getter for the minimal forms.
	 * 
	 * @return minimal forms
	 */
	public List<Set<Mask>> getMinimalForms() {
		return minimalForms;
	}
	
	/**
	 * Getter for the minimal forms as expression.
	 * 
	 * @return minimal forms as expression
	 */
	public List<Node> getMinimalFormsAsExpressions() {
		List<Node> listOfExpressions = new ArrayList<>();
		
		if(dontCareSet.isEmpty() && mintermSet.isEmpty()) {
			listOfExpressions.add(new ConstantNode(false));
			
			return listOfExpressions;
		}
		
		if(dontCareSet.isEmpty() && mintermSet.size() == Math.pow(2, variables.size())) {
			listOfExpressions.add(new ConstantNode(true));
			
			return listOfExpressions;
		}
		
		for(Set<Mask> minimalForm : minimalForms) {
			Node expression = null;
			
			if(minimalForm.size() == 1) {	
				List<Node> children = new ArrayList<>();
				Mask mask = (Mask) minimalForm.toArray()[0];
				for(int i = 0; i < mask.size(); i++) {
					if(mask.getValueAt(i) == 0) {
						VariableNode variableNode = new VariableNode("NOT " + variables.get(i));
						UnaryOperatorNode not = new UnaryOperatorNode("NOT", variableNode, b -> !b);
						
						children.add(not);
					} else if(mask.getValueAt(i) == 1) {
						VariableNode variableNode = new VariableNode(variables.get(i));
						
						children.add(variableNode);
					}
				}
				
				expression = new BinaryOperatorNode("AND", children, (b1, b2) -> b1 && b2);
			} else {
				
				List<Node> andChildren = new ArrayList<>();
				
				for(int j = 0; j < minimalForm.size(); j++) {
					
					List<Node> children = new ArrayList<>();
					Mask mask = (Mask) minimalForm.toArray()[j];
					for(int i = 0; i < mask.size(); i++) {
						
						if(mask.getValueAt(i) == 0) {
							VariableNode variableNode = new VariableNode(variables.get(i));
							UnaryOperatorNode not = new UnaryOperatorNode("NOT", variableNode, b -> !b);
							
							children.add(not);
						} else if(mask.getValueAt(i) == 1) {
							
							VariableNode variableNode = new VariableNode(variables.get(i));
							
							children.add(variableNode);
						}
					}
					
					BinaryOperatorNode andChild = new BinaryOperatorNode("AND", children, (b1, b2) -> b1 && b2);
					andChildren.add(andChild);
				}
				
				expression = new BinaryOperatorNode("OR", andChildren, (b1, b2) -> b1 || b2);
			}
			
			listOfExpressions.add(expression);
		}
		
		return listOfExpressions;
	}
	
	/**
	 * Getter for the minimal forms as string.
	 * 
	 * @return minimal forms as string
	 */
	public List<String> getMinimalFormsAsString() {
		List<String> minimalFormsAsString = new ArrayList<>();
		
		List<Node> listOfExpressions = getMinimalFormsAsExpressions();
		for(Node expression : listOfExpressions) {
			StringBuilder sb = new StringBuilder();
			
			getExpression(sb, expression);
			
			minimalFormsAsString.add(sb.toString());
		}
		
		return minimalFormsAsString;
	}

	/**
	 * Method gets expression as string.
	 * 
	 * @param sb instance of {@link StringBuilder}
	 * @param expression expression
	 */
	private void getExpression(StringBuilder sb, Node expression) {
		if(expression instanceof VariableNode) {
			sb.append(((VariableNode) expression).getName()).append(" ");
		}
		
		if(expression instanceof ConstantNode) {
			sb.append(((ConstantNode) expression).getValue()).append(" ");
		}
		
		if(expression instanceof BinaryOperatorNode) {
			for(int i = 0; i < ((BinaryOperatorNode) expression).getChildren().size(); i++) {
				if(i != 0) {
					sb.append(((BinaryOperatorNode) expression).getName()).append(" ");
				}
				
				getExpression(sb, (Node) ((BinaryOperatorNode) expression).getChildren().toArray()[i]);
			}
		}
		
		if(expression instanceof UnaryOperatorNode) {
			sb.append(((UnaryOperatorNode) expression).getName()).append(" ");
			
			getExpression(sb, ((UnaryOperatorNode) expression).getChild());
		}
	}

}
