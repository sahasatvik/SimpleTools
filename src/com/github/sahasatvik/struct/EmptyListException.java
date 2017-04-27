
package com.github.sahasatvik.struct;

/**
 * This class describes an Exception where items are attempted to be popped/removed
 * from an empty list.
 *
 * 	@author		Satvik Saha
 * 	@version	0.1.0, 04/07/2016
 * 	@see		com.github.sahasatvik.struct.ListException
 * 	@since		0.1.0
 */

public class EmptyListException extends ListException {
	
	/** 
	 * Constructor of EmptyListException.
	 * 	
	 * 	@since	0.1.0
	 */

	public EmptyListException () {
		super("EmptyListException : Cannot pop/remove item from an empty list");
	}
} 
