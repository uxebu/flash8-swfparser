/*
 *   WhilePattern.java
 * 	 @Author Oleg Gorobets
 *   Created: Jul 29, 2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.pattern;

import java.util.List;

import com.jswiff.swfrecords.actions.Action;

public class WhilePattern extends IfPattern {

	private int addedSize = 0;
	
	public WhilePattern(List<Action> actions) {
		super(actions);
	}
	
	public WhilePattern(List<Action> actions, int addedSize) {
		super(actions);
		this.addedSize = addedSize;
	}
	
	@Override
	public int size() {
		return super.size() + 1 + addedSize; // one for the last jump before the block
	}
}
