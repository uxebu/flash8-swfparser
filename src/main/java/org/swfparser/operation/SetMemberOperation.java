/*
 *   SetMemberOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 24.07.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import org.apache.log4j.Logger;

import org.swfparser.CodeUtil;
import org.swfparser.Operation;

public class SetMemberOperation extends AbstractOperation {

	private static Logger logger = Logger.getLogger(SetMemberOperation.class);
	
	private Operation object;
	private Operation member;
	private Operation value;
	
//	private boolean simpleMember;

	public SetMemberOperation(Stack<Operation> stack) {
		super(stack);
		this.value = stack.pop();
		this.member = stack.pop();
		this.object = stack.pop();
//		this.simpleMember = (member instanceof StackValue && StackValue.TYPE_STRING==((StackValue)member).getType());
		
//		logger.debug("value = "+value);

	}
	

	public int getArgsNumber() {
		return 3;
	}

	public String getStringValue(int level) {
        StringBuilder value = new StringBuilder()
            .append(CodeUtil.getIndent(level))
            .append(CodeUtil.getSimpleValue(object, level))
            .append(CodeUtil.getMemberGetExpression(member, level))
            .append(" = ")
            .append(this.value.getStringValue(level));
        return value.toString();
	}
	
	@Override
	public String toString() {
		return "SetMember("+object+","+member+")";
	}


	public List<Operation> getOperations() {
		return Arrays.asList(value,member,object);
	}

}
