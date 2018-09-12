package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IDispatcher;
import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class implements {@link IWebWorker} and represents worker which accepts two integer numbers
 * and returns the result in html table.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class SumWorker implements IWebWorker {

	/**
	 * Default value for the first number.
	 */
	private static final Integer DEFAULT_VALUE_FOR_A = 1;
	
	/**
	 * Default value for the second number.
	 */
	private static final Integer DEFAULT_VALUE_FOR_B = 2;

	@Override
	public void processRequest(RequestContext context) throws Exception {
		Integer a = getNumber(context, DEFAULT_VALUE_FOR_A, "a");
		Integer b = getNumber(context, DEFAULT_VALUE_FOR_B, "b");
		
		context.setTemporaryParameter("a", String.valueOf(a));
		context.setTemporaryParameter("b", String.valueOf(b));
		context.setTemporaryParameter("zbroj", String.valueOf(a + b));
		
		IDispatcher dispatcher = context.getDispatcher();
		dispatcher.dispatchRequest("/private/calc.smscr");
	}

	/**
	 * Method returns integer number from accepted {@link RequestContext}.
	 * 
	 * @param context instance of {@link RequestContext}
	 * @param defaultValue default value
	 * @param name name 
	 * @return integer number
	 */
	private Integer getNumber(RequestContext context, Integer defaultValue, String name) {
		String numberAsString = context.getParameter(name);
		
		Integer number;
		
		if(numberAsString == null) {
			number = defaultValue;
		} else {
			try {
				number = Integer.parseInt(numberAsString);
			} catch(NumberFormatException exc) {
				number = defaultValue;
			}
		}
		
		return number;
	}
}
