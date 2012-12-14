/*
 *   BinaryActionOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 24.07.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Stack;

import org.swfparser.CodeUtil;
import org.swfparser.DualUse;
import org.swfparser.Operation;
import com.jswiff.swfrecords.actions.Action;
import com.jswiff.swfrecords.actions.ActionSign;

public abstract class BinaryActionOperation extends BinaryOperation implements DualUse {

	protected Action action;
	protected boolean isStatement = false;
	
	public BinaryActionOperation(Stack<Operation> stack, Action action) {
		super(stack);
		this.action = action;
	}

	@Override
	public String getSign() {
		return ((ActionSign)action).getSign();
	}
	
	public void markAsStatement() {
		isStatement = true;
	}
	
	@Override
	public String getStringValue(int level) {
		return (isStatement ? CodeUtil.getIndent(level) : "") + super.getStringValue(level);
	}
	
	

}
