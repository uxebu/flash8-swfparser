/*
 *   MbLengthOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 14.08.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Stack;

import org.swfparser.Operation;

/**
 * 
 * Deprecated since Flash Player 5. This function was deprecated in favor of the methods and properties of the String class.
 * Returns the length of the multibyte character string.
 *
 */
public class MbLengthOperation extends ConvertOperation {

	public MbLengthOperation(Stack<Operation> stack) {
		super(stack);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getFunctionName() {
		return "mblength";
	}

}
