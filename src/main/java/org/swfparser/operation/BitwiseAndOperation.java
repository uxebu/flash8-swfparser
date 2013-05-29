/*
 *   BitwiseAndOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 15.08.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Stack;

import org.swfparser.Operation;

public class BitwiseAndOperation extends BinaryBitwiseOperation {

	public BitwiseAndOperation(Stack<Operation> stack) {
		super(stack);
	}

	@Override
	public String getSign() {
		return "&";
	}

}
