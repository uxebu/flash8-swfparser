/*
 *   ImplementsOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 15.08.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.apache.log4j.Logger;

import org.swfparser.Operation;
import com.jswiff.swfrecords.actions.StackValue;

/*
 * ActionImplementsOp does the following:
	1. Pops the constructor function off the stack.
	The constructor function represents the class that will implement the interfaces. The
	constructor function must have a prototype property.
	2. Pops the count of implemented interfaces off the stack.
	3. For each interface count, pops a constructor function off of the stack.
	The constructor function represents an interface.
	4. Sets the constructor function’s list of interfaces to the array collected in the previous step,
	and sets the count of interfaces to the count popped in step 2.
 * 
 * 
 */

// TODO: check implementation
public class ImplementsOperation extends AbstractOperation {

	private static Logger logger = Logger.getLogger(ImplementsOperation.class);
	
	private Operation constructor;
	private List<Operation> interfaces;
	
	public ImplementsOperation(Stack<Operation> stack) {
		super(stack);
		constructor = stack.pop();
		Operation interfaceCount = stack.pop();
		logger.debug("Implements "+ interfaceCount+" interfaces");
		int interfacesCount = ((StackValue)interfaceCount).getIntValue();
		interfaces = new ArrayList<Operation>(interfacesCount);
		for (int j=0;j<interfacesCount;j++) {
			interfaces.add(stack.pop());
		}
	}

	public int getArgsNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getStringValue(int level) {
		StringBuffer buf = new StringBuffer()
		.append(constructor.getStringValue(level))
		.append(" implements ");
		int idx=0;
		for (Operation interFace : interfaces) {
			if (idx++>0) {
				buf.append(",");
			}
			buf.append(interFace.getStringValue(level));
		}
		return buf.toString();
	}

	/* (non-Javadoc)
	 * @see org.swfparser.Operation#getOperations()
	 * 
	 * TODO: Check underlying operations
	 */
	public List<Operation> getOperations() {
		List<Operation> operations = new ArrayList<Operation>();
		operations.add(constructor);
		operations.addAll(interfaces);
		return operations ;
	}

}
