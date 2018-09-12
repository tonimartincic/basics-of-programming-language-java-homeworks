package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * 
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {
	
	/**
	 * Constructor which accepts two arguments; iLocalizationProvider and jFrame.
	 * 
	 * @param iLocalizationProvider instance of {@link ILocalizationProvider}
	 * @param jFrame instance of {@link JFrame}
	 */
	public FormLocalizationProvider(ILocalizationProvider iLocalizationProvider, JFrame jFrame) {
		super(iLocalizationProvider);
		
		jFrame.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
			}
			
		});
	}

}
