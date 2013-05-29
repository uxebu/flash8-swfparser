/*
 *   ToNumberOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 24.07.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Stack;

import org.swfparser.Operation;

public class ToNumberOperation extends ConvertOperation {
	
	public ToNumberOperation(Stack<Operation> stack) {
		super(stack);
	}

	@Override
	protected String getFunctionName() {
		return "Number";
	}

}
