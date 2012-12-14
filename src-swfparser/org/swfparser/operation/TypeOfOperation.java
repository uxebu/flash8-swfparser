/*
 *   TypeOfOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 14.08.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Stack;

import org.swfparser.Operation;

/**
 	typeof operator

	typeof(expression)
	
	Evaluates the expression and returns a string specifying whether the expression is a String, MovieClip, Object, Function, Number, or Boolean value.
	
	Availability: ActionScript 1.0; Flash Player 5
	Operands
	
	expression : Object - A string, movie clip, button, object, or function.
	Returns
	
	String - A String representation of the type of expression. The following table shows the results of the typeof operator on each type of expression.
 
 */
public class TypeOfOperation extends ConvertOperation {

	public TypeOfOperation(Stack<Operation> stack) {
		super(stack);
	}

	@Override
	protected String getFunctionName() {
		return "typeof";
	}

}
