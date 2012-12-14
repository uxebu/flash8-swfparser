/*
 *   NotEqualsOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 17.08.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Stack;

import org.swfparser.Operation;

public class NotEqualsOperation extends BinaryBooleanOperation {

	public NotEqualsOperation(Operation op1, Operation op2) {
		super(op1, op2);
	}

	public NotEqualsOperation(Stack<Operation> stack) {
		super(stack);
	}

	@Override
	public String getSign() {
		return "!=";
	}
	
	@Override
	public Operation getInvertedOperation() {
		return new EqualsOperation(leftOp,rightOp);
	}


}
