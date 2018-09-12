package hr.fer.zemris.java.gui.layouts;

/**
 * Class RCPosition is used as constraint in class {@link CalcLayout}. Class contains two read-only properties,
 * row and column which represent indexes of row and column of the added component. Indexes of rows and colums 
 * start from 1.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class RCPosition {
	
	/**
	 * Index of row of the position of the component.
	 */
	private int row;
	
	/**
	 * Index of column of the position of the component.
	 */
	private int column;
	
	/**
	 * Constructor which accepts two arguments, index of row of the component and index of column of the component.
	 * Indexes start from 1.
	 * 
	 * @param row index of row of the component
	 * @param column index of column of the component
	 * @throws IllegalArgumentException if the row or column is less than 1
	 */
	public RCPosition(int row, int column) {
		if(row < 1 || column < 1) {
			throw new IllegalArgumentException("Row and column can not be less than 1.");
		}
		
		this.row = row;
		this.column = column;
	}
	
	/**
	 * Getter for the row.
	 * 
	 * @return row
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * Getter for the column.
	 * 
	 * @return column
	 */
	public int getColumn() {
		return column;
	}

}
