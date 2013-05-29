/*
 *   StrictEqualsOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 14.08.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Stack;

import org.swfparser.Operation;

public class StrictEqualsOperation extends EqualsOperation {

	public StrictEqualsOperation(Operation op1, Operation op2) {
		super(op1, op2);
	}

	public StrictEqualsOperation(Stack<Operation> stack) {
		super(stack);
	}
	
	@Override
	public String getSign() {
		return "===";
	}

}
