package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;

/**
 * Class which implements this interface represents color provider and contains instances of
 * {@link ColorChangeListener}.
 * 
 * @author Toni Martinčić 
 * @version 1.0
 */
public interface IColorProvider {

	/**
	 * Method returns current color.
	 * 
	 * @return current color
	 */
	public Color getCurrentColor();
	
	/**
	 * Method adds instance of {@link ColorChangeListener}.
	 * 
	 * @param l {@link ColorChangeListener}
	 */
	public void addColorChangeListener(ColorChangeListener l);
	
	/**
	 * Method removes instance of {@link ColorChangeListener}.
	 * 
	 * @param l {@link ColorChangeListener}
	 */
	public void removeColorChangeListener(ColorChangeListener l);
	
	/**
	 * Method returns name of the {@link IColorProvider}.
	 * 
	 * @return name of the {@link IColorProvider}
	 */
	public String getName();

}
