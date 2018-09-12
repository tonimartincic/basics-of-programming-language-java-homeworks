package hr.fer.zemris.bf.qmc;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import hr.fer.zemris.bf.utils.Util;

/**
 * Class represents a specification of product of minterms. Class contains mask which is byte array and represents 
 * product of minterms, set of indexes which represents indexes of minterms of product of minterms, and boolean
 * value which contains information is the product don't care.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class Mask {
	
	/**
	 * Byte for variable in values is set to this value if the variable is not in the product.
	 */
	private static final byte NOT_IN_THE_PRODUCT = 2;

	/**
	 * Mask which represents product of minterms.
	 */
	private byte[] values;
	
	/**
	 * Set of indexes of minterms.
	 */
	private Set<Integer> indexes;
	
	/**
	 * Contains information is the product don't care.
	 */
	private boolean dontCare;
	
	/**
	 * Contains information is the product combined with other products.
	 */
	private boolean combined;
	
	/**
	 * Hashcode of the mask.
	 */
	private int hashcode;
	
	/**
	 * Constructor which accepts three arguments, index which is order number of minterm, number of variables and
	 * boolean value which contains information is the minterm don't care.
	 * 
	 * @param index order number of minterm 
	 * @param numberOfVariables number of variables of function
	 * @param dontCare contains information is minterm don't care
	 * @throws IllegalArgumentException if index is less than 0 or number of variables is less than 1
	 */
	public Mask(int index, int numberOfVariables, boolean dontCare) {
		if(index < 0) {
			throw new IllegalArgumentException("Index can not be less than 0.");
		}
		
		if(numberOfVariables < 1) {
			throw new IllegalArgumentException("Number of variables can not be less than 1.");
		}
		
		this.values = Util.indexToByteArray(index, numberOfVariables);
		
		Set<Integer> indexes = new TreeSet<>();
		indexes.add(index);
		this.indexes = Collections.unmodifiableSet(new TreeSet<>(indexes));
		
		this.dontCare = dontCare;
		this.combined = false;
		
		this.hashcode = Arrays.hashCode(this.values);
	}
	
	/**
	 * Constructor which accepts three arguments, mask which represents product of minterms, set of indexes which
	 * represents indexes of minterms in product of minterms and boolean value which contain information is the
	 * product of minterms don't care.
	 * 
	 * @param values mask which represents product of minterms
	 * @param indexes order number of minterms
	 * @param dontCare contains information is minterm don't care
	 * @throws IllegalArgumentException if the values is null, indexes is null or the indexes is empty
	 */
	public Mask(byte[] values, Set<Integer> indexes, boolean dontCare) {
		if(values == null) {
			throw new IllegalArgumentException("Values can not be null.");
		}
		
		if(indexes == null) {
			throw new IllegalArgumentException("Indexes can not be null");
		}
		
		if(indexes.isEmpty()) {
			throw new IllegalArgumentException("Number of indexes must be greater than 0.");
		}
		
		this.values = Arrays.copyOf(values, values.length);
		this.indexes = Collections.unmodifiableSet(new TreeSet<>(indexes));
		this.dontCare = dontCare;
		this.combined = false;
		
		this.hashcode = Arrays.hashCode(this.values);
	}
	
	@Override
	public int hashCode() {
		return this.hashcode;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		
		if(obj == null) {
			return false;
		}
		
		if (getClass() != obj.getClass()) {
			return false;
		}
		
		Mask other = (Mask) obj;
		
		if(this.hashCode() != other.hashCode()) {
			return false;
		}
		
		if (!Arrays.equals(this.values, other.values))
			return false;
		
		return true;
	}
	
	/**
	 * Method gets the information is the product combined with some other product to create some new product.
	 * 
	 * @return true if the product is combined with some other product, false otherwise
	 */
	public boolean isCombined() {
		return combined;
	}

	/**
	 * Method sets the information is the product combined with some other product to create some new product.
	 * 
	 * @param combined true if the product is combined with some other product, false otherwise
	 */
	public void setCombined(boolean combined) {
		this.combined = combined;
	}

	/**
	 * Method returns information is the product don't care.
	 * 
	 * @return true if the product is don't care, false otherwise
	 */
	public boolean isDontCare() {
		return dontCare;
	}
	
	/**
	 * Method gets the indexes of the minterms of the product of the minterms.
	 * 
	 * @return indexes of the minterms of the product of the minterms
	 */
	public Set<Integer> getIndexes() {
		return indexes;
	}

	/**
	 * Method returns number of ones in the mask.
	 * 
	 * @return number of ones in the mask
	 */
	public int countOfOnes() {
		int numOfOnes = 0;
		
		for(int i = 0; i < values.length; i++) {
			if(values[i] == 1) {
				numOfOnes++;
			}
		}
		
		return numOfOnes;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < values.length; i++) {
			if(values[i] == 2) {
				sb.append("-");
				
				continue;
			} 
			
			sb.append(values[i]) ;
		}
		
		sb.append(dontCare ? " D " : " . ");
		sb.append(combined ? " * " : "   ");
		
		sb.append("[");
		boolean first = true;
		for(Integer index : indexes) {
			if(first) {
				sb.append(index);
				first = false;
				
				continue;
			}
			
			sb.append(", " + index);
		}
		sb.append("]");
		
		return sb.toString();
	}
	
	/**
	 * Method returns number of variables.
	 * 
	 * @return number of variables
	 */
	public int size() {
		return values.length;
	}
	
	/**
	 * Method returns variable at the accepted position.
	 * 
	 * @param position position of the variable
	 * @return variable at the accepted position
	 */
	public byte getValueAt(int position) {
		if(position < 0 || position > values.length - 1) {
			throw new IllegalArgumentException("Valid positions are from 0 to number of variables - 1.");
		}
		
		return values[position];
	}
	
	/**
	 * Method returns new mask which is combination of this and accepted mask.
	 * 
	 * @param other accepted mask
	 * @return new mask
	 * @throws IllegalArgumentException if the given mask is null or the length of the given mask is not equal
	 * to length of this mask
	 */
	public Optional<Mask> combineWith(Mask other) {
		if(other == null) {
			throw new IllegalArgumentException("Given mask can not be null value");
		}
		
		if(this.values.length != other.size()) {
			throw new IllegalArgumentException("Length of the masks must be equal.");
		}
		
		boolean dontCaresAtSamePlaces = checkDontCares(other);
		if(!dontCaresAtSamePlaces) {
			return Optional.empty();
		}
		
		int numberOfDifferences = getNumberOfDifferences(other);
		if(numberOfDifferences != 1) {
			return Optional.empty();
		}
		
		Mask newMask = getNewMask(other);
		
		return Optional.of(newMask);
	}

	/**
	 * Method checks if the don't cares are on the same places in this and in the accepted mask.
	 * 
	 * @param other accepted mask
	 * @return true if the don't cares are on the same places in this and in the accepted mask, false otherwise
	 */
	private boolean checkDontCares(Mask other) {
		boolean dontCaresAtSamePlaces = true;
		
		for(int i = 0; i < this.values.length; i++) {
			if((this.values[i] == NOT_IN_THE_PRODUCT && other.getValueAt(i) != NOT_IN_THE_PRODUCT) ||
			   (this.values[i] != NOT_IN_THE_PRODUCT && other.getValueAt(i) == NOT_IN_THE_PRODUCT)) {
				dontCaresAtSamePlaces = false;
				
				break;
			}
		}
		
		return dontCaresAtSamePlaces;
	}
	
	/**
	 * Method gets number of differences in this and in the accepted mask.
	 * 
	 * @param other accepted mask
	 * @return number of differences in this and in the accepted mask
	 */
	private int getNumberOfDifferences(Mask other) {
		int numberOfDifferences = 0;
		
		for(int i = 0; i < this.values.length; i++) {
			if(this.values[i] != other.getValueAt(i)) {
				numberOfDifferences++;
			}
		}
		
		return numberOfDifferences;
	}
	
	/**
	 * Method gets new mask which is combination of this mask and accepted mask.
	 * 
	 * @param other accepted mask
	 * @return new mask
	 */
	private Mask getNewMask(Mask other) {
		byte[] newValues = getNewValues(other);
		Set<Integer> newIndexes = getNewIndexes(other);
		boolean newDontCare = this.dontCare && other.isDontCare();
		
		return new Mask(newValues, newIndexes, newDontCare);
	}

	/**
	 * Method gets values of the new mask.
	 * 
	 * @param other other mask
	 * @return values of the new mask
	 */
	private byte[] getNewValues(Mask other) {
		byte[] newValues = new byte[this.values.length];
		
		for(int i = 0; i < this.values.length; i++) {
			if(this.values[i] != other.getValueAt(i)) {
				newValues[i] = NOT_IN_THE_PRODUCT;
				
				continue;
			}
			
			newValues[i] = this.values[i];
		}
		
		return newValues;
	}

	/**
	 * Method gets indexes of the new mask.
	 * 
	 * @param other other mask
	 * @return indexes of the new mask
	 */
	private Set<Integer> getNewIndexes(Mask other) {
		Set<Integer> newIndexes = new TreeSet<>();
		
		newIndexes.addAll(this.indexes);
		newIndexes.addAll(other.getIndexes());
		
		return newIndexes;
	}

}
