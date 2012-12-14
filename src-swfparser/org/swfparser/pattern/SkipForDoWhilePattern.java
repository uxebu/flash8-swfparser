/*
 *   SkipForDoWhilePattern.java
 * 	 @Author Oleg Gorobets
 *   Created: 07.09.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.pattern;

import java.util.List;

import com.jswiff.swfrecords.actions.Action;

public class SkipForDoWhilePattern extends SkipPattern {

	public SkipForDoWhilePattern(List<Action> actions) {
		super(actions);
	}
	@Override
	public int size() {
		return 0;
	}


}
