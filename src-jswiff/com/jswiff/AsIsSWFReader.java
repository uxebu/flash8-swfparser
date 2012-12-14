/*
 *   AsIsSWFReader.java
 * 	 @Author Oleg Gorobets
 *   Created: 03.09.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package com.jswiff;

import java.io.InputStream;
import java.util.Set;

import com.jswiff.swfrecords.tags.TagHeader;

public class AsIsSWFReader extends FilteredSWFReader {

	private Set<Integer> tagNumbers;
	private int tagNumber = 0;
	
	public AsIsSWFReader(InputStream stream, String tagString) {
		super(stream);
		tagNumbers = convertStringToSet(tagString);
	}

	@Override
	protected int getTagAction(TagHeader tagHeader) {
		int result = tagNumbers.contains(tagNumber) ? DO_WRITE_AS_IS : DO_SKIP;
		tagNumber++;
		return result;
	}

}
