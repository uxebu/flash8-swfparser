/*
 *   BinaryBooleanOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 26.07.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Stack;

import org.springframework.util.Assert;

import org.apache.log4j.Logger;

import org.swfparser.BooleanOperation;
import org.swfparser.Operation;

public abstract class BinaryBooleanOperation extends BinaryOperation implements BooleanOperation {

	private static Logger logger = Logger.getLogger(BinaryBooleanOperation.class);
	
	public BinaryBooleanOperation(Operation op1, Operation op2) {
		super(op1, op2);
		// Do not test operations to be boolean as they may NOT be boolean e.g. in less,greater operations
		// like 2-1 > 0
		
//		logger.debug("Testing binary operations "+op1+" and "+op2);
//		Assert.isTrue(validateOperation(op1));
//		Assert.isTrue(validateOperation(op2));
	}
	
	public BinaryBooleanOperation(Stack<Operation> stack) {
		super(stack);
	}

	@Override
	public abstract String getSign();
	
//	public abstract String getInvertedSign();

//	public String getInvertedStringValue(int level) {
//		String leftString = leftOp.getStringValue(level);
//				
//				
//		if (leftOp.getPriority() > getPriority()) {
//			leftString = "("+leftString+")";
//		}
//				
//		String rightString = rightOp.getStringValue(level);
//		
//		
//		if (rightOp.getPriority() > getPriority()) {
//			rightString = "("+rightString+")";
//		}		
//				
//		return leftString+" "+getInvertedSign()+" "+rightString;
//	}
	
	protected boolean validateOperation(Operation op) {
		return (op instanceof BooleanOperation);
	}
	
	public abstract Operation getInvertedOperation();
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof NotOperation) {
			return obj.equals(this); // invert comparison
		} else {
			return super.equals(obj);
		}
	}

}
