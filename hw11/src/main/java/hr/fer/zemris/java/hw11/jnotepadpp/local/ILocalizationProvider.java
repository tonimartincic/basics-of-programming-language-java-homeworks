package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Class which implements this interface is provider which provides the translations.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public interface ILocalizationProvider {
	
	/**
	 * Method adds instance of {@link ILocalizationListener}.
	 * 
	 * @param iLocalizationListener accepted instance of {@link ILocalizationListener}
	 */
	public abstract void addLocalizationListener(ILocalizationListener iLocalizationListener);
	
	/**
	 * Method removes instance of {@link ILocalizationListener}.
	 * 
	 * @param iLocalizationListener accepted instance of {@link ILocalizationListener}
	 */
	public abstract void removeLocalizationListener(ILocalizationListener iLocalizationListener);
	
	/**
	 * Method gets string for the accepted key.
	 * 
	 * @param key key
	 * @return string for the accepted key
	 */
	public abstract String getString(String key);

}
