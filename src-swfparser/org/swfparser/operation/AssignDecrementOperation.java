/*
 *   AssignDecrementOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 25.08.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Stack;

import org.swfparser.AssignOperation;
import org.swfparser.CodeUtil;
import org.swfparser.DualUse;
import org.swfparser.Operation;
import org.swfparser.Priority;

public abstract class AssignDecrementOperation extends SimpleDecrementOperation implements AssignOperation, DualUse {

	protected boolean isStatement = false;
	
	public AssignDecrementOperation(Operation op) {
		super(op);
	}
	
	public AssignDecrementOperation(Operation op, boolean isStatement) {
		super(op);
		this.isStatement = isStatement;
	}

	public AssignDecrementOperation(Stack<Operation> stack) {
		super(stack);
	}
	
	public Operation getLeftPart() {
		return op;
	}
	
	public Operation getRightPart() {
		return null;
	}
	
	@Override
	public int getPriority() {
		return Priority.UNARY;
	}
	
	public void markAsStatement() {
		isStatement = true;
	}

	/**
	 * @return
	 */
	protected abstract String getPrefix();
	
	/**
	 * @return
	 */
	protected abstract String getPostfix();
	
	@Override
	public String getStringValue(int level) {
		return new StringBuffer()
			.append(isStatement ? CodeUtil.getIndent(level) : "")
			.append(getPrefix())
			.append(op.getStringValue(level))
			.append(getPostfix())
			.toString();
		
	}

}
