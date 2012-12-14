/*
 *   BitwiseRShiftOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 15.08.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Stack;

import org.swfparser.Operation;

public class BitwiseRShiftOperation extends BinaryBitwiseOperation {

	public BitwiseRShiftOperation(Operation op1, Operation op2) {
		super(op1, op2);
		// TODO Auto-generated constructor stub
	}

	public BitwiseRShiftOperation(Stack<Operation> stack) {
		super(stack);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getSign() {
		return ">>";
	}

}
