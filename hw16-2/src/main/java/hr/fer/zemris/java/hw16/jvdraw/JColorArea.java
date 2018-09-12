package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

/**
 * Class implements {@link IColorProvider} and represents color provider.
 * 
 * @author Toni Martinčić 
 * @version 1.0
 */
public class JColorArea extends JComponent implements IColorProvider {
	
	/**
	 * Universal version identifier for a {@link Serializable} class.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Name of the {@link JColorArea}.
	 */
	String name;
	
	/**
	 * Default width.
	 */
	private static final int DEFAULT_WIDTH = 15;
	
	/**
	 * Default height.
	 */
	private static final int DEFAULT_HEIGHT = 15;
	
	/**
	 * Selected color.
	 */
	private Color selectedColor;
	
	/**
	 * List which stores instances of {@link ColorChangeListener}.
	 */
	List<ColorChangeListener> colorChangeListeners = new ArrayList<>();
	
	/**
	 * Constructor which accepts two aeguments; name of the {@link JColorArea} and the selected color.
	 * 
	 * @param name name of the {@link JColorArea}
	 * @param selectedColor selected color
	 */
	public JColorArea(String name, Color selectedColor) {
		this.name = name;
		this.selectedColor = selectedColor;
		
		addMouseListener();	
	}
	
	@Override
	public String getName() {
		return name;
	}

	/**
	 * Method adds {@link MouseListener}.
	 */
	private void addMouseListener() {
		this.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				Color newColor = JColorChooser.showDialog(JColorArea.this, "Color chooser", selectedColor);
				if(newColor != null && newColor != selectedColor) {
					for(ColorChangeListener colorChangeListener : colorChangeListeners) {
						colorChangeListener.newColorSelected(JColorArea.this, selectedColor, newColor);
					}
					
					selectedColor = newColor;
					repaint();
				}
			};
			
		});
	}
	
	/**
	 * Setter for the selected color.
	 * 
	 * @param selectedColor selected color
	 */
	public void setSelectedColor(Color selectedColor) {
		this.selectedColor = selectedColor;
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	@Override
	public Dimension getMinimumSize() {
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	@Override
	public Dimension getMaximumSize() {
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}

	@Override
	public void addColorChangeListener(ColorChangeListener l) {
		if(colorChangeListeners.contains(l)) {
			throw new IllegalArgumentException("Listener already added.");
		}
		
		colorChangeListeners.add(l);
	}

	@Override
	public void removeColorChangeListener(ColorChangeListener l) {
		colorChangeListeners.remove(l);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Insets ins = getInsets();
		
		g.setColor(selectedColor);
		g.fillRect(ins.left, ins.top, this.getWidth() - ins.left - ins.right, this.getHeight() - ins.top - ins.bottom);
	}

}

