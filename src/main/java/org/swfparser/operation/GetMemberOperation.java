/*
 *   GetMemberOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 24.07.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import org.apache.log4j.Logger;
import org.swfparser.BooleanOperation;
import org.swfparser.CodeUtil;
import org.swfparser.Operation;
import org.swfparser.Priority;
import com.jswiff.swfrecords.actions.StackValue;

public class GetMemberOperation extends AbstractOperation implements BooleanOperation {

	private Operation objectName;
	private Operation memberName;
    private static Logger logger = Logger.getLogger(SetVariableOperation.class);

	public GetMemberOperation(Stack<Operation> stack) {
		super(stack);
		memberName = stack.pop();
		objectName = stack.pop();
        logger.debug("#GetMemberOperation() " + memberName + " . " + objectName);
	}

	public int getArgsNumber() {
		return 2;
	}

	public String getStringValue(int level) {
        StringBuilder buf = new StringBuilder();
        buf.append(objectName.getStringValue(level));
        buf.append(CodeUtil.getMemberGetExpression(memberName, level));
		return buf.toString();
	}
	
	@Override
	public int getPriority() {
		return Priority.HIGHEST;
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

	public List<Operation> getOperations() {
		return Arrays.asList(memberName,objectName);
	}
	
	@Override
	public String toString() {
		return "GetMemberOperation("+objectName+","+memberName+")";
	}

}
