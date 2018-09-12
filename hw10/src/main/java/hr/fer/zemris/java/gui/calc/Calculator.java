package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.awt.Insets;
import java.util.Stack;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;
import static hr.fer.zemris.java.gui.calc.RowAndColumnsConstants.*;

/**
 * When the program is started window frame is created. Inside the window is calculator which contains screen where
 * the typed shows. Calculator contains digit buttons, buttons for the instant operations which are applied directly
 * on value which is on screen of the calculator, buttons for other operations, dot button, equals button, button
 * clr which cleares the screen, res button which resets the calculator, push and pop buttons which work with internal
 * stack of the calculator and the inv checkbox which inverts the meaning of the some operations.
 * 
 * Instant operations are 1/x, log, ln, sin, cos, tg, ctg, +/-. Other operations which expect other argument are
 * +, -, *, /, power.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class Calculator extends JFrame {
	
	/**
	 * Universal version identifier for a Serializable class.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Gap between the components.
	 */
	private static final int GAP = 3;
	
	/**
	 * Inset of the components.
	 */
	private static final int INSET = 0;
	
	/**
	 * Component x of location of the window.
	 */
	private static final int X_LOCATION_OF_THE_WINDOW = 100;
	
	/**
	 * Component y of location of the window.
	 */
	private static final int Y_LOCATION_OF_THE_WINDOW = 100;
	
	/**
	 * Instance of the {@link IOperation} which method calculate returns sum of the accepted values.
	 */
	private static final IOperation ADD = (v1, v2) -> v1 + v2;
	
	/**
	 * Instance of the {@link IOperation} which method calculate subtract accepted values.
	 */
	private static final IOperation SUB = (v1, v2) -> v1 - v2;
	
	/**
	 * Instance of the {@link IOperation} which method calculate multiplies accepted values.
	 */
	private static final IOperation MUL = (v1, v2) -> v1 * v2;
	
	/**
	 * Instance of the {@link IOperation} which method calculate divides accepted values.
	 */
	private static final IOperation DIV = (v1, v2) -> v1 / v2;
	
	/**
	 * Instance of the {@link IOperation} which method calculate returns value1 power value2.
	 */
	private static final IOperation POWER = (v1, v2) -> Math.pow(v1, v2);
	
	/**
	 * Instance of the {@link IOperation} which method calculate returns nsqrt value1 where n is value2.
	 */
	private static final IOperation NSQRT = (v1, v2) -> Math.pow(v1, 1 / v2);
	
	/**
	 * Screen of the calculator.
	 */
	private JLabel screen;
	
	/**
	 * Checkbox which inverts meaning of the operations.
	 */
	private JCheckBox inv;
	
	/**
	 * Current value in the calculator.
	 */
	private double currentValue;
	
	/**
	 * Current operation in the calculator.
	 */
	private IOperation currentOperation;
	
	/**
	 * Internal stack of the calculator.
	 */
	private Stack<String> stack;
	
	/**
	 * Flag which contain information is the needed to clear the screen when the new digit button is pressed before 
	 * this value is printed on the screen.
	 */
	private boolean clearScreen;
	
	/**
	 * Constructor which accepts no arguments. It sets current value of the calculator to the 0 and current operation
	 * to the null. It sets DefaultCloseOperation of the calculator and the location of the calculator on the desktop.
	 */
	public Calculator() {
		super();
		
		currentValue = 0;
		currentOperation = null;
		stack = new Stack<>();
		clearScreen = false;
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		setLocation(X_LOCATION_OF_THE_WINDOW, Y_LOCATION_OF_THE_WINDOW);
		setTitle("Calculator");

		initGUI();
	}
	
	/**
	 * Method which initialize the calculator. It creates screen of the calculator and calls all other methods which
	 * add buttons to the calculator.
	 */
	private void initGUI() {
		Container cp = getContentPane();

		cp.setLayout(new CalcLayout(GAP));
		
		screen = new JLabel();
		screen.setBorder(new LineBorder(Color.BLUE));
		screen.setBackground(Color.YELLOW);
		screen.setOpaque(true);
		screen.setHorizontalAlignment(SwingConstants.RIGHT);
		
		cp.add(screen, new RCPosition(RowAndColumnsConstants.ROW_OF_SCREEN, COLUMN_OF_SCREEN));
		
		inv = new JCheckBox("Inv");
		cp.add(inv, new RCPosition(ROW_OF_CHECKBOX, COLUM_OF_CHECKBOX));
		
		addDigitButtons(cp);
		addNegateButton(cp);
		addDotButton(cp);
		addEqualsButton(cp);
		addClrAndResButtons(cp);
		addStackButtons(cp);
		addInstantOperationButtons(cp);
		addOperationButtons(cp);
		
		setMargins(cp);
		
		pack();
	}

	/**
	 * Method adds dot button to the calculator. Dot is used for decimal numbers. When the dot button is pressed,
	 * dot is added to the text which is on the screen of the calculator.
	 * 
	 * @param cp container of the calculator window
	 */
	private void addDotButton(Container cp) {
		JButton dotButton = new JButton(".");
		cp.add(dotButton, new RCPosition(ROW_OF_DOT_BUTTON, COLUMN_OF_DOT_BUTTON));
		
		dotButton.addActionListener(e -> screen.setText(screen.getText() + "."));
	}

	/**
	 * Method adds negate button to the calculator. When the negate button is pressed, value showed on the screen
	 * of the calculator is negated.
	 * 
	 * @param cp container of the calculator window
	 */
	private void addNegateButton(Container cp) {
		JButton negateButton = new JButton("+/-");
		cp.add(negateButton, new RCPosition(ROW_OF_NEGATE_BUTTON, COLUMN_OF_NEGATE_BUTTON));
		
		negateButton.addActionListener(e -> {
			double value = Double.parseDouble(screen.getText());
			screen.setText(String.valueOf(-value));
		});
	}

	/**
	 * Method adds equals button to the calculator. When the equals button is pressed, current operation in calculator 
	 * is calculated on the current value in the calculator and on the value on the screen of the calculator.
	 * 
	 * @param cp container of the calculator window
	 */
	private void addEqualsButton(Container cp) {
		JButton equalsButton = new JButton("=");
		cp.add(equalsButton, new RCPosition(FIRST_ROW_OF_OPERATION_BUTTONS, COLUMN_OF_OPERATION_BUTTONS));
		
		equalsButton.addActionListener(e -> {
			screen.setText(String.valueOf(currentOperation.calculate(currentValue, Double.parseDouble(screen.getText()))));
			currentOperation = null;
		});
	}

	/**
	 * Method adds clr and res buttons to the calculator. 
	 * When the clr button is pressed, screen of the calculator is cleared.
	 * When the res button is pressed, screen of the calculator is cleared, current value in the calculator is set
	 * to 0 and current operation in the calculator is set to the null value.
	 * 
	 * @param cp container of the calculator window
	 */
	private void addClrAndResButtons(Container cp) {
		JButton clrButton = new JButton("clr");
		cp.add(clrButton, new RCPosition(ROW_OF_CLR_BUTTON, COLUMN_OF_CLR_BUTTON));
		
		clrButton.addActionListener(e -> screen.setText(""));
		
		JButton resButton = new JButton("res");
		cp.add(resButton, new RCPosition(ROW_OF_RES_BUTTON, COLUMN_OF_RES_BUTTON));
		
		resButton.addActionListener(e -> {
			screen.setText("");
			currentValue = 0;
			currentOperation = null;
			clearScreen = false;
			stack.clear();
		});
	}

	/**
	 * Method add operation buttons to the calculator. Operation buttons of operations which calculate with current
	 * value in the calculator and the current value on the screen of the calculator are added. Operations which
	 * calculate with current value in the calculator and with the value on the screen of the calculator are
	 * +, -, *, /, power.
	 * 
	 * @param cp container of the calculator window
	 */
	private void addOperationButtons(Container cp) {
		JButton divButton = new JButton("/");
		cp.add(divButton, new RCPosition(FIRST_ROW_OF_OPERATION_BUTTONS + 1, COLUMN_OF_OPERATION_BUTTONS));
		divButton.addActionListener(e -> doOperation(DIV));
		
		JButton mulButton = new JButton("*");
		cp.add(mulButton, new RCPosition(FIRST_ROW_OF_OPERATION_BUTTONS + 2, COLUMN_OF_OPERATION_BUTTONS));
		mulButton.addActionListener(e -> doOperation(MUL));
		
		JButton subButton = new JButton("-");
		cp.add(subButton, new RCPosition(FIRST_ROW_OF_OPERATION_BUTTONS + 3, COLUMN_OF_OPERATION_BUTTONS));
		subButton.addActionListener(e -> doOperation(SUB));
		
		JButton addButton = new JButton("+");
		cp.add(addButton, new RCPosition(FIRST_ROW_OF_OPERATION_BUTTONS + 4, COLUMN_OF_OPERATION_BUTTONS));
		addButton.addActionListener(e -> doOperation(ADD));
	}
	
	/**
	 * Method adds buttons of the instant operations of the calculator. Instant operations are applied directly on
	 * the value on the screen of the calculator. Instant operations are 1/x, log, ln, sin, cos, tg, ctg, +/-.
	 * 
	 * @param cp container of the calculator window
	 */
	private void addInstantOperationButtons(Container cp) {
		JButton reciprocalButton = new JButton("1/x");
		cp.add(reciprocalButton, new RCPosition(FIRST_ROW_OF_INSTANT_OPERATIONS, FIRST_COLUM_OF_INSTANT_OPERATIONS));
		reciprocalButton.addActionListener(e -> doInstantOperation(v ->  1 / v)); 
		
		JButton logButton = new JButton("log");
		cp.add(logButton, new RCPosition(FIRST_ROW_OF_INSTANT_OPERATIONS + 1, FIRST_COLUM_OF_INSTANT_OPERATIONS));
		logButton.addActionListener(e -> doInstantOperation(v -> inv.isSelected() ? Math.pow(10, v) : Math.log10(v)));
		
		JButton lnButton = new JButton("ln");
		cp.add(lnButton, new RCPosition(FIRST_ROW_OF_INSTANT_OPERATIONS + 2, FIRST_COLUM_OF_INSTANT_OPERATIONS));
		lnButton.addActionListener(e -> doInstantOperation(v -> inv.isSelected() ? Math.exp(v) : Math.log(v)));
		
		JButton powerButton = new JButton("x^n");
		cp.add(powerButton, new RCPosition(FIRST_ROW_OF_INSTANT_OPERATIONS + 3, FIRST_COLUM_OF_INSTANT_OPERATIONS));
		powerButton.addActionListener(e -> doOperation(inv.isSelected() ? NSQRT : POWER));
		
		JButton sinButton = new JButton("sin");
		cp.add(sinButton, new RCPosition(FIRST_ROW_OF_INSTANT_OPERATIONS, SECOND_COLUMN_OF_INSTANT_OPERATIONS));
		sinButton.addActionListener(e -> doInstantOperation(v -> inv.isSelected() ? Math.asin(v) : Math.sin(v)));
			
		JButton cosButton = new JButton("cos");
		cp.add(cosButton, new RCPosition(FIRST_ROW_OF_INSTANT_OPERATIONS + 1, SECOND_COLUMN_OF_INSTANT_OPERATIONS));
		cosButton.addActionListener(e -> doInstantOperation(v -> inv.isSelected() ? Math.acos(v) : Math.cos(v)));
		
		JButton tanButton = new JButton("tan");
		cp.add(tanButton, new RCPosition(FIRST_ROW_OF_INSTANT_OPERATIONS + 2, SECOND_COLUMN_OF_INSTANT_OPERATIONS));
		tanButton.addActionListener(e -> doInstantOperation(v -> inv.isSelected() ? Math.atan(v) : Math.tan(v)));
		
		JButton ctgButton = new JButton("ctg");
		cp.add(ctgButton, new RCPosition(FIRST_ROW_OF_INSTANT_OPERATIONS + 3, SECOND_COLUMN_OF_INSTANT_OPERATIONS));
		ctgButton.addActionListener(e -> doInstantOperation(v -> inv.isSelected() ? Math.atan(1 / v) + Math.PI / 2 : 1 / Math.tan(v)));
	}

	/**
	 * Method adds operation buttons which work with the internal stack of the calculator. Operation which work
	 * with stack are push and pop. Push operation pushes value from the screen of the calculator to the internal 
	 * stack and operation pop pops element from the internal stack and prints it to the screen of the calculator.
	 * 
	 * @param cp container of the calculator window
	 */
	private void addStackButtons(Container cp) {
		JButton pushButton = new JButton("push");
		cp.add(pushButton, new RCPosition(FIRST_ROW_OF_STACK_BUTTONS, COLUMN_OF_STACK_BUTTONS));
		
		pushButton.addActionListener(e -> {
			stack.push(screen.getText());
		});
		
		JButton popButton = new JButton("pop");
		cp.add(popButton, new RCPosition(FIRST_ROW_OF_STACK_BUTTONS + 1, COLUMN_OF_STACK_BUTTONS));
		
		popButton.addActionListener(e -> {
			if(stack.isEmpty()) {
				screen.setText("Stack is empty");
				
				return;
			} 
			
			screen.setText(stack.pop());
		});
	}

	/**
	 * Method adds digit buttons to the calculator. When the digit button is pressed digit of that button is added
	 * to the screen which is showed on the screen of the calculator. 
	 * 
	 * @param cp container of the calculator window
	 */
	private void addDigitButtons(Container cp) {
		int currentDigit = 0;
		
		for(int i = LAST_ROW_OF_DIGITS; i >= FIRST_ROW_OF_DIGITS ; i--) {
			for(int j = FIRST_COLUMN_OF_DIGITS; j <= LAST_COLUMN_OF_DIGITS; j++) {
				if(i == LAST_ROW_OF_DIGITS && (j == LAST_COLUMN_OF_DIGITS - 1 || j == LAST_COLUMN_OF_DIGITS)) {
					continue;
				}
				
				JButton digitButton = new JButton(String.valueOf(currentDigit));
				cp.add(digitButton, new RCPosition(i, j));
				
				digitButton.addActionListener(e -> {
					if(clearScreen) {
						screen.setText("");
						
						clearScreen = false;
					}
					
					screen.setText(screen.getText() + digitButton.getText());
				});
				
				currentDigit++;
			}
		}
	}

	/**
	 * Method sets margins of the components to the value of the constant INSET.
	 * 
	 * @param cp container of the calculator window
	 */
	private void setMargins(Container cp) {
		for(int i = 0; i < cp.getComponentCount(); i++) {
			if(cp.getComponent(i) instanceof JButton) {
				((JButton) cp.getComponent(i)).setMargin(new Insets(INSET, INSET, INSET, INSET));
			}
		}
		
	}

	/**
	 * Method calls method calculate of the accepted instance of {@link IOperation} on the current value in the 
	 * calculator and the current value on the screen of the calculator and shows the result of the operation
	 * on the screen of the calculator.
	 * 
	 * @param operation accepted instance of {@link IOperation}
	 */
	private void doOperation(IOperation operation) {
		if(currentOperation == null) {
			currentValue = Double.parseDouble(screen.getText());
		} else {
			currentValue = currentOperation.calculate(currentValue, Double.parseDouble(screen.getText()));
			screen.setText(String.valueOf(currentValue));
		}
		
		currentOperation = operation;
		clearScreen = true;
	}

	/**
	 * Method calls method calculate of the accepted instance of {@link IInstantOperation} on the current value in
	 * the calculator and the current value on the screen of the calculator and shows the result of the operation
	 * on the screen of the calculator.
	 * 
	 * @param instantOperation
	 */
	private void doInstantOperation(IInstantOperation instantOperation) {
		double value = Double.parseDouble(screen.getText());
		
		screen.setText(String.valueOf(instantOperation.calculate(value)));
	}

	/**
	 * Main method from which program starts.
	 * 
	 * @param args arguments of the command line, unused
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Calculator().setVisible(true);
		});
	}
}
