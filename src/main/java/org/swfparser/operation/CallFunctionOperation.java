/*
 *   CallFunctionOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 26.07.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.swfparser.BooleanOperation;
import org.swfparser.CodeUtil;
import org.swfparser.DualUse;
import org.swfparser.Operation;
import org.swfparser.Priority;
import com.jswiff.swfrecords.actions.StackValue;

public class CallFunctionOperation extends AbstractOperation implements BooleanOperation, DualUse {

	private Operation functionName;
	private List<Operation> args;
	private boolean isStatement = false;
	
	public CallFunctionOperation(Stack<Operation> stack) {
		super(stack);
		functionName = stack.pop();
		int numArgs = ((StackValue)stack.pop()).getIntValue();
		args = new ArrayList<Operation>(numArgs);
		for (int j=0;j<numArgs;j++) {
			args.add(stack.pop());
		}
	}

	public int getArgsNumber() {
		return 0;
	}

	public String getStringValue(int level) {
		StringBuffer buf = new StringBuffer();
		if (isStatement) {
			buf.append(CodeUtil.getIndent(level));
		}
		String funcName = (functionName instanceof StackValue && StackValue.TYPE_STRING==((StackValue)functionName).getType()) 
		? ((StackValue)functionName).getString() 
				: functionName.getStringValue(level);
		buf.append(funcName);
		buf.append("(");

		// print arguments
		int idx=0;
		for (Operation op : args) {
			if (idx++ > 0) {
				buf.append(",");
			}
			buf.append(op.getStringValue(level));
		}
		
		buf.append(")");
		return buf.toString();
	}

	public void markAsStatement() {
		isStatement = true;
		
	}

	public List<Operation> getOperations() {
		List<Operation> operations = new ArrayList<Operation>();
		operations.add(functionName);
		operations.addAll(args);
		return operations;
	}

	@Override
	public String toString() {
		return "CallFunctionOperation("+functionName+"["+args.size()+"]"+")";
	}

	public Operation getInvertedOperation() {
		return new SimpleInvertedOperation(this);
//		return new SimpleInvertedOperation(this) {
//			@Override
//			public int getPriority() {
//				return Priority.CALL_FUNCTION;
//			}
//		};
	}


}
