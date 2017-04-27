
package com.github.sahasatvik.cli;

/**
 * This is the class describes Exceptions related to errors in creation of a Option object.
 *
 * 	@author		Satvik Saha
 * 	@version	0.1.0, 04/09/2016
 * 	@see		com.github.sahasatvik.cli.OptionException
 * 	@see		com.github.sahasatvik.cli.ArgHandlerException
 * 	@since		0.1.0
 * 
 */

public class IncorrectOptionSyntaxException extends OptionException {
	
	/**
	 * Constructor of IncorrectOptionSyntaxException. 
	 *
	 * 	@param	message		a brief description of the Exception
	 * 	@since	0.1.0
	 */

	public IncorrectOptionSyntaxException (String message) {
		super(message);
	}
}
