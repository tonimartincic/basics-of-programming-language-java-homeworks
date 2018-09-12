package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Class represents list model which contains list of the prime numbers. It contains method next which adds next
 * prime numbers in the list of the prime numbers. When the list model is created there is only one prime 
 * number in the list which is number 1.
 * 
 * @author Toni Martinčić
 * @version 1.0
 * @param <E> parameter of data of list model
 */
public class PrimListModel<E> implements ListModel<E> {

	/**
	 * List of prime numbers.
	 */
	private List<Integer> primNumbers = new ArrayList<>();
	
	/**
	 * List of {@link ListDataListener}.
	 */
	private List<ListDataListener> listDataListeners = new ArrayList<>();
	
	/**
	 * Current prime number.
	 */
	private int currentPrimNumber;
	
	/**
	 * Constructor which accepts no arguments. It sets current prime number to 1 and adds it to the list of the
	 * prime numbers.
	 */
	public PrimListModel() {
		super();
		
		currentPrimNumber = 1;
		primNumbers.add(currentPrimNumber);
	}
	
	@Override
	public int getSize() {
		return primNumbers.size();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public E getElementAt(int index) {
		if(index < 0 || index >= primNumbers.size()) {
			throw new IllegalArgumentException(
					"Index must be greater than 0 and less than number of prim numbers in list."); 
		}
		
		return (E) primNumbers.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		if(l == null) {
			throw new IllegalArgumentException("Null value can not be given as argument.");
		}
		
		if(listDataListeners.contains(l)) {
			throw new IllegalArgumentException("Model already contains given list data listener.");
		}
		
		listDataListeners.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listDataListeners.remove(l);
	}

	/**
	 * Method adds next prime number in the list of the prime numbers and calls method interval added on every
	 * {@link ListDataListener} in the list.
	 */
	public void next() {
		currentPrimNumber++;
		
		while(!isPrime(currentPrimNumber)) {
			currentPrimNumber++;
		}
		
		primNumbers.add(currentPrimNumber);
		
		for(ListDataListener l : listDataListeners) {
			l.intervalAdded(
					new ListDataEvent(
							this,
							ListDataEvent.INTERVAL_ADDED,
							primNumbers.size() - 1,
							primNumbers.size() - 1));
		}
	}

	/**
	 * Method checks is the accepted number prime.
	 * 
	 * @return true if the accepted number is prime, false otherwise
	 */
	private boolean isPrime(int number) {
		boolean isPrime = true;
		
		for(int i = 2; i <= Math.sqrt(number); i++) {
			if(number % i == 0){
				isPrime = false;
				
				break;
			}
		}
	
		return isPrime;
	}
}
