/*
 *   CastOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 15.08.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import org.swfparser.Operation;

public class CastOperation extends AbstractOperation {
	
	private Operation scriptObject;
	private Operation constructorFunction;

	public CastOperation(Stack<Operation> stack) {
		super(stack);
		scriptObject = stack.pop();
		constructorFunction = stack.pop();
	}

	public int getArgsNumber() {
		return 2;
	}

	public String getStringValue(int level) {
		return new StringBuffer()
		.append(constructorFunction.getStringValue(level))
		.append("(")
		.append(scriptObject.getStringValue(level))
		.append(")")
				.toString();
	}

	public List<Operation> getOperations() {
		return Arrays.asList(scriptObject,constructorFunction);
	}

}
