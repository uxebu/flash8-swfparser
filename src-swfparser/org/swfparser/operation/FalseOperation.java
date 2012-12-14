/*
 *   ZeroOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 14.08.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

/**
 * 
 */
package org.swfparser.operation;

import java.util.Collections;
import java.util.List;

import org.swfparser.BooleanOperation;
import org.swfparser.Operation;

public class FalseOperation implements BooleanOperation {
	
	public int getArgsNumber() {
		return 0;
	}
	
	public int getPriority() {
		return 0;
	}
	
	public String getStringValue(int level) {
		return "false";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof NotOperation) {
			return obj.equals(this); // invert comparison
		} else {
			return obj instanceof FalseOperation;
		}
	}

	
	@Override
	public String toString() {
		return "FalseOperation";
	}

	public Operation getInvertedOperation() {
		return new TrueOperation();
	}
	
	public List<Operation> getOperations() {
		return Collections.EMPTY_LIST;
	}
	
}