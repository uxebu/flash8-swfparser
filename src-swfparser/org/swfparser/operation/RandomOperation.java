/*
 *   RandomOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: Jul 28, 2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Stack;

import org.swfparser.Operation;

public class RandomOperation extends UnaryOperation {

	public RandomOperation(Stack<Operation> stack) {
		super(stack);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getStringValue(int level) {
		// TODO Auto-generated method stub
		return "random("+op.getStringValue(level)+")";
	}

}
