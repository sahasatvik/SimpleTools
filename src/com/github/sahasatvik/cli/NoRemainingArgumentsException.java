
package com.github.sahasatvik.cli;

/**
 * This is the class describing when no arguments remain in the argument queue in ArgHandler.
 *
 * 	@author		Satvik Saha
 * 	@version	0.1.0, 04/09/2016
 * 	@see		com.github.sahasatvik.cli.ArgumentException
 * 	@see		com.github.sahasatvik.cli.ArgHandlerException
 * 	@since		0.1.0
 * 
 */

public class NoRemainingArgumentsException extends ArgumentException {
	
	/**
	 * Constructor of NoRemainingArgumentsException. 
	 *
	 * 	@since	0.1.0
	 */

	public NoRemainingArgumentsException () {
		super("No arguments remaining in the argument queue !");
	}
}
