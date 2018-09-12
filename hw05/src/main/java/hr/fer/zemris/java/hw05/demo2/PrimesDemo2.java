package hr.fer.zemris.java.hw05.demo2;

/**
 * Class test functionality of {@link PrimesCollection}.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class PrimesDemo2 {

	/**
	 * Method from which program starts.
	 * 
	 * @param args arguments of main method, unused
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(2);
		
		for (Integer prime : primesCollection) {
			for (Integer prime2 : primesCollection) {
				System.out.println("Got prime pair: " + prime + ", " + prime2);
			}
		}

	}

}
