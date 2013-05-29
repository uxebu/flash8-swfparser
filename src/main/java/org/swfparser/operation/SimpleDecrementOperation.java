/*
 *   SimpleDecrementOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 25.08.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Stack;

import org.swfparser.Operation;
import org.swfparser.Priority;

public class SimpleDecrementOperation extends UnaryOperation {

	public SimpleDecrementOperation(Operation op) {
		super(op);
	}

	public SimpleDecrementOperation(Stack<Operation> stack) {
		super(stack);
	}

	@Override
	public String getStringValue(int level) {
		return op.getStringValue(level) +" - 1";
	}
	
	@Override
	public int getPriority() {
		return Priority.PLUS_MINUS;
	}

}
