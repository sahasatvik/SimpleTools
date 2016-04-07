
package com.satvik.struct;

/**
 * This class describes a list comprised of ListItems linked together. Items can 
 * be pushed and popped into and out of the list.
 *
 * 	@author		Satvik Saha
 * 	@version	1.0, 04/07/2016
 * 	@param	<T>	the type of Items the LinkeeList contains		
 * 	@since		1.0
 */

public class LinkedList<T> {
	
	protected ListItem<T> head;
	protected ListItem<T> tail;
	
	/** The size of the LinkedList */
	protected int size;



	/**
	 * This constructor initializes some of the essential fields in the list.
	 *
	 * 	@since	1.0
	 */

	public LinkedList () {
			
		head = new ListItem<>(true, false);
		tail = new ListItem<>(false, true);

		ListItem.<T>link(head, tail);
		getSize();
	}



	/**
	 * This method returns the {@code ListItem<T>} at the index passed to it.
	 *
	 * 	@param	index		the index from which the ListItem is to be fetched
	 * 	@return			the ListItem at index
	 * 	@throws	com.satvik.struct.ListIndexOutOfBoundsException	thrown if the index is out of bounds
	 * 	@see	com.satvik.struct.ListItem
	 * 	@since	1.0
	 */

	public ListItem<T> getListItemAt (int index) throws ListIndexOutOfBoundsException {
		ListItem<T> t = head;
		if (index < size && index >= 0) {
			while (index >= 0) {
				t = t.right;
				index--;
			}
			return t;
		} else if (index == -1) {
			return head;
		} else if (index == size) {
			return tail;
		} else {
			throw new ListIndexOutOfBoundsException(index);
		}
	}



	/**
	 * This method returns the item (type {@code <T>}) at the index passed to it.
	 *
	 * 	@param	index		the index from which the item is to be fetched
	 * 	@return			the item at index 
	 * 	@throws	com.satvik.struct.ListIndexOutOfBoundsException	thrown if the index is out of bounds
	 * 	@since	1.0
	 */
	public T getItemAt (int index) throws ListIndexOutOfBoundsException {
		ListItem<T> t = getListItemAt(index);
		if (t.isHead || t.isTail) {
			throw new ListIndexOutOfBoundsException(index);
		}
		return getListItemAt(index).item;
	}



	/**
	 * This method recalculates and returns the size of the LinkedList, ie, the
	 * number of items in it.
	 *
	 * 	@return			the number of items in the list
	 * 	@since	1.0
	 */

	public int getSize () {
		ListItem<T> t = head;
		size = 0;
		while (!t.right.isTail) {
			t = t.right;
			size++;
		}
		return size;
	}


	
	/**
	 * This method pushes an item (type {@code <T>}) at the index passed to it.
	 * The item which previously occupied the given index will be pushed forward.
	 *
	 * 	@param	item		the item to be pushed to the index
	 * 	@param	index		the index at which the item is to be placed
	 * 	@throws	com.satvik.struct.ListIndexOutOfBoundsException	thrown if the index is out of bounds
	 * 	@since	1.0
	 */

	public void pushItemAt (T item, int index) throws ListIndexOutOfBoundsException {
		if (index <= size && index >= 0) {
			ListItem<T> m = new ListItem<>(item);
			ListItem<T> l = getListItemAt((index-1));
			ListItem<T> r = l.right;
			ListItem.<T>link(l, m);
			ListItem.<T>link(m, r);
			size++;
		} else {
			throw new ListIndexOutOfBoundsException(index);
		}
	}



	/**
	 * This method returns the item at the index passed to it, simultaneously removing
	 * it.
	 *
	 * 	@param	index		the index of the item to be popped.
	 * 	@return			the item at the given index
	 * 	@throws	com.satvik.struct.ListException	thrown if the index is out of bounds
	 * 	@since	1.0
	 */

	public T popItemAt (int index) throws ListException {
		if (size == 0) {
			throw new EmptyListException();
		}
		ListItem<T> t = getListItemAt(index);
		if (index < size && index >= 0) {
			ListItem<T> l = t.left;
			ListItem<T> r = t.right;
			ListItem.<T>link(l, r);
			size--;
		} else {
			throw new ListIndexOutOfBoundsException(index);	
		}
		return t.item;
	}
} 
