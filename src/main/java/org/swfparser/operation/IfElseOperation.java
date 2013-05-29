/*
 *   IfElseOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: Jul 29, 2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import org.springframework.util.Assert;

import org.apache.log4j.Logger;

import org.swfparser.BooleanOperation;
import org.swfparser.CodeUtil;
import org.swfparser.ExecutionContext;
import org.swfparser.Operation;
import org.swfparser.SkipOperation;
import org.swfparser.exception.StatementBlockException;
import com.jswiff.swfrecords.actions.Action;
import com.jswiff.swfrecords.actions.Branch;

public class IfElseOperation extends AbstractCompoundOperation implements SkipOperation {

	private static Logger logger = Logger.getLogger(IfElseOperation.class);
	
	private Operation condition;
	protected boolean skip = false;

//	private List<Action> ifOperations;
//	private List<Action> elseOperations;
	
	private List<Operation> ifOperations = new ArrayList<Operation>();
	private List<Operation> elseOperations = new ArrayList<Operation>();

	public IfElseOperation(Stack<Operation> stack, ExecutionContext context, List<Action> ifActions, List<Action> elseActions) throws StatementBlockException {
		super(context);
		this.condition = stack.pop();
		
//		context.getOperationStack().push(this);
		
		BlockExecutionResult ifExecutionResult = executeWithCopiedStack(context, ifActions, this); 
		ifOperations = ifExecutionResult.getOperations();
		
		BlockExecutionResult elseExecutionResult = executeWithCopiedStack(context, elseActions, this);
		elseOperations = elseExecutionResult.getOperations();
		
		logger.debug("if-size : "+ifOperations.size());
		logger.debug("else-size : "+elseOperations.size());
		
		boolean equalStacks = Arrays.equals(elseExecutionResult.getStack().toArray(), ifExecutionResult.getStack().toArray());
		if (!equalStacks && ifOperations.isEmpty() && elseOperations.isEmpty()) {
			skip = true;
		}
		
		// before stack = stack if jump was executed = else stack
		// after stack = stack if jump was not executed = if stack
		context.setExecStack(handleUnequalStack(condition, elseExecutionResult.getStack(), ifExecutionResult.getStack()));
		
//		context.getOperationStack().pop();
		

	}

	public String getStringValue(int level) {
		StringBuffer buf = new StringBuffer()
			.append(CodeUtil.getIndent(level))
			.append("if")
			.append(" (")
			.append(((BooleanOperation)condition).getInvertedOperation().getStringValue(level))
			.append(") {\n");

		for (Operation op : ifOperations) {
			buf
				.append(op.getStringValue(level + 1))
				.append(CodeUtil.endOfStatement(op))
				.append("\n");
		}

		buf.append(CodeUtil.getIndent(level));
		buf.append("} else {\n");
		
		for (Operation op : elseOperations) {
			buf
				.append(op.getStringValue(level + 1))
				.append(CodeUtil.endOfStatement(op))
				.append("\n");
		}
		
		buf.append(CodeUtil.getIndent(level));
		buf.append("}");

		return buf.toString();
	}
	
	@Override
	public String toString() {
		return "IfElse("+condition+")";
	}

	public boolean skip() {
		return skip;
	}

}
