
package com.github.sahasatvik.struct;

/**
 * This class describes a basic stack, from which items can be popped and pushed.
 *
 * 	@author		Satvik Saha
 *	@version	0.1.0, 04/07/2016
 *	@param	<T>	the type of items the stack holds
 *	@see	com.github.sahasatvik.struct.LinkedList
 *	@since		0.1.0
 */

public class Stack<T> extends LinkedList<T> {
	
	/**
	 * This method pushes an item to the end of the stack.
	 *
	 * 	@param	item		the item to be pushed
	 * 	@since	0.1.0
	 */

	public void push (T item) {
		try {
			super.pushItemAt(item, size);
		} catch (ListException e) {
		}
	}

	

	/**
	 * This method pops the last item off the stack.
	 *
	 * 	@return			the last item in the stack
	 * 	@throws	com.github.sahasatvik.struct.ListException	thrown if there is an error in carrying out the operation
	 * 	@since	0.1.0
	 */

	public T pop () throws ListException {
		return super.popItemAt((size-1));
	}
}
