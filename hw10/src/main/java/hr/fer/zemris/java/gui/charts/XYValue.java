package hr.fer.zemris.java.gui.charts;

/**
 * Class is value which contains two read-only properties. This class is used in {@link BarChartComponent} for the
 * columns of the bar chart.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class XYValue {
	
	/**
	 * Component x of the value.
	 */
	private final int x;
	
	/**
	 * Component y of the value.
	 */
	private final int y;
	
	/**
	 * Constructor which accepts two arguments, value of the x and value of the y component,
	 * 
	 * @param x
	 * @param y
	 */
	public XYValue(int x, int y) {
		super();
		
		this.x = x;
		this.y = y;
	}

	/**
	 * Getter for the x.
	 * 
	 * @return x
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Getter for the y.
	 * 
	 * @return y
	 */
	public int getY() {
		return y;
	}

}
