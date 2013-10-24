/*
 *   ExtendsOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 15.08.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import org.swfparser.CodeUtil;
import org.swfparser.Operation;

// TODO: check implementation
public class ExtendsOperation extends AbstractOperation {

	private Operation superClass;
	private Operation subClass;
	
	public ExtendsOperation(Stack<Operation> stack) {
		super(stack);
		superClass = stack.pop();
		subClass = stack.pop();
	}

	public int getArgsNumber() {
		return 0;
	}

	public String getStringValue(int level) {
        String className = subClass.getStringValue(level);
        String superClassName = superClass.getStringValue(level);
        StringBuffer buf = new StringBuffer();

        return CodeUtil.getIndent(level) + "Class({constructor: " + className + ", superClass: " + superClassName + "})";
	}

	public List<Operation> getOperations() {
		return Arrays.asList(superClass,subClass);
	}

}
