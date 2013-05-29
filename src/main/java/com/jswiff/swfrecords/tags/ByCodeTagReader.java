/*
 *   ByCodeTagReader.java
 * 	 @Author Oleg Gorobets
 *   Created: 02.09.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package com.jswiff.swfrecords.tags;

public class ByCodeTagReader extends FilteredTagReader {

	@Override
	protected int getTagAction(TagHeader header) {
		return tags.contains(header.getCode()) ? DO_WRITE_AS_IS : DO_PARSE;
	}

}
