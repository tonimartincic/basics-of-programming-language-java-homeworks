package hr.fer.zemris.java.hw16.jvdraw.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

/**
 * Class extends {@link GeometricalObject} and represents circle.
 * 
 * @author Toni Martinčić 
 * @version 1.0
 */
public class Circle extends GeometricalObject {
	
	/**
	 * Center point of the circle.
	 */
	private Point center;
	
	/**
	 * Radius of the circle.
	 */
	private int radius;
	
	/**
	 * Color of the circle.
	 */
	private Color lineColor;
	
	/**
	 * Constructor which accepts four arguments; name of the circle, center point of the circle, radius of
	 * the circle and the color of the line of the circle.
	 * 
	 * @param name name of the circle
	 * @param center center point of the circle
	 * @param radius radius of the circle
	 * @param lineColor color of the line of the circle
	 * @throws IllegalArgumentException if the argument name is null or empty, if the argument center is null or if the argument
	 * radius is less than 0
	 */
	public Circle(String name, Point center, int radius, Color lineColor) {
		super(name);
		
		if(center == null) {
			throw new IllegalArgumentException("Argument center can not be null.");
		}
		
		if(radius <= 0) {
			throw new IllegalArgumentException("Radius must be greater than 0.");
		}
		
		this.center = center;
		this.radius = radius;
		this.lineColor = lineColor;
	}
	
	/**
	 * Getter for the center.
	 * 
	 * @return center
	 */
	public Point getCenter() {
		return center;
	}

	/**
	 * Setter for the center.
	 * 
	 * @param center center point of the circle
	 */
	public void setCenter(Point center) {
		this.center = center;
	}

	/**
	 * Getter for the radius.
	 * 
	 * @return radius of the circle
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * Setter for the radius.
	 * 
	 * @param radius radius of the circle
	 */
	public void setRadius(int radius) {
		this.radius = radius;
	}

	/**
	 * Getter for the line color.
	 * 
	 * @return line color
	 */
	public Color getLineColor() {
		return lineColor;
	}

	/**
	 * Setter for the line color.
	 *  
	 * @param lineColor line color
	 */
	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}

	@Override
	public void paint(Graphics graphics) {
		paintShifted(graphics, 0, 0);
	}

	@Override
	public void paintShifted(Graphics graphics, int shiftX, int shiftY) {
		Graphics2D g2D = (Graphics2D) graphics;
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2D.setColor(lineColor);
		g2D.drawOval(getCenter().x - getRadius() + shiftX, getCenter().y - getRadius() + shiftY, 2 * getRadius(), 2 * getRadius());
	}

}
