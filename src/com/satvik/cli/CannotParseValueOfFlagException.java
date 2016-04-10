
package com.satvik.cli;

/**
 * A CannotParseValueOfFlagException describes the nature of an error on parsing values of flags in ArgHandler. This 
 * Exception is thrown when a Flag which does not accept values has been given one, or the value assigned is of the
 * wrong type.
 *
 * 	@author		Satvik Saha
 * 	@version	1.0, 04/09/2016
 * 	@see		com.satvik.cli.FlagException
 * 	@see		com.satvik.cli.ArgHandlerException
 * 	@since		1.0
 * 
 */

public class CannotParseValueOfFlagException extends FlagException {
	
	/**
	 * Constructor of CannotParseValueOfFlagException, to be used when a Flag cannot carry a value.
	 *
	 * 	@param	flag		the flag in question
	 * 	@since	1.0
	 */

	public CannotParseValueOfFlagException (Flag<?> flag) {
		super("Flag " + flag.longForm + " cannot carry a value !");
	}

	/**
	 * Constructor of CannotParseValueOfFlagException, to be used when a Flag has been given a value
	 * of the wrong type.
	 *
	 * 	@param	flag		the flag in question
	 * 	@param	value		the value of wrong type
	 * 	@since	1.0
	 */

	public CannotParseValueOfFlagException (Flag<?> flag, String value) {
		super("Flag " + flag.longForm + " cannot carry the value " + value + ", as it is of the wrong type!");
	}

}
