/*
 *   IfOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 25.07.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import org.springframework.util.Assert;

import org.apache.log4j.Logger;

import org.swfparser.BooleanOperation;
import org.swfparser.CodeUtil;
import org.swfparser.DualUse;
import org.swfparser.ExecutionContext;
import org.swfparser.Operation;
import org.swfparser.SkipOperation;
import org.swfparser.exception.StatementBlockException;
import com.jswiff.swfrecords.actions.Action;

public class IfOperation extends AbstractCompoundOperation implements SkipOperation {
	
	private static Logger logger = Logger.getLogger(IfOperation.class);
	
	protected Operation condition;
//	private List<Action> actions = new ArrayList<Action>();
	protected List<Operation> operations = new ArrayList<Operation>();
	protected boolean skip = false;
	
//	public Operation readIf(Stack<Operation> stack, List<Action> actions, ExecutionContext context) throws StatementBlockException {
//		
//	}
	
	public IfOperation(ExecutionContext context) {
		super(context);
	}
	
	public IfOperation(Stack<Operation> stack, List<Action> actions, ExecutionContext context) throws StatementBlockException {
		super(context);
		this.condition = stack.pop();
		Assert.isTrue(validateCondition(this.condition));
		
		readActions(actions);
		
	}
	
	/**
	 * @param actions
	 * @return execution stack after reading actions
	 * @throws StatementBlockException
	 */
	protected Stack<Operation> readActions(List<Action> actions) throws StatementBlockException {
		// Parse actions
		context.getOperationStack().push(this);
		logger.debug("stack before: "+context.getExecStack().size());
		Stack<Operation> beforeStack = context.getExecStack();
		Stack<Operation> newExecutionStack = copyExecutionStack( context.getExecStack() );
//		Stack<Operation> newExecutionStack = new Stack<Operation>();
//		Stack<Operation> beforeStack = context.getExecStack();
//		for (Operation op : beforeStack) {
//			newExecutionStack.push(op);
//		}
		
		boolean equalStacksBefore = Arrays.equals(context.getExecStack().toArray(), newExecutionStack.toArray());
		Assert.isTrue(equalStacksBefore);
		
		context.setExecStack(newExecutionStack);
		
		statementBlock.setExecutionContext(context);
		statementBlock.read(actions);
		operations = statementBlock.getOperations();
		
		logger.debug("stack after: "+context.getExecStack().size());
		
		Stack<Operation> afterStack = context.getExecStack();
		boolean equalStack = Arrays.equals(beforeStack.toArray(), afterStack.toArray());
		
		// Do not write action at all if unequal stacks (|| or && actions) and empty operations inside.
		if (!equalStack && operations.isEmpty()) {
			skip = true;
		}
		
		// Check if after stack contains some dual use operations in the stack such as CallFunctionOperation etc.
		if (!equalStack && beforeStack.size()<afterStack.size()) {
			while (!afterStack.isEmpty() && (afterStack.peek() instanceof DualUse)) {
				Operation dualUseOperation = afterStack.pop();
				((DualUse)dualUseOperation).markAsStatement();
				logger.debug("Adding dual-use "+dualUseOperation+" operation in the end of \"if\" statement");
				operations.add(dualUseOperation);
			}
		}
		
		// restore previous stack but with unequal stack handling
		context.setExecStack(handleUnequalStack(condition, beforeStack, afterStack));
		
		context.getOperationStack().pop();
		
		return afterStack;
	}
	
	private boolean validateCondition(Operation op) {
		logger.debug("Validating condition "+op);
		return (op instanceof BooleanOperation);
	}

	public int getArgsNumber() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	protected String getLoopHeader() {
		return "if";
	}
	
	protected String getHeaderLine() {
		return new StringBuffer()
			.append(getLoopHeader())
			.append(" (")
//			.append(getInvertedString(condition,0))
			.append(((BooleanOperation)condition).getInvertedOperation().getStringValue(0))
			.append(") {")
			.toString();
	}
	
	protected String getFooterLine() {
		return "}";
	}

	public String getStringValue(int level) {
		

//			logger.debug("Writing if with level "+level);
			StringBuffer buf =  new StringBuffer()
			.append(CodeUtil.getIndent(level))
			.append(getHeaderLine())
			.append("\n");
			
			for (Operation op : operations) {
				buf
					.append(op.getStringValue(level+1))
					.append(CodeUtil.endOfStatement(op))
					.append("\n");
			}
			
			buf.append(CodeUtil.getIndent(level));
			buf.append(getFooterLine());
			
			return buf.toString();
//		} else {
//			//
//			// Skip if() if no operations inside
//			//
//			StringBuffer buf =  new StringBuffer()
//			.append(getLoopHeader())
//			.append(" (")
//			.append(((BooleanOperation)condition).getInvertedOperation().getStringValue(level))
//			.append(")");
//	
//			return CodeUtil.getIndent(level)+"// Empty "+buf.toString()+" skipped";
//			return "";
			
//		}
	}
	
	@Override
	public String toString() {
		return "If("+condition+")";
	}
	
	public boolean skip() {
		return skip;
	}
	
//	protected String getInvertedString(Operation op, int level) {
//		return (condition instanceof BooleanOperation) 
//		? ((BooleanOperation)condition).getInvertedStringValue(level)
//		: "!"+op.getStringValue(level);
//	}

}
