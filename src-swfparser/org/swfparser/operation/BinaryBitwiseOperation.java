/*
 *   BinaryBitwiseOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 15.08.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Stack;

import org.swfparser.BooleanOperation;
import org.swfparser.Operation;

public abstract class BinaryBitwiseOperation extends BinaryOperation implements BooleanOperation {

	public BinaryBitwiseOperation(Operation op1, Operation op2) {
		super(op1, op2);
	}

	public BinaryBitwiseOperation(Stack<Operation> stack) {
		super(stack);
	}

	@Override
	public abstract String getSign();
	
	public Operation getInvertedOperation() {
		return new SimpleInvertedOperation(this);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof NotOperation) {
			return obj.equals(this); // invert comparison
		} else {
			return super.equals(obj);
		}
	}

}
