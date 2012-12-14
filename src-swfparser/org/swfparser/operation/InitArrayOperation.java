/*
 *   InitArrayOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: Jul 30, 2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.text.StyledEditorKit.UnderlineAction;

import org.swfparser.Operation;

public class InitArrayOperation extends InitOperation {

	private List<Operation> arguments;
	
	public InitArrayOperation(Stack<Operation> stack) {
		super(stack);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void readArguments() {
		arguments = new ArrayList<Operation>(numberOfArgs);
		for (int j=0;j<numberOfArgs;j++) {
			arguments.add(stack.pop());
		}
	}

	@Override
	public String getStringValue(int level) {
		StringBuffer buf = new StringBuffer();
		buf.append("[");
		
		int idx=0;
		for (Operation arg : arguments) {
			if (idx++ > 0) {
				buf.append(",");
			}
			buf.append(arg.getStringValue(level));
		}
		
		buf.append("]");
		
		return buf.toString();
	}

	
	@Override
	public String toString() {
		return "InitArray";
	}

	public List<Operation> getOperations() {
		underOperations.addAll(arguments);
		return underOperations;
	}

}
