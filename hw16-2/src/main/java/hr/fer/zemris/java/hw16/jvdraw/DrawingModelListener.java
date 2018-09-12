package hr.fer.zemris.java.hw16.jvdraw;

/**
 * Class which implements this interface represents listeners which is added to instance of the
 * {@link DrawingModel}.
 * 
 * @author Toni Martinčić 
 * @version 1.0
 */
public interface DrawingModelListener {

	/**
	 * This method is called when objects are added.
	 * 
	 * @param source instance of {@link DrawingModel}
	 * @param index0 first index 
	 * @param index1 last index
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);

	/**
	 * This method is called when objects are removed.
	 * 
	 * @param source instance of {@link DrawingModel}
	 * @param index0 first index 
	 * @param index1 last index
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);

	/**
	 * This method is called when objects are changed.
	 * 
	 * @param source instance of {@link DrawingModel}
	 *@param index0 first index 
	 * @param index1 last index
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);

}
