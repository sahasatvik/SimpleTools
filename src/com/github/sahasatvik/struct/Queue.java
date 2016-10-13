
package com.github.sahasatvik.struct;

/**
 * This class describes a basic queue, from which items can be popped and pushed.
 *
 * 	@author		Satvik Saha
 *	@version	1.0, 04/07/2016
 *	@param	<T>	the type of items the queue holds
 *	@see	com.github.sahasatvik.struct.LinkedList
 *	@since		1.0
 */

public class Queue<T> extends LinkedList<T> {

	/**
	 * This method pushes an item to the beginning of the queue.
	 *
	 * 	@param	item		the item to be pushed
	 * 	@since	1.0
	 */

	public void push (T item) {
		try {
			super.pushItemAt(item, size);
		} catch (ListException e) {
		}
	}

	

	/**
	 * This method pops the last item off the queue.
	 *
	 * 	@return			the last item in the queue
	 * 	@throws	com.github.sahasatvik.struct.ListException	thrown if there is an error in carrying out the operation
	 * 	@since	1.0
	 */

	public T pop () throws ListException {
		return super.popItemAt(0);
	}
}
