package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.io.Serializable;

import javax.swing.AbstractAction;

/**
 * Class extends {@link AbstractAction} and represent action which is localizable.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public abstract class LocalizableAction extends AbstractAction {

	/**
	 * Universal version identifier for a {@link Serializable} class.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Key for the name.
	 */
	private String nameKey;
	
	/**
	 * Key for the short description.
	 */
	private String shortDescriptionKey;
	
	/**
	 * Instance of {@link ILocalizationProvider}.
	 */
	private ILocalizationProvider lp;
	
	/**
	 * Constructor which accepts three arguments, nameKey, shortDescriptionKey and instance of
	 * {@link ILocalizationProvider}.
	 * 
	 * @param nameKey key for the name
	 * @param shortDescriptionKey key for the short description
	 * @param lp instance of {@link ILocalizationProvider}
	 */
	public LocalizableAction(String nameKey, String shortDescriptionKey, ILocalizationProvider lp) {
		super();
		
		this.nameKey = nameKey;
		this.shortDescriptionKey = shortDescriptionKey;
		this.lp = lp;
		
		putValues();
		
		lp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				putValues();
			}
			
		});
	}

	/**
	 * Method puts values for the name and the short description.
	 */
	private void putValues() {
		putValue(NAME, lp.getString(nameKey));
		putValue(SHORT_DESCRIPTION, lp.getString(shortDescriptionKey));
	}
}
