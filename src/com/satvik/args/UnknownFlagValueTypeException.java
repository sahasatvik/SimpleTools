
package com.satvik.args;

/**
 * A UnknownFlagValueTypeException describes the nature of an error on parsing values of flags in ArgHandler. This 
 * Exception is thrown when a Flag value type class is not recognized.
 *
 * 	@author		Satvik Saha
 * 	@version	1.0, 04/09/2016
 * 	@see		com.satvik.args.FlagException
 * 	@see		com.satvik.args.ArgHandlerException
 * 	@since		1.0
 * 
 */

public class UnknownFlagValueTypeException extends FlagException {
	
	/**
	 * Constructor of UnknownFlagValueTypeException.
	 *
	 * 	@param	flag		the flag in question
	 * 	@param	clazz		the class in question
	 * 	@since	1.0
	 */

	public UnknownFlagValueTypeException (Flag<?> flag, Class<?> clazz) {
		super("Flag " + flag.longForm + " cannot use a class value type of " + clazz.getSimpleName() + " !");
	}
}
