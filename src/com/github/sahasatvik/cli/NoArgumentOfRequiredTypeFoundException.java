
package com.github.sahasatvik.cli;

/**
 * A NoArgumentOfRequiredTypeFoundException describes the nature of an error on parsing values of arguments in ArgHandler. This 
 * Exception is thrown when no arguments of the required type, or parsable by the provided parser are found.
 *
 * 	@author		Satvik Saha
 * 	@version	0.1.0, 04/09/2016
 * 	@see		com.github.sahasatvik.cli.ArgumentException
 * 	@see		com.github.sahasatvik.cli.ArgHandlerException
 * 	@since		0.1.0
 * 
 */

public class NoArgumentOfRequiredTypeFoundException extends ArgumentException {
	
	/**
	 * Constructor of NoArgumentOfRequiredTypeFoundException, to be used when a class type is provided.
	 *	
	 * 	@param	clazz		the class provided in question
	 * 	@since	0.1.0
	 */

	public NoArgumentOfRequiredTypeFoundException (Class<?> clazz) {
		super("No argument can be parsed as " + clazz.getSimpleName()  + " !");
	}

	/**
	 * Constructor of NoArgumentOfRequiredTypeFoundException, to be used when a Parser has been provided.
	 *
	 * 	@param	parser		the parser provided
	 * 	@since	0.1.0
	 */

	public NoArgumentOfRequiredTypeFoundException (Parser<?> parser) {
		super("No argument can be parsed using the provided Parser !");
	}

}
