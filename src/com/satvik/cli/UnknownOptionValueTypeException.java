
package com.satvik.cli;

/**
 * A UnknownOptionValueTypeException describes the nature of an error on parsing values of options in ArgHandler. This 
 * Exception is thrown when a Option value type class is not recognized.
 *
 * 	@author		Satvik Saha
 * 	@version	1.0, 04/09/2016
 * 	@see		com.satvik.cli.OptionException
 * 	@see		com.satvik.cli.ArgHandlerException
 * 	@since		1.0
 * 
 */

public class UnknownOptionValueTypeException extends OptionException {
	
	/**
	 * Constructor of UnknownOptionValueTypeException.
	 *
	 * 	@param	option		the option in question
	 * 	@param	clazz		the class in question
	 * 	@since	1.0
	 */

	public UnknownOptionValueTypeException (Option<?> option, Class<?> clazz) {
		super("Option " + option.longForm + " cannot use a class value type of " + clazz.getSimpleName() + " !");
	}
}
