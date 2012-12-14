/*
 *   NewMethodOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 04.09.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.apache.log4j.Logger;

import org.swfparser.CodeUtil;
import org.swfparser.ExecutionContext;
import org.swfparser.Operation;
import com.jswiff.swfrecords.actions.StackValue;

public class NewMethodOperation extends NewObjectOperation {

	private static Logger logger = Logger.getLogger(NewMethodOperation.class);
	
	private Operation methodName;
	private Operation object;
	private List<Operation> args = new ArrayList<Operation>();
	
	public NewMethodOperation(ExecutionContext context) {
		super(context);
	}
	
	@Override
	protected void readStack() {
		methodName = stack.pop();
		super.readStack();
	}

	
	@Override
	protected String getObjectMethod() {
		return CodeUtil.getSimpleValue(methodName, 0);
	}


	public List<Operation> getOperations() {
		List<Operation> operations = super.getOperations();
		operations.add(methodName);
		return operations;
	}

//	public String getStringValue(int level) {
//		// FIXME: string representation of NewMethod op
//		StringBuffer buf = new StringBuffer()
//			.append("new ")
//			.append(object.getStringValue(0))
//			.append(methodName.getStringValue(0));
//		
//		buf.append("(");
//		int idx=0;
//		for (Operation arg : args) {
//			if (idx++>0) {
//				buf.append(",");
//			}
//			String argValue = arg.getStringValue(0);
//			buf.append(argValue);
//		}
//		buf.append(")");
//		
//		return buf.toString();
//	}
	
	@Override
	public String toString() {
		return "NewMethodOperation("+object+","+methodName+"["+args.size()+"])";
	}

}
