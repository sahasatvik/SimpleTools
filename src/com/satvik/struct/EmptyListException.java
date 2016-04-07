
package com.satvik.struct;

/**
 * This class describes an Exception where items are attempted to be popped/removed
 * from an empty list.
 *
 * 	@author		Satvik Saha
 * 	@version	1.0, 04/07/2016
 * 	@see		com.satvik.struct.ListException
 * 	@since		1.0
 */

public class EmptyListException extends ListException {
	
	/** 
	 * Constructor of EmptyListException.
	 * 	
	 * 	@since	1.0
	 */

	public EmptyListException () {
		super("EmptyListException : Cannot pop/remove item from an empty list");
	}
} 
