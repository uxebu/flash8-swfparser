/*
 *   WaitForFrameOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 14.08.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.List;
import java.util.Stack;

import org.swfparser.CodeUtil;
import org.swfparser.ExecutionContext;
import org.swfparser.Operation;
import org.swfparser.exception.StatementBlockException;
import com.jswiff.swfrecords.actions.Action;
import com.jswiff.swfrecords.actions.StackValue;
import com.jswiff.swfrecords.actions.WaitForFrame;

public class WaitForFrameOperation extends AbstractCompoundOperation {

	private List<Operation> operations;
	private boolean isScene = false;
	private WaitForFrame waitForFrame;
	protected Operation frame;
	
	public WaitForFrameOperation(Stack<Operation> stack, ExecutionContext context, WaitForFrame waitForFrame, List<Action> actions) throws StatementBlockException {
		super(context);
		this.waitForFrame = waitForFrame;
		frame = getFrame();
		operations = executeWithCopiedStack(context, actions, this).getOperations();
		
	}
	
	protected Operation getFrame() {
		return new StackValue(waitForFrame.getFrame());
	}

	public String getStringValue(int level) {
		StringBuffer buf = new StringBuffer()
			.append(CodeUtil.getIndent(level))
			.append("ifFrameLoaded(");
		
		if (isScene) {
			
		}
		
		buf
			.append(frame.getStringValue(level))
			.append(") {\n");
		
		for (Operation operation : operations) {
			buf
			.append(CodeUtil.getIndent(level+1))
			.append(operation.getStringValue(level))
			.append(";\n");
		}
		
		buf.append("}");
		
		
		return buf.toString();
	}

}
