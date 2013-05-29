/*
 *   StatementBlockContext.java
 * 	 @Author Oleg Gorobets
 *   Created: 26.07.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser;

import com.jswiff.SWFDocument;
import com.jswiff.swfrecords.tags.Tag;

public interface StatementBlockContext {
	/**
	 * @return
	 */
	public SWFDocument getDocument();
	/**
	 * @return
	 */
	public Tag getTag();
}
