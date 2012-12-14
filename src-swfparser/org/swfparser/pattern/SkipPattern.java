/*
 *   SkipPattern.java
 * 	 @Author Oleg Gorobets
 *   Created: Jul 29, 2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.pattern;

import java.util.List;

import com.jswiff.swfrecords.actions.Action;

public class SkipPattern extends IfPattern {

	public SkipPattern(List<Action> actions) {
		super(actions);
	}

}
