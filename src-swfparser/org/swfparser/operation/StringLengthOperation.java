/*
 *   StringLengthOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 14.08.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Stack;

import org.swfparser.Operation;

public class StringLengthOperation extends UnaryOperation {

//	public StringLengthOperation(Operation op) {
//		super(op);
//	}

	public StringLengthOperation(Stack<Operation> stack) {
		super(stack);
	}

	@Override
	public String getStringValue(int level) {
		return "length("+op.getStringValue(level)+")";
	}

}
