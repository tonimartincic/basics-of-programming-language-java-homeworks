package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Base class which represents apstract element.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */

public class Element {
	
	/**
	 * In this class this method returns empty String. Classes which inherit this class
	 * need to override this method.
	 * 
	 * @return empty String
	 */
	
	public String asText() {
		return new String("");
	}
	

	@Override
	public boolean equals(Object e) {
		if (e instanceof Element) {
			if (this.asText().equals(((Element) e).asText())) {
				return true;
			}
		}
		return false;
	}
}
