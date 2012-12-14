/*
 *   SwitchPattern.java
 * 	 @Author Oleg Gorobets
 *   Created: 05.09.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.pattern;

import java.util.List;

import com.jswiff.swfrecords.actions.Action;

public class SwitchPattern extends BranchPattern {

	private List<List<Action>> conditionBlocks;
	private List<List<Action>> switchBlocks;
	private List<Action> defaultActions;
	private int size = 0;
	
	
	
	public SwitchPattern(List<List<Action>> conditionBlocks, List<List<Action>> switchBlocks, List<Action> defaultActions, int size) {
		super();
		this.conditionBlocks = conditionBlocks;
		this.switchBlocks = switchBlocks;
		this.defaultActions = defaultActions;
		this.size = size;
	}

	@Override
	public int size() {
		return size;
	}

	public List<List<Action>> getConditionBlocks() {
		return conditionBlocks;
	}

	public List<List<Action>> getSwitchBlocks() {
		return switchBlocks;
	}
	
	public List<Action> getDefaultActions() {
		return defaultActions;
	}
	
	

}
