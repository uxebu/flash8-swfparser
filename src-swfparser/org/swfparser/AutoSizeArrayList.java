/*
 *   AutoSizeArrayList.java
 * 	 @Author Oleg Gorobets
 *   Created: Jul 28, 2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser;

import java.util.ArrayList;

/**
 * Used for registers.
 *
 * @param <E>
 */
public class AutoSizeArrayList<E> extends ArrayList<E> {
	
	private static final long serialVersionUID = 2321132037643873672L;

	@Override
	public E set(int index, E element) {
		if (index>=size()) {
			int addNullItems = index - size();
			for (int j=0;j<addNullItems;j++) {
				super.add(null);
			}
			super.add(element);
			return null;
		} else {
			return super.set(index, element);
		}
	}
	
	/* (non-Javadoc)
	 * @see java.util.ArrayList#add(int, java.lang.Object)
	 * 
	 * DO NOT shift elements, act like set()
	 * 
	 */
	@Override
	public void add(int index, E element) {
		set(index, element);
	}
	
//	@Override
//	public E get(int index) {
//		if (index>=size()) {
//			return null;
//		} else {
//			return super.get(index);
//		}
//	}
}
