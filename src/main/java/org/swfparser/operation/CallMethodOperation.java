/*
 *   CallMethodOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 24.07.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.springframework.util.Assert;

import org.apache.log4j.Logger;

import org.swfparser.BooleanOperation;
import org.swfparser.CodeUtil;
import org.swfparser.DualUse;
import org.swfparser.Operation;
import org.swfparser.Priority;
import com.jswiff.swfrecords.actions.StackValue;

public class CallMethodOperation extends AbstractOperation implements BooleanOperation,DualUse {

	private static Logger logger = Logger.getLogger(CallMethodOperation.class);
	
	private Operation numberOfArguments;
	private Operation variable;
	private Operation methodName;
	private List<Operation> arguments = new ArrayList<Operation>();
	private boolean isStatement = false;
	
	public CallMethodOperation(Stack<Operation> stack) {
		super(stack);
		methodName = stack.pop();
		variable = stack.pop();
		numberOfArguments = stack.pop();
		
		// Assure numberOfArguments parameter is of type StackValue
		Assert.isInstanceOf(StackValue.class, numberOfArguments);
		
		int numberOfArgs = ((StackValue)numberOfArguments).getIntValue();
		
		logger.debug("Number of args = "+numberOfArgs);
		for (int j=0;j<numberOfArgs;j++) {
			arguments.add( stack.pop() );
		}
		
	}

	public int getArgsNumber() {
		return arguments.size()+2;
	}

	public String getStringValue(int level) {
		StringBuilder buf = new StringBuilder();
		if (isStatement) {
			buf.append(CodeUtil.getIndent(level));
		}
        String simpleValue = variable.getStringValue(level);
        buf.append(simpleValue.equals("super") ? "this.constructor.__super__" : simpleValue);

        buf.append(CodeUtil.getMethodName(methodName));
        buf.append("(");

		// print arguments
		int idx=0;
		for (Operation op : arguments) {
			if (idx++ > 0) {
				buf.append(",");
			}
			String paramName = op.getStringValue(level);
			buf.append(paramName);
		}
		
		buf.append(")");
		
		return buf.toString();
	}

    public int getPriority() {
		return Priority.CALL_FUNCTION;
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

	public void markAsStatement() {
		isStatement = true;
	}

	public List<Operation> getOperations() {
		List<Operation> operations = new ArrayList<Operation>();
		operations.add(methodName);
		operations.add(variable);
		operations.add(numberOfArguments);
		operations.addAll(arguments);
		return operations;
	}
	
}
