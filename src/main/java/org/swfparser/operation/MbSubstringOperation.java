/*
 *   MbSubstringOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 14.08.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Stack;

import org.swfparser.Operation;

public class MbSubstringOperation extends ConvertOperation {

	public MbSubstringOperation(Stack<Operation> stack) {
		super(stack);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getFunctionName() {
		return "mbsubstring";
	}

}
