/*
 *   SimpleFunctionOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: Jul 28, 2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import org.swfparser.Priority;

public class SimpleFunctionOperation extends SimpleOperation {

	public SimpleFunctionOperation(String opName) {
		super(opName);
	}

	@Override
	public String getStringValue(int level) {
		return opName;
	}
	
	@Override
	public int getPriority() {
		return Priority.CALL_FUNCTION;
	}
	
}
