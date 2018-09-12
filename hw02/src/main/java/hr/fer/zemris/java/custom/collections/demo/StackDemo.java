package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Command-line application which accepts a single command-line argument: expression which 
 * should be evaluated. Expression must be in postfix representation.
 * Program evaluates the expression and prints the result to the standard output.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class StackDemo {
	
	/**
	 * The method which starts the execution of this program.
	 * 
	 * @param args command line argument which represents postfix expression
	 */
	public static void main(String[] args) {
		
		if(args.length != 1) {
			System.out.println("Application accepts a single command-line argument: expression which should be evaluated.");
			return;
		}
		
		String expression = args[0];
		String[] parts = expression.split("\\s+");
		
		ObjectStack stack = new ObjectStack();
		
		for(String element : parts) {
			try {
				int number = Integer.parseInt(element);
				stack.push(number);
			} catch(NumberFormatException exc) {
				int number1 = 0;
				int number2 = 0;
				
				try {
					number1 = (int)stack.pop();
					number2 = (int)stack.pop();
				} catch(EmptyStackException exc2) {
					System.out.println("Expression is invalid. Expression must be in postfix representation.");
					return;
				}
				
				switch(element) {
					case "+":
						stack.push(number2 + number1);
						break;
						
					case "-":
						stack.push(number2 - number1);
						break;
						
					case "/":
						if(number1 == 0) {
							System.out.println("Expression is invalid because of dividing with zero.");
							return;
						}
						stack.push(number2 / number1);
						break;
						
					case "*":
						stack.push(number2 * number1);
						break;
						
					case "%":
						if(number1 == 0) {
							System.out.println("Expression is invalid because of dividing with zero.");
							return;
						}
						stack.push(number2 % number1);
						break;
						
					default:
						System.out.println("Expression is invalid. Input must be only integer numbers or operators: +, -, /, *, %.");
						return;
				}
			}
		}
		
		if(stack.size() != 1) {
			System.out.println("Expression is invalid. Expression must be in postfix representation.");
		} else {
			System.out.printf("Expression evaluates to %d.%n", stack.pop());
		}
	}

}
