/*
 *   StatementBlock.java
 * 	 @Author Oleg Gorobets
 *   Created: 24.07.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser;

import java.util.List;

import org.swfparser.exception.StatementBlockException;
import com.jswiff.swfrecords.actions.Action;

public interface StatementBlock {
	
	/**
	 * @param actions
	 * @return
	 */
	public void read(List<Action> actions) throws StatementBlockException;
	
	/**
	 * @return list of statements
	 */
	public List<Operation> getOperations();
	
	/**
	 * @param context
	 */
	public void setExecutionContext(ExecutionContext context);
	
	
}
