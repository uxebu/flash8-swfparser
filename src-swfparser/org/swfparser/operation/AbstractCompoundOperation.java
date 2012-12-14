/*
 *   AbstractCompoundOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 25.07.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import org.springframework.util.Assert;

import org.apache.log4j.Logger;

import org.swfparser.BooleanOperation;
import org.swfparser.CodeUtil;
import org.swfparser.DualUse;
import org.swfparser.ExecutionContext;
import org.swfparser.ExecutionStack;
import org.swfparser.Operation;
import org.swfparser.StatementBlock;
import org.swfparser.exception.StatementBlockException;
import com.jswiff.swfrecords.actions.Action;

// TODO: Refactor to accept only context
public abstract class AbstractCompoundOperation extends AbstractOperation {
	
	private static Logger logger = Logger.getLogger(AbstractCompoundOperation.class);

	protected StatementBlock statementBlock;
	protected ExecutionContext context;
	
	public AbstractCompoundOperation(ExecutionContext context) {
		super(context.getExecStack());
		this.context = context;
		this.statementBlock = CodeUtil.getStatementBlockReader();
	}

	public int getArgsNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public int getPrintOffset() {
		return (context.getActionStack() != null ) ? context.getActionStack().size() : 0;
	}
	
	protected List<Operation> executeWithSameStack(ExecutionContext context, List<Action> actions, Operation parentOperation) throws StatementBlockException {
		StatementBlock block = CodeUtil.getStatementBlockReader();
		context.getOperationStack().push(parentOperation); // save parent operation in operation stack
		block.setExecutionContext(context);
		block.read(actions);
		context.getOperationStack().pop(); // restore operation stack
		return block.getOperations();
	}
	
	protected BlockExecutionResult executeWithCopiedStack(ExecutionContext context, List<Action> actions, Operation parentOperation) throws StatementBlockException {
		StatementBlock block = CodeUtil.getStatementBlockReader();
		context.getOperationStack().push(parentOperation); // save parent operation in operation stack
		Stack<Operation> currentExecutionStack = context.getExecStack(); // save current execution stack
		context.setExecStack(copyExecutionStack(currentExecutionStack)); // set a copy of current stack
		
		block.setExecutionContext(context);
		block.read(actions);
		BlockExecutionResult blockExecutionResult = new BlockExecutionResult(block.getOperations(),context.getExecStack());
		
		context.setExecStack(currentExecutionStack); // restore previous execution stack
		context.getOperationStack().pop(); // restore operation stack
		return blockExecutionResult;
	}
	
	protected Stack<Operation> copyExecutionStack(Stack<Operation> currentExecutionStack) {
		Stack<Operation> newExecutionStack = new ExecutionStack<Operation>();
		for (Operation op : currentExecutionStack) {
			newExecutionStack.push(op);
		}
//		newExecutionStack.setSize(currentExecutionStack.size());
//		Collections.copy(newExecutionStack, currentExecutionStack);
		return newExecutionStack;
	}
	
	protected Stack<Operation> createEmptyExecutionStack() {
		return new ExecutionStack<Operation>();
	}
	
	
	/**
	 *    -- before stack
	 * 00: jump 04 (jump to 04 on jumpCondition)
	 * 01:
	 * 02:
	 * 03:
	 *    -- after stack (if jump not succeeded)
	 * 04:
	 * 
	 * @param jumpCondition
	 * @param beforeStack = jump executed stack
	 * @param afterStack = jump NOT executed stack
	 * 
	 * @return a new copy of stack
	 */
	protected Stack<Operation> handleUnequalStack(Operation jumpCondition, Stack<Operation> beforeStack, Stack<Operation> afterStack) {
		boolean equalStacks = Arrays.equals(beforeStack.toArray(), afterStack.toArray());
		if (equalStacks) {
			return copyExecutionStack(afterStack);
		}
		
		logger.debug("Unequal stacks");
		
		// Handle only equal size stacks
		if (beforeStack.size() == afterStack.size()) {
			return handleEqualSizeStack(jumpCondition, beforeStack, afterStack);
		} else {
			return handleUnEqualSizeStack(jumpCondition, beforeStack, afterStack);
		}
		
		
			
			
	}

	protected Stack<Operation> handleUnEqualSizeStack(Operation jumpCondition, Stack<Operation> beforeStack, Stack<Operation> afterStack) {
		logger.debug("Unequal stack size, beforeStack = "+beforeStack.size()+", afterStack = "+afterStack.size());
		
//		Assert.isTrue(afterStack.size() > beforeStack.size());
//		while (!afterStack.isEmpty() && (afterStack.peek() instanceof DualUse)) {
//			context.getafterStack.pop();
//		}
		
		return beforeStack;
	}
	
	protected Stack<Operation> handleEqualSizeStack(Operation jumpCondition, Stack<Operation> beforeStack, Stack<Operation> afterStack) {
		int size = beforeStack.size();
		int unequalItems = 0;
		for (int j=size-1; j>=0; j--) {
			logger.debug("CHK(PREV<->NEXT): "+beforeStack.get(j)+" <-> "+afterStack.get(j));
			if (!beforeStack.get(j).equals(afterStack.get(j))) {
				Assert.isTrue(j == size-1); // allow only stack-top items to be unequal
				unequalItems++;
			}
		}
		
		logger.debug("Unequal items = "+unequalItems);
		
		// Handle only 1 unequal item
		Assert.isTrue(unequalItems == 1);
		
		// Handle only boolean operations
		if (! (beforeStack.peek() instanceof BooleanOperation)) {
//			throw new IllegalArgumentException("Not boolean: "+beforeStack.peek());
			logger.error("Not boolean: "+beforeStack.peek());
		}
		if (! (afterStack.peek() instanceof BooleanOperation)) {
//			throw new IllegalArgumentException("Not boolean: "+afterStack.peek());
			logger.error("Not boolean: "+afterStack.peek());
		}
		
		logger.debug("Try to simplify if condition...");
		/* 
		 
		 condition = a
		 before stack = b
		 after stack = c
		 a&&b || !a&&c
		 
		 */
		Stack<Operation> newExecutionStack = copyExecutionStack(afterStack);
		if (jumpCondition.equals(beforeStack.peek())) {
			// a&&a || !a&&c = a || !a&&c = a||c;
			logger.debug("Simplifying to OR");
			Operation nextStackValue = newExecutionStack.pop();
			newExecutionStack.push(new OrOperation(jumpCondition,nextStackValue));
		} else if (jumpCondition.equals(new NotOperation(beforeStack.peek()))) {
			// a&&!a || !a&&c = 0 || !a&&c = !a&&c
			logger.debug("Simplifying to AND");
			Operation nextStackValue = newExecutionStack.pop();
			newExecutionStack.push(AndOperation.createInstance(new NotOperation(jumpCondition),nextStackValue));
		} else {
			logger.debug("Simplifying to TERNARY");
//			Operation op1 = AndOperation.createInstance(jumpCondition,beforeStack.peek());
//			Operation op2 = AndOperation.createInstance(new NotOperation(jumpCondition),newExecutionStack.pop());
//			newExecutionStack.push(BranchOperation.createInstance(op1, op2));
			newExecutionStack.push(
					new TernaryOperation(jumpCondition,beforeStack.peek(),newExecutionStack.pop())
					);
		}
		
		return newExecutionStack;
	}
	
	protected static class BlockExecutionResult {
		private List<Operation> operations;
		private Stack<Operation> stack;
		
	

		public BlockExecutionResult(List<Operation> operations, Stack<Operation> stack) {
			super();
			this.operations = operations;
			this.stack = stack;
		}

		public List<Operation> getOperations() {
			return operations;
		}

		public void setOperations(List<Operation> operations) {
			this.operations = operations;
		}

		public Stack<Operation> getStack() {
			return stack;
		}

		public void setStack(Stack<Operation> stack) {
			this.stack = stack;
		}

		
		
		
		
	}
	
	protected boolean equalStacks(List<Stack<Operation>> stacks) {
		boolean eq = true;
		for (int j = 0; j<stacks.size()-1; j++) {
			if (! Arrays.equals(stacks.get(j).toArray(), stacks.get(j+1).toArray()) ) {
				eq = false;
				break;
			}
		}
		return eq;
	}
	
	/* (non-Javadoc)
	 * @see org.swfparser.Operation#getOperations()
	 *
	 * Return empty list for compound operations as they are usually statements
	 * 
	 */
	public List<Operation> getOperations() {
		return Collections.EMPTY_LIST;
	}

}
