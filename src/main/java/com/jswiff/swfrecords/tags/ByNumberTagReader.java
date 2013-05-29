/*
 *   ByNumberTagReader.java
 * 	 @Author Oleg Gorobets
 *   Created: 02.09.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package com.jswiff.swfrecords.tags;

import java.util.HashSet;
import java.util.Set;

public class ByNumberTagReader extends FilteredTagReader {

	private Set<Integer> tags = new HashSet<Integer>();
	private int tagNumber;
	
	@Override
	protected int getTagAction(TagHeader header) {
		int result = this.tags.contains(tagNumber) ? DO_WRITE_AS_IS : DO_PARSE;
		
		tagNumber++;
		
		return result;
	}
	
	@Override
	public void setTags(String tags) {
		super.setTags(tags);
		for (short number : super.tags) {
			this.tags.add(new Integer(number));
		}
	}

}
