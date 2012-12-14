/*
 *   GetVariableOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 24.07.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Stack;

import org.swfparser.BooleanOperation;
import org.swfparser.NameResolver;
import org.swfparser.Operation;
import org.swfparser.Priority;
import com.jswiff.swfrecords.actions.StackValue;

// TODO: check BooleanOperation 
public class GetVariableOperation extends UnaryOperation implements BooleanOperation {

	public GetVariableOperation(Stack<Operation> stack) {
		super(stack);
	}

	public GetVariableOperation(Operation op) {
		super(op);
	}

	@Override
	public String getStringValue(int level) {
		String variableName;
		if (op instanceof StackValue && StackValue.TYPE_STRING==((StackValue)op).getType()) { 
			variableName = ((StackValue)op).getString();
		} else {
			variableName = "eval("+op.getStringValue(level)+")";
		}
//		return NameResolver.getVariableName(vaiableName);
		return variableName;
	}
	
	@Override
	public String toString() {
		return "GetVariable("+op+")";
	}
	
	@Override
	public int getPriority() {
		return Priority.HIGHEST;
	}

	public Operation getInvertedOperation() {
		return new SimpleInvertedOperation(this);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof NotOperation) {
			return obj.equals(this); // invert comparison
		} else {
			return super.equals(obj);
		}
	}
	
	

}
