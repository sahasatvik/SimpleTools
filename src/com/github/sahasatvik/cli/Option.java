
package com.github.sahasatvik.cli;

/**
 * This is the class describing a command line Option. Options are used extensively in many programs
 * so that the user of the program can pass on optional information during execution, eliminating the
 * need for unnecessary input and dialogues once the program is running.
 * <p>
 * In this method of interpreting options, each option may have two synonimous forms : a 'short' form 
 * (-[character]) and a long form (--[option-name]), eg. an option for 'help' may be represented by 
 * '-h' or '--help'. Options have two states : on(true) and off(false) (off/false by default).
 * Options may also carry values with a '=' character immediately following either the long or short 
 * form, and preceding the actual value given to it, eg. '--max-val=20' will assign the string '20'
 * to the option represented by '--max-val'. 
 * <p>
 * If an Option object is to carry a value, it must call the constructor {@code Option(String, String, Class<T>)},
 * and it's container variable must be of type {@code Option<T>}, where T is the type of value the Option
 * can hold. An example of it's usage follows : <br>
 * <pre> {@code
 *
 * 	.
 * 	.
 * 	.
 *
 *	Option help = new Option("-h", "--help");	// Normal option without value. Note that 
 *							// no value can be queried from it using 
 *							// option.getValue() : attempting to 
 *							// do so throws a OptionException
 *	Option<Integer> min = new Option<Integer>("-m", "--min").canHaveValue(true)
 *							    .setValueType(Integer.class);
 *							// This Option may be assigned an Integer by 
 *							// ArgHandler while processing
 *	Option<Integer> max = new Option<Integer>("-M", "--max").canHaveValue(true)
 *							    .setValueType(Integer.class)
 *							    .setDefaultValue(10);
 *							// This Option will have a default value of 10, if no
 *							// value is set by ArgHandler
 *	Option<String> capsName = new Option<String>("-n", "--name").canHaveValue(true)
 *								.useParser((s) -> (s.toUpperCase()));
 *							// This option uses a lambda expression for 
 *							// Parser<T>, to parse the name into upperCase.
 *							// This means that while setting the value of the Option,
 *							// the raw String is parsed by the parser specified above,
 *							// which is a lambda expression here for brevity.
 *							
 *	try {
 *		ArgHandler a = new ArgHandler(args).useOptions(help, min, max, cpasName);
 *							// Create an ArgHandler and pass the Options to it
 *	} catch (ArgumentException e) {
 *
 *	} catch ( . . .
 *	.
 *	.						// Continue catching all Exceptions
 *	.
 *	
 *	Boolean needsHelp = help.getState();		// The state of the Option can be retrieved 
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
 * 	@version	0.1.2, 04/08/2016
 * 	@param	<T>	the type of value the Option can hold - use a raw type if you need a normal Option without a value
 * 	@see		com.github.sahasatvik.cli.ArgHandler
 * 	@since		0.1.0
 */

public class Option<T> {

	/** The state of the Option */
	private boolean state;
	/** The short representation of the Option */
	public String shortForm;
	
	/** The long representation of the Option */
	public String longForm;
	
	/** The boolean storing whether the Option can have a value */
	public boolean canHaveValue;
	/** The boolean storing whether the value is to be parsed using a parser provided */
	private boolean useParser;
	/** The parser to use, if provided */
	private Parser<T> parser;

	/** The value assigned to the Option */
	public T value;
	
	/** The class of the value assigned to the Option */
	private Class<T> valueTypeClass;



	/**
	 * This is the constructor of Option which accepts the short and long forms of the Option.
	 * 	
	 * 	@param	shortForm	the short form of the Option
	 * 	@param	longForm	the long form of the Option
	 * 	@throws	com.github.sahasatvik.cli.IncorrectOptionSyntaxException	thrown if there is a syntax error while invoking the constructor
	 * 	@since	0.1.0
	 */

	public Option (String shortForm, String longForm) throws IncorrectOptionSyntaxException {
		if (shortForm.length() != 2) {
			throw new IncorrectOptionSyntaxException("The length of shortForm of " + longForm + "  must be exactly 2 characters !");
		} else if (shortForm.charAt(0) != '-') {
			throw new IncorrectOptionSyntaxException("The shortForm of " + longForm + " must start with a '-' !");
		} else if (shortForm.charAt(1) == '-') {
			throw new IncorrectOptionSyntaxException("The shortForm of " + longForm +" cannot be '--' !");
		} else if (longForm.charAt(0) != '-' || longForm.charAt(1) != '-') {
			throw new IncorrectOptionSyntaxException("The longForm of " + shortForm + " must start with '--' !");
		}

		this.shortForm = shortForm;
		this.longForm = longForm;
		this.state = false;
		this.canHaveValue = false;
		this.useParser = false;
	}



	/**
	 * This method sets whether the Option can be assigned a value or not.
	 *
	 * 	@param	canHaveValue		true/false, depending on whther the Option can be assigned a value
	 * 	@return				this Option
	 * 	@since	0.1.2
	 */

	public Option<T> canHaveValue (boolean canHaveValue) {
		this.canHaveValue = canHaveValue;
		return this;
	}
	
	
	/**
	 * This method accepts the Option's value type's class. 
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
	 * Any value type class other than the above will throw an InvalidOptionTypeException.
	 * 	
	 * 	@param	valueTypeClass		the class of the type of value which can be assigned to the OptionWithValue
	 * 	@return				this Option
	 * 	@throws	com.github.sahasatvik.cli.UnknownOptionValueTypeException		thrown if valueTypeClass does not belong to the list of accepted classes
	 * 	@throws	com.github.sahasatvik.cli.CannotParseValueOfOptionException	thrown if the Option cannot carry a value
	 * 	@since	0.1.0
	 */

	public Option<T> setValueType (Class<T> valueTypeClass) throws UnknownOptionValueTypeException, CannotParseValueOfOptionException {
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
					throw new UnknownOptionValueTypeException(this, valueTypeClass);
			}
		} else {
			throw new CannotParseValueOfOptionException(this);
		}
		return this;
	}
	


	/**
	 * This method sets a Parser for the Option's value. This allows you to define your own way of converting
	 * a String into another Object and apply this to the Option value.
	 *
	 * 	@param	parser			the parser to be used while parsing the Option value
	 * 	@return				this Option
	 * 	@throws	com.github.sahasatvik.cli.CannotParseValueOfOptionException	thrown if canHaveValue is false
	 * 	@since	0.1.2
	 */

	public Option<T> useParser (Parser<T> parser) throws CannotParseValueOfOptionException {
		if (canHaveValue) {
			this.useParser = true;
			this.parser = parser;
		} else {
			throw new CannotParseValueOfOptionException(this);
		}
		return this;
	}
	

	
	/**
	 * This method sets a default value to the Option.
	 * 	
	 * 	@param	defaultValue		the defaultValue assigned to the Option
	 * 	@return				this Option
	 * 	@since	0.1.0
	 */

	public Option<T> setDefaultValue (T defaultValue) {
		setValue(defaultValue);
		return this;
	}



	/**
	 * This method sets the state of the Option to the state passed to it.
	 *
	 * 	@param	state			the state to which the Option is to be set
	 * 	@since	0.1.1
	 */

	public void setState (boolean state) {
		this.state = state;	
	}



	/**
	 * This method returns the current state of the Option.
	 *
	 * 	@return				the current state of the Option
	 * 	@since	0.1.1
	 */

	public boolean getState () {
		return state;
	}
	


	/**
	 * This method sets the Option value to the accepted parameter.
	 *
	 * 	@param	value			the value to be assigned to the Option
	 * 	@since	0.1.1
	 */

	public void setValue (T value) {
		this.value = value;
	}
	

	/**
	 * This method sets the value of the Option to the 'value type' form of rawValue. If rawValue cannot
	 * be converted to the value type of the Option, an Exception is thrown.
	 *
	 * 	@param	rawValue		the raw String to be processed into an Object of class valueType
	 * 	@throws	com.github.sahasatvik.cli.CannotParseValueOfOptionException	thrown if there is an error in prsing the raw String, or the Option cannot carry  value
	 * 	@since	0.1.0
	 */

	public void parseValue (String rawValue) throws CannotParseValueOfOptionException {
		if (canHaveValue && (rawValue.length() > 0)) {
			try {
				if (useParser) {
					value = parser.parse(rawValue);
				} else {
					value = Parser.<T>parse(rawValue, valueTypeClass);
				}
			} catch (Exception e) {
				throw new CannotParseValueOfOptionException(this, rawValue);
			}
		} else if (!canHaveValue && (rawValue.length() > 0)) {
			throw new CannotParseValueOfOptionException(this);
		}
	}
	
	
	
	/**
	 * This method returns the value of the Option, if set previously. If not, and Exception is thrown.
	 *
	 * 	@return				the value stored in Option
	 * 	@throws	com.github.sahasatvik.cli.MissingOptionValueException	thrown if the Option is empty
	 * 	@throws	com.github.sahasatvik.cli.CannotParseValueOfOptionException	thrown if the Option cannot carry a value
	 * 	@since	0.1.0
	 */

	public T getValue () throws CannotParseValueOfOptionException, MissingOptionValueException {
		if (value == null) {
			throw new MissingOptionValueException(this);
		} else if (!canHaveValue) {
			throw new CannotParseValueOfOptionException(this);
		}
		return value;
	}



	/**
	 * This method extracts the value after a "=" present in a String, and returns it.
	 *
	 * 	@param	rawValue		the raw string from which the value is to be extracted
	 * 	@return				the value present after the "=" symbol in rawValue
	 * 	@since	0.1.2
	 */

	public static String extractValue (String rawValue) {
		return rawValue.substring((rawValue.indexOf("=") == -1)? rawValue.length() : (rawValue.indexOf("=") + 1), rawValue.length());
	}
	
	
	
	/**
	 * This method returns whether the String passed to it is a representation of this Option, ie, 
	 * it is either one of the long or short forms. The "=" and subsequent characters are ignored.
	 *
	 * 	@param	s			the string to be matched against the Option
	 * 	@return				true/false depending on whether s is the long or short form of the Option
	 * 	@since	0.1.2
	 */

	public boolean matches (String s) {
		s = s.substring(0, (s.indexOf("=") == -1)? s.length() : s.indexOf("=")); 
		return s.equals(shortForm) || s.equals(longForm);
	}	
	


	/**
	 * This method returns whether the character passed to it is a representation of this Option, ie, 
	 * it is the second characterof the short form. The "=" and subsequent characters are ignored.
	 *
	 * 	@param	c			the character to be matched against the Option
	 * 	@return				true/false depending on whether c is the short form of the Option
	 * 	@since	0.1.2
	 */

	public boolean matches (char c) {
		return c == shortForm.charAt(1);
	}
} 
