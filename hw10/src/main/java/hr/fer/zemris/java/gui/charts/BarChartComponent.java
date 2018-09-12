package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.AffineTransform;
import javax.swing.JComponent;


/**
 * Class extends {@link JComponent} and represents component which is bar chart. In constructor it gets instance of
 * {@link BarChart} which contains all informations of the bar chart. It contains list of {@link XYValue}. 
 * It contains x description which is description of the abscissa and y description which is
 * description of the ordinate. It contains minimal y value of the bar chart and maximal y value of the bar chart
 * and it contains space between two y values in the bar chart.
 * 
 * When the BarChartComponent is painted on the window it contains x and y axis and columns for the values in
 * list of {@link XYValue} of accepted instance of {@link BarChart}. It shows description of the axis.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class BarChartComponent extends JComponent {
	
	/**
	 * Universal version identifier for a Serializable class.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Space between description of the axis and values of the axis.
	 */
	private static final int SPACE_BETWEEN_DESCRIPTION_AND_VALUES = 10;
	
	/**
	 * Space between values of the y axis and the graph.
	 */
	private static final int SPACE_BETWEEN_YVALUES_AND_GRAPH = 15;
	
	/**
	 * Space between values of the x axis and the graph.
	 */
	private static final int SPACE_BETWEEN_XVALUES_AND_GRAPH = 10;
	
	/**
	 * Space below description of the x axis.
	 */
	private static final int SPACE_BELOW_XDESCRIPTION = 5;
	
	/**
	 * Length of the short gray lines of the graph.
	 */
	private static final int LENGTH_OF_GRAY_LINES = 10;
	
	/**
	 * Space between right edge of the window and the graph.
	 */
	private static final int SPACE_BETWEEN_RIGHT_EDGE_AND_GRAPH = 18;
	
	/**
	 * Random digit which is used for calculation of the length of the string with some number of digits.
	 */
	private static final String DIGIT = "0";
	
	/**
	 * Width of the arrow which is at the ends of the axis.
	 */
	private static final int ARROW_WIDTH = 8;
	
	/**
	 * Height of the arrow which is at the ends of the axis.
	 */
	private static final int ARROW_HEIGHT = 6;
	
	/**
	 * Number of the vertices of the arrow which is at the ends of the axis.
	 */
	private static final int NUM_OF_VERTICES_OF_ARROW = 3;
	
	/**
	 * Width of the shadow of the column divided with the width of the column. 
	 */
	private static final double SHADOW_DIVIDED_WITH_COLUMN = 0.05;
	
	/**
	 * Color of the columns of the graph. Color is "sienna 2" from cloford.com.
	 */
	private static final Color COLOR_OF_COLUMNS = new Color(238, 121, 66);
	
	/**
	 * Color of the lines of the graph. Color is rosybrow 1 from cloford.com.
	 */
	private static final Color COLOR_OF_LINES = new Color(255, 193, 193);
	
	/**
	 * Instance of the {@link BarChart} which contains all the informations about the bar chart.
	 */
	private BarChart barChart;
	
	/**
	 * Constructor which accepts one argument, instance of the {@link BarChart} which contains all the 
	 * informations about the bar chart.
	 * 
	 * @param barChart instance of the {@link BarChart} which contains all the informations about the bar chart.
	 * @throws IllegalArgumentException if accepted barChart is null value
	 */
	public BarChartComponent(BarChart barChart) {
		if(barChart == null) {
			throw new IllegalArgumentException("Argument barChart can not be null value.");
		}
		
		this.barChart = barChart;
		barChart.getListOfXYValues().sort((xyV1, xyV2) -> xyV1.getX() - xyV2.getX());
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Dimension dimension = getSize();
		Insets insets = getInsets();

		int maxNumberOfCharsInYValue = findMaxNumberOfCharsInYValue();
		
		int widthWOinsets = dimension.width - (insets.left + insets.right);
		int heightWOinsets = dimension.height - (insets.top + insets.bottom);
		
		int stringHeight = g.getFontMetrics().getHeight();
		
		int graphWidth = widthWOinsets - 
						 (stringHeight +  g.getFontMetrics().stringWidth(DIGIT) * maxNumberOfCharsInYValue +
				          SPACE_BETWEEN_DESCRIPTION_AND_VALUES + SPACE_BETWEEN_YVALUES_AND_GRAPH + SPACE_BETWEEN_RIGHT_EDGE_AND_GRAPH);
		
		int graphHeight = heightWOinsets - 
						  (SPACE_BELOW_XDESCRIPTION + stringHeight + SPACE_BETWEEN_DESCRIPTION_AND_VALUES +
						   stringHeight + SPACE_BETWEEN_XVALUES_AND_GRAPH);
		
		int minY = barChart.getMinY();
		int maxY = barChart.getMaxY();
		int spaceY = barChart.getSpace();
		
		int	numOfYValues = (int) Math.ceil((maxY - minY) / (spaceY * 1.0)) + 1;
		int distanceBetweenTwoY = graphHeight / numOfYValues;
		int distanceBetweenTwoX = graphWidth / barChart.getListOfXYValues().size();
		
		paintXAndYValuesOfTheGraph(
				g, minY, maxY, spaceY, numOfYValues, maxNumberOfCharsInYValue, stringHeight, insets,
				distanceBetweenTwoX, distanceBetweenTwoY, heightWOinsets, widthWOinsets, graphHeight, graphWidth);
		
		paintHorizontalLines(g, insets, widthWOinsets, graphWidth, graphHeight, numOfYValues, distanceBetweenTwoY);
		paintVerticalLines(g, insets, widthWOinsets, stringHeight, graphWidth, graphHeight, numOfYValues, distanceBetweenTwoY);
		paintColumns(g, insets, widthWOinsets, graphWidth, graphHeight, minY, maxY, spaceY, numOfYValues, distanceBetweenTwoY, distanceBetweenTwoX);
		paintXAndYDescription(g, widthWOinsets, heightWOinsets);
	}

	/**
	 * Method paints all the columns of the graph and calls the method which paints shadows of the columns
	 * 
	 * @param g instance of {@link Graphics}
	 * @param insets insets of the component
	 * @param widthWOinsets width of the component without the insets
	 * @param graphWidth width of the graph
	 * @param graphHeight height of the graph
	 * @param minY minimum y of the graph
	 * @param maxY maximum y of the graph
	 * @param spaceY space between two y on the graph
	 * @param numOfYValues num of the y values of the graph
	 * @param distanceBetweenTwoY distance between two y on the graph in pixels
	 * @param distanceBetweenTwoX distance between two x on the graph in pixels
	 */
	private void paintColumns(
			Graphics g, Insets insets, int widthWOinsets, int graphWidth, int graphHeight, int minY,
			int maxY, int spaceY, int numOfYValues, int distanceBetweenTwoY, int distanceBetweenTwoX) {
		
		int lastYValue = 0;
		int lastY1 = 0;
		int lastY2 = 0;
		
		int yOfZero = findYOfZero(g, graphHeight, minY, maxY, spaceY, numOfYValues, distanceBetweenTwoY);
		
		for(int i = 0, n = barChart.getListOfXYValues().size(); i < n; i++) {
			if (barChart.getListOfXYValues().get(i).getY() < minY && minY >= 0) {
				continue;
			}
			
			int yValue = barChart.getListOfXYValues().get(i).getY();
			yValue = yValue > maxY ? maxY + maxY % spaceY : yValue;
			yValue = yValue < minY ? minY : yValue;
			
			int y = (int) (graphHeight - (1.0 * (yValue - minY) / spaceY) * distanceBetweenTwoY - g.getFontMetrics().getDescent());
			int y2 = graphHeight - y - g.getFontMetrics().getDescent() - (graphHeight - yOfZero - g.getFontMetrics().getDescent());
			int d = y2 - y;
			int x = widthWOinsets - graphWidth + i * distanceBetweenTwoX - SPACE_BETWEEN_RIGHT_EDGE_AND_GRAPH;
			
			g.setColor(COLOR_OF_COLUMNS);
			
			y = yValue > 0 ? y : yOfZero;
			y2 = (int) (yValue > 0 ? y2 : (yOfZero + d) * (-1.0 / 2));
			
			if(y2 > insets.top + graphHeight) {
				y2 = graphHeight;
			}
		
			g.fillRect(x + 1, y, distanceBetweenTwoX - 1, y2);
			
			if(yValue > 0 && lastYValue > 0 && yValue < lastYValue ||
			   yValue > 0 && lastYValue < 0 || 
			   yValue < 0 && lastYValue > 0 ||
			   yValue < 0 && lastYValue < 0 && yValue > lastYValue || 
			   yValue == 0) {
				
				paintShadow(g, distanceBetweenTwoX, lastYValue, lastY1, lastY2, yOfZero, yValue, y2, x);
			}
			
			if(i == n - 1) {
				paintShadowOfLastColumn(g, distanceBetweenTwoX, yOfZero, yValue, y, y2, x);
			}
			
			g.setColor(Color.WHITE);
			g.drawLine(x + distanceBetweenTwoX, y, x + distanceBetweenTwoX, y + y2);
			
			lastYValue = yValue;
			lastY1 = y;
			lastY2 = y2;
		}
	}

	/**
	 * Method paints shadow of the last column on the graph.
	 * 
	 * @param g instance of {@link Graphics}
	 * @param distanceBetweenTwoX distance between two x on the graph in pixels
	 * @param yOfZero position of the zero on the graph
	 * @param yValue value of the current y which is last painted on the graph
	 * @param y y position of the column
	 * @param y2 height of the column
	 * @param x x position of the column
	 */
	private void paintShadowOfLastColumn(Graphics g, int distanceBetweenTwoX, int yOfZero, int yValue, int y, int y2, int x) {
		g.setColor(Color.LIGHT_GRAY);
		
		g.fillRect(
				x + 1 + distanceBetweenTwoX,
				yValue > 0 ? y + (int)(SHADOW_DIVIDED_WITH_COLUMN * distanceBetweenTwoX) : yOfZero + (int)(SHADOW_DIVIDED_WITH_COLUMN * distanceBetweenTwoX),
				(int)(SHADOW_DIVIDED_WITH_COLUMN * distanceBetweenTwoX),
			    y2 - (int)(SHADOW_DIVIDED_WITH_COLUMN * distanceBetweenTwoX));
	}

	/**
	 * Method paints shadows of the columns on the graph.
	 * 
	 * @param g instance if {@link Graphics}
	 * @param distanceBetweenTwoX distance between two x on the graph in pixels
	 * @param lastYValue y value of the previous column
	 * @param lastY1 y position of the previous column
	 * @param lastY2 height of the previous column
	 * @param yOfZero position of the zero on the graph
	 * @param yValue y value of the current column
	 * @param y2 height of current column
	 * @param x x position of the column
	 */
	private void paintShadow(Graphics g, int distanceBetweenTwoX, int lastYValue, int lastY1, int lastY2, int yOfZero, int yValue, int y2, int x) {
		g.setColor(Color.LIGHT_GRAY);
		
		int y1Shadow = lastY1  + (int)(SHADOW_DIVIDED_WITH_COLUMN * distanceBetweenTwoX);
		int heightShadow = lastY2 - y2 - (int)(SHADOW_DIVIDED_WITH_COLUMN * distanceBetweenTwoX);
		
		if(yValue < 0 && lastYValue > 0) {
			heightShadow = yOfZero - lastY1 - (int)(SHADOW_DIVIDED_WITH_COLUMN * distanceBetweenTwoX);
		}
		
		if(yValue > 0 && lastYValue < 0) {
			y1Shadow = yOfZero;
			heightShadow = lastY2;
		}
		
		if(yValue < 0 && lastYValue < 0) {
			y1Shadow = yOfZero + y2;
			heightShadow = lastY2 - y2;
		}

		heightShadow = heightShadow > 0 ? heightShadow : yOfZero - lastY1 - (int)(SHADOW_DIVIDED_WITH_COLUMN * distanceBetweenTwoX);
		
		g.fillRect(x + 1, y1Shadow, (int)(SHADOW_DIVIDED_WITH_COLUMN * distanceBetweenTwoX), heightShadow);
	}

	/**
	 * Method finds y position of the zero on the graph.
	 * 
	 * @param g instance of {@link Graphics}
	 * @param graphHeight height of the graph
	 * @param minY minimum y of the graph
	 * @param maxY maximum y of the graph
	 * @param spaceY space between two y on the graph
	 * @param numOfYValues number of the y values on the graph
	 * @param distanceBetweenTwoY distance between two y on the graph in pixels
	 * @return y position of the zero on the graph
	 */
	private int findYOfZero(Graphics g, int graphHeight, int minY, int maxY, int spaceY, int numOfYValues, int distanceBetweenTwoY) {
		int nOfNegative = minY / spaceY * -1;
		int yOfZero = (int) (graphHeight - (1.0 * nOfNegative * distanceBetweenTwoY + g.getFontMetrics().getDescent()));
		
		if(Math.abs(minY) % spaceY != 0) {
			double p = 1.0 * maxY / (maxY - minY);
			yOfZero = (int) ((numOfYValues - 1) * distanceBetweenTwoY - (p) * (numOfYValues - 1) * distanceBetweenTwoY);
		}
		
		if(nOfNegative < 1) {
			yOfZero = graphHeight - g.getFontMetrics().getDescent();
		}
		
		if(nOfNegative == numOfYValues) {
			yOfZero = graphHeight - (numOfYValues - 1) * distanceBetweenTwoY - g.getFontMetrics().getDescent();
		}
		return yOfZero;
	}

	/**
	 * Method paints vertical lines on the graph.
	 * 
	 * @param g instance of {@link Graphics}
	 * @param insets insets of the component
	 * @param widthWOinsets width of the component without the insets
	 * @param stringHeight height of the string painted on the component
	 * @param graphWidth width of the graph
	 * @param graphHeight height of the graph
	 * @param numOfYValues number of the y values on the graph
	 * @param distanceBetweenTwoY distance between two y on the graph in pixels
	 */
	private void paintVerticalLines(
			Graphics g, Insets insets, int widthWOinsets, int stringHeight, int graphWidth,  int graphHeight, int numOfYValues, int distanceBetweenTwoY) {
		
		int y1 = insets.top + graphHeight - g.getFontMetrics().getDescent();
		for(int i = 0, n = barChart.getListOfXYValues().size(); i <= n; i++) {
			int x = widthWOinsets - graphWidth + i * (int) (1.0 * graphWidth / barChart.getListOfXYValues().size()) - SPACE_BETWEEN_RIGHT_EDGE_AND_GRAPH;
			int y2 = insets.top + graphHeight - (numOfYValues - 1) * distanceBetweenTwoY - stringHeight;
			
			if(i == 0) {
				g.setColor(Color.DARK_GRAY);
				g.drawLine(x, y1, x, y2);
				g.drawLine(x, y1 + LENGTH_OF_GRAY_LINES, x, y1);
				g.fillPolygon(new int[] {x - ARROW_WIDTH / 2, x + ARROW_WIDTH / 2, x}, 
						      new int[] {y2 + ARROW_HEIGHT, y2 + ARROW_HEIGHT, y2}, 
						      NUM_OF_VERTICES_OF_ARROW);
				
				continue;
			}
			
			g.setColor(COLOR_OF_LINES);
			g.drawLine(x, y1, x, y2);
			
			g.setColor(Color.DARK_GRAY);
			g.drawLine(x, y1 + LENGTH_OF_GRAY_LINES, x, y1);
		}
	}

	/**
	 * Method paints vertical lines on the graph.
	 * 
	 * @param g instance of {@link Graphics}
	 * @param insets insets of the component
	 * @param widthWOinsets width of the component without the insets
	 * @param graphWidth width of the graph
	 * @param graphHeight height of the graph
	 * @param numOfYValues number of the y values on the graph
	 * @param distanceBetweenTwoY distance between two y on the graph in pixels
	 */
	private void paintHorizontalLines(Graphics g, Insets insets, int widthWOinsets, int graphWidth, int graphHeight,
			int numOfYValues, int distanceBetweenTwoY) {
		
		int x1 = insets.left + widthWOinsets - graphWidth - SPACE_BETWEEN_RIGHT_EDGE_AND_GRAPH;
		int x2 = (int) (insets.left + widthWOinsets - SPACE_BETWEEN_RIGHT_EDGE_AND_GRAPH / 2);
		for(int i = 0; i < numOfYValues; i++) {
			int y = graphHeight - g.getFontMetrics().getDescent() - i * distanceBetweenTwoY;
			
			if(i == 0) {
				g.setColor(Color.DARK_GRAY);
				g.drawLine(x1, y, x2, y);
				g.drawLine(x1 - LENGTH_OF_GRAY_LINES, y, x1, y);
				g.fillPolygon(new int[] {x2, x2, x2 + ARROW_HEIGHT}, 
						      new int[]{y + ARROW_WIDTH / 2, y - ARROW_WIDTH / 2, y}, 
						      NUM_OF_VERTICES_OF_ARROW);
				
				continue;
			}
			
			g.setColor(COLOR_OF_LINES);
			g.drawLine(x1, y, x2, y);
			
			g.setColor(Color.DARK_GRAY);
			g.drawLine(x1 - LENGTH_OF_GRAY_LINES, y, x1, y);
		}
	}
	
	/**
	 * Method paints descriptions of the x and y axis on the graph. 
	 * 
	 * @param g instance of {@link Graphics}
	 * @param widthWOinsets width of the component without the insets
	 * @param heightWOinsets height of the component without the insets
	 */
	private void paintXAndYDescription(Graphics g, int widthWOinsets, int heightWOinsets) {
		g.setColor(Color.BLACK);
		
		g.drawString(
				barChart.getDescriptionX(), 
			    widthWOinsets / 2 - g.getFontMetrics().stringWidth(barChart.getDescriptionX()) / 2 + g.getFontMetrics().getHeight(), 
				heightWOinsets - SPACE_BELOW_XDESCRIPTION);
		
		Graphics2D g2D = (Graphics2D) g;
		AffineTransform at = new AffineTransform();
		at.rotate(-Math.PI / 2);
		g2D.setTransform(at);
		
		g2D.drawString(
				barChart.getDescriptionY(),
				-(heightWOinsets / 2 + g.getFontMetrics().stringWidth(barChart.getDescriptionY()) / 2 + g.getFontMetrics().getHeight()),
			    g.getFontMetrics().getHeight());
	}

	/**
	 * Method paints x and y values of the axis on the graph.
	 * 
	 * @param g instance of {@link Graphics}
	 * @param minY minimum y of the graph
	 * @param maxY maximum y of the graph
	 * @param spaceY space between two y on the graph
	 * @param numOfYValues number of y values on the graph
	 * @param maxNumberOfCharsInYValue maximum number of chars in the y value
	 * @param stringHeight height of the string painted on the component
	 * @param insets insets of the component
	 * @param distanceBetweenTwoX distance between two x on the graph in pixels
	 * @param distanceBetweenTwoY distance between two y on the graph in pixels
	 * @param heightWOinsets height of the component without the insets
	 * @param widthWOinsets width of the component without the insets
	 * @param graphHeight height of the graph
	 * @param graphWidth widht of the graph
	 */
	private void paintXAndYValuesOfTheGraph(
			Graphics g, int minY, int maxY, int spaceY, int numOfYValues, int maxNumberOfCharsInYValue, int stringHeight, Insets insets,
			int distanceBetweenTwoX, int distanceBetweenTwoY, int heightWOinsets, int widthWOinsets, int graphHeight, int graphWidth) {
		
		//Paint y values
		for(int i = 0, y = minY; i < numOfYValues; i++, y += spaceY) {
			String yAsString = String.valueOf(y);
			while(yAsString.length() < maxNumberOfCharsInYValue) {
				yAsString = " " + yAsString;
				
			}
			
			int move = maxNumberOfCharsInYValue * g.getFontMetrics().stringWidth(DIGIT) - g.getFontMetrics().stringWidth(yAsString);
			
			g.drawString(
					yAsString,
					stringHeight + SPACE_BETWEEN_DESCRIPTION_AND_VALUES + move, 
					insets.top + graphHeight - i * distanceBetweenTwoY);
		}
		
		//Paint x values
		for(int i = 0, n = barChart.getListOfXYValues().size(); i < n; i++) {
			String xValueAsString = String.valueOf(barChart.getListOfXYValues().get(i).getX());
			
			g.drawString(
					xValueAsString,
					(int) (widthWOinsets - graphWidth + (i + 0.5) * distanceBetweenTwoX - 
					       SPACE_BETWEEN_RIGHT_EDGE_AND_GRAPH - g.getFontMetrics().stringWidth(xValueAsString) / 2),
				    heightWOinsets - (SPACE_BELOW_XDESCRIPTION + stringHeight + SPACE_BETWEEN_DESCRIPTION_AND_VALUES));
		}
		
	}

	/**
	 * Method finds and returns max number of chars in y value.
	 * 
	 * @return max number of chars in y value
	 */
	private int findMaxNumberOfCharsInYValue() {
		int max = 0;
		
		int numOfYValues = (int) Math.ceil((barChart.getMaxY() - barChart.getMinY() + 1) / (barChart.getSpace() * 1.0)) + 1;
		for(int i = 0, y = barChart.getMinY(); i < numOfYValues; i++, y += barChart.getSpace()) {
			boolean wasNegative = y < 0;
			int yPom = y < 0 ? y * -1 : y;
			
			int numOfChars = y == 0 ? 1 : (int)(Math.log10(yPom) + 1);
			numOfChars = wasNegative ? numOfChars += 1 : numOfChars;
			
			if(numOfChars > max) {
				max = numOfChars;
			}
		}
		
		return max;
	}

}
