/*
 *   BinaryOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 24.07.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import org.swfparser.Operation;

public abstract class BinaryOperation extends AbstractOperation {

	protected Operation leftOp;
	protected Operation rightOp;
	
	public BinaryOperation(Operation op1, Operation op2) {
		super();
		leftOp = op1;
		rightOp = op2;
	}
	
	public BinaryOperation(Stack<Operation> stack) {
		super(stack);
		rightOp = stack.pop();
		leftOp = stack.pop();
	}

	public int getArgsNumber() {
		return 2;
	}

	public String getStringValue(int level) {
		return getLeftValue()+ " "	+ getSign() + " " + getRightValue();

	}
	
	public abstract String getSign();
	
	public String getLeftValue() {
		String leftString = leftOp.getStringValue(0);
		
//		if (leftOp instanceof ConstantOperation) {
//			leftString = ((ConstantOperation)leftOp).formatString();
//		}		
				
		if (leftOp.getPriority() >= getPriority()) {
			leftString = "("+leftString+")";
		}
		
		return leftString;
	}
	
	public String getRightValue() {
		String rightString = rightOp.getStringValue(0);
		
		if (rightOp.getPriority() >= getPriority()) {
			rightString = "("+rightString+")";
		}
		
		return rightString;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof BinaryOperation)) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		BinaryOperation op = (BinaryOperation) obj;
		return new EqualsBuilder()
			.append(getSign(), op.getSign())
			.append(leftOp, op.leftOp)
			.append(rightOp, op.rightOp)
			.isEquals();
		
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(leftOp)
			.append(rightOp)
			.toHashCode();
	}

	public Operation getLeftOp() {
		return leftOp;
	}

	public Operation getRightOp() {
		return rightOp;
	}
	
	public List<Operation> getOperations() {
		return Arrays.asList(leftOp,rightOp);
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName()+"("+leftOp+","+rightOp+")";
	}
	

}
