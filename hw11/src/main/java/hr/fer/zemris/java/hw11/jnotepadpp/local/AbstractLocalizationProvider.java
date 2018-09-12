package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents abstract {@link ILocalizationProvider}.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

	/**
	 * List of the {@link ILocalizationListener}.
	 */
	private static List<ILocalizationListener> iLocalizationListeners;
	
	/**
	 * Constructor which accepts no arguments.
	 */
	public AbstractLocalizationProvider() {
		super();
		iLocalizationListeners = new ArrayList<>();
	}
	
	@Override
	public void addLocalizationListener(ILocalizationListener iLocalizationListener) {
		if(iLocalizationListener == null) {
			throw new IllegalArgumentException("Argument iLocalizationListener can not be null");
		}
		
		iLocalizationListeners.add(iLocalizationListener);
	}
	
	@Override
	public void removeLocalizationListener(ILocalizationListener iLocalizationListener) {
		iLocalizationListeners.remove(iLocalizationListener);
	}
	
	/**
	 * Method on every {@link ILocalizationListener} calls method localizationChanged().
	 */
	public void fire() {
		for(ILocalizationListener iLocalizationListener : iLocalizationListeners) {
			iLocalizationListener.localizationChanged();
		}
	}
	
}