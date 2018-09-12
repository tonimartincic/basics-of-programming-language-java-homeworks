package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class is provider which provides the translations.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class LocalizationProvider extends AbstractLocalizationProvider {

	/**
	 * Instance of {@link ILocalizationProvider}.
	 */
	private static final LocalizationProvider instance = new LocalizationProvider();
	
	/**
	 * Current language.
	 */
	private static String language;
	
	/**
	 * Instance of {@link ResourceBundle}
	 */
	private static ResourceBundle bundle;
	
	/**
	 * Private constructor which accepts no arguments.
	 */
	private LocalizationProvider() {
		super();
		
		LocalizationProvider.language = "en";
		
		setLanguage(language);
	}
	
	/**
	 * Getter for the instance of {@link LocalizationProvider}.
	 * 
	 * @return instance of {@link LocalizationProvider}
	 */
	public static LocalizationProvider getInstance() {
		return instance;
	}
	
	@Override
	public String getString(String key)  {
		String str = bundle.getString(key); 
	
		try {
			str =  new String(str.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException exc) {
			
		}
		
		return str;
	}

	/**
	 * Setter for the language.
	 * 
	 * @param language accepted language
	 */
	public void setLanguage(String language) {
		LocalizationProvider.language = language;
		
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.translations", locale);
		
		fire();
	}
}
