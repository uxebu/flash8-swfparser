/*
 *   ToIntegerOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 14.08.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Stack;

import org.swfparser.Operation;

public class ToIntegerOperation extends ConvertOperation {

	public ToIntegerOperation(Stack<Operation> stack) {
		super(stack);
	}

	@Override
	protected String getFunctionName() {
		return "int";
	}

}
