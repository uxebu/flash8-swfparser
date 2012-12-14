/*
 *   SetTarget2Operation.java
 * 	 @Author Oleg Gorobets
 *   Created: 15.08.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.List;
import java.util.Stack;

import org.swfparser.ExecutionContext;
import org.swfparser.Operation;
import org.swfparser.exception.StatementBlockException;
import com.jswiff.swfrecords.actions.Action;
import com.jswiff.swfrecords.actions.SetTarget2;

public class SetTarget2Operation extends SetTargetOperation {

	public SetTarget2Operation(Stack<Operation> stack, ExecutionContext context, List<Action> actions, SetTarget2 target) throws StatementBlockException {
		super(stack, context, actions, null);
	}
	
	@Override
	protected Operation getTarget() {
		return stack.pop();
	}

}
