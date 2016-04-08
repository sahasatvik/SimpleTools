
package com.satvik.args;

/**
 * This class describes a command line argument. It's raw form is a String, but can be parsed into
 * other forms.
 *
 * 	@author		Satvik Saha
 * 	@version	1.0, 04/08/2016
 * 	@since		1.0
 */

public class Argument {
	
	/** The raw value of the Argument */
	public String rawValue;

	/**
	 * Constructor for Argument, where it's raw value can be assigned
	 *
	 * 	@param	rawValue		the string to be assigned to the argument.
	 * 	@since	1.0
	 */

	public Argument (String rawValue) {
		this.rawValue = rawValue;
	}
	

	
	/**
	 * This method returns the raw value of the Argument.
	 *
	 * 	@return				the raw string value of the argument.
	 * 	@since	1.0
	 */

	public String getValue () {
		return rawValue;
	}



	/**
	 * This method returns the value of the Argument, parsed into an Object of Class
	 * 'clazz'.
	 *
	 * 	@param	<T>			the target type to be returned
	 * 	@param	clazz			the class of the target type
	 * 	@return				the value of the Argument, parsed into class 'clazz'
	 * 	@throws	NumberFormatException	thrown if the parsing was unsuccessful
	 * 	@since	1.0
	 */

	public <T> T getValue (Class<T> clazz) throws NumberFormatException {
		return Parser.<T>parse(rawValue, clazz); 
	}


	
	/**
	 * This method returns the value of the Argument, parsed using the Parser passed
	 * to it.
	 *
	 * 	@param	<T>			the target type to be returned
	 * 	@param	parser			the parser which parses the raw value
	 * 	@return				the value of the Argument, parsed by 'parser'
	 * 	@throws	Exception		thrown if the parser throws an Exception
	 * 	@since	1.0
	 */

	public <T> T getValue (Parser<T> parser) throws Exception {
		return parser.parse(rawValue);
	}

} 
