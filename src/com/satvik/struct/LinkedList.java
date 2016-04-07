
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
	 * 	@see	com.satvik.struct.ListItem
	 * 	@since	1.0
	 */

	public ListItem<T> getListItemAt (int index) {
		ListItem<T> t = head;
		if (index <= size && index >= 0) {
			while (index >= 0) {
				t = t.right;
				index--;
			}
			return t;
		}
		return tail;
	}



	/**
	 * This method returns the item (type {@code <T>}) at the index passed to it.
	 *
	 * 	@param	index		the index from which the item is to be fetched
	 * 	@return			the item at index 
	 * 	@since	1.0
	 */
	public T getItemAt (int index) {
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
	 * 	@since	1.0
	 */

	public void pushItemAt (T item, int index) {
		if (index <= size && index >= 0) {
			ListItem<T> m = new ListItem<>(item);
			ListItem<T> r = getListItemAt(index);
			ListItem<T> l = r.left;
			ListItem.<T>link(l, m);
			ListItem.<T>link(m, r);
			size++;
		}
	}



	/**
	 * This method returns the item at the index passed to it, simultaneously removing
	 * it.
	 *
	 * 	@param	index		the index of the item to be popped.
	 * 	@return			the item at the given index
	 * 	@since	1.0
	 */

	public T popItemAt (int index) {
		ListItem<T> t = getListItemAt(index);
		if (index < size && index >= 0) {
			ListItem<T> l = t.left;
			ListItem<T> r = t.right;
			ListItem.<T>link(l, r);
			size--;
		}
		return t.item;
	}
} 
