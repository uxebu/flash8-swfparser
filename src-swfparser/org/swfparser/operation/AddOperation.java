/*
 *   AddOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 24.07.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Stack;

import org.swfparser.CodeUtil;
import org.swfparser.Operation;
import org.swfparser.Priority;
import com.jswiff.swfrecords.actions.Action;
import com.jswiff.swfrecords.actions.StackValue;

public class AddOperation extends BinaryActionOperation {
	public AddOperation(Stack<Operation> stack, Action action) {
		super(stack, action);
	}
	
	/* (non-Javadoc)
	 * @see org.swfparser.operation.BinaryOperation#getLeftValue()
	 * 
	 * Left value can also be constant if concatenating strings
	 * 
	 */
	public String getLeftValue() {
		String leftString = leftOp.getStringValue(0);
		
		if (leftOp.getPriority() > getPriority()) {
			leftString = "("+leftString+")";
		}
		
		return leftString;
	}
	
	
	@Override
	public String toString() {
		return "Add("+leftOp+" (+) "+rightOp+")";
	}
	
	@Override
	public int getPriority() {
		return Priority.PLUS_MINUS;
	}
}
