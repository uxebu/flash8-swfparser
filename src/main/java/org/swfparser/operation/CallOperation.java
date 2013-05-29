/*
 *   CallOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 15.08.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Stack;

import org.swfparser.CodeUtil;
import org.swfparser.Operation;

/**
 * call(frame:Object)
 * 
 * Deprecated since Flash Player 5. This action was deprecated in favor of the function statement.
 *
 */
public class CallOperation extends UnaryOperation {

	
	public CallOperation(Stack<Operation> stack) {
		super(stack);
	}

	@Override
	public String getStringValue(int level) {
		return CodeUtil.getIndent(level)+"call("+op.getStringValue(level)+")";
	}

}
