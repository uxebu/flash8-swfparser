/*
 *   SimpleInvertedOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 17.08.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Arrays;
import java.util.List;

import org.swfparser.BooleanOperation;
import org.swfparser.Operation;
import org.swfparser.Priority;

public class SimpleInvertedOperation implements BooleanOperation {

	protected Operation operation;
	
	public SimpleInvertedOperation(Operation operation) {
		super();
		this.operation = operation;
	}

	public Operation getInvertedOperation() {
		return operation;
	}

	public int getArgsNumber() {
		return 1;
	}

	public int getPriority() {
		return Priority.UNARY;
	}

	public String getStringValue(int level) {
		String operationString = operation.getStringValue(level);
		if (getPriority() < operation.getPriority()) {
			operationString = "("+operationString+")";
		}
		return "!"+operationString;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof NotOperation) {
			return obj.equals(this); // invert comparison
		} else {
			return super.equals(obj);
		}
	}

	public List<Operation> getOperations() {
		return Arrays.asList(operation);
	}

}
