
package com.satvik.cli;

/**
 * This is the class describing when no arguments remain in the argument queue in ArgHandler.
 *
 * 	@author		Satvik Saha
 * 	@version	1.0, 04/09/2016
 * 	@see		com.satvik.cli.ArgumentException
 * 	@see		com.satvik.cli.ArgHandlerException
 * 	@since		1.0
 * 
 */

public class NoRemainingArgumentsException extends ArgumentException {
	
	/**
	 * Constructor of NoRemainingArgumentsException. 
	 *
	 * 	@since	1.0
	 */

	public NoRemainingArgumentsException () {
		super("No arguments remaining in the argument queue !");
	}
}
