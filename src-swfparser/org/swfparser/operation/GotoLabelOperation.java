/*
 *   GotoLabelOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: Jul 30, 2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

import org.swfparser.ActionAware;
import org.swfparser.CodeUtil;
import org.swfparser.Operation;
import com.jswiff.swfrecords.actions.GoToLabel;

public class GotoLabelOperation extends AbstractOperation implements ActionAware {

	private GoToLabel gotoLabel;
	private String action = GotoFrameOperation.ACTION_STOP;
	
	public GotoLabelOperation(Stack<Operation> stack, GoToLabel action) {
		super(stack);
		this.gotoLabel = action;
	}

	public int getArgsNumber() {
		return 0;
	}

	public String getStringValue(int level) {
		return CodeUtil.getIndent(level)+action+"(\""+(gotoLabel.getFrameLabel())+"\")";
	}

	public void setAction(String action) {
		this.action = action;
		
	}
	
	public List<Operation> getOperations() {
		return Collections.EMPTY_LIST;
	}

}
