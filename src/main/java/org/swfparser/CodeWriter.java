/*
 *   CodeWriter.java
 * 	 @Author Oleg Gorobets
 *   Created: 24.07.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser;

import java.io.OutputStream;
import java.util.List;

import com.jswiff.swfrecords.actions.Action;

public interface CodeWriter {
	
	/**
	 * @param actions
	 * @param outputStream
	 */
	public void write(List<Action> actions, OutputStream outputStream);
	
	/**
	 * @param bytes
	 * @param outputStream
	 */
	public void write(byte[] bytes, OutputStream outputStream);
	
}
