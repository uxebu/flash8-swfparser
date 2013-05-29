/*
 *   SubtractOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: Jul 28, 2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Stack;

import org.swfparser.Operation;
import org.swfparser.Priority;
import com.jswiff.swfrecords.actions.Action;

public class SubtractOperation extends BinaryActionOperation {

	public SubtractOperation(Stack<Operation> stack, Action action) {
		super(stack, action);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int getPriority() {
		return Priority.PLUS_MINUS;
	}

}
