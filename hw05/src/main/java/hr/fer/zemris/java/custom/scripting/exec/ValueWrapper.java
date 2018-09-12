package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Class has a read-write property value of type Object which can be null value or object of 
 * any type.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class ValueWrapper {
	
	/**
	 * Default value if argument is null.
	 */
	private static final Integer DEFAULT_INT_VALUE = 0;
	
	/**
	 * Value of the ValueWrapper.
	 */
	private Object value;
	
	/**
	 * Constructor which accepts one argument, value.
	 * 
	 * @param value value
	 */
	public ValueWrapper(Object value) {
		super();
		
		this.value = value;
	}
	
	/**
	 * Getter for the value.
	 * 
	 * @return value
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * Setter for the value
	 * 
	 * @param value value
	 */
	public void setValue(Object value) {
		this.value = value;
	}
	
	/**
	 * Method adds accepted value to the this value.
	 * 
	 * @param incValue accepted value which is added to this value
	 */
	public void add(Object incValue) {
		Object incValue2 = prepareValues(incValue);
	
		if((value instanceof Double) && (incValue2 instanceof Double)) {
			value = (Double)value + (Double)incValue2; 
			
			return;
		}
		
		value = (Integer)value + (Integer)incValue2; 
	}
	
	/**
	 * Method subtracts accepted value from this value.
	 * 
	 * @param decValue value which is subtracted from this value
	 */
	public void subtract(Object decValue) {
		Object decValue2 = prepareValues(decValue);
		
		if((value instanceof Double) && (decValue2 instanceof Double)) {
			value = (Double)value - (Double)decValue2; 
			
			return;
		}
		
		value = (Integer)value - (Integer)decValue2; 
	}

	/**
	 * Method multiplies this value with accepted value.
	 * 
	 * @param mulValue value with which this value is multiplied
	 */
	public void multiply(Object mulValue) {
		Object mulValue2 = prepareValues(mulValue);
		
		if((value instanceof Double) && (mulValue2 instanceof Double)) {
			value = (Double)value * (Double)mulValue2; 
			
			return;
		}
		
		value = (Integer)value * (Integer)mulValue2;
	}
	
	/**
	 * Method divides this value with accepted value.
	 * 
	 * @param divValue value with which this value is divided
	 */
	public void divide(Object divValue) {
		Object divValue2 = prepareValues(divValue);
		
		if((value instanceof Double) && (divValue2 instanceof Double)) {
			if((Double) divValue2 == 0.0) {
				throw new IllegalArgumentException("Can't divide with 0.");
			}
	
			value = (Double)value / (Double)divValue2; 
			
			return;
		}
		
		if((Integer) divValue2 == 0) {
			throw new IllegalArgumentException("Can't divide with 0.");
		}
		
		value = (Integer)value / (Integer)divValue2;
	}
	
	/**
	 * Method compares this value with accepted value.
	 * 
	 * @param withValue value with which this value is compared
	 * @return 0 if values are equals, positive number if this value is greater than accepted
	 * value, and negative number if this value is less than accepted value
	 */
	public int numCompare(Object withValue) {
		Object withValue2 = prepareValues(withValue);
		
		if((value instanceof Double) && (withValue2 instanceof Double)) {
			return (int) ((Double)value - (Double)withValue2); 
			
		}
		
		return (Integer)value - (Integer)withValue2; 
	} 
	
	/**
	 * Method converts values by rules below.
	 * If any of current value or argument is null it is converted into Integer with value 0.
	 * String is converted into Double or Integer. If either current value or argument is Double, 
	 * operation should be performed on Doubles, and result should be stored as an instance of Double.
	 * If not, both arguments must be Integers so operation should be performed on Integers and
	 * result stored as an Integer.
	 * 
	 * @param value accepted value
	 * @return converted accepted value
	 */
	private Object prepareValues(Object value) {
		value = prepareValue(value);
		this.value = prepareValue(this.value);
		
		return convertToDoubleIfNeeded(value);
	}
	
	/**
	 * Method converts accepted value by rules in javadoc of method prepareValues.
	 * 
	 * @param value accepted value
	 * @return converted accepted value
	 * @throws RuntimeException if value is not null or instance of Integer, Double or String
	 * or String can not be parsed into Double or Integer, {@link RuntimeException} is thrown
	 */
	private Object prepareValue(Object value) {
		if(value == null) {
			return new Integer(DEFAULT_INT_VALUE);
		}
		
		if(value instanceof Integer) {
			return (Integer) value;
		}
		
		if(value instanceof Double) {
			return (Double) value;
		}
		
		if(value instanceof String) {
			try {
				if(((String) value).contains(".") || 
				   ((String) value).toUpperCase().contains("E")) {
					
					return Double.parseDouble((String) value);
				}
				
				return Integer.parseInt((String) value);
				
			} catch(NumberFormatException exc) {
				throw new RuntimeException(
						"Value is not valid String. It must represent Integer or Double number");
			}
		}
		
		throw new RuntimeException(
				"Given value must be null or instance of  Integer, Double or String");
	}
	
	/**
	 * Method converts value of this ValueWrapper or accepted value to Double if it is needed.
	 * If one of them is instance of Double, method converts other one into the Double.
	 * 
	 * @param value accepted value
	 * @return accepted value
	 */
	private Object convertToDoubleIfNeeded(Object value) {
		if(((this.value instanceof Integer) && (value instanceof Integer)) ||
		   ((this.value instanceof Double) && (value instanceof Double))){
			return value;
		}
		
		if(this.value instanceof Integer) {
			this.value = new Double((Integer) this.value);
		}
		
		if(value instanceof Integer) {
			value = new Double((Integer) value);
		}
		
		return value;
	}
	
}
