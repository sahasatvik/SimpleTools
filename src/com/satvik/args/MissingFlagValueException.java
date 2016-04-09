
package com.satvik.args;

/**
 * An MissingFlagValueException describes the nature of an error on parsing flags in ArgHandler. This 
 * Exception is thrown when a Flag has not been assigned a value.
 *
 * 	@author		Satvik Saha
 * 	@version	1.0, 04/09/2016
 * 	@see		com.satvik.args.FlagException
 * 	@see		com.satvik.args.ArgHandlerException
 * 	@since		1.0
 * 
 */

public class MissingFlagValueException extends FlagException {
	
	/**
	 * Constructor of MissingFlagValueException. 
	 *
	 * 	@param	flag		the invalid flag present
	 * 	@since	1.0
	 */

	public MissingFlagValueException (Flag<?> flag) {
		super("Flag " + flag.longForm + " has not been given a value !");
	}
}
