package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;

/**
 * Class which implements this listener is listener added to {@link IColorProvider}.
 * 
 * @author Toni Martinčić 
 * @version 1.0
 */
public interface ColorChangeListener {

	/**
	 * This method is called from {@link IColorProvider} when the new color is selected.
	 * 
	 * @param source instance of the {@link IColorProvider}
	 * @param oldColor old color
	 * @param newColor new color
	 */
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
	
}
