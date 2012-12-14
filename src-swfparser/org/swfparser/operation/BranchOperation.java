/*
 *   BranchOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: Jul 28, 2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import org.apache.log4j.Logger;

import org.swfparser.AndOrOptimizer;
import org.swfparser.BooleanOperation;
import org.swfparser.Operation;
import org.swfparser.Priority;

public class BranchOperation extends OrOperation {

	private static Logger logger = Logger.getLogger(BranchOperation.class);
	
	private Operation conditionOperation;
	private Operation stackOperation;
	
	public BranchOperation(Operation conditionOperation, Operation stackOperation) {
		super(conditionOperation,stackOperation);
		this.conditionOperation = conditionOperation;
		this.stackOperation = stackOperation;
		
	}
	
	public static Operation createInstance(Operation op1, Operation op2) {
		BranchOperation op = new BranchOperation(op1,op2);
		logger.debug("Creating BRANCH-OR with "+op1+" and "+op2);
		AndOrOptimizer optimizer = new AndOrOptimizer(op);
		if (optimizer.getOptimizedOperation()!=null && !op.equals(optimizer.getOptimizedOperation())) {
			logger.debug("Optimized operation = "+optimizer.getOptimizedOperation());
			return optimizer.getOptimizedOperation() ;
		} else {
			return op;
		}
	}

	public int getArgsNumber() {
		return 2;
	}


	/* (non-Javadoc)
	 * INVERT *ONLY* stack value
	 */
	@Override
	public Operation getInvertedOperation() {
		return new AndOperation(conditionOperation,new NotOperation(stackOperation));
	}
	
	
//	public String getInvertedStringValue(int level) {
//		String conditionValue = conditionOperation.getStringValue(level);
//		if (conditionOperation.getPriority() > Priority.AND) {
//			conditionValue = "("+conditionValue+")";
//		}
//		
//		String stackValue = (stackOperation instanceof BooleanOperation) 
//			?
//				((BooleanOperation)stackOperation).getInvertedOperation().getStringValue(level)
//			:
//				"!"+conditionOperation.getStringValue(level);
//		if (stackOperation.getPriority() > Priority.AND) {
//			stackValue = "("+stackValue+")";
//		}
//		
//		return conditionValue + "&&" + stackValue;
//	}

	
//	public String getStringValue(int level) {
//		String conditionValue = conditionOperation.getStringValue(level);
//		if (conditionOperationPriority > Priority.OR) {
//			conditionValue = "("+conditionValue+")";
//		}
//		
//		String stackValue = stackOperation.getStringValue(level);
//		if (stackOperationPriority > Priority.OR) {
//			stackValue = "("+stackValue+")";
//		}
//		
//		return conditionValue + " || " + stackValue;
//	}


}
