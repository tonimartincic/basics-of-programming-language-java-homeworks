package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Class demonstrates functionality of the {@link BarChartComponent}. Program gets as argument of the command line 
 * path to the file which contains informations of the bar chart. It creates instance of the {@link BarChart} and then
 * it creates instance of the {@link BarChartComponent} with this informations. Program creates window which contains
 * two components, created instance of {@link BarChartComponent} and {@link JLabel} which is path to the file
 * with the informations for the bar chart.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class BarChartDemo extends JFrame {
	
	/**
	 * Universal version identifier for a Serializable class.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Component x of location of the window.
	 */
	private static final int X_LOCATION_OF_THE_WINDOW = 50;
	
	/**
	 * Component y of location of the window.
	 */
	private static final int Y_LOCATION_OF_THE_WINDOW = 50;
	
	/**
	 * Width of the window.
	 */
	private static final int WIDTH_OF_THE_WINDOW = 800;
	
	/**
	 * Height of the window.
	 */
	private static final int HEIGHT_OF_THE_WINDOW = 600;
	
	/**
	 * Number of rows in file with informations for bar chart.
	 */
	private static final int NUMBER_OF_ROWS_IN_FILE = 6;
	
	/**
	 * Instance of {@link BarChartComponent}.
	 */
	private static BarChartComponent barChartComponent;
	
	/**
	 * Path to the file with the informations for the bar chart.
	 */
	private static Path path;
	
	/**
	 * Constructor which accepts no arguments. It sets DefaultCloseOperation, Location and the Size of the window
	 * and calls method initGUI.
	 */
	public BarChartDemo() {
		super();
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		setLocation(X_LOCATION_OF_THE_WINDOW, Y_LOCATION_OF_THE_WINDOW);
		setSize(WIDTH_OF_THE_WINDOW, HEIGHT_OF_THE_WINDOW);
		
		initGUI();
	}
	
	/**
	 * Method creates {@link JLabel} with the path to the file with informations of the bar chart and adds label
	 * and the {@link BarChartComponent} to the window.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		
		cp.setLayout(new BorderLayout());
		
		JLabel label = new JLabel(path.toString());
		label.setHorizontalAlignment(SwingConstants.CENTER);
		cp.add(label, BorderLayout.PAGE_START);
		
		cp.add(barChartComponent, BorderLayout.CENTER);
	}

	/**
	 * Main method from which program starts.
	 * 
	 * @param args arguments of the command line, path to the file with informations of the bar chart is 
	 * accepted as argument
	 */
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("Path to the file with data for the bar chart expected as argument.");
			
			return;
		}
		
		path = Paths.get(args[0]);
		if(!Files.exists(path)) {
			System.out.println("File does not exist.");
			
			return;
		}
		
		List<String> data;
		try {
			data = Files.readAllLines(path);
		} catch (IOException e) {
			System.out.println("Error while reading from file.");
			
			return;
		}
		
		BarChart barChart;
		try {
			barChart = createBarChart(data);
		} catch(NumberFormatException exc) {
			System.out.println("Invalid arguments.");
			
			return;
	    } catch(IllegalArgumentException exc) {
			System.out.println(exc.getMessage());
			
			return;
		}
		
		barChartComponent = new BarChartComponent(barChart);
		
		SwingUtilities.invokeLater(() -> {
			new BarChartDemo().setVisible(true);
		});
	}

	/**
	 * Method creates {@link BarChart} from accepted list of strings which are informations of the bar chart.
	 * 
	 * @param data list of strings which are informations of the bar chart
	 * @return created {@link BarChart}
	 * @throws IllegalArgumentException if the data is null, data size is not correct, miny is greater than maxy,
	 * space between two y is less than zero or there are duplicate x values
	 */
	private static BarChart createBarChart(List<String> data) {
		if(data == null || data.size() != NUMBER_OF_ROWS_IN_FILE) {
			throw new IllegalArgumentException("Number of rows in file must be " + NUMBER_OF_ROWS_IN_FILE + ".");
		}
		
		String descriptionX = data.get(0);
		String descriptionY = data.get(1);
		
		List<XYValue> listOfXYValues = new ArrayList<>();
		String[] parts = data.get(2).split(" ");
		for(int i = 0; i < parts.length; i++) {
			String[] xAndY = parts[i].split(",");
			XYValue xyValue = new XYValue(Integer.parseInt(xAndY[0]), Integer.parseInt(xAndY[1]));
			
			listOfXYValues.add(xyValue);
		}
		
		checkIfThereAreDuplicateXValues(listOfXYValues);
		
		int minY = Integer.parseInt(data.get(3));
		int maxY = Integer.parseInt(data.get(4));
		int space = Integer.parseInt(data.get(5));
		
		if(minY > maxY) {
			throw new IllegalArgumentException("Min y can not be greater than max y.");
		}
		
		if(space <= 0) {
			throw new IllegalAccessError("Space between two y must be greater than zero.");
		}
		
		return new BarChart(listOfXYValues, descriptionX, descriptionY, minY, maxY, space);
	}

	/**
	 * Method checks is there duplicate x values in accepted list of {@link XYValue}.
	 * 
	 * @param listOfXYValues accepted list of {@link XYValue}
	 * @throws IllegalArgumentException if there are duplicate values in accepted list of {@link XYValue}
	 */
	private static void checkIfThereAreDuplicateXValues(List<XYValue> listOfXYValues) {
		Set<Integer> temp = new HashSet<>();
		
		for(XYValue xyValue : listOfXYValues) {
			if(!temp.add(xyValue.getX())) {
				throw new IllegalArgumentException("Two same values given as x.");
			}
		}
	}

}
