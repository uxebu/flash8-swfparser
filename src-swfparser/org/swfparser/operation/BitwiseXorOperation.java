/*
 *   BitwiseXorOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 15.08.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Stack;

import org.swfparser.Operation;

public class BitwiseXorOperation extends BinaryBitwiseOperation {

	public BitwiseXorOperation(Operation op1, Operation op2) {
		super(op1, op2);
		// TODO Auto-generated constructor stub
	}

	public BitwiseXorOperation(Stack<Operation> stack) {
		super(stack);
	}

	@Override
	public String getSign() {
		return "^";
	}

}
