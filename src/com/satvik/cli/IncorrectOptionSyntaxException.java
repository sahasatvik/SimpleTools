
package com.satvik.cli;

/**
 * This is the class describes Exceptions related to errors in creation of a Option object.
 *
 * 	@author		Satvik Saha
 * 	@version	1.0, 04/09/2016
 * 	@see		com.satvik.cli.OptionException
 * 	@see		com.satvik.cli.ArgHandlerException
 * 	@since		1.0
 * 
 */

public class IncorrectOptionSyntaxException extends OptionException {
	
	/**
	 * Constructor of IncorrectOptionSyntaxException. 
	 *
	 * 	@param	message		a brief description of the Exception
	 * 	@since	1.0
	 */

	public IncorrectOptionSyntaxException (String message) {
		super(message);
	}
}
