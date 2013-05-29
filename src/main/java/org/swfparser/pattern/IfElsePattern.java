/*
 *   IfElsePattern.java
 * 	 @Author Oleg Gorobets
 *   Created: Jul 29, 2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.pattern;

import java.util.List;

import com.jswiff.swfrecords.actions.Action;

public class IfElsePattern extends IfPattern {

	private List<Action> elseActions;
	
	public IfElsePattern(List<Action> ifActions, List<Action> elseActions) {
		super(ifActions);
		this.elseActions = elseActions;
	}
	
	@Override
	public int size() {
		return actions.size() + elseActions.size() + 1;
	}

	public List<Action> getElseActions() {
		return elseActions;
	}
	
	public List<Action> getIfActions() {
		return actions;
	}
}
