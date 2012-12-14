/*
 *   MbOrdOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 14.08.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Stack;

import org.swfparser.Operation;


/**
 * Deprecated since Flash Player 5. This function was deprecated in favor of String.charCodeAt().
 * Converts the specified character to a multibyte number.
 *
 */
public class MbOrdOperation extends ConvertOperation {

	public MbOrdOperation(Stack<Operation> stack) {
		super(stack);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getFunctionName() {
		return "mbord";
	}

}
