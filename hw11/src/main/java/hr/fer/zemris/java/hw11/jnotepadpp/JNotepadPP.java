package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Utilities;
import hr.fer.zemris.java.hw11.jnotepadpp.local.*;

/**
 * Class is the application which is simple text editor application. When the program is started, 
 * window of application is created. Application supports some number of functionalities.
 * 
 * Application supports operation for creating new blank document, operation for opening existing
 * document, operations for saving the document, operation for closing the document in currently
 * active tab, operation for cut text, operation for copy text, operation for paste text,
 * operation for getting the statistical info of the document, operation for exiting the application,
 * operations for changing the case of selected text, operations for sorting the selected lines and
 * operation for removing the duplicate lines from selected lines.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class JNotepadPP extends JFrame {
	
	/**
	 * Universal version identifier for a {@link Serializable} class.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Component x of the position of the window.
	 */
	private static final int X_POSITION_OF_THE_WINDOW = 0;
	
	/**
	 * Component y of the position of the window.
	 */
	private static final int Y_POSITION_OF_THE_WINDOW = 0;
	
	/**
	 * Width of the window.
	 */
	private static final int WIDTH_OF_THE_WINDOW = 1350;
	
	/**
	 * Height of the window.
	 */
	private static final int HEIGHT_OF_THE_WINDOW = 700;
	
	/**
	 * Number of columns of the JPanel.
	 */
	private static final int NUM_OF_COLS_OF_JPANEL = 3;
	
	/**
	 * Number of rows of the JPanel.
	 */
	private static final int NUM_OF_ROWS_OF_JPANEL = 1;
	
	/**
	 * Insets of JMenuItems in JToolBar.
	 */
	private static final int INSETS = 0;

	/**
	 * Number of milis that clock sleep.
	 */
	private static final long NUMBER_OF_MILIS_CLOCK_SLEEP = 1000;

	/**
	 * Index of the lenghtLabel on JPanel.
	 */
	private static final int INDEX_OF_LENGTH_LABEL_ON_JPANEL = 0;
	
	/**
	 * Index of lnColSelLabel on JPanel.
	 */
	private static final int INDEX_OF_LN_COL_SEL_LABEL_ON_JPANEL = 1;
	
	/**
	 * Index of clockLabel on JPanel.
	 */
	private static final int INDEX_OF_CLOCK_LABEL_ON_JPANEL = 2;
	
	/**
	 * Index of editor on JPanel.
	 */
	private static final int INDEX_OF_EDITOR_ON_JPANEL = 0;
	
	/**
	 * Index of status bar on JPanel.
	 */
	private static final int INDEX_OF_STATUS_BAR_ON_JPANEL = 1;
	
	/**
	 * Index of changeCase in Tools.
	 */
	private static final int INDEX_OF_CHANGE_CASE_IN_TOOLS = 0;
	
	/**
	 * Index of sort in Tools.
	 */
	private static final int INDEX_OF_SORT_IN_TOOLS = 1;
	
	/**
	 * Size of the border top.
	 */
	private static final int SIZE_OF_THE_BORDER_TOP = 3;

	/**
	 * Size of the border left.
	 */
	private static final int SIZE_OF_THE_BORDER_LEFT = 0;

	/**
	 * Size of the border bottom.
	 */
	private static final int SIZE_OF_THE_BORDER_BOTTOM = 0;

	/**
	 * Size of the border right.
	 */
	private static final int SIZE_OF_THE_BORDER_RIGHT = 0;
	
	/**
	 * Instance of {@link JTabbedPane} which contains all tabs with opened documents.
	 */
	private JTabbedPane jTabbedPane;
	
	/**
	 * Current opened editor.
	 */
	private JTextArea currentEditor;
	
	/**
	 * Current file path of document which is curently edited.
	 */
	private Path currentFilePath;
	
	/**
	 * Map which maps components to paths. Used to get paths for currently opened tabs.
	 */
	private Map<Component, Path> componentToPath = new HashMap<>();
	
	/**
	 * Icon of green diskette.
	 */
	private final Icon greenDiskette = getIcon("greenDiskette.png");
	
	/**
	 * Icon of red diskette.
	 */
	private final Icon redDiskette = getIcon("redDiskette.png");
	
	/**
	 * Icon of new file.
	 */
	private final Icon newFile = getIcon("newFile.png");;
	
	/**
	 * Icon for open file action.
	 */
	private final Icon openFile = getIcon("openFile.png");;
	
	/**
	 * Save icon.
	 */
	private final Icon save = getIcon("save.png");
	
	/**
	 * Icon of closing tab.
	 */
	private final Icon closeTab = getIcon("closeTab.png");
	
	/**
	 * Info icon.
	 */
	private final Icon info = getIcon("info.png");
	
	/**
	 * Exit icon.
	 */
	private final Icon exit = getIcon("exit.png");
	
	/**
	 * Cut icon.
	 */
	private final Icon cut = getIcon("cut.png");
	
	/**
	 * Copy icon.
	 */
	private final Icon copy = getIcon("copy.png");
	
	/**
	 * Paste icon.
	 */
	private final Icon paste = getIcon("paste.png");
	
	/**
	 * Icon of flag of Great Britain.
	 */
	private final Icon gbFlag = getIcon("gbFlag.png");
	
	/**
	 * Icon of flag of Germany.
	 */
	private final Icon deFlag = getIcon("deFlag.png");
	
	/**
	 * Icon of flag of Croatia.
	 */
	private final Icon hrFlag = getIcon("hrFlag.png");
	
	/**
	 * Icon for toUppercase action.
	 */
	private final Icon toUpperCase = getIcon("toUpperCase.png");
	
	/**
	 * Icon for toLowercase action.
	 */
	private final Icon toLowerCase = getIcon("toLowerCase.png");
	
	/**
	 * Icon for invertCase action.
	 */
	private final Icon invertCase = getIcon("invertCase.png");
	
	/**
	 * Icon for sortAscending action.
	 */
	private final Icon sortAscending = getIcon("sortAscending.png");
	
	/**
	 * Icon for sortDescending action.
	 */
	private final Icon sortDescending = getIcon("sortDescending.png");
	
	/**
	 * Icon for unique action.
	 */
	private final Icon unique = getIcon("unique.png");
	
	/**
	 * Clipboard.
	 */
	private String clipBoard;
	
	/**
	 * List of indexes of blank documents.
	 */
	private List<Integer> indexesOfBlankDocuments;
	
	/**
	 * Flag that contain information is requested stop of the thread Clock.
	 */
	private volatile boolean stopRequested;
	
	/**
	 * Instance of {@link FormLocalizationProvider} which is used for getting the translations.
	 */
	private final FormLocalizationProvider flp = 
			new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
	
	/**
	 * Current name which is used for the blank documents. It is different for the different languages.
	 */
	private String currentNameForBlankDocument;
	
	/**
	 * Constructor which accepts no arguments. It creates window of the application and calls the
	 * method which initialize the GUI.
	 */
	public JNotepadPP() {
		super();
		
		currentNameForBlankDocument = flp.getString("new");
		
		flp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				changeTabNames();
			}
		});
	
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocation(X_POSITION_OF_THE_WINDOW, Y_POSITION_OF_THE_WINDOW);
		setSize(WIDTH_OF_THE_WINDOW, HEIGHT_OF_THE_WINDOW);
		setTitle("JNotepadPP");
		
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				askUserForClosingTheApplication();
			};
			
		});
		
		initGUI();
	}
	
	/**
	 * Method change tab names of the tabs which contain blank documents when the language is
	 * changed. For example 'New1' becomes 'Novi1'.
	 */
	private void changeTabNames() {
		if(jTabbedPane.getComponentCount() == 0) {
			return;
		}
		
		for(int i = 0, n = jTabbedPane.getComponentCount(); i < n; i++) {
			String title = jTabbedPane.getTitleAt(i);
			
			if(title.startsWith(currentNameForBlankDocument)) {
				String valueAsString = title.substring(currentNameForBlankDocument.length());
				
				int value;
				try {
					value = Integer.parseInt(valueAsString);
				} catch(NumberFormatException ignorable) {
					continue;
				}
				
				jTabbedPane.setTitleAt(i, flp.getString("new") + value);
			}
		}
		
		currentNameForBlankDocument = flp.getString("new");
	}
	
	/**
	 * Method gets {@link Icon} for accepted name of the icon.
	 * 
	 * @param name name of the icon
	 * @return icon
	 */
	private Icon getIcon(String name) {
		Path path = Paths.get("icons/", name);
		byte[] bytes = null;
		
		try(InputStream is = this.getClass().getResourceAsStream(path.toString())) {
			bytes = new byte[is.available()];
			is.read(bytes, 0, is.available());
		} catch(IOException exc) {
			JOptionPane.showMessageDialog(
					JNotepadPP.this,
					flp.getString("errorWhileReadingFromFile") + " "+ path + ".",
					flp.getString("error"),
					JOptionPane.ERROR_MESSAGE);
			
			return null;
		}
	
		return new ImageIcon(bytes);
	}

	/**
	 * Method initialize the GUI.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		jTabbedPane = new JTabbedPane();
		cp.add(jTabbedPane, BorderLayout.CENTER);
		
		createActions();
		createMenus();
		createToolbars();
		
		setCutCopyPasteDisabled();
		setChangeCaseActionsDisabled();
		setSortActionsDisabled();
		uniqueAction.setEnabled(false);
		
		setOtherActionsEnabledOrDisabled(false);
		
		addChangeListenerToJTabbedPane();
		
		createClock();
	}
	
	/**
	 * Method create the actions of the application.
	 */
	private void createActions() {
		createFileActions();
		createEditActions();
		createToolsActions();
	}

	/**
	 * Method creates file actions. It sets acceleratorKey, mnemonicKey and the smallIcon of the actions.
	 */
	private void createFileActions() {
		createNewBlankDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		createNewBlankDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		createNewBlankDocumentAction.putValue(Action.SMALL_ICON, newFile);

		openExistingDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openExistingDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		openExistingDocumentAction.putValue(Action.SMALL_ICON, openFile);
	
		saveDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveDocumentAction.putValue(Action.SMALL_ICON, save);
		
		saveAsDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt S"));
		saveAsDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveAsDocumentAction.putValue(Action.SMALL_ICON, save);
		
		closeDocumentShownInTabAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control L"));
		closeDocumentShownInTabAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
		closeDocumentShownInTabAction.putValue(Action.SMALL_ICON, closeTab);
		
		statisticalInfoAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		statisticalInfoAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
		statisticalInfoAction.putValue(Action.SMALL_ICON, info);
		
		exitApplicationAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
		exitApplicationAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		exitApplicationAction.putValue(Action.SMALL_ICON, exit);
	}

	/**
	 * Method creates the actions which are used for the editing the text.
	 * It sets acceleratorKey, mnemonicKey and the smallIcon of the actions.
	 */
	private void createEditActions() {
		cutTextAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control T"));
		cutTextAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		cutTextAction.putValue(Action.SMALL_ICON, cut);
		
		copyTextAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Y"));
		copyTextAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_Y);
		copyTextAction.putValue(Action.SMALL_ICON, copy);
		
		pasteTextAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control P"));
		pasteTextAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
		pasteTextAction.putValue(Action.SMALL_ICON, paste);
	}
	
	/**
	 * Method creates tool actions.
	 * It sets acceleratorKey, mnemonicKey and the smallIcon of the actions.
	 */
	private void createToolsActions() {
		createChangeCaseActions();
		createSortActions();
		
		uniqueAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control K"));
		uniqueAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_K);
		uniqueAction.putValue(Action.SMALL_ICON, unique);
	}

	/**
	 * Method creates actions which are used for changing the case of the selected text.
	 * It sets acceleratorKey, mnemonicKey and the smallIcon of the actions.
	 */
	private void createChangeCaseActions() {
		toUppercaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));
		toUppercaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
		toUppercaseAction.putValue(Action.SMALL_ICON, toUpperCase);
		
		toLowercaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control A"));
		toLowercaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		toLowercaseAction.putValue(Action.SMALL_ICON, toLowerCase);
		
		invertCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control R"));
		invertCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_R);
		invertCaseAction.putValue(Action.SMALL_ICON, invertCase);
	}

	/**
	 * Method creates actions which are used for sorting the lines of the selected lines.
	 * It sets acceleratorKey, mnemonicKey and the smallIcon of the actions.
	 */
	private void createSortActions() {
		sortAscendingAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control B"));
		sortAscendingAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_B);
		sortAscendingAction.putValue(Action.SMALL_ICON, sortAscending);
		
		sortDescendingAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		sortDescendingAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		sortDescendingAction.putValue(Action.SMALL_ICON, sortDescending);
	}

	/**
	 * Method creates menus of the application. Menus of the application are fileMenu, editMenu
	 * languagesMenu and toolsMenu.
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = createFileMenu();
		menuBar.add(fileMenu);
		
		JMenu editMenu = createEditMenu();
		menuBar.add(editMenu);
		
		JMenu languagesMenu = createLanguagesMenu();
		menuBar.add(languagesMenu);
		
		JMenu toolsMenu = createToolsMenu();
		menuBar.add(toolsMenu);
		
		setJMenuBar(menuBar);
		
		flp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				fileMenu.setText(flp.getString("file"));
				editMenu.setText(flp.getString("edit"));
				languagesMenu.setText(flp.getString("languages"));
				toolsMenu.setText(flp.getString("tools"));
				
				JMenu changeCase = (JMenu) toolsMenu.getAccessibleContext().
						getAccessibleChild(INDEX_OF_CHANGE_CASE_IN_TOOLS);
				changeCase.setText(flp.getString("changeCase"));
				
				JMenu sort = (JMenu) toolsMenu.getAccessibleContext().
						getAccessibleChild(INDEX_OF_SORT_IN_TOOLS);
				sort.setText(flp.getString("sort"));
			}
			
		});
	}
	
	/**
	 * Method creates and returns fileMenu. Actions of the fileMenu are: action for creating the
	 * new blank document, action for opening the existing document, actions for saving the documents,
	 * action for closing the current tab, action for getting the statictical informations about the
	 * documents and action for exiting the application.
	 * 
	 * @return fileMenu
	 */
	private JMenu createFileMenu() {
		JMenu fileMenu = new JMenu("File");
		
		fileMenu.add(new JMenuItem(createNewBlankDocumentAction));
		fileMenu.add(new JMenuItem(openExistingDocumentAction));
		
		fileMenu.addSeparator();
		
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveAsDocumentAction));
		
		fileMenu.addSeparator();
		
		fileMenu.add(new JMenuItem(closeDocumentShownInTabAction));
		
		fileMenu.addSeparator();
		
		fileMenu.add(new JMenuItem(statisticalInfoAction));
		
		fileMenu.addSeparator();
		
		fileMenu.add(new JMenuItem(exitApplicationAction));
		
		return fileMenu;
	}
	
	/**
	 * Method creates and returns the editMenu. Actions of the editMenu are: action for the cutting 
	 * selected text, action for the copying the selected text and the action for the pasting text.
	 * 
	 * @return editMenu
	 */
	private JMenu createEditMenu() {
		JMenu editMenu = new JMenu("Edit");
		
		editMenu.add(new JMenuItem(cutTextAction));
		editMenu.add(new JMenuItem(copyTextAction));
		editMenu.add(new JMenuItem(pasteTextAction));
		
		return editMenu;
	}

	/**
	 * Method creates and returns languagesMenu. In languagesMenu user can select language of the
	 * application.
	 * 
	 * @return languagesMenu
	 */
	private JMenu createLanguagesMenu() {
		JMenu languagesMenu = new JMenu("Languages");
		
		JMenuItem hr = new JMenuItem("hr", hrFlag);
		languagesMenu.add(hr);
		hr.addActionListener(e -> {
			LocalizationProvider.getInstance().setLanguage("hr");
		});
		
		JMenuItem en = new JMenuItem("en", gbFlag);
		languagesMenu.add(en);
		en.addActionListener(e -> {
			LocalizationProvider.getInstance().setLanguage("en");
		});
		
		JMenuItem de = new JMenuItem("de", deFlag);
		languagesMenu.add(de);
		de.addActionListener(e -> {
			LocalizationProvider.getInstance().setLanguage("de");
		});
		
		return languagesMenu;
	}
	
	/**
	 * Method creates and returns toolsMenu. Actions of the toolsMenu are: actions for changing the 
	 * case of the selected text, actions for sorting the lines of the selected lines and action
	 * which removes duplicate lines from the selected lines.
	 * 
	 * @return toolsMenu
	 */
	private JMenu createToolsMenu() {
		JMenu toolsMenu = new JMenu("Tools");
		
		JMenu changeCase = new JMenu("Change case");
		toolsMenu.add(changeCase);
		
		changeCase.add(new JMenuItem(toUppercaseAction));
		changeCase.add(new JMenuItem(toLowercaseAction));
		changeCase.add(new JMenuItem(invertCaseAction));
		
		JMenu sort = new JMenu("Sort");
		toolsMenu.add(sort);
		
		sort.add(new JMenuItem(sortAscendingAction));
		sort.add(new JMenuItem(sortDescendingAction));
		
		toolsMenu.add(new JMenuItem(uniqueAction));
		
		return toolsMenu;
	}

	/**
	 * Method create toolbar which contains buttons with actions.
	 * Actions of the toolbar are: action for creating the new blank document, action for opening 
	 * the existing document, actions for saving the documents,
	 * action for closing the current tab, action for getting the statictical informations about the
	 * documents and action for exiting the application, actions for changing the 
	 * case of the selected text, actions for sorting the lines of the selected lines and action
	 * which removes duplicate lines from the selected lines.
	 */
	private void createToolbars() {
		JToolBar jToolBar = new JToolBar(flp.getString("toolBar"));
		getContentPane().add(jToolBar, BorderLayout.PAGE_START);
		
		jToolBar.add(new JButton(createNewBlankDocumentAction));
		jToolBar.add(new JButton(openExistingDocumentAction));
		
		jToolBar.addSeparator();
		
		jToolBar.add(new JButton(saveDocumentAction));
		jToolBar.add(new JButton(saveAsDocumentAction));
		
		jToolBar.addSeparator();
		
		jToolBar.add(new JButton(closeDocumentShownInTabAction));
		
		jToolBar.addSeparator();
		
		jToolBar.add(new JButton(statisticalInfoAction));
		
		jToolBar.addSeparator();
		
		jToolBar.add(new JButton(exitApplicationAction));
		
		jToolBar.addSeparator();

		jToolBar.add(new JButton(cutTextAction));
		jToolBar.add(new JButton(copyTextAction));
		jToolBar.add(new JButton(pasteTextAction));
		
		jToolBar.addSeparator();
		
		jToolBar.add(new JButton(toUppercaseAction));
		jToolBar.add(new JButton(toLowercaseAction));
		jToolBar.add(new JButton(invertCaseAction));
		
		jToolBar.addSeparator();
		
		jToolBar.add(new JButton(sortAscendingAction));
		jToolBar.add(new JButton(sortDescendingAction));
		
		jToolBar.addSeparator();
		
		jToolBar.add(new JButton(uniqueAction));
		
		setMargins(jToolBar);
	}

	/**
	 * Method sets margins of buttons of the accepted toolbar.
	 * 
	 * @param jToolBar accepted toolbar
	 */
	private void setMargins(JToolBar jToolBar) {
		for(Component component : jToolBar.getComponents()) {
			if(component instanceof JButton) {
				JButton jButton = (JButton) component;
				jButton.setMargin(new Insets(INSETS, INSETS, INSETS, INSETS));
			}	
		}
	}
	
	/**
	 * Method sets actions for changing the case of the selected text disabled.
	 */
	private void setChangeCaseActionsDisabled() {
		toUppercaseAction.setEnabled(false);
		toLowercaseAction.setEnabled(false);
		invertCaseAction.setEnabled(false);
	}
	
	/**
	 * Method sets actions which cut, copy and paste the selected text disabled.
	 */
	private void setCutCopyPasteDisabled() {
		cutTextAction.setEnabled(false);
		copyTextAction.setEnabled(false);
		pasteTextAction.setEnabled(false);
	}
	
	/**
	 * Method sets actions which are used for sorting the selected text disabled.
	 */
	private void setSortActionsDisabled() {
		sortAscendingAction.setEnabled(false);
		sortDescendingAction.setEnabled(false);
	}

	/**
	 * Method sets save actions, action for closing the current tab and action for getting the
	 * staticstical info of the current document to the accepted value.
	 * 
	 * @param enabled accepted value
	 */
	private void setOtherActionsEnabledOrDisabled(boolean enabled) {
		saveDocumentAction.setEnabled(enabled);
		saveAsDocumentAction.setEnabled(enabled);
		closeDocumentShownInTabAction.setEnabled(enabled);
		statisticalInfoAction.setEnabled(enabled);	
	}
	
	/**
	 * Method adds {@link ChangeListener} to the jTabbedPane.
	 */
	private void addChangeListenerToJTabbedPane() {
		jTabbedPane.addChangeListener(e -> {
			if(jTabbedPane.getSelectedIndex() == -1) {
				currentEditor = null;
			} else {
				JScrollPane jScrollPane = (JScrollPane) jTabbedPane.
						getSelectedComponent().getAccessibleContext().getAccessibleChild(INDEX_OF_EDITOR_ON_JPANEL);
				
				currentEditor = (JTextArea) jScrollPane.getViewport().getView();
			}
			
			currentFilePath = componentToPath.get(jTabbedPane.getSelectedComponent());
			setTitle((currentFilePath == null ? "" : currentFilePath.toString() + " - ") + "JNotepadPP");
			
			int i = jTabbedPane.getSelectedIndex();
			
			setOtherActionsEnabledOrDisabled(i == -1 ? false : true);
		});
	}
	
	/**
	 * Action creates new blank document.
	 */
	private final Action createNewBlankDocumentAction = new LocalizableAction("new", "newDesc", flp) {
		
		/**
		 * Universal version identifier for a {@link Serializable} class.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if(indexesOfBlankDocuments == null) {
				indexesOfBlankDocuments = new ArrayList<>();
			}
			
			JTextArea newJTextArea = new JTextArea();
			currentEditor = newJTextArea;
			
			JPanel jPanel = new JPanel(new BorderLayout());
			jPanel.add(new JScrollPane(newJTextArea), BorderLayout.CENTER);
			
			JPanel jPanel2 = new JPanel(new GridLayout(NUM_OF_ROWS_OF_JPANEL, NUM_OF_COLS_OF_JPANEL));
			jPanel2.setBorder(BorderFactory.createMatteBorder(
					SIZE_OF_THE_BORDER_TOP,
					SIZE_OF_THE_BORDER_LEFT,
					SIZE_OF_THE_BORDER_BOTTOM,
					SIZE_OF_THE_BORDER_RIGHT,
					Color.GRAY));
			
			jPanel.add(jPanel2, BorderLayout.PAGE_END);
			addComponentsToJPanel2(jPanel2);
			
			setTabForNewBlankDocument(jPanel);
			
			addDocumentListenerToNewJTextArea(newJTextArea);
			addCaretListenerToNewJTextArea(newJTextArea);
		}

		private void addComponentsToJPanel2(JPanel jPanel2) {
			JLabel lengthLabel = new JLabel(flp.getString("length") + " 0");
			jPanel2.add(lengthLabel);
			
			String lnString = flp.getString("ln");
			String colString = flp.getString("col");
			String selString = flp.getString("sel");
			
			String textToSet = String.format(
					"%s %d  %s %d  %s %d", 
					lnString, 0, colString, 0, selString, 0);
			
			JLabel lnColSelLabel = new JLabel(textToSet);
			lnColSelLabel.setHorizontalAlignment(SwingConstants.CENTER);
			jPanel2.add(lnColSelLabel);
			
			JLabel clockLabel = new JLabel(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
			clockLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			jPanel2.add(clockLabel);
		}

		private void setTabForNewBlankDocument(JPanel jPanel) {
			componentToPath.put(jPanel, null);
			
			jTabbedPane.addTab(flp.getString("new") + findIndex(), jPanel);
			jTabbedPane.setSelectedIndex(jTabbedPane.getTabCount() - 1);
			jTabbedPane.setIconAt(jTabbedPane.getSelectedIndex(), greenDiskette);
			
			JScrollPane jScrollPane = (JScrollPane) jTabbedPane.
					getSelectedComponent().getAccessibleContext().getAccessibleChild(INDEX_OF_EDITOR_ON_JPANEL);
			
			currentEditor = (JTextArea) jScrollPane.getViewport().getView();
			
			setTitle("JNotepadPP++");
		}

		private void addDocumentListenerToNewJTextArea(JTextArea newJTextArea) {
			newJTextArea.getDocument().addDocumentListener(new DocumentListener() {
				
				@Override
				public void removeUpdate(DocumentEvent e) {
					editorUpdated();
				}
				
				@Override
				public void insertUpdate(DocumentEvent e) {
					editorUpdated();
				}
				
				@Override
				public void changedUpdate(DocumentEvent e) {
					editorUpdated();
				}

				private void editorUpdated() {
					jTabbedPane.setIconAt(jTabbedPane.getSelectedIndex(), redDiskette);
					
					JPanel jPanel = (JPanel) jTabbedPane.
							getSelectedComponent().getAccessibleContext().getAccessibleChild(INDEX_OF_STATUS_BAR_ON_JPANEL);
					
					JLabel lengthLabel = (JLabel) jPanel.getComponent(INDEX_OF_LENGTH_LABEL_ON_JPANEL);
					
					String length = flp.getString("length");
					lengthLabel.setText(length + " " + newJTextArea.getDocument().getLength());
				}
				
			});
			
		}
		
		private void addCaretListenerToNewJTextArea(JTextArea newJTextArea) {
			newJTextArea.addCaretListener(new CaretListener() {
				
				@Override
				public void caretUpdate(CaretEvent e) {
					updateStatusBar();
					updateCopyAndCutActions();	
					updateChangeCaseActions();
					updateSortActions();
					
					if(jTabbedPane.getSelectedIndex() == -1) {
						uniqueAction.setEnabled(false);
						
						return;
					}
					
					int length = Math.abs(currentEditor.getCaret().getDot() - currentEditor.getCaret().getMark());
					uniqueAction.setEnabled(length == 0 ? false : true);
				}
				
				private void updateStatusBar() {
					JPanel jPanel = (JPanel) jTabbedPane.
							getSelectedComponent().getAccessibleContext().getAccessibleChild(INDEX_OF_STATUS_BAR_ON_JPANEL);
					
					JLabel lnColSelLabel = (JLabel) jPanel.getComponent(INDEX_OF_LN_COL_SEL_LABEL_ON_JPANEL);
					
					int caretPosition = newJTextArea.getCaretPosition();
					int ln = 0;
					int col = 0;
				
					try {
					    ln = (caretPosition == 0) ? 1 : 0;
						for (int offset = caretPosition; offset > 0;) {
							offset = Utilities.getRowStart(newJTextArea, offset) - 1;
						    ln++;
						}
						
						int offset = Utilities.getRowStart(newJTextArea, caretPosition);
						col = caretPosition - offset + 1;
					} catch (BadLocationException ignorable) {
					}
					
					int sel = Math.abs(currentEditor.getCaret().getDot() - currentEditor.getCaret().getMark());
					
					String lnString = flp.getString("ln");
					String colString = flp.getString("col");
					String selString = flp.getString("sel");
					
					String textToSet = String.format(
							"%s %d  %s %d  %s %d", 
							lnString, ln, colString, col, selString, sel);
					
					lnColSelLabel.setText(textToSet);
				}

				private void updateCopyAndCutActions() {
					if(jTabbedPane.getSelectedIndex() == -1) {
						cutTextAction.setEnabled(false);
						copyTextAction.setEnabled(false);
						
						return;
					}
					
					int length = Math.abs(currentEditor.getCaret().getDot() - currentEditor.getCaret().getMark());
					cutTextAction.setEnabled(length == 0 ? false : true);
					copyTextAction.setEnabled(length == 0 ? false : true);
				}
				

				private void updateChangeCaseActions() {
					if(jTabbedPane.getSelectedIndex() == -1) {
						toUppercaseAction.setEnabled(false);
						toLowercaseAction.setEnabled(false);
						invertCaseAction.setEnabled(false);
						
						return;
					}
					
					int length = Math.abs(currentEditor.getCaret().getDot() - currentEditor.getCaret().getMark());
					toUppercaseAction.setEnabled(length == 0 ? false : true);
					toLowercaseAction.setEnabled(length == 0 ? false : true);
					invertCaseAction.setEnabled(length == 0 ? false : true);
				}
				

				private void updateSortActions() {
					if(jTabbedPane.getSelectedIndex() == -1) {
						sortAscendingAction.setEnabled(false);
						sortDescendingAction.setEnabled(false);
						
						return;
					}
					
					int length = Math.abs(currentEditor.getCaret().getDot() - currentEditor.getCaret().getMark());
					sortAscendingAction.setEnabled(length == 0 ? false : true);
					sortDescendingAction.setEnabled(length == 0 ? false : true);
				}
				
			});
		}
	};
	
	/**
	 * Actions opens the existing document.
	 */
	private final Action openExistingDocumentAction = new LocalizableAction("open", "openDesc", flp) {
		
		/**
		 * Universal version identifier for a {@link Serializable} class.
		 */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle(flp.getString("openFile"));
			
			if(fc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			
			File fileName = fc.getSelectedFile();
			Path filePath = fileName.toPath();
			
			if(!Files.isReadable(filePath)) {
				JOptionPane.showMessageDialog(
						JNotepadPP.this,
						flp.getString("file") + " " + filePath + " " + flp.getString("isNotReadable"),
						flp.getString("error"),
						JOptionPane.ERROR_MESSAGE);
				
				return;
			}
			
			byte[] data = null;
			
			try {
				data = Files.readAllBytes(filePath);
			} catch(IOException exc) {
				JOptionPane.showMessageDialog(
						JNotepadPP.this,
						flp.getString("errorWhileReadingFromFile") + " " + filePath + ".",
						flp.getString("error"),
						JOptionPane.ERROR_MESSAGE);
				
				return;
			}
			
			String text = new String(data, StandardCharsets.UTF_8);
			
			createNewBlankDocumentAction.actionPerformed(e);
			
			setTabForOpenedDocument(filePath, text);
		}

		private void setTabForOpenedDocument(Path filePath, String text) {
			currentFilePath = filePath;
			
			JScrollPane jScrollPane = (JScrollPane) jTabbedPane.
					getSelectedComponent().getAccessibleContext().getAccessibleChild(INDEX_OF_EDITOR_ON_JPANEL);
			
			currentEditor = (JTextArea) jScrollPane.getViewport().getView();
			currentEditor.setText(text);
			
			jTabbedPane.setTitleAt(jTabbedPane.getSelectedIndex(), currentFilePath.getFileName().toString());
			jTabbedPane.setToolTipTextAt(jTabbedPane.getSelectedIndex(), filePath.toString());
			jTabbedPane.setIconAt(jTabbedPane.getSelectedIndex(), greenDiskette);
			
			componentToPath.put(jTabbedPane.getComponentAt(jTabbedPane.getSelectedIndex()), currentFilePath);
			setTitle(currentFilePath.toString() + " - " + "JNotepadPP++");
		}
	};
	
	/**
	 * Action saves the current document.
	 */
	private final Action saveDocumentAction = new LocalizableAction("save", "saveDesc", flp) {
		
		/**
		 * Universal version identifier for a {@link Serializable} class.
		 */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(currentFilePath == null) {
				currentFilePath = getCurrentFilePath();
			}
			
			if(currentFilePath != null) {
				saveFile();
			}
		}
	};
	
	/**
	 * Action saves the current document.
	 */
	private final Action saveAsDocumentAction = new LocalizableAction("saveAs", "saveAsDesc", flp) {
		
		/**
		 * Universal version identifier for a {@link Serializable} class.
		 */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			currentFilePath = getCurrentFilePath();
			
			if(currentFilePath != null) {
				saveFile();
			}
		}
	};
	
	/**
	 * Method gets from the user filePath.
	 * 
	 * @return filePath
	 */
	private Path getCurrentFilePath() {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle(flp.getString("saveFile"));
		
		if(fc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(
					JNotepadPP.this,
					flp.getString("nothingIsSaved"),
					flp.getString("information"),
					JOptionPane.INFORMATION_MESSAGE);
			
			return currentFilePath;
		}
		
		if(Files.exists(fc.getSelectedFile().toPath())) {
			int pressed = JOptionPane.showConfirmDialog(
					JNotepadPP.this,
					flp.getString("fileExists"),
					flp.getString("overwritingTheFile"),
					JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE);
			
			if(pressed == JOptionPane.NO_OPTION) {
				return currentFilePath;
			}
		}
		
		checkIfNeedRemoveIndexFromList(jTabbedPane.getTitleAt(jTabbedPane.getSelectedIndex()));
		
		return fc.getSelectedFile().toPath();
	}
	
	/**
	 * Method saves file. It writes file data to the filePath.
	 */
	private void saveFile() {
		try {
			Files.write(currentFilePath, currentEditor.getText().getBytes(StandardCharsets.UTF_8));
		} catch(Exception exc) {
			JOptionPane.showMessageDialog(
					JNotepadPP.this,
					flp.getString("savingDidNotSucced"),
					flp.getString("error"),
					JOptionPane.ERROR_MESSAGE);
			
			return;
		}
		
		jTabbedPane.setIconAt(jTabbedPane.getSelectedIndex(), greenDiskette);
		jTabbedPane.setTitleAt(jTabbedPane.getSelectedIndex(), currentFilePath.getFileName().toString());
		jTabbedPane.setToolTipTextAt(jTabbedPane.getSelectedIndex(), currentFilePath.toString());
		
		componentToPath.put(jTabbedPane.getComponentAt(jTabbedPane.getSelectedIndex()), currentFilePath);
		
		currentFilePath = componentToPath.get(jTabbedPane.getSelectedComponent());
		setTitle((currentFilePath == null ? "" : currentFilePath.toString() + " - ") + "JNotepadPP++");
		
		JOptionPane.showMessageDialog(
				JNotepadPP.this,
				flp.getString("fileIsSaved"),
				flp.getString("information"),
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Action closes the current tab.
	 */
	private final Action closeDocumentShownInTabAction = new LocalizableAction("close", "closeDesc", flp) {
		
		/**
		 * Universal version identifier for a {@link Serializable} class.
		 */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(jTabbedPane.getIconAt(jTabbedPane.getSelectedIndex()).equals(redDiskette)) {
				int pressed = JOptionPane.showConfirmDialog(
						JNotepadPP.this,
						flp.getString("saveQuestion") + jTabbedPane.getTitleAt(jTabbedPane.getSelectedIndex()) + "'?",
						flp.getString("saving"),
						JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.WARNING_MESSAGE);
				
				if(pressed == JOptionPane.CANCEL_OPTION) {
					return;
				}
				
				if(pressed == JOptionPane.YES_OPTION) {
					saveDocumentAction.actionPerformed(null);
				}
			}
			
			checkIfNeedRemoveIndexFromList(jTabbedPane.getTitleAt(jTabbedPane.getSelectedIndex()));
			
			jTabbedPane.remove(jTabbedPane.getSelectedIndex());
		}
	};
	
	/**
	 * Action cuts the selected text.
	 */
	private final Action cutTextAction = new LocalizableAction("cut", "cutDesc", flp) {
			
		/**
		 * Universal version identifier for a {@link Serializable} class.
		 */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Document doc = currentEditor.getDocument();
			
			int length = Math.abs(currentEditor.getCaret().getDot() - currentEditor.getCaret().getMark());
			int offset = Math.min(currentEditor.getCaret().getDot(), currentEditor.getCaret().getMark());
			
			try {
				clipBoard = doc.getText(offset, length);
				doc.remove(offset, length);
			} catch (BadLocationException ignorable) {
			}
			
			pasteTextAction.setEnabled(true);
		}
	};
	
	/**
	 * Action copies the selected text.
	 */
	private final Action copyTextAction = new LocalizableAction("copy", "copyDesc", flp) {
		
		/**
		 * Universal version identifier for a {@link Serializable} class.
		 */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Document doc = currentEditor.getDocument();
			
			int length = Math.abs(currentEditor.getCaret().getDot() - currentEditor.getCaret().getMark());
			int offset = Math.min(currentEditor.getCaret().getDot(), currentEditor.getCaret().getMark());
			
			try {
				clipBoard = doc.getText(offset, length);
			} catch (BadLocationException ignorable) {
			}
			
			pasteTextAction.setEnabled(true);
		}
	};
	
	/**
	 * Action pastes the selected text.
	 */
	private final Action pasteTextAction = new LocalizableAction("paste", "pasteDesc", flp) {
		
		/**
		 * Universal version identifier for a {@link Serializable} class.
		 */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Document doc = currentEditor.getDocument();
			
			int length = Math.abs(currentEditor.getCaret().getDot() - currentEditor.getCaret().getMark());
			int offset = Math.min(currentEditor.getCaret().getDot(), currentEditor.getCaret().getMark());
			
			try {
				doc.remove(offset, length);
				doc.insertString(offset, clipBoard, null);
			} catch (BadLocationException ignorable) {
			}
		}
	};
	
	/**
	 * Action gets the statistical info for the current document.
	 */
	private final Action statisticalInfoAction = new LocalizableAction("info", "infoDesc", flp) {
			
		/**
		 * Universal version identifier for a {@link Serializable} class.
		 */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int numberOfCharacters = currentEditor.getText().length();
			
			int numberOfNonBlankCharacters = 0;
			for(int i = 0, n = currentEditor.getText().length(); i < n; i++) {
				if(!Character.isWhitespace(currentEditor.getText().charAt(i))) {
					numberOfNonBlankCharacters++;
				}
			}
			
			int numberOfLines = currentEditor.getLineCount();
			
			String firstPart = flp.getString("firstPartOfInfo");
			String secondPart = flp.getString("secondPartOfInfo");
			String thirdPart = flp.getString("thirdPartOfInfo");
			String fourthPart = flp.getString("fourthPartOfInfo");
			
			String message = String.format(
					"%s %d %s %d %s %d %s",
					firstPart, numberOfCharacters, secondPart, numberOfNonBlankCharacters, 
					thirdPart, numberOfLines, fourthPart);
			
			JOptionPane.showMessageDialog(
					JNotepadPP.this,
					message,
					flp.getString("statInfo"),
					JOptionPane.INFORMATION_MESSAGE);
		}
	};
	
	/**
	 * Action exits the application.
	 */
	private final Action exitApplicationAction = new LocalizableAction("exit", "exitDesc", flp) {
		
		/**
		 * Universal version identifier for a {@link Serializable} class.
		 */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			askUserForClosingTheApplication();
		}
	};
	
	/**
	 * Action changes the case of the selected text to uppercase.
	 */
	private final Action toUppercaseAction = new LocalizableAction("toUppercase", "toUppercaseDesc", flp) {
		
		/**
		 * Universal version identifier for a {@link Serializable} class.
		 */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			changeText(new IChangeText() {
				
				@Override
				public char changeChar(char c) {
					return Character.toUpperCase(c);
				}
			});
		}
	};
	
	/**
	 * Action changes the case of the selected text to lowercase.
	 */
	private final Action toLowercaseAction = new LocalizableAction("toLowerCase", "toLowerCaseDesc", flp) {
		
		/**
		 * Universal version identifier for a {@link Serializable} class.
		 */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			changeText(new IChangeText() {
				
				@Override
				public char changeChar(char c) {
					return Character.toLowerCase(c);
				}
			});
		}
	};
	
	/**
	 * Action inverts the case of the selected text.
	 */
	private final Action invertCaseAction = new LocalizableAction("invertCase", "invertCaseDesc", flp) {
		
		/**
		 * Universal version identifier for a {@link Serializable} class.
		 */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			changeText(new IChangeText() {
				
				@Override
				public char changeChar(char c) {
					if(Character.isLetter(c)) {
						if(Character.isUpperCase(c)) {
							return Character.toLowerCase(c);
						} else {
							return Character.toUpperCase(c);
						}
					}
					
					return c;
				}
			});
		}

	};
	
	/**
	 * Action change the selected text with method of interface {@link IChangeText}.
	 * 
	 * @param iChangeText
	 */
	private void changeText(IChangeText iChangeText) {
		Document doc = currentEditor.getDocument();
		
		int length = Math.abs(currentEditor.getCaret().getDot() - currentEditor.getCaret().getMark());
		int offset = Math.min(currentEditor.getCaret().getDot(), currentEditor.getCaret().getMark());
		
		String text = null;
		
		try {
			text = doc.getText(offset, length);
		} catch (BadLocationException ignorable) {
		}
		
		StringBuilder sb = new StringBuilder(text.length());
		
		for(char c : text.toCharArray()) {
			sb.append(iChangeText.changeChar(c));
		}
		
		text = sb.toString();
		
		try {
			doc.remove(offset, length);
			doc.insertString(offset, text, null);
		} catch (BadLocationException ignorable) {
		}
		
	}
	
	/**
	 * Action sorts ascending the selected text.
	 */
	private final Action sortAscendingAction = new LocalizableAction(
			"sortAscending", "sortAscendingDesc", flp) {
		
		/**
		 * Universal version identifier for a {@link Serializable} class.
		 */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int offsetStart = Math.min(currentEditor.getCaret().getDot(), currentEditor.getCaret().getMark());
			int offsetEnd = Math.max(currentEditor.getCaret().getDot(), currentEditor.getCaret().getMark());
			
			int lineStart = 0;
			int lineEnd = 0;
			
			try {
				lineStart = currentEditor.getLineOfOffset(offsetStart);
				lineEnd = currentEditor.getLineOfOffset(offsetEnd);
			} catch (BadLocationException ignorable) {
			}
			
			List<String> listOfSelectedLines = getListOfSelectedLines(lineStart, lineEnd);
			
			Locale hrLocale = new Locale("hr");
			Collator hrCollator = Collator.getInstance(hrLocale);
			
			Collections.sort(listOfSelectedLines, new Comparator<String>() {
				
				@Override
				public int compare(String o1, String o2) {
					return hrCollator.compare(o1, o2);
				};
			});
			
			printNewLines(listOfSelectedLines, lineStart, lineEnd);
		}

	};
	
	/**
	 * Action sorts descending the selected text.
	 */
	private final Action sortDescendingAction = new LocalizableAction(
			"sortDescending", "sortDescendingDesc", flp) {
		
		/**
		 * Universal version identifier for a {@link Serializable} class.
		 */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int offsetStart = Math.min(currentEditor.getCaret().getDot(), currentEditor.getCaret().getMark());
			int offsetEnd = Math.max(currentEditor.getCaret().getDot(), currentEditor.getCaret().getMark());
			
			int lineStart = 0;
			int lineEnd = 0;
			
			try {
				lineStart = currentEditor.getLineOfOffset(offsetStart);
				lineEnd = currentEditor.getLineOfOffset(offsetEnd);
			} catch (BadLocationException ignorable) {
			}
			
			List<String> listOfSelectedLines = getListOfSelectedLines(lineStart, lineEnd);
			
			Locale hrLocale = new Locale("hr");
			Collator hrCollator = Collator.getInstance(hrLocale);
			
			Collections.sort(listOfSelectedLines, new Comparator<String>() {
				
				@Override
				public int compare(String o1, String o2) {
					return -1 * hrCollator.compare(o1, o2);
				};
			});
			
			printNewLines(listOfSelectedLines, lineStart, lineEnd);
		}
	};
	
	/**
	 * Action removes the duplicate lines from the selected lines.
	 */
	private final Action uniqueAction = new LocalizableAction("unique", "uniqueDesc", flp) {
		
		/**
		 * Universal version identifier for a {@link Serializable} class.
		 */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int offsetStart = Math.min(currentEditor.getCaret().getDot(), currentEditor.getCaret().getMark());
			int offsetEnd = Math.max(currentEditor.getCaret().getDot(), currentEditor.getCaret().getMark());
			
			int lineStart = 0;
			int lineEnd = 0;
			
			try {
				lineStart = currentEditor.getLineOfOffset(offsetStart);
				lineEnd = currentEditor.getLineOfOffset(offsetEnd);
			} catch (BadLocationException ignorable) {
			}
			
			List<String> listOfSelectedLines = getListOfSelectedLines(lineStart, lineEnd);
			String lastString = listOfSelectedLines.get(listOfSelectedLines.size() - 1);
			if(lastString.charAt(lastString.length() - 1) == '\n') {
				lastString = lastString.substring(0, lastString.length() - 1);
			}
			
			Set<String> setOfSelectedLines = new LinkedHashSet<>(listOfSelectedLines);
			listOfSelectedLines = new ArrayList<>(setOfSelectedLines);
			
			printNewLines(listOfSelectedLines, lineStart, lineEnd - ((lineEnd - lineStart + 1) - listOfSelectedLines.size()));
		}
	};
	
	/**
	 * Method gets list of strings where every string is one line of the selected lines.
	 * 
	 * @param lineStart index of first line
	 * @param lineEnd index of last line
	 * @return ist of strings where every string is one line of the selected lines
	 */
	private List<String> getListOfSelectedLines(int lineStart, int lineEnd) {
		List<String> listOfSelectedLines = new ArrayList<>();
		
		for(int i = lineStart; i <= lineEnd; i++) {
			try {
				int start = currentEditor.getLineStartOffset(i);
				int end = currentEditor.getLineEndOffset(i) - 1;
				
				if(i == lineEnd) {
					end++;
				}
				
				String line = currentEditor.getDocument().getText(start, end - start);
				
				listOfSelectedLines.add(line);
			} catch (BadLocationException ignorable) {
			}
		}
		
		for(int i = lineStart; i <= lineEnd; i++) {
			try {
				int start = currentEditor.getLineStartOffset(i);
				int end = currentEditor.getLineEndOffset(i) - 1;
				
				if(i == lineEnd) {
					end++;
				}
				
				currentEditor.getDocument().remove(start, end - start);
			} catch (BadLocationException ignorable) {
			}
		}
		
		return listOfSelectedLines;
	}
	
	/**
	 * Method prints new lines to the text area.
	 * 
	 * @param listOfSelectedLines list of the selected lines
	 * @param lineStart index of the first line
	 * @param lineEnd index of the last line
	 */
	private void printNewLines(List<String> listOfSelectedLines, int lineStart, int lineEnd) {
		for(int i = lineStart, j = 0; i <= lineEnd; i++, j++) {
			try {
				int start = currentEditor.getLineStartOffset(i);
				
				String line = listOfSelectedLines.get(j);
				
				currentEditor.getDocument().insertString(start, line, null);
			} catch (BadLocationException ignorable) {
			}
		}
	}

	/**
	 * Method asks user if he wants to close the application.
	 */
	private void askUserForClosingTheApplication() {
		int pressed = checkIfThereAreUnsavedDocuments();
		
		if(pressed == JOptionPane.NO_OPTION) {
			return;
		}
		
		pressed = JOptionPane.showConfirmDialog(
				JNotepadPP.this,
				flp.getString("closeQuestion"),
				flp.getString("closing"),
				JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE);
		
		if(pressed == JOptionPane.NO_OPTION) {
			return;
		}
		
		terminateClock();
		dispose();
	}

	/**
	 * Method checks if there are unsaved document and asks user if he wants to save them.
	 * 
	 * @return option of {@link JOptionPane}
	 */
	private int checkIfThereAreUnsavedDocuments() {
		for(int i = 0; i < jTabbedPane.getTabCount(); i++) {
			jTabbedPane.setSelectedIndex(i);
			
			Icon icon = jTabbedPane.getIconAt(i);
			
			if(icon.equals(redDiskette)) {
				int pressed = JOptionPane.showConfirmDialog(
						JNotepadPP.this,
						flp.getString("saveQuestion") + jTabbedPane.getTitleAt(i) + "'?",
						flp.getString("saving"),
						JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.WARNING_MESSAGE);
				
				if(pressed == JOptionPane.NO_OPTION) {
					continue;
				}
				
				if(pressed == JOptionPane.CANCEL_OPTION) {
					return JOptionPane.NO_OPTION;
				}
				
				saveDocumentAction.actionPerformed(null);
			}
		}
	
		return JOptionPane.YES_OPTION;
	}
	
	/**
	 * Method finds index of next blank document.
	 * 
	 * @return index of next blank document
	 */
	private int findIndex() {
		if(indexesOfBlankDocuments.isEmpty()) {
			indexesOfBlankDocuments.add(1);
			
			return 1;
		}
		
		Collections.sort(indexesOfBlankDocuments);
		for(int i = 0, n = indexesOfBlankDocuments.size(); i < n - 1; i++) {
			if(i == 0 && indexesOfBlankDocuments.get(i) != 1) {
				indexesOfBlankDocuments.add(1);
				
				return 1;
			}
			
			if(indexesOfBlankDocuments.get(i + 1) - indexesOfBlankDocuments.get(i) != 1) {
				indexesOfBlankDocuments.add(indexesOfBlankDocuments.get(i) + 1);
				
				return indexesOfBlankDocuments.get(i) + 1;
			}
		}
		
		indexesOfBlankDocuments.add(indexesOfBlankDocuments.get(indexesOfBlankDocuments.size() - 1) + 1);
		
		return indexesOfBlankDocuments.get(indexesOfBlankDocuments.size() - 1);
	}
	
	/**
	 * Method checks if it is needed to remove the index from list of indexes of blank documents.
	 * 
	 * @param nameOfTheTab name of the accepted tab
	 */
	private void checkIfNeedRemoveIndexFromList(String nameOfTheTab) {
		if(nameOfTheTab.startsWith(flp.getString("new"))) {
			String valueAsString = nameOfTheTab.substring(flp.getString("new").length());
			
			int value;
			try {
				value = Integer.parseInt(valueAsString);
				
				indexesOfBlankDocuments.remove(indexesOfBlankDocuments.indexOf(value));
			} catch(NumberFormatException ignorable) {
			}
			
		}
	}
	
	/**
	 * Method creates the thread Clock.
	 */
	private void createClock() {
		Thread clock = new Thread(() -> {
			while(true) {
				SwingUtilities.invokeLater(() -> {
					if(jTabbedPane.getSelectedIndex() != -1) {
						JPanel jPanel = (JPanel) jTabbedPane.
								getSelectedComponent().getAccessibleContext().getAccessibleChild(INDEX_OF_STATUS_BAR_ON_JPANEL);
						
						JLabel clockLabel = (JLabel) jPanel.getComponent(INDEX_OF_CLOCK_LABEL_ON_JPANEL);
						
						clockLabel.setText(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
					}
				});
				
				try {
					Thread.sleep(NUMBER_OF_MILIS_CLOCK_SLEEP);
				} catch (InterruptedException ignorable) {
				}
				
				if(stopRequested) {
					return;
				}
			}	
		});
		
		clock.setDaemon(true);
		clock.start();
	}
	
	/**
	 * Method sets the flag which contains information is the needed to terminate the clock to true.
	 */
	public void terminateClock() {
		stopRequested = true;
	}
	
	/**
	 * Main method from which program starts.
	 * 
	 * @param args arguments of the command line, unused
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JNotepadPP().setVisible(true);
		});

	}
	
	/**
	 * Interface contains one method which changes and returns accepted char.
	 * 
	 * @author Toni Martinčić
	 * @version 1.0
	 */
	private static interface IChangeText {
		
		/**
		 * Method changes and returns accepted char.
		 * 
		 * @param c accepted char
		 * @return changed char
		 */
		public abstract char changeChar(char c);
		
	}
}