/*
 *   TellTargetPattern.java
 * 	 @Author Oleg Gorobets
 *   Created: 19.08.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.pattern;

import java.util.List;

import com.jswiff.swfrecords.actions.Action;

public class TellTargetPattern implements Pattern {
	
	private List<Action> actions;
	private boolean skipOneMoreTellTargetAction = false;
	
//	public TellTargetPattern(List<Action> actions) {
//		super();
//		this.actions = actions;
//	}
	public TellTargetPattern(List<Action> actions, boolean skipOneMoreTellTargetAction) {
		super();
		this.actions = actions;
		this.skipOneMoreTellTargetAction = skipOneMoreTellTargetAction;
	}

	public int size() {
		return actions.size() + 1 // 1 - skip last SetTarget('')
		+ (skipOneMoreTellTargetAction ? 1 : 0)
		; 
	}
	
	public List<Action> getActions() {
		return actions;
	}

}
