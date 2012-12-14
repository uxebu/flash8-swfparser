/*
 *   PatternContext.java
 * 	 @Author Oleg Gorobets
 *   Created: 06.09.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.swfparser.pattern.Pattern;
import com.jswiff.swfrecords.actions.Action;

public class PatternContext {
	private Stack<Pattern> stack = new Stack<Pattern>();
	private Map<String,Pattern> patterns = new HashMap<String, Pattern>();
	private Map<String,Action> labels = new HashMap<String, Action>();
	private Map<Action,Integer> actionPointerMap = new HashMap<Action, Integer>();

	public Stack<Pattern> getStack() {
		return stack;
	}

	public void setStack(Stack<Pattern> stack) {
		this.stack = stack;
	}

	public Map<String, Pattern> getPatterns() {
		return patterns;
	}

	public void setPatterns(Map<String, Pattern> patterns) {
		this.patterns = patterns;
	}

	public Map<String, Action> getLabels() {
		return labels;
	}

	public void setLabels(Map<String, Action> labels) {
		this.labels = labels;
	}

	public Map<Action, Integer> getActionPointerMap() {
		return actionPointerMap;
	}

	public void setActionPointerMap(Map<Action, Integer> actionPointerMap) {
		this.actionPointerMap = actionPointerMap;
	}
	
	
	
	
}
