/*
 *   MbChrOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 14.08.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Stack;

import org.swfparser.Operation;

/**
 * Deprecated since Flash Player 5. This function was deprecated in favor of the String.fromCharCode() method.
 *
 *	Converts an ASCII code number to a multibyte character.
 * 
 * @author stealth
 *
 */
public class MbChrOperation extends ConvertOperation {

	public MbChrOperation(Stack<Operation> stack) {
		super(stack);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getFunctionName() {
		return "mbchr";
	}

}
