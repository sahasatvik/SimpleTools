
package com.satvik.args;

/**
 * This is the superclass of all Exceptions related to the parsing of flags in ArgHandler.
 *
 * 	@author		Satvik Saha
 * 	@version	1.0, 04/03/2016
 * 	@see		com.satvik.args.ArgHandlerException
 * 	@since		1.0
 * 
 */

public class FlagException extends ArgHandlerException {
	
	/**
	 * Constructor of FlagException. 
	 *
	 * 	@param	message		a brief description of the Exception
	 * 	@since	1.0
	 */

	public FlagException (String message) {
		super("FlagParser : " + message);
	}
}
