/*
 *   DivideOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 24.07.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Stack;

import org.swfparser.Operation;
import org.swfparser.Priority;
import com.jswiff.swfrecords.actions.Action;

public class DivideOperation extends BinaryActionOperation {

	public DivideOperation(Stack<Operation> stack, Action action) {
		super(stack, action);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int getPriority() {
		return Priority.ARITHMETIC;
	}


}
