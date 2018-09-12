package hr.fer.zemris.java.hw16.jvdraw;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricalObject;

/**
 * Implementation of the {@link DrawingModel} which stores instances of the {@link GeometricalObject} in
 * the {@link List}.
 * 
 * @author Toni Martinčić 
 * @version 1.0
 */
public class DrawingModelImplementation implements DrawingModel {
	
	/**
	 * List which stores instances of the {@link GeometricalObject}.
	 */
	private List<GeometricalObject> geometricalObjects = new ArrayList<>();
	
	/**
	 * List which stores instances of the {@link DrawingModelListener}.
	 */
	private List<DrawingModelListener> drawingModelListeners = new ArrayList<>();

	@Override
	public int getSize() {
		return geometricalObjects.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		if(index < 0 || index > geometricalObjects.size() - 1) {
			throw new IllegalArgumentException("Invalid index.");
		}
		
		return geometricalObjects.get(index);
	}

	@Override
	public void add(GeometricalObject object) {
		if(object == null) {
			throw new IllegalArgumentException("Argument object can not be null.");
		}
		
		geometricalObjects.add(object);
	
		for(DrawingModelListener drawingModelListener : drawingModelListeners) {
			drawingModelListener.objectsAdded(this, drawingModelListeners.size() - 1, drawingModelListeners.size() - 1);
		}
	}

	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		if(drawingModelListeners.contains(l)) {
			throw new IllegalArgumentException("Model already contains given listener.");
		}
		
		drawingModelListeners.add(l);
	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		drawingModelListeners.remove(l);
	}

	@Override
	public void removeObject(GeometricalObject object) {
		int index = geometricalObjects.indexOf(object);
		
		geometricalObjects.remove(object);
		
		for(DrawingModelListener drawingModelListener : drawingModelListeners) {
			drawingModelListener.objectsRemoved(this, index, index);
		}	
	}

	@Override
	public void objectChanged(GeometricalObject object) {
		int index = geometricalObjects.indexOf(object);
		
		for(DrawingModelListener drawingModelListener : drawingModelListeners) {
			drawingModelListener.objectsChanged(this, index, index);
		}	
	}

	@Override
	public void clear() {
		if(geometricalObjects.size() == 0) {
			return;
		}
		
		for(DrawingModelListener drawingModelListener : drawingModelListeners) {
			drawingModelListener.objectsRemoved(this, 0, geometricalObjects.size() - 1);
		}	
		
		geometricalObjects.clear();
	}

}
