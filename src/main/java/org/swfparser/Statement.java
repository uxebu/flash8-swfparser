/*
 *   Statement.java
 * 	 @Author Oleg Gorobets
 *   Created: 24.07.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser;

import java.io.OutputStream;
import java.util.List;

import com.jswiff.swfrecords.actions.Action;

public interface Statement {
	
	/**
	 * Writes statement to output
	 * @param outStream
	 */
	public void write(OutputStream outStream);
	
	/**
	 * Actions which comprise this statement
	 * @return
	 */
	public List<Action> getActions();
}
