
package com.satvik.cli;

/**
 * An InvalidOptionException describes the nature of an error on parsing options in ArgHandler. This 
 * Exception is thrown when an unknown option is present in the unprocessed argument queue.
 *
 * 	@author		Satvik Saha
 * 	@version	1.0, 04/09/2016
 * 	@see		com.satvik.cli.OptionException
 * 	@see		com.satvik.cli.ArgHandlerException
 * 	@since		1.0
 * 
 */

public class InvalidOptionException extends OptionException {
	
	/**
	 * Constructor of InvalidOptionException. 
	 *
	 * 	@param	option		the invalid option present
	 * 	@since	1.0
	 */

	public InvalidOptionException (String option) {
		super("Invalid option : " + option);
	}
}
