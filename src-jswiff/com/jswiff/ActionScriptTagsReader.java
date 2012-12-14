/*
 *   ActionScriptTagsReader.java
 * 	 @Author Oleg Gorobets
 *   Created: 12.09.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package com.jswiff;

import java.io.InputStream;

public class ActionScriptTagsReader extends ByCodeSWFReader {

	/**
	 * 
	 * Parse all actions necessary to dump action script.
	 * 
	 * @param stream
	 */
	public ActionScriptTagsReader(InputStream stream) {
		super(stream, "1,7,12,26,34,39,59,70");
	}

}
