/*
 *   DefineLocalOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 24.07.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Stack;

import org.swfparser.CodeUtil;
import org.swfparser.Operation;
import com.jswiff.swfrecords.actions.StackValue;

public class DefineLocalOperation extends BinaryOperation {

	public DefineLocalOperation(Operation leftOp, Operation rightOp) {
		super(leftOp,rightOp);
	}
	
	public DefineLocalOperation(Stack<Operation> stack) {
		super(stack);
	}

	@Override
	public String getStringValue(int level) {
		
		// TODO: check if left string == "undefined"
		String leftString = CodeUtil.getSimpleValue(leftOp, level);
		String rightString = rightOp.getStringValue(level);
		
		StringBuffer buf = new StringBuffer()
			.append(CodeUtil.getIndent(level))
			.append("var ")
			.append(leftString)
			.append(" ")
			.append(getSign())
			.append(" ")
			.append(rightString);
		
		return buf.toString();
	}
	
	@Override
	public String getSign() {
		return "=";
	}
	

}
