
package com.satvik.args;

/**
 * ArgHandlerException is the superclass of all Exceptions related to ArgHandler. Methods in ArgHandler
 * can throw Exceptions which are subclasses of ArgHandlerException.
 *
 * 	@author		Satvik Saha
 * 	@version	1.0, 04/03/2016
 * 	@since		1.0
 * 
 */

public class ArgHandlerException extends Exception {
	
	/**
	 * Constructor of ArgHandlerException. 
	 *
	 * 	@param	message		a brief description of the Exception
	 * 	@since	1.0
	 */

	public ArgHandlerException (String message) {
		super("ArgHandler : " + message);
	}
}
