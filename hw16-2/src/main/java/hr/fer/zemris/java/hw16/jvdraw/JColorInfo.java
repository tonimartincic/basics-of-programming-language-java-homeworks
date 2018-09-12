package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;
import java.io.Serializable;

import javax.swing.JLabel;

/**
 * Class extends {@link JLabel} and contains informations about the foreground and the background color 
 * of the {@link JDrawingCanvas}.
 * 
 * @author Toni Martinčić 
 * @version 1.0
 */
public class JColorInfo extends JLabel implements ColorChangeListener {
	
	/**
	 * Universal version identifier for a {@link Serializable} class.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instance of {@link IColorProvider} which provides foreground color.
	 */
	private IColorProvider fgColorProvider;
	
	/**
	 * Instance of {@link IColorProvider} which provides backgorund color.
	 */
	private IColorProvider bgColorProvider;
	
	/**
	 * Constructor which accepts two arguments; instance of {@link IColorProvider} which provides backgorund 
	 * color and instance of {@link IColorProvider} which provides backgorund color.
	 * 
	 * @param fgColorProvider instance of {@link IColorProvider} which provides foreground color
	 * @param bgColorProvider instance of {@link IColorProvider} which provides backgorund color
	 */
	public JColorInfo(IColorProvider fgColorProvider, IColorProvider bgColorProvider) {
		if(fgColorProvider == null) {
			throw new IllegalArgumentException("Argument fgColorProvider can not be null.");
		}
		
		if(bgColorProvider == null) {
			throw new IllegalArgumentException("Argument bgColorProvider can not be null.");
		}
		
		this.fgColorProvider = fgColorProvider;
		this.bgColorProvider = bgColorProvider;
		
		this.setText(getTextInfo());
	}

	/**
	 * Getter for the fgColorProvider.
	 * 
	 * @return fgColorProvider
	 */
	public IColorProvider getFgColorProvider() {
		return fgColorProvider;
	}

	/**
	 * Setter for the fgColorProvider.
	 * 
	 * @param fgColorProvider fgColorProvider
	 */
	public void setFgColorProvider(IColorProvider fgColorProvider) {
		this.fgColorProvider = fgColorProvider;
	}

	/**
	 * Getter for the bgColorProvider.
	 * 
	 * @return bgColorProvider
	 */
	public IColorProvider getBgColorProvider() {
		return bgColorProvider;
	}

	/**
	 * Setter for the bgColorProvider.
	 * 
	 * @param bgColorProvider bgColorProvider
	 */
	public void setBgColorProvider(IColorProvider bgColorProvider) {
		this.bgColorProvider = bgColorProvider;
	}

	@Override
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
		this.setText(getTextInfo());
	}
	
	/**
	 * Method returns text info.
	 * 
	 * @return text info
	 */
	private String getTextInfo() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Foreground color: (");
		sb.append(fgColorProvider.getCurrentColor().getRed()).append(", ");
		sb.append(fgColorProvider.getCurrentColor().getGreen()).append(", ");
		sb.append(fgColorProvider.getCurrentColor().getBlue()).append("), ");
		sb.append("background color: (");
		sb.append(bgColorProvider.getCurrentColor().getRed()).append(", ");
		sb.append(bgColorProvider.getCurrentColor().getGreen()).append(", ");
		sb.append(bgColorProvider.getCurrentColor().getBlue()).append(").");
		
		return sb.toString();
	}
	
}
