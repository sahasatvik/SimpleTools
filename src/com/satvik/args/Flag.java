
package com.satvik.args;

/**
 * This is the class describing a type of Flag which must be assigned a value. Flags are used extensively 
 * by classes in this package.
 * <p>
 * In this method of interpreting flags, eaddch flag may have two synonimous forms : a 'short' form 
 * (-[charcacter]) and a long form (--[flag-name]), eg. a flag for 'help' may be represented by 
 * '-h' or '--help'. Flags have two states : on(true) and off(false) (off/false by default).
 * Flags may also carry values with a '=' character immediately following either the long or short 
 * form, and preceding the actual value given to it, eg. '--max-val=20' will assign the string '20'
 * to the flag represented by '--max-val'. 
 * <p>
 * If a Flag object is to carry a value, it must call the constructor {@code Flag(String, String, Class<T>)},
 * and it's container variable must be of type {@code Flag<T>}, where T is the type of value the Flag
 * can hold. An example of it's usage follows : <br>
 * <pre> {@code
 *
 * 	.
 * 	.
 * 	.
 *
 *	Flag help = new Flag("-h", "--help");		// Normal flag without value. Note that 
 *							// no value can be queried from it using 
 *							// flag.getValue() : attempting to 
 *							// do so throws a FlagException
 *	Flag<Integer> min = new Flag<>("-m", "--min", Integer.class);
 *							// This Flag may be assigned an Integer by 
 *							// ArgHandler while processing
 *	Flag<Integer> max = new Flag<>("-M", "--max", Integer.class, 10);
 *							// This Flag also has a default value of 10
 *	.
 *	.						// Create an ArgHandler and pass the Flags to it
 *	.
 *	
 *	Boolean needsHelp = help.getState();		// The state of the Flag can be retrieved 
 *							// by calling help.getState()
 *	Integer minVal = min.getValue();		// The value of min.getValue() can be stored 
 *							// directly in minVal, due to the use of generics
 *	Integer maxVal = max.getValue();		// If no value has been set, the default value
 *							// specified will be returned
 *	
 *	.
 *	.
 *	.
 *
 * }</pre>
 * <p>
 *
 * 	@author		Satvik Saha
 * 	@version	1.1, 04/05/2016
 * 	@param	<T>	the type of value the Flag can hold - use a raw type if you need a normal Flag without a value
 * 	@see		com.satvik.args.ArgHandler
 * 	@since		1.0
 */

public class Flag<T> {

	/** The state of the Flag */
	boolean state;
	/** The short representation of the Flag */
	public String shortForm;
	
	/** The long representation of the Flag */
	public String longForm;
	
	/** The boolean storing whether the Flag can have a value */
	boolean canHaveValue;
	
	/** The value assigned to the Flag */
	public T value;
	
	/** The class of the value assigned to the Flag */
	public Class<T> valueTypeClass;



	/**
	 * This is the constructor of Flag which accepts the short and long forms of the Flag. The raw form
	 * of Flag must be used, as it is implied that the Flag cannot be assigned a value. 
	 * 	
	 * 	@param	shortForm	the short form of the Flag
	 * 	@param	longForm	the long form of the Flag
	 * 	@since	1.0
	 */

	public Flag (String shortForm, String longForm) {
		this.shortForm = shortForm;
		this.longForm = longForm;
		this.state = false;
		this.canHaveValue = false;
	}
	
	
	
	/**
	 * This is the constructor of Flag which accepts the short and long forms of the Flag, as 
	 * well as it's value type's class. The value type class must be one of the following : <br>
	 * <pre>{@code
	 *	Character.class
	 *	String.class
	 *	Byte.class
	 *	Short.class
	 *	Integer.class
	 *	Long.class
	 *	Float.class
	 *	Double.class
	 * }</pre><br>
	 * Any value type class other than the above will throw an InvalidFlagTypeException.
	 * 	
	 * 	@param	shortForm		the short form of the FlagWithValue
	 * 	@param	longForm		the long form of the FlagWithValue
	 * 	@param	valueTypeClass		the class of the type of value which can be assigned to the FlagWithValue
	 * 	@throws	FlagException		thrown if valueTypeClass does not belong to the list of accepted classes
	 * 	@see	com.satvik.args.Flag#Flag(String, String)
	 * 	@since	1.0
	 */

	public Flag (String shortForm, String longForm, Class<T> valueTypeClass) throws FlagException {
		this(shortForm, longForm);
		this.canHaveValue = true;
		this.valueTypeClass = valueTypeClass;
		switch (this.valueTypeClass.getName()) {
			case "java.lang.Character":
			case "java.lang.String"	:
			case "java.lang.Byte"	:
			case "java.lang.Short"	:
			case "java.lang.Integer":
			case "java.lang.Long"	:
			case "java.lang.Float"	:
			case "java.lang.Double" :
				break;
			default :
				throw new FlagException("Unknown flagValueType " + this.valueTypeClass.getSimpleName() + " assigned to flag " + this.longForm);
		}
	}



	/**
	 * This is the constructor of Flag which accepts the short and long forms of the Flag, as 
	 * well as it's value type's class and a default value.
	 * 	
	 * 	@param	shortForm		the short form of the FlagWithValue
	 * 	@param	longForm		the long form of the FlagWithValue
	 * 	@param	valueTypeClass		the class of the type of value which can be assigned to the FlagWithValue
	 * 	@param	defaultValue		the defaultValue assigned to the Flag
	 * 	@throws	FlagException		thrown if valueTypeClass does not belong to the list of accepted classes
	 * 	@see	com.satvik.args.Flag#Flag(String, String, Class)
	 * 	@see	com.satvik.args.Flag#Flag(String, String)
	 * 	@since	1.0
	 */

	public Flag (String shortForm, String longForm, Class<T> valueTypeClass, T defaultValue) throws FlagException {
		this(shortForm, longForm, valueTypeClass);
		setValue(defaultValue);
	}



	/**
	 * This method sets the state of the Flag to the state passed to it.
	 *
	 * 	@param	state			the state to which the Flag is to be set
	 * 	@since	1.1
	 */

	public void setState (boolean state) {
		this.state = state;	
	}



	/**
	 * This method returns the current state of the Flag.
	 *
	 * 	@return				the current state of the Flag
	 * 	@since	1.1
	 */

	public boolean getState () {
		return state;
	}
	


	/**
	 * This method sets the Flag value to the accepted parameter.
	 *
	 * 	@param	value			the value to be assigned to the Flag
	 * 	@since	1.1
	 */

	public void setValue (T value) {
		this.value = value;
	}
	

	/**
	 * This method sets the value of the Flag to the 'value type' form of rawValue. If rawValue cannot
	 * be converted to the value type of the Flag, an Exception is thrown.
	 *
	 * 	@param	rawValue		the raw String to be processed into an Object of class valueType
	 * 	@throws	FlagException		thrown if there is an error in prsing the raw String, or the Flag cannot carry  value
	 * 	@since	1.0
	 */

	public void setParsedValue (String rawValue) throws FlagException {
		if (canHaveValue && (rawValue.length() > 0)) {
			try {
				value = Parser.<T>parse(rawValue, valueTypeClass);
			} catch(NumberFormatException e) {
				throw new FlagException("Value : " + rawValue + " given to " +  longForm + " has a wrong value type! Required : " + valueTypeClass.getSimpleName());
			}
		} else {
			throw new FlagException("Flag " + longForm + " cannot have a value !");
		}
	}
	
	
	
	/**
	 * This method returns the value of the Flag, if set previously. If not, and Exception is thrown.
	 *
	 * 	@return				the value stored in Flag
	 * 	@throws	FlagException		thrown if the Flag is empty, or cannot carry a value
	 * 	@since	1.0
	 */

	public T getValue () throws FlagException {
		if (value == null) {
			throw new FlagException("Flag " + longForm + " not assigned any value !");
		} else if (!canHaveValue) {
			throw new FlagException("Flag " + longForm + " cannot have a value !");
		}
		return value;
	}
} 
