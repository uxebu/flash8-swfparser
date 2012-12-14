/*
 *   OrdOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 15.08.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Stack;

import org.swfparser.Operation;

public class OrdOperation extends ConvertOperation {

	public OrdOperation(Stack<Operation> stack) {
		super(stack);
	}

	@Override
	protected String getFunctionName() {
		return "ord";
	}

}
