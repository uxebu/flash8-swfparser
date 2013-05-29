/*
 *   DoWhileOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 16.08.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.List;
import java.util.Stack;

import org.swfparser.ExecutionContext;
import org.swfparser.Operation;
import org.swfparser.exception.StatementBlockException;
import org.swfparser.pattern.Pattern;
import com.jswiff.swfrecords.actions.Action;

public class DoWhileOperation extends IfOperation {

	public DoWhileOperation(Stack<Operation> stack, List<Action> actions, ExecutionContext context) throws StatementBlockException {
		super(context);
		
		if (!actions.isEmpty()) {
			// remove DoWhilePattern from first action
			Pattern pattern = context.getPatternAnalyzerEx().getPatternByLabel(actions.get(0).getLabel());
			context.getPatternAnalyzerEx().clearBranchPattern(actions.get(0).getLabel());
			Stack<Operation> afterStack = readActions(actions);
			condition = afterStack.pop();
		}
		
	}
	
	@Override
	protected String getHeaderLine() {
		return "do {";
	}
	
	@Override
	protected String getFooterLine() {
		return new StringBuffer()
		.append("} while (")
		.append(condition.getStringValue(0))
		.append(")")
		.toString();
	}
	
	@Override
	public String toString() {
		return "do-while("+condition+")";
	}
	
	/* (non-Javadoc)
	 * @see org.swfparser.operation.AbstractCompoundOperation#handleUnequalStack(org.swfparser.Operation, java.util.Stack, java.util.Stack)
	 * TODO: Consider to handle unequal stack for do-while
	 */
	@Override
	protected Stack<Operation> handleUnequalStack(Operation jumpCondition, Stack<Operation> beforeStack, Stack<Operation> afterStack) {
//		return super.handleUnequalStack(jumpCondition, beforeStack, afterStack);
		return copyExecutionStack(afterStack);
	}
	
	/* (non-Javadoc)
	 * @see org.swfparser.operation.IfOperation#skip()
	 * 
	 * Do NOT skip empty while loops
	 * 
	 */
	@Override
	public boolean skip() {
		return false;
	}

}
