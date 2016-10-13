
package com.github.sahasatvik.cli;

/**
 * This is the superclass of all Exceptions related to the parsing of arguments in ArgHandler.
 *
 * 	@author		Satvik Saha
 * 	@version	1.0, 04/03/2016
 * 	@see		com.github.sahasatvik.cli.ArgHandlerException
 * 	@since		1.0
 * 
 */

public class ArgumentException extends ArgHandlerException {
	
	/**
	 * Constructor of ArgumentException. 
	 *
	 * 	@param	message		a brief description of the Exception
	 * 	@since	1.0
	 */

	public ArgumentException (String message) {
		super("ArgParser : " + message);
	}
}
