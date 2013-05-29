/*
 *   DecerementOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 26.07.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Stack;

import org.swfparser.Operation;

public class PostDecrementOperation extends AssignDecrementOperation {

	public PostDecrementOperation(Operation op) {
		super(op);
	}

	public PostDecrementOperation(Stack<Operation> stack) {
		super(stack);
	}
	
	public PostDecrementOperation(Operation op, boolean isStatement) {
		super(op, isStatement);
	}

	@Override
	protected String getPostfix() {
		return "--";
	}

	@Override
	protected String getPrefix() {
		return "";
	}

}
