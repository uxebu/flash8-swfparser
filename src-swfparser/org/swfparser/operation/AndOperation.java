/*
 *   AndOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: Jul 28, 2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Stack;

import org.apache.log4j.Logger;

import org.swfparser.AndOrOptimizer;
import org.swfparser.BooleanOperation;
import org.swfparser.Operation;
import org.swfparser.Priority;

public class AndOperation extends BinaryLogicalOperation {

	private static Logger logger = Logger.getLogger(AndOperation.class);
	
	public AndOperation(Stack<Operation> stack) {
		super(stack);
	}
	
	public AndOperation(Operation op1, Operation op2) {
		super(op1,op2);
	}
	
	public static Operation createInstance(Operation op1, Operation op2) {
		AndOperation op = new AndOperation(op1,op2);
		logger.debug("Creating AND with "+op1+" and "+op2);
		AndOrOptimizer optimizer = new AndOrOptimizer(op);
		if (optimizer.getOptimizedOperation()!=null && !op.equals(optimizer.getOptimizedOperation())) {
			logger.debug("Optimized operation = "+optimizer.getOptimizedOperation());
			return optimizer.getOptimizedOperation() ;
		} else {
			return op;
		}
	}

//	public AndOperation(Stack<Operation> stack) {
//		super(stack);
//	}

//	@Override
//	public String getInvertedSign() {
//		return "||";
//	}

	@Override
	public String getStringValue(int level) {
		return super.getStringValue(level);
	}
	
	@Override
	public String getSign() {
		return "&&";
	}

	public int getPriority() {
		return Priority.AND;
	}
	
	@Override
	public String toString() {
		return "AndOperation("+leftOp+","+rightOp+")";
	}

	@Override
	public Operation getInvertedOperation() {
		return new OrOperation(new NotOperation(leftOp),new NotOperation(rightOp));
	}
	


}
