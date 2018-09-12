package hr.fer.zemris.java.hw05.demo2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class represents collection which contains prime numbers.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class PrimesCollection implements Iterable<Integer> {

	/**
	 * Number of prime numbers that collection contains.
	 */
	private int numberOfPrimes;
	
	/**
	 * Constructor which accepts one argument, number of prime numbers that will collection contain.
	 * 
	 * @param numberOfPrimes number of prime numbers in collection
	 * @throws IllegalArgumentException if argument is less than 0, {@link IllegalArgumentException}
	 * is thrown
	 */
	public PrimesCollection(int numberOfPrimes) {
		if(numberOfPrimes < 0) {
			throw new IllegalArgumentException("Number of primes can't be less than 0.");
		}
		
		this.numberOfPrimes = numberOfPrimes;
	}
	
	@Override
	public Iterator<Integer> iterator() {
		return new MyIterator();
	}
	
	/**
	 * Class implements {@link Iterator} and iterates thru numbers of collection.
	 * 
	 * @author Toni Martinčić
	 * @version 1.0
	 */
	public class MyIterator implements Iterator<Integer> {
		
		/**
		 * Number of prime numbers already calculated.
		 */
		private int numberOfPrimesCalculated;
		
		/**
		 * Next prime number.
		 */
		private int nextPrime;
		
		/**
		 * Constructor which accepts no arguments.
		 */
		public MyIterator() {
			numberOfPrimesCalculated = 0;
			nextPrime = 2;
		}

		@Override
		public boolean hasNext() {
			return numberOfPrimesCalculated < numberOfPrimes;
		}

		@Override
		public Integer next() {
			if(!hasNext()) {
				throw new NoSuchElementException("No more elements.");
			}
			
			numberOfPrimesCalculated++;
			
			if(numberOfPrimesCalculated == 1) {
				return nextPrime;
			}
			
			calculateNextPrime();
			return nextPrime;
		}

		/**
		 * Method calculates next prime number.
		 */
		private void calculateNextPrime() {
			nextPrime++;
			
			while(!isPrime(nextPrime)) {
				nextPrime++;
			}
		}

		/**
		 * Method checks if accepted number is prime.
		 * 
		 * @param nextPrime number which is checked
		 * @return true if accepted number is prime, false otherwise
		 */
		private boolean isPrime(int nextPrime) {
			boolean isPrime = true;
			
			for(int i = 2; i <= Math.sqrt(nextPrime); i++) {
				if(nextPrime % i == 0){
					isPrime = false;
					
					break;
				}
			}
			
			return isPrime;
		}
		
	}

}
