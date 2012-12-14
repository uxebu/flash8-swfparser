/*
 *   ConvertOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 14.08.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Stack;

import org.swfparser.Operation;

public abstract class ConvertOperation extends UnaryOperation {

	public ConvertOperation(Stack<Operation> stack) {
		super(stack);
	}

	@Override
	public String getStringValue(int level) {
		return getFunctionName()+"("+op.getStringValue(level)+")";
	}

	protected abstract String getFunctionName();
	
	@Override
	public String toString() {
		return getFunctionName()+"("+op+")";
	}

}
