/*
 *   ReturnOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: Jul 28, 2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Stack;

import org.swfparser.CodeUtil;
import org.swfparser.Operation;

public class ReturnOperation extends UnaryOperation {

	public ReturnOperation(Stack<Operation> stack) {
		super(stack);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getStringValue(int level) {
		return CodeUtil.getIndent(level)+"return "+op.getStringValue(level);
	}

}
