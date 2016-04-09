
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
 *	Flag<Integer> min = new Flag<Integer>("-m", "--min").canHaveValue(true)
 *							    .setValueType(Integer.class);
 *							// This Flag may be assigned an Integer by 
 *							// ArgHandler while processing
 *	Flag<Integer> max = new Flag<Integer>("-M", "--max").canHaveValue(true)
 *							    .setValueType(Integer.class)
 *							    .setDefaultValue(10);
 *							// This Flag will have a default value of 10, if no
 *							// value is set by ArgHandler
 *	Flag<String> capsName = new Flag<String>("-n", "--name").canHaveValue(true)
 *								.useParser((s) -> (s.toUpperCase()));
 *							// This flag uses a lambda expression for 
 *							// Parser<T>, to parse the name into upperCase.
 *							// This means that while setting the value of the Flag,
 *							// the raw String is parsed by the parser specified above,
 *							// which is a lambda expression here for brevity.
 *							
 *	try {
 *		ArgHandler a = new ArgHandler(args).useFlags(help, min, max, cpasName);
 *							// Create an ArgHandler and pass the Flags to it
 *	} catch (ArgException e) {
 *
 *	} catch ( . . .
 *	.
 *	.						// Continue catching all Exceptions
 *	.
 *	
 *	Boolean needsHelp = help.getState();		// The state of the Flag can be retrieved 
 *							// by calling help.getState()
 *	Integer minVal = min.getValue();		// The value of min.getValue() can be stored 
 *							// directly in minVal, due to the use of generics
 *	Integer maxVal = max.getValue();		// If no value has been set, the default value
 *	String name = capsName.hetValue()		// specified will be returned
 *	
 *	.
 *	.
 *	.
 *
 * }</pre>
 * <p>
 *
 * 	@author		Satvik Saha
 * 	@version	1.2, 04/08/2016
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
	/** The boolean storing whether the value is to be parsed using a parser provided */
	boolean useParser;
	/** The parser to use, if provided */
	Parser<T> parser;

	/** The value assigned to the Flag */
	public T value;
	
	/** The class of the value assigned to the Flag */
	public Class<T> valueTypeClass;



	/**
	 * This is the constructor of Flag which accepts the short and long forms of the Flag.
	 * 	
	 * 	@param	shortForm	the short form of the Flag
	 * 	@param	longForm	the long form of the Flag
	 * 	@throws	com.satvik.args.FlagException	thrown if there is s syntax error while invoking the constructor
	 * 	@since	1.0
	 */

	public Flag (String shortForm, String longForm) throws FlagException {
		if (shortForm.length() != 2) {
			throw new FlagException("The length of shortForm must be exactly 2 characters !");
		} else if (shortForm.charAt(0) != '-') {
			throw new FlagException("The shortForm must start with a '-' !");
		} else if (shortForm.charAt(1) == '-') {
			throw new FlagException("The shortForm cannot be '--' !");
		} else if (longForm.charAt(0) != '-' || longForm.charAt(1) != '-') {
			throw new FlagException("The longForm must start with '--' !");
		}

		this.shortForm = shortForm;
		this.longForm = longForm;
		this.state = false;
		this.canHaveValue = false;
		this.useParser = false;
	}



	/**
	 * This method sets whether the Flag can be assigned a value or not.
	 *
	 * 	@param	canHaveValue		true/false, depending on whther the Flag can be assigned a value
	 * 	@return				this Flag
	 * 	@since	1.2
	 */

	public Flag<T> canHaveValue (boolean canHaveValue) {
		this.canHaveValue = canHaveValue;
		return this;
	}
	
	
	/**
	 * This method accepts the Flag's value type's class. 
	 * The value type class must be one of the following : <br>
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
	 * 	@param	valueTypeClass		the class of the type of value which can be assigned to the FlagWithValue
	 * 	@return				this Flag
	 * 	@throws	FlagException		thrown if valueTypeClass does not belong to the list of accepted classes
	 * 	@since	1.0
	 */

	public Flag<T> setValueType (Class<T> valueTypeClass) throws FlagException {
		if (canHaveValue) {
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
					this.useParser = false;
					break;
				default :
					throw new FlagException("Unknown flagValueType " + this.valueTypeClass.getSimpleName() + " assigned to flag " + this.longForm);
			}
		} else {
			throw new FlagException("Flag " + longForm + " cannot have a value !");
		}
		return this;
	}
	


	/**
	 * This method sets a Parser for the Flag's value. This allows you to define your own way of converting
	 * a String into another Object and apply this to the Flag value.
	 *
	 * 	@param	parser			the parser to be used while parsing the Flag value
	 * 	@return				this Flag
	 * 	@throws	com.satvik.args.FlagException	thrown if canHaveValue is false
	 * 	@since	1.2
	 */

	public Flag<T> useParser (Parser<T> parser) throws FlagException {
		if (canHaveValue) {
			this.useParser = true;
			this.parser = parser;
		} else {
			throw new FlagException("Flag " + longForm + " cannot have a value !");
		}
		return this;
	}
	

	
	/**
	 * This method sets a default value to the Flag.
	 * 	
	 * 	@param	defaultValue		the defaultValue assigned to the Flag
	 * 	@return				this Flag
	 * 	@since	1.0
	 */

	public Flag<T> setDefaultValue (T defaultValue) {
		setValue(defaultValue);
		return this;
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

	public void parseValue (String rawValue) throws FlagException {
		if (canHaveValue && (rawValue.length() > 0)) {
			try {
				if (useParser) {
					value = parser.parse(rawValue);
				} else {
					value = Parser.<T>parse(rawValue, valueTypeClass);
				}
			} catch (Exception e) {
				throw new FlagException("Value : " + rawValue + " cannot be given to flag " +  longForm + " !");
			}
		} else if (!canHaveValue && (rawValue.length() > 0)) {
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



	/**
	 * This method extracts the value after a "=" present in a String, and returns it.
	 *
	 * 	@param	rawValue		the raw string from which the value is to be extracted
	 * 	@return				the value present after the "=" symbol in rawValue
	 * 	@since	1.2
	 */

	public static String extractValue (String rawValue) {
		return rawValue.substring((rawValue.indexOf("=") == -1)? rawValue.length() : (rawValue.indexOf("=") + 1), rawValue.length());
	}
	
	
	
	/**
	 * This method returns whether the String passed to it is a representation of this Flag, ie, 
	 * it is either one of the long or short forms. The "=" and subsequent characters are ignored.
	 *
	 * 	@param	s			the string to be matched against the Flag
	 * 	@return				true/false depending on whether s is the long or short form of the Flag
	 * 	@since	1.2
	 */

	public boolean matches (String s) {
		s = s.substring(0, (s.indexOf("=") == -1)? s.length() : s.indexOf("=")); 
		return s.equals(shortForm) || s.equals(longForm);
	}	
	


	/**
	 * This method returns whether the character passed to it is a representation of this Flag, ie, 
	 * it is the second characterof the short form. The "=" and subsequent characters are ignored.
	 *
	 * 	@param	c			the character to be matched against the Flag
	 * 	@return				true/false depending on whether c is the short form of the Flag
	 * 	@since	1.2
	 */

	public boolean matches (char c) {
		return c == shortForm.charAt(1);
	}
} 
