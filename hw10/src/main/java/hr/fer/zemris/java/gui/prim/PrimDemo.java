package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Method demonstrates functionality of the {@link PrimListModel}. It creates window which contains three 
 * components, two lists and one button which when is pressed it adds next prime number to the list model.
 * Both list uses the same list model.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class PrimDemo extends JFrame {
	
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
	private static final int WIDTH_OF_THE_WINDOW = 400;
	
	/**
	 * Height of the window.
	 */
	private static final int HEIGHT_OF_THE_WINDOW = 300;
	
	/**
	 * Number of columns of the grid layout.
	 */
	private static final int NUM_OF_COLUMNS = 2;
	
	/**
	 * Number of rows of the grid layout.
	 */
	private static final int NUM_OF_ROWS = 1;
	
	/**
	 * Constructor which accepts no arguments. It sets DefaultCloseOperation, Location and the Size of the window 
	 * and calls method iniGui.
	 */
	public PrimDemo() {
		super();
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		setLocation(X_LOCATION_OF_THE_WINDOW, Y_LOCATION_OF_THE_WINDOW);
		setSize(WIDTH_OF_THE_WINDOW, HEIGHT_OF_THE_WINDOW);
		
		initGui();
	}

	/**
	 * Method creates window which contains three components, two lists and one button which when is pressed it 
	 * adds next prime number to the list model. Both list uses the same list model.
	 */
	private void initGui() {
		Container cp = getContentPane();
		
		cp.setLayout(new BorderLayout());
		
		PrimListModel<Integer> primListModel = new PrimListModel<>();
		
		JPanel jPanel = new JPanel(new GridLayout(NUM_OF_ROWS, NUM_OF_COLUMNS));
		for(int i = 0; i < NUM_OF_ROWS * NUM_OF_COLUMNS; i++) {
			JList<Integer> list = new JList<>(primListModel);
			JScrollPane jScrollPane = new JScrollPane(list);
			
			jPanel.add(jScrollPane);
		}
				
		cp.add(jPanel, BorderLayout.CENTER);
		
		JButton next = new JButton("sljedeći");
		cp.add(next, BorderLayout.PAGE_END);
		
		next.addActionListener(e -> {
			primListModel.next();
		});
	}

	/**
	 * Main method from which program starts.
	 * 
	 * @param args arguments of the command line, unused
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new PrimDemo().setVisible(true);
		});
	}
}
