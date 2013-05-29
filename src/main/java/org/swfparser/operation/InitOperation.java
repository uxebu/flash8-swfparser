/*
 *   InitOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: Jul 30, 2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.apache.log4j.Logger;

import org.swfparser.Operation;
import com.jswiff.swfrecords.actions.StackValue;

public abstract class InitOperation extends AbstractOperation {

	private static Logger logger = Logger.getLogger(InitOperation.class);
	protected int numberOfArgs = 0;
	protected List<Operation> underOperations = new ArrayList<Operation>();
	
	public InitOperation(Stack<Operation> stack) {
		super(stack);
		Operation numOfValues = stack.pop();
		underOperations.add(numOfValues);
		logger.debug("Number of arguments = "+numOfValues);
		StackValue stackValue = (StackValue) numOfValues;
		numberOfArgs = stackValue.getIntValue();
		
		readArguments();
		
	}
	
	protected abstract void readArguments();

	public int getArgsNumber() {
		return 0;
	}

	public abstract String getStringValue(int level);
	

}
