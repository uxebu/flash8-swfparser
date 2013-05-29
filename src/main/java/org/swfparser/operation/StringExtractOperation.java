/*
 *   StringExtractOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 14.08.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import org.swfparser.Operation;

public class StringExtractOperation extends AbstractOperation {

	private Operation string;
	private Operation index;
	private Operation count;
	
	public StringExtractOperation(Stack<Operation> stack) {
		super(stack);
		count = stack.pop();
		index = stack.pop();
		string = stack.pop();
	}

	public int getArgsNumber() {
		return 3;
	}

	public String getStringValue(int level) {
		return new StringBuffer()
		.append("substring(")
		.append(string.getStringValue(level))
		.append(",")
		.append(index.getStringValue(level))
		.append(",")
		.append(count.getStringValue(level))
		.append(")")
		.toString();
	}
	
	@Override
	public String toString() {
		return new StringBuffer()
		.append("StringExtractOperation(")
		.append(string)
		.append(",")
		.append(index)
		.append(",")
		.append(count)
		.append(")")
		.toString();
	}

	public List<Operation> getOperations() {
		return Arrays.asList(count,index,string);
	}

}
