/*
 *   StringAddOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 14.08.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Stack;

import org.swfparser.Operation;
import org.swfparser.Priority;

public class StringAddOperation extends BinaryOperation {

	public StringAddOperation(Operation op1, Operation op2) {
		super(op1, op2);
	}

	public StringAddOperation(Stack<Operation> stack) {
		super(stack);
	}

	public String getLeftValue() {
		String leftString = leftOp.getStringValue(0);
		
		if (leftOp.getPriority() > getPriority()) {
			leftString = "("+leftString+")";
		}
		
		return leftString;
	}
	
	@Override
	public String getSign() {
		return "+";
	}
	
	@Override
	public int getPriority() {
		return Priority.PLUS_MINUS;
	}

}
