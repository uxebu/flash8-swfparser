/*
 *   PostIncrementOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 26.07.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Stack;

import org.swfparser.Operation;

public class PostIncrementOperation extends AssignIncrementOperation {

	public PostIncrementOperation(Operation op) {
		super(op);
	}
	
	public PostIncrementOperation(Operation op, boolean isStatement) {
		super(op, isStatement);
	}

	public PostIncrementOperation(Stack<Operation> stack) {
		super(stack);
	}

	@Override
	protected String getPostfix() {
		return "++";
	}

	@Override
	protected String getPrefix() {
		return "";
	}

}
