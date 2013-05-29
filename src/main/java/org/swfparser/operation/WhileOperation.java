/*
 *   WhileOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: Jul 28, 2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.List;
import java.util.Stack;

import org.swfparser.ExecutionContext;
import org.swfparser.Operation;
import org.swfparser.exception.StatementBlockException;
import com.jswiff.swfrecords.actions.Action;

public class WhileOperation extends IfOperation {

	public WhileOperation(Stack<Operation> stack, List<Action> actions, ExecutionContext context) throws StatementBlockException {
		super(stack, actions, context);
		
		// if "continue" is the last operation, remove it
		if (!operations.isEmpty()) {
			Operation lastOperation = operations.get(operations.size()-1);
			if (lastOperation.equals(new SimpleOperation("continue"))) {
//				operations.set(operations.size()-1, new CommentOperation("continue skipped"));
				operations.remove(operations.size()-1);
			}
		}
		
	}
	
	@Override
	protected String getLoopHeader() {
		return "while";
	}
	
	public Operation getCondition() {
		return condition;
	}
	
	public List<Operation> getInlineOperations() {
		return operations;
	}


}
