package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager2;
import hr.fer.zemris.java.gui.calc.Calculator;

/**
 * Class is layout manager which implement {@link LayoutManager2}. It is used in class {@link Calculator}.
 * It works with constraints which are instances of class {@link RCPosition}. 
 * 
 * CalcLayout is similiar to {@link GridLayout}. It stores components in two dimensional array. Differences between 
 * this layout manager and {@link GridLayout} are that in this layout manager number of row and number of columns are
 * constant numbers. Number of rows is 5 and numbers of columns is 7. Indexes of rows and columns starts with 1.
 * 
 * First row of components is different from all other rows. In first row there are only three components.
 * Component at index (1,1) is big as 5 other components plus gap between them. All other components have
 * same width and same height.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class CalcLayout implements LayoutManager2 {
	
	/**
	 * Number of rows of two dimensional field of components.
	 */
	private static final int NUMBER_OF_ROWS = 5;
	
	/**
	 * Number of columns of two dimensional field of components.
	 */
	private static final int NUMBER_OF_COLUMNS = 7;
	
	/**
	 * Minimum index of rows.
	 */
	private static final int MINIMUM_ROW = 1;
	
	/**
	 * Maximum index of rows.
	 */
	private static final int MINIMUM_COLUMN = 1;
	
	/**
	 * Minimum illegal index in first column. 
	 */
	private static final int MIN_ILLEGAL_COL_IN_FIRST_ROW = 2;
	
	/**
	 * Maximum illegal index in first column.
	 */
	private static final int MAX_ILLEGAL_COL_IN_FIRST_ROW = 5;
	
	/**
	 * Number of chars of constraints as string.
	 */
	private static final int LENGTH_OF_CONSTRAINTS_AS_STRING = 3;
	
	/**
	 * Default gap between components.
	 */
	private static final int DEFAULT_GAP = 0;
	
	/**
	 * Gap between components.
	 */
	private int gap;

	/**
	 * Two dimensional array of components.
	 */
	private Component[][] components;
	
	/**
	 * Constructor which accepts no arguments. It delegates job to the other constructor which accepts gap
	 * between components as arguments. As gap it gives default gap constant.
	 */
	public CalcLayout() {
		this(DEFAULT_GAP);
	}
	
	/**
	 * Constructor which accepts one argument, gap between the components. It throws {@link IllegalArgumentException}
	 * if the gap is less than 0.
	 * 
	 * @param gap gap between the components
	 * @throws IllegalArgumentException if the gap is less than 0
	 */
	public CalcLayout(int gap) {
		if(gap < 0) {
			throw new IllegalArgumentException("Gap can not be less than zero.");
		}
		
		this.gap = gap;
		
		this.components = new Component[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];
	}
	
	/**
	 * CalcLayout does not support addaddLayoutComponent method which accepts {@link String} and
	 * {@link Component} as argument.
	 */
	@Override
	public void addLayoutComponent(String name, Component comp) {}

	@Override
	public void removeLayoutComponent(Component comp) {
		for(int i = 0; i < NUMBER_OF_ROWS; i++) {
			for(int j = 0; j < NUMBER_OF_COLUMNS; j++) {
				if(comp == components[i][j]) {
					components[i][j] = null;
					
					break;
				}
			}
		}
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		synchronized (parent.getTreeLock()) {
			int rowHeight = 0;
			int columnWidth = 0;
			
			int numberOfComponents = parent.getComponentCount();
			for(int i = 0; i < numberOfComponents; i++) {
				 Component component = parent.getComponent(i);
		         Dimension dimension = component.getPreferredSize();
		         
		         if(dimension == null) {
		        	 continue;
		         }
		         
		         if(dimension.height > rowHeight) {
		        	 rowHeight = dimension.height;
		         }
		         
		         if(dimension.width > columnWidth) {
		        	 columnWidth = dimension.width;
		         }
			}
			
			Insets insets = parent.getInsets();
			
			int widthOfTheContainer = 
					insets.left + 
					insets.right + 
					NUMBER_OF_COLUMNS * columnWidth + 
					(NUMBER_OF_COLUMNS - 1) * gap;
			
			int heightOfTheContainer =
					insets.top +
					insets.bottom + 
					NUMBER_OF_ROWS * rowHeight + 
					(NUMBER_OF_ROWS - 1) * gap;
			
			return new Dimension(widthOfTheContainer, heightOfTheContainer);
		}
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		synchronized (parent.getTreeLock()) {
			int rowHeight = 0;
			int columnWidth = 0;
			
			int numberOfComponents = parent.getComponentCount();
			for(int i = 0; i < numberOfComponents; i++) {
				 Component component = parent.getComponent(i);
		         Dimension dimension = component.getMinimumSize();
		         
		         if(dimension == null) {
		        	 continue;
		         }
		         
		         if(dimension.height > rowHeight) {
		        	 rowHeight = dimension.height;
		         }
		         
		         if(dimension.width > columnWidth) {
		        	 columnWidth = dimension.width;
		         }
			}
			
			Insets insets = parent.getInsets();
			
			int widthOfTheContainer = 
					insets.left + 
					insets.right + 
					NUMBER_OF_COLUMNS * columnWidth + 
					(NUMBER_OF_COLUMNS - 1) * gap;
			
			int heightOfTheContainer =
					insets.top +
					insets.bottom + 
					NUMBER_OF_ROWS * rowHeight + 
					(NUMBER_OF_ROWS - 1) * gap;
			
			return new Dimension(widthOfTheContainer, heightOfTheContainer);
		}
	}

	@Override
	public void layoutContainer(Container parent) {
		synchronized (parent.getTreeLock()) {
			int numberOfComponents = parent.getComponentCount();
			
			if (numberOfComponents == 0) {
	            return;
	        }
			
			Insets insets = parent.getInsets();
			
			int widthOfContainerWOInsets = parent.getWidth() - insets.left - insets.right;
			int heightOfContainerWOInsets = parent.getHeight() - insets.top - insets.bottom;
			
			int widthOfOneComponent = (widthOfContainerWOInsets  - (NUMBER_OF_COLUMNS - 1) * gap) / NUMBER_OF_COLUMNS;
			int heightOfOneComponent = (heightOfContainerWOInsets - (NUMBER_OF_ROWS - 1) * gap) / NUMBER_OF_ROWS;
			
			for(int i = 0; i < NUMBER_OF_ROWS; i++) {
				for(int j = 0; j < NUMBER_OF_COLUMNS; j++) {
					if(i == 0 && j >= MIN_ILLEGAL_COL_IN_FIRST_ROW - 1 && j < MAX_ILLEGAL_COL_IN_FIRST_ROW) {
						continue;
					}
					
					if(components[i][j] == null) {
						continue;
					}
					
					int x;
					int y;
					int width;
					int height;
					
					if(i == 0 && j == 0) {
						x = insets.left;
						y = insets.top;
						width = (MAX_ILLEGAL_COL_IN_FIRST_ROW - MIN_ILLEGAL_COL_IN_FIRST_ROW + 2) * widthOfOneComponent +
								(MAX_ILLEGAL_COL_IN_FIRST_ROW - MIN_ILLEGAL_COL_IN_FIRST_ROW + 1) * gap;
						height = heightOfOneComponent;
						
						components[i][j].setBounds(x, y, width, height);
						
						continue;
					}
					
					x = insets.left + j * (widthOfOneComponent + gap);
					y = insets.top + i * (heightOfOneComponent + gap);
					width = widthOfOneComponent;
					height = heightOfOneComponent;
					
					if(i == NUMBER_OF_ROWS - 1) {
						height = heightOfContainerWOInsets - y;
					}
					
					if(j == NUMBER_OF_COLUMNS - 1) {
						width = widthOfContainerWOInsets - x;
					}
					
					components[i][j].setBounds(x, y, width, height);
				}
			}
		}
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if(comp == null) {
			throw new IllegalArgumentException("Argument comp can not be null.");
		}
		
		if(constraints == null) {
			throw new IllegalArgumentException("Argument constraints can not be null.");
		}
		
		if(!(constraints instanceof RCPosition) && !(constraints instanceof String)) {
			throw new IllegalArgumentException("Argument constraints must be instance of RCPosition or String.");
		}
		
		int row = 0;
		int column = 0;
		
		if(constraints instanceof RCPosition) {
			RCPosition rcPosition = (RCPosition) constraints;
			
			row = rcPosition.getRow();
			column = rcPosition.getColumn();
		}
		
		if(constraints instanceof String) {
			String constraintsAsString = (String) constraints;
			String[] rowAndCol = parseRowAndCol(constraintsAsString);
			
			row = Integer.parseInt(rowAndCol[0]);
			column = Integer.parseInt(rowAndCol[1]);
		}
		
		if((row < MINIMUM_ROW || row > NUMBER_OF_ROWS || column < MINIMUM_COLUMN || column > NUMBER_OF_COLUMNS) ||
		   (row == MINIMUM_ROW && column >= MIN_ILLEGAL_COL_IN_FIRST_ROW && column <= MAX_ILLEGAL_COL_IN_FIRST_ROW)) {
			throw new IllegalArgumentException("Pair (" + row + "," + column + ") for row, column is not legal");
		}
		
		if(components[row - 1][column - 1] != null) {
			throw new IllegalArgumentException("On position (" + row + "," + column +") component already exists.");
		}
		
		components[row - 1][column - 1] = comp;
	}

	/**
	 * Method parse accepted constraint as to the array of strings in which first element is row of the
	 * constraint and other element is column of the constraint. Accepted constraint must be 
	 * like indexOfRow,indexOfColumn.
	 * 
	 * @param constraintsAsString constraint as string
	 * @return string array with two elements in which first element is row of the constraint and other element 
	 * is column of the constraint
	 * @throws IllegalArgumentException if accepted constraint as string is not like number,number
	 */
	private String[] parseRowAndCol(String constraintsAsString) {
		char[] data = constraintsAsString.toCharArray();
		
		if(data.length != LENGTH_OF_CONSTRAINTS_AS_STRING || 
		   !Character.isDigit(data[0]) ||
		   data[1] != ',' ||
		   !Character.isDigit(data[2])) {
			throw new IllegalArgumentException("Constraint as string must be like \"number,number\"");
		}
		
		return constraintsAsString.split(",");
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		synchronized (target.getTreeLock()) {
			int rowHeight = target.getComponent(0).getHeight();
			int columnWidth = target.getComponent(0).getWidth();
			
			int numberOfComponents = target.getComponentCount();
			for(int i = 1; i < numberOfComponents; i++) {
				 Component component = target.getComponent(i);
		         Dimension dimension = component.getMaximumSize();
		         
		         if(dimension == null) {
		        	 continue;
		         }
		         
		         if(dimension.height < rowHeight) {
		        	 rowHeight = dimension.height;
		         }
		         
		         if(dimension.width < columnWidth) {
		        	 columnWidth = dimension.width;
		         }
			}
			
			Insets insets = target.getInsets();
		
			int widthOfTheContainer = 
					insets.left + 
					insets.right + 
					NUMBER_OF_COLUMNS * columnWidth + 
					(NUMBER_OF_COLUMNS - 1) * gap;
			
			int heightOfTheContainer =
					insets.top +
					insets.bottom + 
					NUMBER_OF_ROWS * rowHeight + 
					(NUMBER_OF_ROWS - 1) * gap;
			
			return new Dimension(widthOfTheContainer, heightOfTheContainer);
		}
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	/**
	 * Class CalcLayout does not support method invalidateLayout.
	 */
	@Override
	public void invalidateLayout(Container target) {}
	
}
