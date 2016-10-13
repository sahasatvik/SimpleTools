
package com.github.sahasatvik.struct;

/**
 * This class is the superclass of Exceptions related to lists.
 *
 * 	@author		Satvik Saha
 * 	@version	1.0, 04/07/2016
 * 	@since		1.0
 */

public class ListException extends Exception {
	
	/**
	 * Constructor of ListExcepion.
	 *
	 * 	@param	message			a brief description of the Exceptions
	 * 	@since	1.0
	 */

	public ListException (String message) {
		super("ListException : " + message);
	}
} 
