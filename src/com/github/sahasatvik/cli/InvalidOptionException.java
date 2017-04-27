
package com.github.sahasatvik.cli;

/**
 * An InvalidOptionException describes the nature of an error on parsing options in ArgHandler. This 
 * Exception is thrown when an unknown option is present in the unprocessed argument queue.
 *
 * 	@author		Satvik Saha
 * 	@version	0.1.0, 04/09/2016
 * 	@see		com.github.sahasatvik.cli.OptionException
 * 	@see		com.github.sahasatvik.cli.ArgHandlerException
 * 	@since		1.0
 * 
 */

public class InvalidOptionException extends OptionException {
	
	/**
	 * Constructor of InvalidOptionException. 
	 *
	 * 	@param	option		the invalid option present
	 * 	@since	0.1.0
	 */

	public InvalidOptionException (String option) {
		super("Invalid option : " + option);
	}
}
