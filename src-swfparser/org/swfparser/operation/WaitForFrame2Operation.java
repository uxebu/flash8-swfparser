/*
 *   WaitForFrame2Operation.java
 * 	 @Author Oleg Gorobets
 *   Created: 14.08.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.List;
import java.util.Stack;

import org.swfparser.ExecutionContext;
import org.swfparser.Operation;
import org.swfparser.exception.StatementBlockException;
import com.jswiff.swfrecords.actions.Action;
import com.jswiff.swfrecords.actions.StackValue;
import com.jswiff.swfrecords.actions.WaitForFrame;
import com.jswiff.swfrecords.actions.WaitForFrame2;

public class WaitForFrame2Operation extends WaitForFrameOperation {

	public WaitForFrame2Operation(Stack<Operation> stack, ExecutionContext context, WaitForFrame2 waitForFrame2, List<Action> actions) throws StatementBlockException {
		super(stack, context, null, actions);
	}
	
	@Override
	protected Operation getFrame() {
		return stack.pop();
	}

}
