
package com.satvik.args;

/**
 * An InvalidFlagException describes the nature of an error on parsing flags in ArgHandler. This 
 * Exception is thrown when an unknown flag is present in the unprocessed argument queue.
 *
 * 	@author		Satvik Saha
 * 	@version	1.0, 04/03/2016
 * 	@see		com.satvik.args.FlagException
 * 	@see		com.satvik.args.ArgHandlerException
 * 	@since		1.0
 * 
 */

public class InvalidFlagException extends FlagException {
	
	/**
	 * Constructor of InvalidFlagException. 
	 *
	 * 	@param	flag		the invalid flag present
	 * 	@since	1.0
	 */

	public InvalidFlagException (String flag) {
		super("Invalid flag : " + flag);
	}
}
