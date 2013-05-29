/*
 *   SetTargetOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 15.08.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.List;
import java.util.Stack;

import org.apache.log4j.Logger;

import org.swfparser.CodeUtil;
import org.swfparser.ExecutionContext;
import org.swfparser.Operation;
import org.swfparser.exception.StatementBlockException;
import com.jswiff.swfrecords.actions.Action;
import com.jswiff.swfrecords.actions.SetTarget;
import com.jswiff.swfrecords.actions.StackValue;

public class SetTargetOperation extends AbstractCompoundOperation {

	private static Logger logger = Logger.getLogger(SetTargetOperation.class);
	
	private SetTarget target;
	private Operation targetName;
	protected List<Operation> operations;
	
	public SetTargetOperation(Stack<Operation> stack, ExecutionContext context, List<Action> actions, SetTarget target) throws StatementBlockException {
		super(context);
		this.target = target;
		this.targetName = getTarget();
		logger.debug("Setting target to "+targetName);
		
		operations = executeWithSameStack(context, actions, this);
	}

	protected Operation getTarget() {
		return new StackValue(target.getName());
	}
	
	public String getStringValue(int level) {
		StringBuffer buf = new StringBuffer()
		.append(CodeUtil.getIndent(level))
		.append("tellTarget(")
		.append(targetName.getStringValue(level))
		.append(") {\n")
		;
		
		for (Operation op : operations) {
			buf
				.append(op.getStringValue(level+1))
				.append(CodeUtil.endOfStatement(op))
				.append("\n");
		}
		buf
		.append(CodeUtil.getIndent(level))	
		.append("}");
		
		return buf.toString();
	}

}
