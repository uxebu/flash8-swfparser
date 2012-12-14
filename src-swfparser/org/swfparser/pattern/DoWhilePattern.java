/*
 *   DoWhilePattern.java
 * 	 @Author Oleg Gorobets
 *   Created: Jul 29, 2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.pattern;

import java.util.List;

import com.jswiff.swfrecords.actions.Action;

public class DoWhilePattern extends IfPattern {

	public DoWhilePattern(List<Action> actions) {
		super(actions);
		// TODO Auto-generated constructor stub
	}
	
//	@Override
//	public int size() {
//		return 0; // do while pattern set on last conditional jump
//	}

}
