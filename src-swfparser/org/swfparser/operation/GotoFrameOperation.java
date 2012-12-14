/*
 *   GotoFrameOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 26.07.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Collections;
import java.util.List;

import org.swfparser.ActionAware;
import org.swfparser.CodeUtil;
import org.swfparser.Operation;
import org.swfparser.Priority;
import com.jswiff.swfrecords.actions.GoToFrame;

public class GotoFrameOperation implements Operation,ActionAware {

	public static final String ACTION_PLAY = "gotoAndPlay";
	public static final String ACTION_STOP = "gotoAndStop";
	
	private GoToFrame gotoFrame;
	private String action = ACTION_STOP;
	
	public GotoFrameOperation(GoToFrame gotoFrame) {
		this.gotoFrame = gotoFrame;
	}
	
	public int getArgsNumber() {
		return 0;
	}

	public String getStringValue(int level) {
		return CodeUtil.getIndent(level)+action+"("+(gotoFrame.getFrame()+1)+")";
	}

	public int getPriority() {
		return Priority.LOWEST;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public List<Operation> getOperations() {
		return Collections.EMPTY_LIST;
	}

}
