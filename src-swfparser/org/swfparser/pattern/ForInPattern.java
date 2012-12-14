/*
 *   ForInPattern.java
 * 	 @Author Oleg Gorobets
 *   Created: 03.09.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.pattern;

import java.util.List;

import com.jswiff.swfrecords.actions.Action;

public class ForInPattern extends IfPattern {

	private int addedSize = 0;
	private List<Action> varActions;
	
	public ForInPattern(List<Action> actions, List<Action> varActions, int addedSize) {
		super(actions);
		this.addedSize = addedSize;
		this.varActions = varActions;
	}
	
	@Override
	public int size() {
		return super.size() + addedSize;
	}
	
	public List<Action> getVarActions() {
		return varActions;
	}
	
}
