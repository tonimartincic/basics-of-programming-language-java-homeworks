package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.hw16.jvdraw.shapes.Circle;
import hr.fer.zemris.java.hw16.jvdraw.shapes.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.shapes.Line;

/**
 * Class represents desktop application which contains one {@link JFrame}. {@link JFrame} contains
 * instance of {@link JDrawingCanvas} which contains instances of the {@link GeometricalObject}. User
 * can add new {@link GeometricalObject} to the canvas.
 * 
 * Application supports three types of {@link GeometricalObject}: {@link Line}, {@link Color} and
 * {@link FilledCircle}.
 * 
 * @author Toni Martinčić 
 * @version 1.0
 */
public class JVDraw extends JFrame {
	
	/**
	 * Universal version identifier for a {@link Serializable} class.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Component x of the position of the window.
	 */
	private static final int X_POSITION_OF_THE_WINDOW = 50;
	
	/**
	 * Component y of the position of the window.
	 */
	private static final int Y_POSITION_OF_THE_WINDOW = 50;
	
	/**
	 * Width of the window.
	 */
	private static final int WIDTH_OF_THE_WINDOW = 1000;
	
	/**
	 * Height of the window.
	 */
	private static final int HEIGHT_OF_THE_WINDOW = 500;

	/**
	 * Default foreground color.
	 */
	private static final Color DEFAULT_FOREGROUND_COLOR = Color.RED;

	/**
	 * Default background color.
	 */
	private static final Color DEFAULT_BACKGROUND_COLOR = Color.BLUE;

	/**
	 * Border thickness.
	 */
	private static final int BORDER_THICKNESS = 3;

	/**
	 * Border color.
	 */
	private static final Color BORDER_COLOR = Color.BLACK;
	
	/**
	 * Open icon.
	 */
	private final Icon openIcon = getIcon("open.png");
	
	/**
	 * Save icon.
	 */
	private final Icon saveIcon = getIcon("save.png");
	
	/**
	 * Export icon.
	 */
	private final Icon exportIcon = getIcon("export.png");
	
	/**
	 * Exit icon.
	 */
	private final Icon exitIcon = getIcon("exit.png");
	
	/**
	 * Line icon.
	 */
	private final Icon lineIcon = getIcon("line.png");
	
	/**
	 * Circle icon.
	 */
	private final Icon circleIcon = getIcon("circle.png");
	
	/**
	 * Filled circle icon.
	 */
	private final Icon filledCircleIcon = getIcon("filledCircle.png");
	
	/**
	 * Instance of the {@link JColorArea} which provides foreground color.
	 */
	private JColorArea fgColorArea;
	
	/**
	 * Instance of the {@link JColorArea} which provides background color.
	 */
	private JColorArea bgColorArea;
	
	/**
	 * Instance of the {@link ButtonGroup}.
	 */
	private ButtonGroup buttonGroup;
	
	/**
	 * Line toggle button.
	 */
	private JToggleButton lineToggleButton;
	
	/**
	 * Circle toggle button.
	 */
	private JToggleButton circleToggleButton;
	
	/**
	 * Filled circle toggle button.
	 */
	private JToggleButton filledCircleToggleButton;
	
	/**
	 * Instance of {@link DrawingModelImplementation}.
	 */
	private DrawingModelImplementation drawingModelImplementation;
	
	/**
	 * Instance of {@link DrawingObjectListModel}.
	 */
	private DrawingObjectListModel drawingObjectListModel;
	
	/**
	 * Instance of {@link JDrawingCanvas}.
	 */
	private JDrawingCanvas jDrawingCanvas;
	
	/**
	 * Current file path.
	 */
	private Path currentFilePath;
	
	/**
	 * Method gets {@link Icon} for accepted name of the icon.
	 * 
	 * @param name name of the icon
	 * @return icon
	 */
	private Icon getIcon(String name) {
		Path path = Paths.get("icons/", name);
		byte[] bytes = null;
		
		try(InputStream is = this.getClass().getResourceAsStream(path.toString())) {
			bytes = new byte[is.available()];
			is.read(bytes, 0, is.available());
		} catch(IOException exc) {
			JOptionPane.showMessageDialog(
					JVDraw.this,
					"errorWhileReadingFromFile "+ path + ".",
					"error",
					JOptionPane.ERROR_MESSAGE);
			
			return null;
		}
	
		return new ImageIcon(bytes);
	}
	
	/**
	 * Constructor which accepts no arguments.
	 */
	public JVDraw() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocation(X_POSITION_OF_THE_WINDOW, Y_POSITION_OF_THE_WINDOW);
		setSize(WIDTH_OF_THE_WINDOW, HEIGHT_OF_THE_WINDOW);
		setTitle("JVDraw");
		
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				askUserForClosingTheApplication();
			};
			
		});
		
		initGUI();
	}
	
	/**
	 * Method asks user if he wants to close the application.
	 */
	private void askUserForClosingTheApplication() {
		if(jDrawingCanvas.hasChanges()) {
			int pressed = JOptionPane.showConfirmDialog(
					JVDraw.this,
					"Dou you want to save current file?",
					"Saving",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.WARNING_MESSAGE);
			
			if(pressed == JOptionPane.CANCEL_OPTION) {
				return;
			} else if(pressed == JOptionPane.YES_OPTION) {
				saveAction.actionPerformed(null);
			}
		}
		
		int pressed = JOptionPane.showConfirmDialog(
				JVDraw.this,
				"Do you want to close the application?",
				"Closing the application",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE);
		
		if(pressed == JOptionPane.NO_OPTION) {
			return;
		}
		
		dispose();
	}

	/**
	 * Method initialize the GUI.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		drawingModelImplementation = new DrawingModelImplementation();
		drawingObjectListModel = new DrawingObjectListModel(drawingModelImplementation);
		drawingModelImplementation.addDrawingModelListener(drawingObjectListModel);
		
		createFileActions();
		createMenu();
		createToolbar();
		createJColorInfo();
		createCanvas();
		createList();	
	}

	/**
	 * Method creates the file actions.
	 */
	private void createFileActions() {
		openAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		openAction.putValue(Action.SMALL_ICON, openIcon);
		openAction.putValue(Action.NAME, "Open");
		openAction.putValue(Action.SHORT_DESCRIPTION, "Opens a text file with extension *.jvd");

		saveAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveAction.putValue(Action.SMALL_ICON, saveIcon);
		saveAction.putValue(Action.NAME, "Save");
		saveAction.putValue(Action.SHORT_DESCRIPTION, "Saves as a text file with extension *.jvd");
	
		saveAsAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt S"));
		saveAsAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveAsAction.putValue(Action.SMALL_ICON, saveIcon);
		saveAsAction.putValue(Action.NAME, "Save as");
		saveAsAction.putValue(Action.SHORT_DESCRIPTION, "Saves as a text file with extension *.jvd");
		
		exportAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt S"));
		exportAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		exportAction.putValue(Action.SMALL_ICON, exportIcon);
		exportAction.putValue(Action.NAME, "Export");
		exportAction.putValue(Action.SHORT_DESCRIPTION, "Exports the image");
		
		exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control L"));
		exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
		exitAction.putValue(Action.SMALL_ICON, exitIcon);
		exitAction.putValue(Action.NAME, "Exit");
		exitAction.putValue(Action.SHORT_DESCRIPTION, "Exits the application");
	}

	/**
	 * Method creates the menu.
	 */
	private void createMenu() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		
		fileMenu.add(new JMenuItem(openAction));
		
		fileMenu.addSeparator();
		
		fileMenu.add(new JMenuItem(saveAction));
		fileMenu.add(new JMenuItem(saveAsAction));
		
		fileMenu.addSeparator();
		
		fileMenu.add(new JMenuItem(exportAction));
		
		fileMenu.addSeparator();
		
		fileMenu.add(new JMenuItem(exitAction));
		
		menuBar.add(fileMenu);
		setJMenuBar(menuBar);
	}

	/**
	 * Method creates the toolbar.
	 */
	private void createToolbar() {
		JToolBar jToolBar = new JToolBar("Toolbar");
		getContentPane().add(jToolBar, BorderLayout.PAGE_START);
		
		addJColorAreas(jToolBar);
		
		jToolBar.addSeparator();
		
		addButtons(jToolBar);
	}

	/**
	 * Method adds instances of the {@link JColorArea} to the toolbar.
	 * 
	 * @param jToolBar toolbar
	 */
	private void addJColorAreas(JToolBar jToolBar) {
		jToolBar.add(new JLabel("Foreground color:  "));
		fgColorArea = new JColorArea("foreground", DEFAULT_FOREGROUND_COLOR);
		jToolBar.add(fgColorArea);
		
		jToolBar.addSeparator();
		
		jToolBar.add(new JLabel("Background color:  "));
		bgColorArea = new JColorArea("background", DEFAULT_BACKGROUND_COLOR);
		jToolBar.add(bgColorArea);
	}
	
	/**
	 * Method adds buttons to the toolbar.
	 * 
	 * @param jToolBar toolbar
	 */ 
	private void addButtons(JToolBar jToolBar) {
		jToolBar.add(new JLabel("   Shapes:  "));
		
		buttonGroup = new ButtonGroup();
		
		lineToggleButton = new JToggleButton("Line");
		lineToggleButton.setIcon(lineIcon);
		lineToggleButton.setMnemonic(0);
		buttonGroup.add(lineToggleButton);
		jToolBar.add(lineToggleButton);
		
		circleToggleButton = new JToggleButton("Circle");
		circleToggleButton.setIcon(circleIcon);
		circleToggleButton.setMnemonic(1);
		buttonGroup.add(circleToggleButton);
		jToolBar.add(circleToggleButton);
		
		filledCircleToggleButton = new JToggleButton("Filled Circle");
		filledCircleToggleButton.setIcon(filledCircleIcon);
		filledCircleToggleButton.setMnemonic(2);
		buttonGroup.add(filledCircleToggleButton);
		jToolBar.add(filledCircleToggleButton);
	}

	/**
	 * Method creates instance of {@link JColorInfo}.
	 */
	private void createJColorInfo() {
		JColorInfo jColorInfo = new JColorInfo(fgColorArea, bgColorArea);
		jColorInfo.setHorizontalAlignment(SwingConstants.CENTER);
		
		fgColorArea.addColorChangeListener(jColorInfo);
		bgColorArea.addColorChangeListener(jColorInfo);
		
		getContentPane().add(jColorInfo, BorderLayout.PAGE_END);
	}
	
	/**
	 * Method creates canvas.
	 */
	private void createCanvas() {
		jDrawingCanvas = new JDrawingCanvas(
				drawingModelImplementation,
				buttonGroup,
				DEFAULT_FOREGROUND_COLOR,
				DEFAULT_BACKGROUND_COLOR);
		
		jDrawingCanvas.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, BORDER_THICKNESS));
		
		getContentPane().add(jDrawingCanvas, BorderLayout.CENTER);
		
		fgColorArea.addColorChangeListener(jDrawingCanvas);
		bgColorArea.addColorChangeListener(jDrawingCanvas);
		
		drawingModelImplementation.addDrawingModelListener(jDrawingCanvas);
	}

	/**
	 * Method creates list.
	 */
	private void createList() {
		JPanel jPanel = new JPanel(new BorderLayout());
		jPanel.add(new JLabel("    Drawn shapes:    "), BorderLayout.PAGE_START);
		 
		JList<GeometricalObject> jList = new JList<>(drawingObjectListModel);
		jList.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() != 2) {
					return;
				}
				
				JPanel messagePanel = new JPanel();
				
				int index = ((JList<?>)e.getComponent()).getSelectedIndex();
				GeometricalObject geometricalObject = drawingObjectListModel.getElementAt(index);
				
				if(geometricalObject instanceof Line) {
					changeLine(messagePanel, geometricalObject); 
				} else {
					if(geometricalObject instanceof FilledCircle) {
						messagePanel.setLayout(new GridLayout(8, 3));
					} else {
						messagePanel.setLayout(new GridLayout(6, 3));
					}
					
					changeCircle(messagePanel, geometricalObject);
				}
			}

			private void changeLine(JPanel messagePanel, GeometricalObject geometricalObject) {
				Line line = (Line) geometricalObject;
				
				messagePanel.setLayout(new GridLayout(6, 3));
				
				messagePanel.add(new JLabel("Start coordinate: "));
				messagePanel.add(new JLabel(""));
				messagePanel.add(new JLabel(""));
				
				JTextField xStart = new JTextField(String.valueOf(line.getStart().x));
				messagePanel.add(xStart);
				JTextField yStart = new JTextField(String.valueOf(line.getStart().y));
				messagePanel.add(yStart);
				messagePanel.add(new JLabel(""));
				
				messagePanel.add(new JLabel("End coordinate: "));
				messagePanel.add(new JLabel(""));
				messagePanel.add(new JLabel(""));
				
				JTextField xEnd = new JTextField(String.valueOf(line.getEnd().x));
				messagePanel.add(xEnd);
				JTextField yEnd = new JTextField(String.valueOf(line.getEnd().y));
				messagePanel.add(yEnd);
				messagePanel.add(new JLabel(""));
				
				messagePanel.add(new JLabel("Line color: "));
				messagePanel.add(new JLabel(""));
				messagePanel.add(new JLabel(""));
				
				JTextField r = new JTextField(String.valueOf(line.getLineColor().getRed()));
				messagePanel.add(r);
				JTextField g = new JTextField(String.valueOf(line.getLineColor().getGreen()));
				messagePanel.add(g);
				JTextField b = new JTextField(String.valueOf(line.getLineColor().getBlue()));
				messagePanel.add(b);
				
				int optionSelected = JOptionPane.showConfirmDialog(
						JVDraw.this,
						messagePanel, 
						geometricalObject.getName(),
						JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				
				if(optionSelected == JOptionPane.OK_OPTION) {
					try {
						String name = line.getName();
	
						Point start = new Point(Integer.parseInt(xStart.getText()), Integer.parseInt(yStart.getText()));
						Point end = new Point(Integer.parseInt(xEnd.getText()), Integer.parseInt(yEnd.getText()));
						
						Color lineColor = new Color(Integer.parseInt(r.getText()), Integer.parseInt(g.getText()), Integer.parseInt(b.getText()));
						
						line.setName(name);
						line.setStart(start);
						line.setEnd(end);
						line.setLineColor(lineColor);
						
						drawingModelImplementation.objectChanged(line);
					} catch(Exception exc) {
						JOptionPane.showMessageDialog(JVDraw.this, "Invalid arguments");
					}
				}
			}
			
			private void changeCircle(JPanel messagePanel, GeometricalObject geometricalObject) {
				Circle circle = (Circle) geometricalObject;
				
				messagePanel.add(new JLabel("Center: "));
				messagePanel.add(new JLabel(""));
				messagePanel.add(new JLabel(""));
				
				JTextField xCenter = new JTextField(String.valueOf(circle.getCenter().x));
				messagePanel.add(xCenter);
				JTextField yCenter = new JTextField(String.valueOf(circle.getCenter().y));
				messagePanel.add(yCenter);
				messagePanel.add(new JLabel(""));
				
				messagePanel.add(new JLabel("Radius: "));
				messagePanel.add(new JLabel(""));
				messagePanel.add(new JLabel(""));
				
				JTextField radius = new JTextField(String.valueOf(circle.getRadius()));
				messagePanel.add(radius);
				messagePanel.add(new JLabel(""));
				messagePanel.add(new JLabel(""));
				
				messagePanel.add(new JLabel("Line color: "));
				messagePanel.add(new JLabel(""));
				messagePanel.add(new JLabel(""));
				
				JTextField r = new JTextField(String.valueOf(circle.getLineColor().getRed()));
				messagePanel.add(r);
				JTextField g = new JTextField(String.valueOf(circle.getLineColor().getGreen()));
				messagePanel.add(g);
				JTextField b = new JTextField(String.valueOf(circle.getLineColor().getBlue()));
				messagePanel.add(b);
				
				FilledCircle filledCircle = null;
				JTextField ra = null;
				JTextField ga = null;
				JTextField ba = null;
				if(geometricalObject instanceof FilledCircle) {
					filledCircle = (FilledCircle) circle;
					
					messagePanel.add(new JLabel("Area color: "));
					messagePanel.add(new JLabel(""));
					messagePanel.add(new JLabel(""));
					
					ra = new JTextField(String.valueOf(filledCircle.getAreaColor().getRed()));
					messagePanel.add(ra);
					ga = new JTextField(String.valueOf(filledCircle.getAreaColor().getGreen()));
					messagePanel.add(ga);
					ba = new JTextField(String.valueOf(filledCircle.getAreaColor().getBlue()));
					messagePanel.add(ba);
				}
				
				int optionSelected = JOptionPane.showConfirmDialog(
						JVDraw.this,
						messagePanel, 
						geometricalObject.getName(),
						JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				
				if(optionSelected == JOptionPane.OK_OPTION) {
					try {					
						String name = circle.getName();
	
						Point center = new Point(Integer.parseInt(xCenter.getText()), Integer.parseInt(yCenter.getText()));
						int rad = Integer.parseInt(radius.getText());
						Color lineColor = new Color(Integer.parseInt(r.getText()), Integer.parseInt(g.getText()), Integer.parseInt(b.getText()));
						
						circle.setName(name);
						circle.setCenter(center);
						circle.setRadius(rad);
						circle.setLineColor(lineColor);
						
						drawingModelImplementation.objectChanged(circle);
						
						if(filledCircle != null) {
							Color areaColor = new Color(Integer.parseInt(ra.getText()), Integer.parseInt(ga.getText()), Integer.parseInt(ba.getText()));
							filledCircle.setAreaColor(areaColor);
							
							drawingModelImplementation.objectChanged(filledCircle);
						} 
					} catch(Exception exc) {
						JOptionPane.showMessageDialog(JVDraw.this, "Invalid arguments");
					}
				}
			}
			
		});
		
		jList.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(jList.getSelectedIndex() == -1) {
					return;
				}
				
				if(e.getKeyCode() == KeyEvent.VK_DELETE) {
					drawingModelImplementation.removeObject(jList.getSelectedValue());
				}
			}
			
		});
		
		JScrollPane jScrollPane = new JScrollPane(jList);
		jScrollPane.setPreferredSize(new Dimension((int) Math.round(getWidth() * 0.20), getHeight()));
		jPanel.add(jScrollPane, BorderLayout.CENTER);
		
		getContentPane().add(jPanel, BorderLayout.LINE_END);	
	}
	
	/**
	 * Instance of {@link AbstractAction} which opens the file.
	 */
	private final Action openAction = new AbstractAction() {
		
		/**
		 * Universal version identifier for a {@link Serializable} class.
		 */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Open file");
			
			if(fc.showOpenDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			
			File fileName = fc.getSelectedFile();
			Path filePath = fileName.toPath();
			
			if(!filePath.toString().endsWith(".jvd")) {
				JOptionPane.showMessageDialog(
						JVDraw.this,
						"File must have '.jvd' extension",
						"Error",
						JOptionPane.ERROR_MESSAGE);
				
				return;
			}
			
			currentFilePath = filePath;
			
			List<String> allLines;
			
			try {
				allLines = Files.readAllLines(filePath);
			} catch(IOException exc) {
				JOptionPane.showMessageDialog(
						JVDraw.this,
						"Error while reading from file",
						"Error",
						JOptionPane.ERROR_MESSAGE);
				
				return;
			}
			
			drawingModelImplementation.clear();
			
			Map<String, Integer> names = new HashMap<>();
			names.put("line", 1);
			names.put("circle", 1);
			names.put("filledCircle", 1);
			
			for(String line : allLines) {
				String[] parts = line.split("\\s+");
				
				if(parts[0].equals("LINE")) {
					int x0 = Integer.parseInt(parts[1]);
					int y0 = Integer.parseInt(parts[2]);
					int x1 = Integer.parseInt(parts[3]);
					int y1 = Integer.parseInt(parts[4]);
					
					int red = Integer.parseInt(parts[5]);
					int green = Integer.parseInt(parts[6]);
					int blue = Integer.parseInt(parts[7]);
					Color lineColor = new Color(red, green, blue);
					
					String name = "line" + names.get("line");
					names.put("line", names.get("line") + 1);
					
					Line newLine = new Line(name, new Point(x0, y0), new Point(x1, y1), lineColor);
					drawingModelImplementation.add(newLine);
				} else {
					int centerX = Integer.parseInt(parts[1]);
					int centerY = Integer.parseInt(parts[2]);
					
					int radius = Integer.parseInt(parts[3]);
					
					int red = Integer.parseInt(parts[4]);
					int green = Integer.parseInt(parts[5]);
					int blue = Integer.parseInt(parts[6]);
					Color lineColor = new Color(red, green, blue);
					
					if(parts[0].equals("CIRCLE")) {
						String name = "circle" + names.get("circle");
						names.put("circle", names.get("circle") + 1);
						
						Circle newCircle = new Circle(name, new Point(centerX, centerY), radius, lineColor);
						drawingModelImplementation.add(newCircle);
					} else {
						int reda = Integer.parseInt(parts[7]);
						int greena = Integer.parseInt(parts[8]);
						int bluea = Integer.parseInt(parts[9]);
						Color areaColor = new Color(reda, greena, bluea);
						
						String name = "filledCircle" + names.get("filledCircle");
						names.put("filledCircle", names.get("filledCircle") + 1);
						
						FilledCircle newFilledCircle = new FilledCircle(name, new Point(centerX, centerY), radius, lineColor, areaColor);
						drawingModelImplementation.add(newFilledCircle);
					}
				}
			}
		}
	};
	
	/**
	 * Instance of {@link AbstractAction} which saves the file.
	 */
	private final Action saveAction = new AbstractAction() {
		
		/**
		 * Universal version identifier for a {@link Serializable} class.
		 */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(currentFilePath == null) {
				currentFilePath = getCurrentFilePath();
			}
			
			if(currentFilePath != null) {
				saveFile();
			}
		}
	};
	
	/**
	 * Instance of {@link AbstractAction} which saves file as.
	 */
	private final Action saveAsAction = new AbstractAction() {
		
		/**
		 * Universal version identifier for a {@link Serializable} class.
		 */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			currentFilePath = getCurrentFilePath();
			
			if(currentFilePath != null) {
				saveFile();
			}
		}
	};
	
	/**
	 * Method gets the current file path.
	 * 
	 * @return current file path
	 */
	private Path getCurrentFilePath() {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Save file");
		
		if(fc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(
					JVDraw.this,
					"Nothing is saved",
					"Information",
					JOptionPane.INFORMATION_MESSAGE);
			
			return currentFilePath;
		}
		
		if(Files.exists(fc.getSelectedFile().toPath())) {
			int pressed = JOptionPane.showConfirmDialog(
					JVDraw.this,
					"File exists",
					"Overwriting the file",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE);
			
			if(pressed == JOptionPane.NO_OPTION) {
				return currentFilePath;
			}
		}
		
		return fc.getSelectedFile().toPath();
	}
	
	/**
	 * Method saves the file.
	 */
	private void saveFile() {
		try {
			StringBuilder sb = new StringBuilder();
			for(int i = 0, n = drawingModelImplementation.getSize(); i < n; i++) {
				GeometricalObject geometricalObject = drawingModelImplementation.getObject(i);
				
				if(geometricalObject instanceof Line) {
					Line line = (Line) geometricalObject;
					
					sb.append("LINE ");
					
					sb.append(String.valueOf(line.getStart().x)).append(" ");
					sb.append(String.valueOf(line.getStart().y)).append(" ");
					sb.append(String.valueOf(line.getEnd().x)).append(" ");
					sb.append(String.valueOf(line.getEnd().y)).append(" ");
					
					sb.append(String.valueOf(line.getLineColor().getRed())).append(" ");
					sb.append(String.valueOf(line.getLineColor().getGreen())).append(" ");
					sb.append(String.valueOf(line.getLineColor().getBlue()));
					
					sb.append("\n");
				} else {
					Circle circle = (Circle) geometricalObject;
					
					if(geometricalObject instanceof FilledCircle) {
						sb.append("FCIRCLE ");
					} else {
						sb.append("CIRCLE ");
					}
					
					sb.append(String.valueOf(circle.getCenter().x)).append(" ");
					sb.append(String.valueOf(circle.getCenter().y)).append(" ");
					
					sb.append(String.valueOf(circle.getRadius())).append(" ");
					
					sb.append(String.valueOf(circle.getLineColor().getRed())).append(" ");
					sb.append(String.valueOf(circle.getLineColor().getGreen())).append(" ");
					sb.append(String.valueOf(circle.getLineColor().getBlue()));
					
					if(geometricalObject instanceof FilledCircle) {
						FilledCircle filledCircle = (FilledCircle) circle;
						
						sb.append(" ");
						
						sb.append(String.valueOf(filledCircle.getAreaColor().getRed())).append(" ");
						sb.append(String.valueOf(filledCircle.getAreaColor().getGreen())).append(" ");
						sb.append(String.valueOf(filledCircle.getAreaColor().getBlue()));
					}
					
					sb.append("\n");
				}
			}
			
			Files.write(currentFilePath, sb.toString().getBytes());
			
			jDrawingCanvas.setHasChanges(false);
		} catch(Exception exc) {
			exc.printStackTrace();
			JOptionPane.showMessageDialog(
					JVDraw.this,
					"Saving did not succed",
					"Error",
					JOptionPane.ERROR_MESSAGE);
			
			return;
		}
		
		JOptionPane.showMessageDialog(
				JVDraw.this,
				"File is saved",
				"Information",
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Instance of {@link AbstractAction} which exports the picture.
	 */
	private final Action exportAction = new AbstractAction() {
		
		/**
		 * Universal version identifier for a {@link Serializable} class.
		 */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int topLeftX = jDrawingCanvas.getWidth() - 1;
			int topLeftY = jDrawingCanvas.getHeight() - 1;
			int bottomRightX = 0;
			int bottomRightY = 0;
			
			for(int i = 0, n = drawingModelImplementation.getSize(); i < n; i++) {
				GeometricalObject geometricalObject = drawingModelImplementation.getObject(i);
				
				if(geometricalObject instanceof Line) {
					Line line = (Line) geometricalObject;
					
					topLeftX = line.getStart().x < topLeftX ? line.getStart().x : topLeftX;
					topLeftX = line.getEnd().x < topLeftX ? line.getEnd().x : topLeftX;
					topLeftY = line.getStart().y < topLeftY ? line.getStart().y : topLeftY;
					topLeftY = line.getEnd().y < topLeftY ? line.getEnd().y : topLeftY;
					
					bottomRightX = line.getStart().x > bottomRightX ? line.getStart().x : bottomRightX;
					bottomRightX = line.getEnd().x > bottomRightX ? line.getEnd().x : bottomRightX;
					bottomRightY = line.getStart().y > bottomRightY ? line.getStart().y : bottomRightY;
					bottomRightY = line.getEnd().y > bottomRightY ? line.getEnd().y : bottomRightY;
				} else {
					Circle circle = (Circle) geometricalObject;
					
					int circleTopLeftX = circle.getCenter().x - circle.getRadius();
					int circleTopLeftY = circle.getCenter().y - circle.getRadius();
					int circleBottomRightX = circle.getCenter().x + circle.getRadius();
					int circleBottomRightY = circle.getCenter().y + circle.getRadius(); 
					
					topLeftX = circleTopLeftX < topLeftX ? circleTopLeftX : topLeftX;
					topLeftY = circleTopLeftY < topLeftY ? circleTopLeftY : topLeftY;
					
					bottomRightX = circleBottomRightX > bottomRightX ? circleBottomRightX : bottomRightX;
					bottomRightY = circleBottomRightY > bottomRightY ? circleBottomRightY : bottomRightY;
				}
			}
			
			int boxWidth = bottomRightX - topLeftX + 1;
			int boxHeight = bottomRightY - topLeftY + 1;
			
			int shiftX = -1 * topLeftX;
			int shiftY = -1 * topLeftY;
			
			BufferedImage image = new BufferedImage(boxWidth, boxHeight, BufferedImage.TYPE_3BYTE_BGR);
			
			Graphics2D g = image.createGraphics();
			
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, boxWidth - 1, boxHeight - 1);
			
			for(int i = 0, n = drawingModelImplementation.getSize(); i < n; i++) {
				drawingModelImplementation.getObject(i).paintShifted(g, shiftX, shiftY);
			}
			
			g.dispose();
			
			File file = null;
			while(true) {
				file = getFile();
				
				if(file == null) {
					return;
				}
				
				String s = file.toString();
				if(s.endsWith(".jpg") || s.endsWith(".png") || s.endsWith(".gif")) {
					break;
				}
				
				JOptionPane.showMessageDialog(
						JVDraw.this,
						"Nothing is exported. File extension must be jpg, png or gif. Choose another file.",
						"Information",
						JOptionPane.WARNING_MESSAGE);
			}
		 
			String ext = file.toString().substring(file.toString().indexOf('.') + 1);
	
			try {
				ImageIO.write(image, ext, file);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(
						JVDraw.this,
						"Error while exporting the file.",
						"Error",
						JOptionPane.ERROR_MESSAGE);
				
				return;
			}
			
			JOptionPane.showMessageDialog(
					JVDraw.this,
					"File is exported.",
					"Information",
					JOptionPane.INFORMATION_MESSAGE);
		}

		private File getFile() {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Export file");
			
			if(fc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(
						JVDraw.this,
						"Nothing is exported",
						"Information",
						JOptionPane.INFORMATION_MESSAGE);
				
				return null;
			}
			
			return fc.getSelectedFile();
		}
	};
	
	/**
	 * Instance of {@link AbstractAction} which exits the application.
	 */
	private final Action exitAction = new AbstractAction() {
		
		/**
		 * Universal version identifier for a {@link Serializable} class.
		 */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			askUserForClosingTheApplication();
		}
	};
	
	/**
	 * Main method from which execution of the program starts.
	 * 
	 * @param args arguments of the command line, unused
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JVDraw().setVisible(true);
		});

	}

}
