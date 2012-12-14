/*
 *   SetVariableOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 26.07.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import org.apache.log4j.Logger;

import org.swfparser.AssignOperation;
import org.swfparser.CodeUtil;
import org.swfparser.ExecutionContext;
import org.swfparser.NameResolver;
import org.swfparser.Operation;
import org.swfparser.OperationFactory;
import org.swfparser.SkipOperation;
import com.jswiff.swfrecords.actions.NullStackValue;
import com.jswiff.swfrecords.actions.StackValue;

public class SetVariableOperation extends BinaryOperation implements OperationFactory, AssignOperation, SkipOperation {

	private static Logger logger = Logger.getLogger(SetVariableOperation.class);
	private Operation returnOperation = null;
	private boolean skipOperation = false; 
	
	public SetVariableOperation(Operation leftOp, Operation rightOp) {
		super(leftOp,rightOp);
		logger.debug("#SetVariableOperation() "+leftOp+" = "+rightOp);
		handleIncrementDecrement();
	}
	
	public SetVariableOperation(ExecutionContext context) {
		super(context.getExecStack());
		logger.debug("#SetVariableOperation() "+leftOp+" = "+rightOp);
		handleIncrementDecrement();
		handleEnumerate(context);
	}
	
	/**
	 * 
	 */
	private void handleEnumerate(ExecutionContext context) {
		
		// If operation under Enumerate/Enumerate2 and NullStackValue is assigned, 
		// set leftOp as variable in for..in statement and skip this statement.
		if (!context.getOperationStack().isEmpty() 
				&& (context.getOperationStack().peek() instanceof ForInOperation)
				&& rightOp instanceof NullStackValue) {
			
			((ForInOperation)context.getOperationStack().peek()).setVariable(leftOp);
			skipOperation = true;
		}
		
	}
	
	protected void handleIncrementDecrement() {
		if (rightOp instanceof SimpleIncrementOperation) {
			Operation underlyingOp = ((SimpleIncrementOperation)rightOp).getOp();
			if (underlyingOp instanceof GetVariableOperation) {
				GetVariableOperation getVariable = (GetVariableOperation) underlyingOp; 
				Operation variableName = getVariable.getOp();
				if (variableName.equals(leftOp)) {
					returnOperation = new PostIncrementOperation(underlyingOp, true);
				}
			}
			
		}
		if (rightOp instanceof SimpleDecrementOperation) {
			Operation underlyingOp = ((SimpleDecrementOperation)rightOp).getOp();
			if (underlyingOp instanceof GetVariableOperation) {
				GetVariableOperation getVariable = (GetVariableOperation) underlyingOp; 
				Operation variableName = getVariable.getOp();
				if (variableName.equals(leftOp)) {
					returnOperation = new PostDecrementOperation(underlyingOp, true);
				}
			}
			
		}
		logger.debug("Return operation is " + returnOperation );
	}

	@Override
	public String getSign() {
		return "=";
	}
	
	@Override
	public String getLeftValue() {
		String variableName;
		if (leftOp instanceof StackValue && StackValue.TYPE_STRING==((StackValue)leftOp).getType())  {
//			NameResolver.getVariableName( ((StackValue)leftOp).getString() )
			variableName = ((StackValue)leftOp).getString();
		} else {  
			variableName = "eval(" + leftOp.getStringValue(0) + ")";
		}
		
		return variableName;
	}
	
	@Override
	public String getStringValue(int level) {
		return CodeUtil.getIndent(level) + super.getStringValue(level);
	}
	
	/* (non-Javadoc)
	 * @see org.swfparser.operation.BinaryOperation#getRightValue()
	 * 
	 * DO NOT escape strings in right part of expression
	 * 
	 */
	@Override
	public String getRightValue() {
		String rightString = rightOp.getStringValue(0);
		
		if (rightOp.getPriority() > getPriority()) {
			rightString = "("+rightString+")";
		}
		
		return rightString;
	}

	public Operation getObject() {
		return returnOperation != null ? returnOperation : this;
	}

	public Operation getLeftPart() {
		return leftOp;
	}

	public Operation getRightPart() {
		return rightOp;
	}

	public boolean skip() {
		return skipOperation;
	}

}
