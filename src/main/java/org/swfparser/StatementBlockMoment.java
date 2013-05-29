/*
 *   StetementBlockMoment.java
 * 	 @Author Oleg Gorobets
 *   Created: Jul 28, 2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser;

import java.util.List;

import com.jswiff.swfrecords.actions.Action;

public class StatementBlockMoment {
	
	private List<Action> actions;
	private int actionIndex;
	private List<Operation> statements;
	
	public List<Action> getActions() {
		return actions;
	}
	public void setActions(List<Action> actions) {
		this.actions = actions;
	}
	public int getActionIndex() {
		return actionIndex;
	}
	public void setActionIndex(int actionIndex) {
		this.actionIndex = actionIndex;
	}
	public List<Operation> getStatements() {
		return statements;
	}
	public void setStatements(List<Operation> statements) {
		this.statements = statements;
	}
	
	
}
