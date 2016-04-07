
package com.satvik.struct;

/**
 * This class describes an Exception where an item at a non-existing
 * index is attempted to be accessed.
 *
 * 	@author		Satvik Saha
 * 	@version	1.0, 04/07/2016
 * 	@see		com.satvik.struct.ListException
 * 	@since		1.0
 */

public class ListIndexOutOfBoundsException extends ListException {
	
	/** 
	 * Constructor of ListIndexOutOfBounds.
	 * 	
	 * 	@param	index		the index which is out of bounds
	 * 	@since	1.0
	 */

	public ListIndexOutOfBoundsException (int index) {
		super("ListIndexOutOfBounds : No such index " + index + " exists !");
	}
} 
