package hr.fer.zemris.java.gui.layouts.demo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Class demonstrates functionality of the {@link CalcLayout}. It creates new frame and sets instance of
 * {@link CalcLayout} as layout manager.
 * It adds several components to the frame.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class DemoCalcLayout extends JFrame {
	
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
	 * Universal version identifier for a Serializable class.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor which accepts no arguments. It sets DefaultCloseOperation, Location and the Size of the window 
	 * and calls method iniGUI.
	 */
	public DemoCalcLayout() {
		super();
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		setLocation(X_LOCATION_OF_THE_WINDOW, Y_LOCATION_OF_THE_WINDOW);
		setSize(WIDTH_OF_THE_WINDOW, HEIGHT_OF_THE_WINDOW);
		
		initGUI();
	}

	/**
	 * Method adds several components to the frame.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		JPanel p = new JPanel(new CalcLayout(3));
		
		JLabel label1 = new JLabel("x");
		label1.setBorder(new LineBorder(Color.BLACK));
		p.add(label1, new RCPosition(1,1));
		
		JLabel label2 = new JLabel("y");
		label2.setBorder(new LineBorder(Color.BLACK));
		p.add(label2, new RCPosition(2,3));
		
		JLabel label3 = new JLabel("z");
		label3.setBorder(new LineBorder(Color.BLACK));
		p.add(label3, new RCPosition(2,7));
		
		JLabel label4 = new JLabel("w");
		label4.setBorder(new LineBorder(Color.BLACK));
		p.add(label4, new RCPosition(4,2));
		
		JLabel label5 = new JLabel("a");
		label5.setBorder(new LineBorder(Color.BLACK));
		p.add(label5, new RCPosition(4,5));
		
		JLabel label6 = new JLabel("b");
		label6.setBorder(new LineBorder(Color.BLACK));
		p.add(label6, new RCPosition(4,7));
		
		cp.add(p, BorderLayout.CENTER);
	}

	/**
	 * Main method from which program starts.
	 * 
	 * @param args arguments of the command line, unused
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new DemoCalcLayout().setVisible(true);
		});
	}

}
