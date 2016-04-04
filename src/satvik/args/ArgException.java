
package com.satvik.args;

/**
 * ArgException is the superclass of all Exceptions related to the parsing of arguments in ArgHandler.
 *
 * 	@author		Satvik Saha
 * 	@version	1.0, 04/03/2016
 * 	@see		com.satvik.args.ArgHandlerException
 * 	@since		1.0
 * 
 */

public class ArgException extends ArgHandlerException {
	
	/**
	 * Constructor of ArgException. 
	 *
	 * 	@param	message		a brief description of the Exception
	 * 	@since	1.0
	 */

	public ArgException (String message) {
		super("ArgParser : " + message);
	}
}
