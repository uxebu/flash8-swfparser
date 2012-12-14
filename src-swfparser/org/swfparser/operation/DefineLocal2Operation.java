/*
 *   DefineLocal2Operation.java
 * 	 @Author Oleg Gorobets
 *   Created: 26.07.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Stack;

import org.swfparser.CodeUtil;
import org.swfparser.Operation;
import com.jswiff.swfrecords.actions.StackValue;

public class DefineLocal2Operation extends UnaryOperation {

	public DefineLocal2Operation(Stack<Operation> stack) {
		super(stack);
	}
	
	public String getStringValue(int level) {
		String variableName = (op instanceof StackValue && StackValue.TYPE_STRING==((StackValue)op).getType()) 
		? ((StackValue)op).getString() 
				: op.getStringValue(level);
		return CodeUtil.getIndent(level)+"var "+variableName;
	}

}
