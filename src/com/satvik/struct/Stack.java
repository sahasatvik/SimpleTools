
package com.satvik.struct;

/**
 * This class describes a basic stack, from which items can be popped and pushed.
 *
 * 	@author		Satvik Saha
 *	@version	1.0, 04/07/2016
 *	@param	<T>	the type of items the stack holds
 *	@see	com.satvik.struct.LinkedList
 *	@since		1.0
 */

public class Stack<T> extends LinkedList<T> {
	
	/**
	 * This method pushes an item to the end of the stack.
	 *
	 * 	@param	item		the item to be pushed
	 * 	@since	1.0
	 */

	public void push (T item) {
		try {
			super.pushItemAt(item, size);
		} catch (ListException e) {
			System.out.println(e);
		}
	}

	

	/**
	 * This method pops the last item off the stack.
	 *
	 * 	@return			the last item in the stack
	 * 	@throws	com.satvik.struct.ListException	thrown if there is an error in carrying out the operation
	 * 	@since	1.0
	 */

	public T pop () throws ListException {
		return super.popItemAt((size-1));
	}
}
