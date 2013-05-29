/*
 *   UnaryOperation.java
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
import org.apache.log4j.Logger;

import org.swfparser.CodeUtil;
import org.swfparser.Operation;

public abstract class UnaryOperation extends AbstractOperation {

	private static Logger logger = Logger.getLogger(UnaryOperation.class);
	protected Operation op;
	
	public UnaryOperation(Operation op) {
		this.op = op;
	}
	
	public UnaryOperation(Stack<Operation> stack) {
		super(stack);
		op =  stack.pop();
	}

	public int getArgsNumber() {
		return 1;
	}

	public abstract String getStringValue(int level);

	@Override
	public boolean equals(Object obj) {
//		logger.debug("Testing equals of "+obj+" to "+this);
		if (!(obj instanceof UnaryOperation)) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		
		UnaryOperation otherOp = (UnaryOperation) obj;
//		logger.debug("Eq "+this.op+" and "+op.op);
		return new EqualsBuilder()
			.append(this.op, otherOp.op)
			.isEquals();
		
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(op)
			.toHashCode();
	}

	public Operation getOp() {
		return op;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName()+"("+op+")";
	}
	
	public List<Operation> getOperations() {
		return Arrays.asList(op);
	}

	public void setOp(Operation op) {
		this.op = op;
	}
	
}
