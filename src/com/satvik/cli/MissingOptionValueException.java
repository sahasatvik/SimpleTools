
package com.satvik.cli;

/**
 * An MissingOptionValueException describes the nature of an error on parsing options in ArgHandler. This 
 * Exception is thrown when a Option has not been assigned a value.
 *
 * 	@author		Satvik Saha
 * 	@version	1.0, 04/09/2016
 * 	@see		com.satvik.cli.OptionException
 * 	@see		com.satvik.cli.ArgHandlerException
 * 	@since		1.0
 * 
 */

public class MissingOptionValueException extends OptionException {
	
	/**
	 * Constructor of MissingOptionValueException. 
	 *
	 * 	@param	option		the invalid option present
	 * 	@since	1.0
	 */

	public MissingOptionValueException (Option<?> option) {
		super("Option " + option.longForm + " has not been given a value !");
	}
}
