/*
 *   NewObjectOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 26.07.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import org.swfparser.ExecutionContext;
import org.swfparser.Operation;
import com.jswiff.swfrecords.actions.StackValue;

public class NewObjectOperation extends AbstractOperation {

	protected Operation objectName;
	protected List<Operation> args = new ArrayList<Operation>();
	
	public NewObjectOperation(ExecutionContext context) {
		super(context.getExecStack());
		readStack();
	}
	
	/**
	 * 
	 */
	protected void readStack() {
		objectName = stack.pop();
		int numArgs = ((StackValue)stack.pop()).getIntValue();
		for (int j=0; j<numArgs; j++) {
			args.add( stack.pop() );
		}
	}
	
	/**
	 * 
	 */
	protected String getObjectMethod() {
		return "";
	}

	public int getArgsNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getStringValue(int level) {
		StringBuffer buf = new StringBuffer();
		buf.append("new ");
		
		String newObjectName  = (objectName instanceof StackValue && StackValue.TYPE_STRING==((StackValue)objectName).getType()) 
		? ((StackValue)objectName).getString() 
				: objectName.getStringValue(level);
		
		buf.append(newObjectName);
		
		if (StringUtils.hasText(getObjectMethod())) {
			buf.append(".").append(getObjectMethod());
		}
		
		buf.append("(");
		int idx=0;
		for (Operation arg : args) {
			if (idx++>0) {
				buf.append(",");
			}
			String argValue = arg.getStringValue(level);
			buf.append(argValue);
		}
		buf.append(")");
		return buf.toString();
	}

	public List<Operation> getOperations() {
		List<Operation> operations = new ArrayList<Operation>();
		operations.add(objectName);
		operations.addAll(args);
		return operations;
	}

}
