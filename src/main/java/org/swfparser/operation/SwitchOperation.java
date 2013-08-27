/*
 *   SwitchOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 05.09.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.springframework.util.Assert;

import org.apache.log4j.Logger;

import org.swfparser.CodeUtil;
import org.swfparser.ExecutionContext;
import org.swfparser.Operation;
import org.swfparser.exception.StatementBlockException;
import org.swfparser.pattern.SwitchPattern;
import com.jswiff.swfrecords.actions.Action;

public class SwitchOperation extends AbstractCompoundOperation {

	private static Logger logger = Logger.getLogger(SwitchOperation.class);
	private Operation switchParam;
	private Operation externalCondition;
	private List<Operation> conditions = new ArrayList<Operation>();
	private List<List<Operation>> switchStatements = new ArrayList<List<Operation>>();
	private Set<Operation> leftConditionOperations = new HashSet<Operation>();
	private Set<Operation> rightConditionOperations = new HashSet<Operation>();
	private List<Operation> defaultOperations = new ArrayList<Operation>();
	
	
	public SwitchOperation(ExecutionContext context, SwitchPattern pattern) throws StatementBlockException {
		super(context);
		externalCondition = stack.pop();
		
		Map<Operation,Integer> opCountMap = new HashMap<Operation, Integer>();
		
		Assert.isInstanceOf(EqualsOperation.class, externalCondition);
		
		conditions.add( externalCondition );
		leftConditionOperations.add(((EqualsOperation)externalCondition).getLeftOp());
		rightConditionOperations.add(((EqualsOperation)externalCondition).getRightOp());
		logger.debug("Cond.1 = "+externalCondition);
		
		int idx = 2;
		for (List<Action> actionBlock : pattern.getConditionBlocks()) {
			executeWithSameStack(context, actionBlock, this);
			EqualsOperation switchCondition = (EqualsOperation) stack.pop(); // emulate if statement
			conditions.add( switchCondition );
			leftConditionOperations.add( switchCondition.getLeftOp() );
			rightConditionOperations.add( switchCondition.getRightOp() );
			logger.debug("Cond."+(idx++)+" = "+switchCondition);
		}
		
//		if (leftConditionOperations.size() == 1) {
//			switchParam = leftConditionOperations.iterator().next();
//			conditions = getLeftOrRightOperations(false);
//		} else if (rightConditionOperations.size() == 1) {
//			switchParam = rightConditionOperations.iterator().next();
//			conditions = getLeftOrRightOperations(true);
//		}
		
		// Assume left op is switch param
		conditions = getLeftOrRightOperations(false);
		switchParam = ((EqualsOperation)externalCondition).getLeftOp();
		
		// Read statements
		List<Stack<Operation>> stacksAfterSwitchBlocks = new ArrayList<Stack<Operation>>();
		for (List<Action> actionBlock : pattern.getSwitchBlocks()) {
			BlockExecutionResult executionResult = executeWithCopiedStack(context, actionBlock, this);
			stacksAfterSwitchBlocks.add( executionResult.getStack() );
			switchStatements.add( executionResult.getOperations() );
		}
		
		// Read default block
		if (!pattern.getDefaultActions().isEmpty()) {
			defaultOperations.addAll( executeWithCopiedStack(context, pattern.getDefaultActions(), this).getOperations() );
		}
		
		int i = 1;
		for (Stack<Operation> s : stacksAfterSwitchBlocks) {
			logger.debug("Dumping stack "+(i++)+":");
			for (int j = s.size()-1; j>=0; j--) {
				logger.debug("###S:"+s.get(j));
			}
		}
		
		// set stack to the stack we get from executing \switch statements
		if (equalStacks(stacksAfterSwitchBlocks)) {
			context.setExecStack( copyExecutionStack(stacksAfterSwitchBlocks.get(0)) );
		} else {
			logger.error("Unequal stacks after switch operation!");
		}

	}

	public String getStringValue(int level) {
		StringBuffer buf = new StringBuffer()
			.append(CodeUtil.getIndent(level))
			.append("switch (")
			.append(switchParam.getStringValue(level))
			.append(") {\n");
		
		for (int j=0; j<conditions.size(); j++) {
			Operation cond = conditions.get(j);
			buf
			.append(CodeUtil.getIndent(level+1))
			.append("case ")
			.append(cond.getStringValue(0))
			.append(":\n");
			
			// switch statements
			for (Operation op : switchStatements.get(j)) {
				buf
				.append(op.getStringValue(level+2))
				.append(CodeUtil.endOfStatement(op))
				.append("\n");
			}
			buf.append(CodeUtil.getIndent(level+1)).append("\n");
		}
        
        // default clause
        if (!defaultOperations.isEmpty()) {
            buf
            .append(CodeUtil.getIndent(level+1))
            .append("default:\n");

            for (Operation op : defaultOperations) {
                buf
                .append(op.getStringValue(level+2))
                .append(CodeUtil.endOfStatement(op))
                .append("\n");
            }
        }

		buf.append(CodeUtil.getIndent(level)).append("}");
		
		return buf.toString();
	}
	
	private Operation getFirstCondition(Operation condition) {
		if (condition instanceof EqualsOperation) {
			return getSimpleOp ( ((EqualsOperation)condition).getLeftOp(), ((EqualsOperation)condition).getRightOp());
		} else {
			throw new IllegalArgumentException("Invalid condition in switch(): "+condition);
		}
	}
	
	/**
	 * TODO: consider using op1 or op2
	 * 
	 * @param op1
	 * @param op2
	 * @return
	 */
	private Operation getSimpleOp(Operation op1, Operation op2) {
		return op1;
//		if (op1 instanceof StackValue) {
//			return op1;
//		} else if (op2 instanceof StackValue) {
//			return op2;
//		} else {
//			throw new IllegalArgumentException("Invalid type of condition args in switch(): "+op1+" and "+op2);
//		}
	}
	
	private List<Operation> getLeftOrRightOperations(boolean left) {
		List<Operation> newConditions = new ArrayList<Operation>();
		for (Operation op : conditions) {
			newConditions.add(left ? ((EqualsOperation)op).getLeftOp() : ((EqualsOperation)op).getRightOp()); 
		}
		return newConditions;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName()+"("+switchParam+")";
	}

}
