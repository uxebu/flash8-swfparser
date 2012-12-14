/*
 *   OrOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: Jul 28, 2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Stack;

import org.apache.log4j.Logger;

import org.swfparser.Operation;
import org.swfparser.Priority;

public class OrOperation extends BinaryLogicalOperation {

	private static Logger logger = Logger.getLogger(OrOperation.class);
	
	public OrOperation(Stack<Operation> stack) {
		super(stack);
	}
	
	public OrOperation(Operation op1, Operation op2) {
		super(op1,op2);
		logger.debug("Creating OR with "+op1+" and "+op2);
	}
	
//	public OrOperation(Stack<Operation> stack) {
//		super(stack);
//	}

//	@Override
//	public String getInvertedSign() {
//		return "&&";
//	}

	@Override
	public String getSign() {
		return "||";
	}
	
	@Override
	public String toString() {
		return "OrOperation("+leftOp+","+rightOp+")";
	}

	@Override
	public Operation getInvertedOperation() {
		return new AndOperation(new NotOperation(leftOp),new NotOperation(rightOp));
	}
	
	@Override
	public int getPriority() {
		return Priority.OR;
	}

}
