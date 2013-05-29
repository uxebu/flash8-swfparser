/*
 *   AbstractOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 24.07.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Stack;

import org.swfparser.Operation;
import org.swfparser.Priority;

public abstract class AbstractOperation implements Operation {
	
	protected Stack<Operation> stack;

	public AbstractOperation() {}
	
	/**
	 * TODO: Fix to receive context
	 * @param stack
	 */
	public AbstractOperation(Stack<Operation> stack) {
		super();
		this.stack = stack;
	}
	
	public int getPriority() {
		return Priority.LOWEST;
	}
}
