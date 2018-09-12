package hr.fer.zemris.java.gui.charts;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents bar chart. It contains all informations of the bar chart. It contains list of  
 * {@link XYValue}. It contains x description which is description of the abscissa and y description which is
 * description of the ordinate. It contains minimal y value of the bar chart and maximal y value of the bar chart
 * and it contains space between two y values in the bar chart.
 * 
 * Values of the bar chart are unmodifiable.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class BarChart {
	
	/**
	 * List of {@link XYValue} of the bar chart.
	 */
	private final List<XYValue> listOfXYValues;
	
	/**
	 * Description of the abscissa of the bar chart.
	 */
	private final String descriptionX;
	
	/**
	 * Description of the ordinate of the bar chart.
	 */
	private final String descriptionY;
	
	/**
	 * Minimum y value of the bar chart.
	 */
	private final int minY;
	
	/**
	 * Maximum y value of the bar chart.
	 */
	private final int maxY;
	
	/**
	 * Space between two y values in the bar chart.
	 */
	private final int space;
	
	/**
	 * Constructor which accepts five arguments, listOfXYValues, descriptionX, descriptionY, minY, maxY and space.
	 * 
	 * @param listOfXYValues list of {@link XYValue} of the bar chart
	 * @param descriptionX description of the abscissa of the bar chart
	 * @param descriptionY description of the ordinate of the bar chart
	 * @param minY minimum y value of the bar chart
	 * @param maxY maximum y value of the bar chart
	 * @param space space between two y values in the bar chart
	 * @throws IllegalArgumentException if the listOfXYValues is null, minY is greater than maxY or space is not
	 * greater than zero
	 */
	public BarChart(List<XYValue> listOfXYValues, String descriptionX, String descriptionY, int minY, int maxY, int space) {
		if(listOfXYValues == null) {
			throw new IllegalArgumentException("List of XYValues can not be null.");
		}
		
		if(minY > maxY) {
			throw new IllegalArgumentException("Minimum y can not be greater than maximum y.");
		}
		
		if(space <= 0) {
			throw new IllegalArgumentException("Space between two y must be greater than 0.");
		}
		
		this.listOfXYValues = new ArrayList<>(listOfXYValues);
		this.descriptionX = descriptionX;
		this.descriptionY = descriptionY;
		this.minY = minY;
		this.maxY = maxY;
		this.space = space;
	}

	/**
	 * Getter for the listOfXYValues.
	 * 
	 * @return listOfXYValues
	 */
	public List<XYValue> getListOfXYValues() {
		return listOfXYValues;
	}

	/**
	 * Getter for the descriptionX.
	 * 
	 * @return descriptionX
	 */
	public String getDescriptionX() {
		return descriptionX;
	}

	/**
	 * Getter for the descriptionY.
	 * 
	 * @return descriptionY
	 */
	public String getDescriptionY() {
		return descriptionY;
	}

	/**
	 * Getter for the minY.
	 * 
	 * @return minY
	 */
	public int getMinY() {
		return minY;
	}

	/**
	 * Getter for the maxY.
	 * 
	 * @return maxY
	 */
	public int getMaxY() {
		return maxY;
	}

	/**
	 * Getter for the space.
	 * 
	 * @return space
	 */
	public int getSpace() {
		return space;
	}
}
