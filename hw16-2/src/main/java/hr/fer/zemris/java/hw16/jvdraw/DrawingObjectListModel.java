package hr.fer.zemris.java.hw16.jvdraw;

import java.io.Serializable;

import javax.swing.AbstractListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricalObject;

/**
 * Class extends {@link AbstractListModel} and represents list model which stores instances of the
 * {@link GeometricalObject}.
 * 
 * @author Toni Martinčić 
 * @version 1.0
 */
public class DrawingObjectListModel extends AbstractListModel<GeometricalObject> implements DrawingModelListener {

	/**
	 * Universal version identifier for a {@link Serializable} class.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instance of {@link DrawingModel}.
	 */
	private DrawingModel drawingModel;
	
	/**
	 * Constructor which accepts only one argument, instance of {@link DrawingModel}.
	 * 
	 * @param drawingModel instance of {@link DrawingModel}
	 */
	public DrawingObjectListModel(DrawingModel drawingModel) {
		if(drawingModel == null) {
			throw new IllegalArgumentException("Argument drawingModel can not be null.");
		}
		
		this.drawingModel = drawingModel;
	}

	@Override
	public int getSize() {
		return drawingModel.getSize();
	}

	@Override
	public GeometricalObject getElementAt(int index) {
		return drawingModel.getObject(index);
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		for(ListDataListener listDataListener : getListDataListeners()) {
			listDataListener.intervalAdded(new ListDataEvent(source, ListDataEvent.INTERVAL_ADDED, index0, index1));
		}
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		for(ListDataListener listDataListener : getListDataListeners()) {
			listDataListener.intervalRemoved(new ListDataEvent(source, ListDataEvent.INTERVAL_REMOVED, index0, index1));
		}
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		for(ListDataListener listDataListener : getListDataListeners()) {
			listDataListener.contentsChanged(new ListDataEvent(source, ListDataEvent.CONTENTS_CHANGED, index0, index1));
		}
	}

}
