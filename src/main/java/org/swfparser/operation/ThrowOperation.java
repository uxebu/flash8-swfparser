package org.swfparser.operation;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import org.swfparser.CodeUtil;
import org.swfparser.Operation;

public class ThrowOperation extends AbstractOperation {

    protected Operation valueToThrow;

    public ThrowOperation(Stack<Operation> stack) {
   		super(stack);
   		valueToThrow = stack.pop();
   	}

	public int getArgsNumber() {
		return 1;
	}

    public String getStringValue(int level) {
   		return
   		    new StringBuffer()
   		        .append(CodeUtil.getIndent(level))
                .append("throw ")
                .append(CodeUtil.getSimpleValue(valueToThrow, level))
                .toString();
   	}

   	public List<Operation> getOperations() {
   		return Arrays.asList(valueToThrow);
   	}

   	@Override
   	public String toString() {
   		return "ThrowOperation("+valueToThrow+")";
   	}
}
