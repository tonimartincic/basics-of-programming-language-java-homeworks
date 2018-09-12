package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Class represents bridge between window and {@link ILocalizationProvider}.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {
	
	/**
	 * Instance of {@link ILocalizationProvider}.
	 */
	private ILocalizationProvider parent;
	
	/**
	 * Instance of {@link ILocalizationListener}.
	 */
	private ILocalizationListener listener = new ILocalizationListener() {
		
		@Override
		public void localizationChanged() {
		}
	};

	/**
	 * Flag contains information is the class connected to the {@link ILocalizationProvider}.
	 */
	private boolean connected;
	
	/**
	 * Constructor which accepts one argument, instance of {@link ILocalizationProvider}.
	 * 
	 * @param parent instance of {@link ILocalizationProvider}
	 */
	public LocalizationProviderBridge(ILocalizationProvider parent) {
		super();
		
		this.parent = parent;
		connected = false;
	}
	
	/**
	 * Method connects class to the {@link ILocalizationProvider}.
	 */
	public void connect() {
		if(connected == true) {
			throw new IllegalStateException("Already connected.");
		}
		
		connected = true;
		
		parent.addLocalizationListener(listener);
	}
	
	/**
	 * Method disconnects class from the {@link ILocalizationProvider}
	 */
	public void disconnect() {
		connected = false;
		
		parent.removeLocalizationListener(listener);
	}

	@Override
	public String getString(String key) {
		return parent.getString(key);
	}
}
