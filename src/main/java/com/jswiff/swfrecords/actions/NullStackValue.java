/*
 *   NullStackValue.java
 * 	 @Author Oleg Gorobets
 *   Created: 04.09.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package com.jswiff.swfrecords.actions;

public class NullStackValue extends StackValue {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3082886510398740236L;

	public NullStackValue() {
		type = TYPE_NULL;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof NullStackValue;
	}
}
