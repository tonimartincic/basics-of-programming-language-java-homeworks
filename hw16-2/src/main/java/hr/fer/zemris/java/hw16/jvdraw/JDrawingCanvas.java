package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JComponent;

import hr.fer.zemris.java.hw16.jvdraw.shapes.Circle;
import hr.fer.zemris.java.hw16.jvdraw.shapes.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.shapes.Line;

/**
 * Class extends {@link JComponent} and represent component which is canvas which contains instances
 * of the {@link GeometricalObject}.
 * 
 * @author Toni Martinčić 
 * @version 1.0
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener, ColorChangeListener {
	
	/**
	 * Universal version identifier for a {@link Serializable} class.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instance of {@link DrawingModel}.
	 */
	private DrawingModel drawingModel;
	
	/**
	 * {@link ButtonGroup}.
	 */
	private ButtonGroup buttonGroup;
	
	/**
	 * First point clicked on canvas while drawing the {@link GeometricalObject}.
	 */
	private Point firstPoint;
	
	/**
	 * Color of the lines.
	 */
	private Color lineColor;
	
	/**
	 * Area color.
	 */
	private Color areaColor;
	
	/**
	 * Map contains names of the objects as keys and the indices of the names as values.
	 */
	private Map<String, Integer> names = new HashMap<>();
	
	/**
	 * Instance of the {@link GeometricalObject} which is used for the object which is temporary painted on
	 * the canvas.
	 */
	private GeometricalObject tempObject;
	
	/**
	 * Flag which contain information are there any unsaved changes.
	 */
	private boolean hasChanges;
	
	/**
	 * Constructor which accepts four arguments; instance of the {@link DrawingModel}, {@link ButtonGroup},
	 * color of the lines and the area color.
	 * 
	 * @param drawingModel instance of {@link DrawingModel}
	 * @param buttonGroup {@link ButtonGroup}
	 * @param lineColor line color
	 * @param areaColor area color
	 */
	public JDrawingCanvas(DrawingModel drawingModel, ButtonGroup buttonGroup, Color lineColor, Color areaColor) {
		this.drawingModel = drawingModel;
		this.buttonGroup = buttonGroup;
		this.lineColor = lineColor;
		this.areaColor = areaColor;
		
		names.put("line", 1);
		names.put("circle", 1);
		names.put("filledCircle", 1);
		
		addMouseListeners();
	}
	
	/**
	 * Method adds mouse listeners.
	 */
	private void addMouseListeners() {
		this.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				ButtonModel selected = buttonGroup.getSelection();
				if(selected == null) {
					return;
				}
				
				if(firstPoint == null) {
					firstPoint = new Point(e.getX(), e.getY());
				} else {
					Graphics g = getGraphics();
					Graphics2D g2D = (Graphics2D) g;
					g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  RenderingHints.VALUE_ANTIALIAS_ON);
					g2D.setColor(lineColor);
					
					int mnemonic = selected.getMnemonic();
					
					if(mnemonic == 0) {
						g2D.drawLine(firstPoint.x, firstPoint.y, e.getX(), e.getY());
						
						String name = "line" + names.get("line");
						names.put("line", names.get("line") + 1);
						
						Line line = new Line(name, firstPoint, new Point(e.getX(), e.getY()), lineColor);
						drawingModel.add(line);
					} else {
						int a = firstPoint.x - e.getX();
						int b = firstPoint.y - e.getY();
						int r = (int) Math.round(Math.sqrt(a*a + b*b));
						
						int x = firstPoint.x - r;
						int y = firstPoint.y - r;
						
						g2D.drawOval(x, y, 2 * r, 2 * r);		
						
						if(mnemonic == 1) {
							String name = "circle" + names.get("circle");
							names.put("circle", names.get("circle") + 1);
							
							Circle circle = new Circle(name, firstPoint, r, lineColor);
							drawingModel.add(circle);
						}
						
						if(mnemonic == 2) {
							g2D.setColor(areaColor);
							g2D.fillOval(x + 1, y + 1, 2 * r - 1, 2 * r - 1);
							
							String name = "filledCircle" + names.get("filledCircle");
							names.put("filledCircle", names.get("filledCircle") + 1);
							
							FilledCircle filledCircle = new FilledCircle(name, firstPoint, r, lineColor, areaColor);
							drawingModel.add(filledCircle);
						}
					}
					
					firstPoint = null;
					tempObject = null;
					
					hasChanges = true;
				}		
			}
		});
		
		this.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				if(firstPoint == null) {
					return;
				}
				
				ButtonModel selected = buttonGroup.getSelection();
				if(selected == null) {
					return;
				}
					
				Graphics g = getGraphics();
				Graphics2D g2D = (Graphics2D) g;
				g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  RenderingHints.VALUE_ANTIALIAS_ON);
				g2D.setColor(lineColor);
				
				int mnemonic = selected.getMnemonic();
				if(mnemonic == 0) {
					tempObject = new Line("temp", firstPoint, new Point(e.getX(), e.getY()), lineColor);
				} else {
					int a = firstPoint.x - e.getX();
					int b = firstPoint.y - e.getY();
					int r = (int) Math.round(Math.sqrt(a*a + b*b));
					
					if(mnemonic == 1) {
						tempObject = new Circle("temp", firstPoint, r, lineColor);
					} else {
						tempObject = new FilledCircle("temp", firstPoint, r, lineColor, areaColor);
					}
				}
				
				repaint();
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);

		for(int i = 0, n = drawingModel.getSize(); i < n; i++) {
			drawingModel.getObject(i).paint(g);
		}
		
		if(tempObject != null) {
			tempObject.paint(g);
		}
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		hasChanges = true;
		
		repaint();
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		hasChanges = true;
		
		repaint();
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		hasChanges = true;
		
		repaint();
	}

	@Override
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
		if(source.getName().equals("foreground")) {
			lineColor = newColor;
		} else {
			areaColor = newColor;
		}
	}

	/**
	 * Method returns the hasChanges flag.
	 * 
	 * @return hasChanges flag
	 */
	public boolean hasChanges() {
		return hasChanges;
	}

	/**
	 * Method sets the hasChanges flag
	 * 
	 * @param hasChanges hasChanges flag
	 */
	public void setHasChanges(boolean hasChanges) {
		this.hasChanges = hasChanges;
	}
	
}
