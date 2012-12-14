/*
 *   InstanceOfOpertion.java
 * 	 @Author Oleg Gorobets
 *   Created: 15.08.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Stack;

import org.swfparser.BooleanOperation;
import org.swfparser.Operation;

public class InstanceOfOperation extends BinaryOperation implements BooleanOperation {
	
	public InstanceOfOperation(Stack<Operation> stack) {
		super(stack);
	}

	public Operation getInvertedOperation() {
		return new SimpleInvertedOperation(this);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof NotOperation) {
			return obj.equals(this); // invert comparison
		} else {
			return super.equals(obj);
		}
	}
	@Override
	public String getSign() {
		return "instanceof";
	}




}
