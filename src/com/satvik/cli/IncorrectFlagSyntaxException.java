
package com.satvik.cli;

/**
 * This is the class describes Exceptions related to errors in creation of a Flag object.
 *
 * 	@author		Satvik Saha
 * 	@version	1.0, 04/09/2016
 * 	@see		com.satvik.cli.FlagException
 * 	@see		com.satvik.cli.ArgHandlerException
 * 	@since		1.0
 * 
 */

public class IncorrectFlagSyntaxException extends FlagException {
	
	/**
	 * Constructor of IncorrectFlagSyntaxException. 
	 *
	 * 	@param	message		a brief description of the Exception
	 * 	@since	1.0
	 */

	public IncorrectFlagSyntaxException (String message) {
		super(message);
	}
}
