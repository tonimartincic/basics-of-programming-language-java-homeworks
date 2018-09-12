package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class executes the smartcripts. Smartscripts are parsed with {@link SmartScriptParser} into 
 * document tree.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class SmartScriptEngine {
	
	/**
	 * Document node of the tree.
	 */
	private DocumentNode documentNode;
	
	/**
	 * Instance of {@link RequestContext}.
	 */
	private RequestContext requestContext;
	
	/**
	 * Instance of {@link ObjectMultistack}.
	 */
	private ObjectMultistack multistack = new ObjectMultistack();
	
	/**
	 * Map which maps functions tu number of arguments.
	 */
	private static Map<String, Integer> functionNumOfArgs = new HashMap<>();
	
	static {
		functionNumOfArgs.put("sin", 1);
		functionNumOfArgs.put("decfmt", 2);
		functionNumOfArgs.put("dup", 1);
		functionNumOfArgs.put("swap", 2);
		functionNumOfArgs.put("setMimeType", 1);
		functionNumOfArgs.put("paramGet", 2);
		functionNumOfArgs.put("pparamGet", 2);
		functionNumOfArgs.put("pparamSet", 2);
		functionNumOfArgs.put("pparamDel", 1);
		functionNumOfArgs.put("tparamGet", 2);
		functionNumOfArgs.put("tparamSet", 2);
		functionNumOfArgs.put("tparamDel", 1);
	}
	
	/**
	 * Instance of the {@link INodeVisitor}.
	 */
	private INodeVisitor visitor = new INodeVisitor() {

		@Override
		public void visitTextNode(TextNode node) {		
			try {
				requestContext.write(node.getText());
			} catch (IOException exc) {
				System.out.println("Error while writing text");
				
				System.exit(1);
			}
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			String variableName = node.getVariable().getName();
			
			int initialValue = Integer.parseInt(node.getStartExpression().asText());
			int endValue = Integer.parseInt(node.getEndExpression().asText());
			int stepValue = Integer.parseInt(node.getStepExpression().asText());
			
			if(initialValue > endValue) {
				return;
			}
			
			ValueWrapper valueWrapper = new ValueWrapper(initialValue);
			
			multistack.push(variableName, valueWrapper);
			
			while(true) {
				for(int i = 0, n = node.numberOfChildren(); i < n; i++) {
					node.getChild(i).accept(this);
				}
				
				int currentValue = (int) multistack.pop(variableName).getValue();
				
				currentValue += stepValue;
				
				valueWrapper.setValue(currentValue);
				multistack.push(variableName, valueWrapper);
				
				if(currentValue > endValue) {
					break;
				}
				
			}
			
			multistack.pop(variableName);
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			String temporaryStack = "";
			
			for(Element element : node.getElements()) {
				if(element instanceof ElementConstantInteger) {
					pushIntValueToStack(temporaryStack, element);
					
					continue;
				}
				
				if(element instanceof ElementConstantDouble) {
					pushDoubleValueToStack(temporaryStack, element);
					
					continue;
				}
				
				if(element instanceof ElementString) {
					if(element.asText().contains("\r\n")) {
						continue;
					}
					
					String newString = element.asText().replace("\\n", "\n")
													   .replace("\\r", "\r")
													   .replace("\\t", "\t");;
					
					Element newElement = new ElementString(newString);
					
					pushStringValueToStack(temporaryStack, newElement);
					
					continue;
				}
				
				if(element instanceof ElementVariable) {
					pushVariableValueToStack(temporaryStack, element);
					
					continue;
				}
				
				if(element instanceof ElementOperator) {
					doOperationAndPushResultToStack(temporaryStack, element);
					
					continue;
				}
				
				if(element instanceof ElementFunction) {
					calculateFunction(temporaryStack, element);
				}
			}
			
			List<ValueWrapper> listOfValues = new ArrayList<>();
			while(!multistack.isEmpty(temporaryStack)) {
				listOfValues.add(multistack.pop(temporaryStack));
			}
			
			for(int i = listOfValues.size() - 1; i >= 0; i--) {
				ValueWrapper valueWrapper = listOfValues.get(i);
				Object value = valueWrapper.getValue();
				
				try {
					requestContext.write(value.toString());
				} catch (IOException exc) {
					System.out.println("Error while writing data");
					
					System.exit(1);
				}
			}
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for(int i = 0, n = node.numberOfChildren(); i < n; i++) {
				node.getChild(i).accept(this);
			}
		}
		
	};

	/**
	 * Constructor which accepts two arguments; documentNode and requestContext.
	 * 
	 * @param documentNode accepted document node
	 * @param requestContext accepted instance of {@link RequestContext}
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		if(documentNode == null) {
			throw new IllegalArgumentException("Argument documentNode can not be null.");
		}
		
		if(requestContext == null) {
			throw new IllegalArgumentException("Argument requestContext can not be null.");
		}
		
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}

	/**
	 * Method calls method accept on document node with visitor created in this class.
	 */
	public void execute() {
		documentNode.accept(visitor);
	}
	
	/**
	 * Method pushes int value to the stack.
	 * 
	 * @param temporaryStack temporary stack
	 * @param element element which is pushed to the stack
	 */
	private void pushIntValueToStack(String temporaryStack, Element element) {
		int value = ((ElementConstantInteger) element).getValue();
		multistack.push(temporaryStack, new ValueWrapper(value));
	}
	
	/**
	 * Method pushes double value to the stack.
	 * 
	 * @param temporaryStack temporary stack
	 * @param element element which is pushed to the stack
	 */
	private void pushDoubleValueToStack(String temporaryStack, Element element) {
		double value = ((ElementConstantDouble) element).getValue();
		multistack.push(temporaryStack, new ValueWrapper(value));
	}
	
	/**
	 * Method pushes string value to the stack.
	 * 
	 * @param temporaryStack temporary stack
	 * @param element element which is pushed to the stack
	 */
	private void pushStringValueToStack(String temporaryStack, Element element) {
		String value = ((ElementString) element).getValue();
		multistack.push(temporaryStack, new ValueWrapper(value));
	}
	
	/**
	 * Method pushes value to the stack.
	 * 
	 * @param temporaryStack temporary stack
	 * @param element element which is pushed to the stack
	 */
	private void pushVariableValueToStack(String temporaryStack, Element element) {
		String variableName = ((ElementVariable) element).getName();
		
		ValueWrapper valueWrapper = multistack.peek(variableName);
		multistack.push(temporaryStack, valueWrapper);
	}
	
	/**
	 * Method does the operation and pushes the result to the stack. 
	 * 
	 * @param temporaryStack temporary stack
	 * @param element accepted element
	 */
	private void doOperationAndPushResultToStack(String temporaryStack, Element element) {
		String symbol = ((ElementOperator) element).getSymbol();
		
		ValueWrapper firstValue = multistack.pop(temporaryStack);
		ValueWrapper secondValue = multistack.pop(temporaryStack);
		
		ValueWrapper result = new ValueWrapper(firstValue.getValue());

		doOperation(symbol, result, secondValue);
		
		multistack.push(temporaryStack, result);
	}

	/**
	 * Method does the operation.
	 * 
	 * @param symbol symbol of the operation
	 * @param result result of the operation
	 * @param secondValue second operator
	 */
	private void doOperation(String symbol, ValueWrapper result, ValueWrapper secondValue) {
		switch (symbol) {
			case "+" :
				
				result.add(secondValue.getValue());
				
				break;
				
			case "-" :
				result.subtract(secondValue.getValue());
				
				break;
				
			case "*" :
				result.multiply(secondValue.getValue());
				
				break;
			
			case "/" :
				result.divide(secondValue.getValue());
				
				break;
				
			default :
				throw new IllegalArgumentException("Invalid symbol for operator.");
		}
	}
	
	/**
	 * Method calculates the function.
	 * 
	 * @param temporaryStack temporary stack
	 * @param element accepted element
	 */
	private void calculateFunction(String temporaryStack, Element element) {
		String functionName = ((ElementFunction) element).getName();
		int numberOfArguments = functionNumOfArgs.get(functionName);
		
		ValueWrapper[] arguments = new ValueWrapper[numberOfArguments];
		
		for(int i = 0; i < numberOfArguments; i++) {
			arguments[i] = multistack.pop(temporaryStack);
		}
		
		switch (functionName) {
			case "sin" :
				doSinFunction(temporaryStack, arguments);
				
				break;
				
			case "decfmt" :
				doDecfmtFunction(temporaryStack, arguments);
				
				break;
				
			case "dup" :
				doDupFunction(temporaryStack, arguments);
				
				break;
				
			case "swap" :
				doSwapFunction(temporaryStack, arguments);
				
				break;
			
			case "setMimeType" :
				doSetMimeTypeFunction(arguments);
				
				break;
				
			case "paramGet" :
				doParamGetFunction(temporaryStack, arguments);
				
				break;
				
			case "pparamGet" :
				doPParamGetFunction(temporaryStack, arguments);
				
				break;
				
			case "pparamSet" :
				doPParamSetFunction(arguments);
				
				break;
			
			case "pparamDel" :
				doPParamDelFunction(arguments);
				
				break;
				
			case "tparamGet" :
				doTParamGetFunction(temporaryStack, arguments);
				
				break;
				
			case "tparamSet" :
				doTParamSetFunction(arguments);
				
				break;
				
			case "tparamDel" :
				doTParamDelFunction(arguments);
				
				break;
				
			default :
				throw new IllegalArgumentException("Invalid function name.");
		}
	}

	/**
	 * Method does the TParamSetFunction.
	 * 
	 * @param arguments accepted arguments for the function
	 */
	private void doTParamSetFunction(ValueWrapper[] arguments) {
		String value = String.valueOf(arguments[1].getValue());
		
		requestContext.setTemporaryParameter(
				(String)arguments[0].getValue(),
				value);
	}

	/**
	 * Method does the SinFunction.
	 * 
	 * @param temporaryStack temporary stack
	 * @param arguments accepted arguments for the function
	 */
	private void doSinFunction(String temporaryStack, ValueWrapper[] arguments) {
		if(arguments[0].getValue() instanceof String) {
			double value = Double.parseDouble((String) arguments[0].getValue());
			arguments[0] = new ValueWrapper(value);
		}
		
		double result = 0.0;
		
		if(arguments[0].getValue() instanceof Double) {
			double value = (double) arguments[0].getValue();
			
			result = Math.sin(value);
		} else {
			int value = (int) arguments[0].getValue();
			double doubleValue = value * 1.0 * Math.PI / 180.0;
			
			result = Math.sin(doubleValue);
		}
		
		multistack.push(temporaryStack, new ValueWrapper(result));
	}
	
	/**
	 * Method does the DecfmtFunction.
	 * 
	 * @param temporaryStack temporary stack
	 * @param arguments accepted arguments for the function
	 */
	private void doDecfmtFunction(String temporaryStack, ValueWrapper[] arguments) {
		DecimalFormat decimalFormat = new DecimalFormat((String) arguments[0].getValue());
		double value = (double) arguments[1].getValue();
		
		String result = decimalFormat.format(value);
		multistack.push(temporaryStack, new ValueWrapper(result));
	}

	/**
	 * Method does the DupFunction.
	 * 
	 * @param temporaryStack temporary stack
	 * @param arguments accepted arguments for the function
	 */
	private void doDupFunction(String temporaryStack, ValueWrapper[] arguments) {
		multistack.push(temporaryStack, new ValueWrapper(arguments[0].getValue()));
		multistack.push(temporaryStack, new ValueWrapper(arguments[0].getValue()));
	}
	
	/**
	 * Method does the SwapFunction.
	 * 
	 * @param temporaryStack temporary stack.
	 * @param arguments accepted arguments for the function
	 */
	private void doSwapFunction(String temporaryStack, ValueWrapper[] arguments) {
		multistack.push(temporaryStack, arguments[0]);
		multistack.push(temporaryStack, arguments[1]);
	}
	
	/**
	 * Method does the SetMimeTypeFunction.
	 * 
	 * @param arguments accepted arguments for the function
	 */
	private void doSetMimeTypeFunction(ValueWrapper[] arguments) {
		requestContext.setMimeType((String) arguments[0].getValue());
	}
	
	/**
	 * Method does the ParamGetFunction.
	 * 
	 * @param temporaryStack temporary stack
	 * @param arguments accepted arguments for the function
	 */
	private void doParamGetFunction(String temporaryStack, ValueWrapper[] arguments) {
		String value = requestContext.getParameter((String) arguments[1].getValue());
	
		ValueWrapper valueWrapper = new ValueWrapper(value == null ? arguments[0].getValue() : value);
		multistack.push(temporaryStack, valueWrapper);
	}
	
	/**
	 * Method does PParamGetFunction.
	 * 
	 * @param temporaryStack temporary stack
	 * @param arguments accepted arguments for the function
	 */
	private void doPParamGetFunction(String temporaryStack, ValueWrapper[] arguments) {
		String value = requestContext.getPersistentParameter((String) arguments[1].getValue());
		
		ValueWrapper valueWrapper = new ValueWrapper(value == null ? arguments[0].getValue() : value);
		multistack.push(temporaryStack, valueWrapper);
	}
	
	/**
	 * Method does the PParamSetFunction.
	 * 
	 * @param arguments accepted arguments for the function
	 */
	private void doPParamSetFunction(ValueWrapper[] arguments) {
		String value = String.valueOf(arguments[1].getValue());
		
		requestContext.setPersistentParameter(
				(String)arguments[0].getValue(),
				value);
	}
	
	/**
	 * Method does the PParamDelFunction.
	 * 
	 * @param arguments accepted arguments for the function
	 */
	private void doPParamDelFunction(ValueWrapper[] arguments) {
		requestContext.removePersistentParameter((String) arguments[0].getValue());
	}

	/**
	 * Method does the TParamGetFunction.
	 * 
	 * @param temporaryStack temporary stack
	 * @param arguments accepted arguments for the function
	 */
	private void doTParamGetFunction(String temporaryStack, ValueWrapper[] arguments) {
		String value = requestContext.getTemporaryParameter((String) arguments[1].getValue());
		
		ValueWrapper valueWrapper = new ValueWrapper(value == null ? arguments[0] : value);
		multistack.push(temporaryStack, valueWrapper);
	}
	
	/**
	 * Method does the TParamDelFunction.
	 * 
	 * @param arguments accepted arguments for the function
	 */
	private void doTParamDelFunction(ValueWrapper[] arguments) {
		requestContext.removeTemporaryParameter((String) arguments[0].getValue());
	}
}