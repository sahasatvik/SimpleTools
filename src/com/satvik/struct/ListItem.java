
package com.satvik.struct;

/**
 * This class describes a container object which can be linked with other objects in a
 * linked list.
 *
 * 	@author		Satvik Saha
 * 	@version	1.0, 04/07/2016
 * 	@param	<T>	the type of object the ListItem contains		
 * 	@since		1.0
 */

public class ListItem<T> {
	
	/** The item contained in the ListItem */
	public T item;
	
	/** The ListItem linked to the right of this */
	public ListItem<T> right;
	/** The ListItem linked to the left of this */
	public ListItem<T> left;
	
	/** Contains whether this is a head of a list */
	boolean isHead;
	/** Contains whether this is a tail of a list */
	boolean isTail;

	/**
	 * This constructor assigns an item to the ListItem.
	 *
	 * 	@param	item		the item to be contained in the ListItem
	 * 	@since	1.0
	 */

	public ListItem (T item) {
		this.item = item;
	}

	ListItem (boolean isHead, boolean isTail) {
		this.isHead = isHead;
		this.isTail = isTail;
	}



	/**
	 * This method links two ListItems, such that the first is on the left of the second.
	 *
	 * 	@param	<T>		the type of both ListItems
	 * 	@param	left		the ListItem to stay on the left
	 * 	@param	right		the ListItem to stay on the right
	 * 	@since	1.0
	 */

	public static <T> void link (ListItem<T> left, ListItem<T> right) {
		left.right = right;
		right.left = left;
	}
} 
