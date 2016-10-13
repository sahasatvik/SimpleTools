
package com.github.sahasatvik.cli;

/**
 * A CannotParseValueOfOptionException describes the nature of an error on parsing values of options in ArgHandler. This 
 * Exception is thrown when a Option which does not accept values has been given one, or the value assigned is of the
 * wrong type.
 *
 * 	@author		Satvik Saha
 * 	@version	1.0, 04/09/2016
 * 	@see		com.github.sahasatvik.cli.OptionException
 * 	@see		com.github.sahasatvik.cli.ArgHandlerException
 * 	@since		1.0
 * 
 */

public class CannotParseValueOfOptionException extends OptionException {
	
	/**
	 * Constructor of CannotParseValueOfOptionException, to be used when a Option cannot carry a value.
	 *
	 * 	@param	option		the option in question
	 * 	@since	1.0
	 */

	public CannotParseValueOfOptionException (Option<?> option) {
		super("Option " + option.longForm + " cannot carry a value !");
	}

	/**
	 * Constructor of CannotParseValueOfOptionException, to be used when a Option has been given a value
	 * of the wrong type.
	 *
	 * 	@param	option		the option in question
	 * 	@param	value		the value of wrong type
	 * 	@since	1.0
	 */

	public CannotParseValueOfOptionException (Option<?> option, String value) {
		super("Option " + option.longForm + " cannot carry the value " + value + ", as it is of the wrong type!");
	}

}
