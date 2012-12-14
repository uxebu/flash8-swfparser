/*
 *   IfPattern.java
 * 	 @Author Oleg Gorobets
 *   Created: Jul 29, 2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.pattern;

import java.util.List;

import com.jswiff.swfrecords.actions.Action;

public class IfPattern extends BranchPattern {
	
	protected List<Action> actions;
	
	public IfPattern(List<Action> actions) {
		super();
		this.actions = actions;
	}

	public List<Action> getActions() {
		return actions;
	}

	@Override
	public int size() {
		return actions.size();
	}
	
	
}
