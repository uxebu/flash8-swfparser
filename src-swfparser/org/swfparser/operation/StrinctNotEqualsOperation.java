/*
 *   StrinctNotEqualsOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 17.08.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Stack;

import org.swfparser.Operation;

public class StrinctNotEqualsOperation extends NotEqualsOperation {

	public StrinctNotEqualsOperation(Operation op1, Operation op2) {
		super(op1, op2);
	}

	public StrinctNotEqualsOperation(Stack<Operation> stack) {
		super(stack);
	}
	
	@Override
	public Operation getInvertedOperation() {
		return new StrictEqualsOperation(leftOp,rightOp);
	}

}
