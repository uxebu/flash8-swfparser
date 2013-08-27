/*
 *   StartDragOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 14.08.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.swfparser.CodeUtil;
import org.swfparser.Operation;
import com.jswiff.swfrecords.actions.StackValue;

public class StartDragOperation extends AbstractOperation {

	/**
	 * Object - The target path of the movie clip to drag.
	 */
	protected Operation target;
	
	/**
	 * Boolean [optional] - A Boolean value specifying whether the draggable movie clip is locked to the center of the mouse position (true ) or locked to the point where the user first clicked the movie clip (false ).
	 */
	protected Operation lock;
	
	
	protected Operation constrain;
	protected Operation x1;
	protected Operation x2;
	protected Operation y1;
	protected Operation y2;
	
	
	public StartDragOperation(Stack<Operation> stack) {
		super(stack);
		target = stack.pop();
		lock = stack.pop();
		constrain = stack.pop();
		
		if (constrain instanceof StackValue && ((StackValue)constrain).getIntValue() != 0) {
			y2 = stack.pop();
			x2 = stack.pop();
			y1 = stack.pop();
			x1 = stack.pop();
		}
	}

	public int getArgsNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getStringValue(int level) {
		
		StringBuffer buf = new StringBuffer()
			.append(CodeUtil.getIndent(level))
			.append("startDrag(")
			.append(target.getStringValue(level))
			.append(",")
			.append(lock.getStringValue(level))
			;
		
		if (x1!=null && x2!=null && y1!=null && y2!=null) {
			buf
			.append(",")
			.append(x1.getStringValue(level))
			.append(",")
			.append(y1.getStringValue(level))
			.append(",")
			.append(x2.getStringValue(level))
			.append(",")
			.append(y2.getStringValue(level))
				;
		}
		
		buf.append(")");
		
		return buf.toString();
	}

	public List<Operation> getOperations() {
		List<Operation> operations = new ArrayList<Operation>();
		operations.add(target);
		operations.add(lock);
		operations.add(constrain);
		if (x1!=null && x2!=null && y1!=null && y2!=null) {
			operations.add(y2);
			operations.add(x2);
			operations.add(y1);
			operations.add(x1);
		}
		return operations;
	}

}
