package hr.fer.zemris.java.hw16.jvdraw;

import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricalObject;

/**
 * Class which implements this interface stores instances of {@link GeometricalObject}.
 * 
 * @author Toni Martinčić 
 * @version 1.0
 */
public interface DrawingModel {

	/**
	 * Method returns number of objects stored.
	 * 
	 * @return number of objects stored
	 */
	public int getSize();

	/**
	 * Method returns object for the accepted index.
	 * 
	 * @param index accepted index
	 * @return object for the accepted index
	 */
	public GeometricalObject getObject(int index);

	/**
	 * Method adds accepted object.
	 * 
	 * @param object accepted object
	 */
	public void add(GeometricalObject object);

	/**
	 * Method adds accepted {@link DrawingModelListener}.
	 * 
	 * @param l accepted {@link DrawingModelListener}
	 */
	public void addDrawingModelListener(DrawingModelListener l);

	/**
	 * Method removes {@link DrawingModelListener}.
	 * 
	 * @param l {@link DrawingModelListener}
	 */
	public void removeDrawingModelListener(DrawingModelListener l);
	
	/**
	 * Method removes {@link GeometricalObject}.
	 * 
	 * @param object {@link GeometricalObject}
	 */
	public void removeObject(GeometricalObject object);
	
	/**
	 * This method is called when the {@link GeometricalObject} is changed.
	 * 
	 * @param object {@link GeometricalObject}
	 */
	public void objectChanged(GeometricalObject object);
	
	/**
	 * Method deletes all objects.
	 */
	public void clear();

}
