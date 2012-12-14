/*
 *   RemoveMovieClipOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 14.08.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Stack;

import org.swfparser.CodeUtil;
import org.swfparser.Operation;

public class RemoveMovieClipOperation extends UnaryOperation {

	public RemoveMovieClipOperation(Stack<Operation> stack) {
		super(stack);
	}

	@Override
	public String getStringValue(int level) {
		return CodeUtil.getIndent(level)+"removeMovieClip("+op.getStringValue(level)+")";
	}
	
}
