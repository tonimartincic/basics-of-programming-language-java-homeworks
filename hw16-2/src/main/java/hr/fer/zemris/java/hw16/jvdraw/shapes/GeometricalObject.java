package hr.fer.zemris.java.hw16.jvdraw.shapes;

import java.awt.Graphics;

/**
 * Class represents abstract geometrical object.
 * 
 * @author Toni Martinčić 
 * @version 1.0
 */
public abstract class GeometricalObject {
	
	/**
	 * Object name.
	 */
	private String name;
	
	/**
	 * Constructor which accepts only one argument, name of the object.
	 * 
	 * @param name name of the object
	 * @throws IllegalArgumentException if argument name is null or empty
	 */
	public GeometricalObject(String name) {
		if(name == null) {
			throw new IllegalArgumentException("Name can not be null.");
		}
		
		if(name.isEmpty()) {
			throw new IllegalArgumentException("Name can not be empty string.");
		}
		
		this.name = name;
	}
	
	/**
	 * Getter for the name.
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for the name
	 * 
	 * @param name name of the object
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Method paints the object.
	 * 
	 * @param graphics instance of {@link Graphics}
	 */
	public abstract void paint(Graphics graphics);
	
	/**
	 * Method paints the object shifted by accepted values.
	 * 
	 * @param graphics instance of {@link Graphics}
	 * @param shiftX x shift
	 * @param shiftY y shift
	 */
	public abstract void paintShifted(Graphics graphics, int shiftX, int shiftY);
	
	@Override
	public String toString() {
		return name;
	}
	
}
