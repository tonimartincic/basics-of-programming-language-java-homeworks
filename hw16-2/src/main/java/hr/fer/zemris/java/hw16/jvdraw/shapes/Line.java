package hr.fer.zemris.java.hw16.jvdraw.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

/**
 * Class extends {@link GeometricalObject} and represents line.
 * 
 * @author Toni Martinčić 
 * @version 1.0
 */
public class Line extends GeometricalObject {
	
	/**
	 * Start point of the line.
	 */
	private Point start;
	
	/**
	 * End point of the line.
	 */
	private Point end;
	
	/**
	 * Color of the line.
	 */
	private Color lineColor;
	
	/**
	 * Constructor which accepts four arguments; name of the line, start point of the line, end point of
	 * the line and color of the line.
	 * 
	 * @param name name of the line
	 * @param start start point of the line
	 * @param end end point of the line
	 * @param lineColor color of the line
	 */
	public Line(String name, Point start, Point end, Color lineColor) {
		super(name);
		
		this.start = start;
		this.end = end;
		this.lineColor = lineColor;
	}
	
	/**
	 * Getter for the start.
	 * 
	 * @return start
	 */
	public Point getStart() {
		return start;
	}

	/**
	 * Setter for the start.
	 * 
	 * @param start start point of the line
	 */
	public void setStart(Point start) {
		this.start = start;
	}

	/**
	 * Getter for the end.
	 * 
	 * @return end
	 */
	public Point getEnd() {
		return end;
	}

	/**
	 * Setter for the end.
	 * 
	 * @param end end point of the line
	 */
	public void setEnd(Point end) {
		this.end = end;
	}

	/**
	 * Getter for the line of the color.
	 *  
	 * @return line of the color
	 */
	public Color getLineColor() {
		return lineColor;
	}

	/**
	 * Setter for the line of the color.
	 * 
	 * @param lineColor line of the color
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
		g2D.drawLine(start.x + shiftX, start.y + shiftY, end.x + shiftX, end.y + shiftY);
	}

}
