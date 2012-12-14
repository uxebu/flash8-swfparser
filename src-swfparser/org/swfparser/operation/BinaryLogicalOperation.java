/*
 *   BinaryLogicalOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: Jul 31, 2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Stack;

import org.apache.log4j.Logger;

import org.swfparser.AndOrOptimizer;
import org.swfparser.BooleanOperation;
import org.swfparser.Operation;

public abstract class BinaryLogicalOperation extends BinaryBooleanOperation {

	private static Logger logger = Logger.getLogger(BinaryLogicalOperation.class);
	
	protected Operation optimizedOperation;
	
	public BinaryLogicalOperation(Stack<Operation> stack) {
		super(stack);
	}
	
	public BinaryLogicalOperation(Operation op1, Operation op2) {
		super(op1, op2);
//		AndOrOptimizer optimizer = new AndOrOptimizer(this);
//		if (optimizer.getOptimizedOperation()!=null && !this.equals(optimizer.getOptimizedOperation())) {
//			optimizedOperation = optimizer.getOptimizedOperation();
//			logger.debug("Optimized operation = "+optimizedOperation);
//		}
	}

//	@Override
//	public String getInvertedStringValue(int level) {
//		String leftString = (leftOp instanceof BooleanOperation) ?  
//				((BooleanOperation)leftOp).getInvertedOperation().getStringValue(level) :
//					"!"+leftOp.getStringValue(level);
//
//				
//		if (leftOp.getPriority() > getPriority()) {
//			leftString = "("+leftString+")";
//		}
//				
//		String rightString = (rightOp instanceof BooleanOperation) ?  
//				((BooleanOperation)rightOp).getInvertedOperation().getStringValue(level) :
//					"!"+rightOp.getStringValue(level);
//		
//			
//		if (rightOp.getPriority() > getPriority()) {
//			rightString = "("+rightString+")";
//		}
//		
//		return leftString+" "+getInvertedSign()+" "+rightString;
//
//				
//	}
	

}
