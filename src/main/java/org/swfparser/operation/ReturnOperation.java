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
        StringBuilder buf = new StringBuilder();
        buf.append(CodeUtil.getIndent(level));
        buf.append("return");

        String stringValue = op.getStringValue(level);
        if (!stringValue.equals("undefined")) {
            buf.append(" ");
            buf.append(stringValue);
        }
        return buf.toString();
	}

}
