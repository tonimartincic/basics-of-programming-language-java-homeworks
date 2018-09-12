package hr.fer.zemris.java.hw16.jvdraw.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

/**
 * Class extends {@link Color} and represents filled circle.
 * 
 * @author Toni Martinčić 
 * @version 1.0
 */
public class FilledCircle extends Circle {
	
	/**
	 * Area color of the circle.
	 */
	private Color areaColor;
	
	/**
	 * Constructor which accepts five arguments; name of the circle, center point of the circle, radius
	 * of the circle, line color of the circle and the area color of the circle.
	 * 
	 * @param name name of the circle
	 * @param center center point of the circle
	 * @param radius radius of the circle
	 * @param lineColor line color of the circle
	 * @param areaColor area color of the circle
	 */
	public FilledCircle(String name, Point center, int radius, Color lineColor, Color areaColor) {
		super(name, center, radius, lineColor);
		
		this.areaColor = areaColor;
	}
	
	/**
	 * Getter for the area color.
	 * 
	 * @return area color.
	 */
	public Color getAreaColor() {
		return areaColor;
	}
	
	/**
	 * Setter for the area color.
	 * 
	 * @param areaColor area color
	 */
	public void setAreaColor(Color areaColor) {
		this.areaColor = areaColor;
	}

	@Override
	public void paint(Graphics graphics) {
		paintShifted(graphics, 0, 0);
	}
	
	@Override
	public void paintShifted(Graphics graphics, int shiftX, int shiftY) {
		Graphics2D g2D = (Graphics2D) graphics;
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2D.setColor(getLineColor());
		g2D.drawOval(getCenter().x - getRadius() + shiftX, getCenter().y - getRadius() + shiftY, 2 * getRadius(), 2 * getRadius());
		
		g2D.setColor(areaColor);
		g2D.fillOval(getCenter().x - getRadius() + 1 + shiftX, getCenter().y - getRadius() + 1 + shiftY, 2 * getRadius() - 1, 2 * getRadius() - 1);
	}
}
