/*
 *   UndefinedStackValue.java
 * 	 @Author Oleg Gorobets
 *   Created: 11.09.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package com.jswiff.swfrecords.actions;

public class UndefinedStackValue extends StackValue {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8597409941302812904L;

	public UndefinedStackValue() {
		type = TYPE_UNDEFINED;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof UndefinedStackValue;
	}
	
}
