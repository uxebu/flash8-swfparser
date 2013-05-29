/*
 *   ByCodeSWFReader.java
 * 	 @Author Oleg Gorobets
 *   Created: 12.09.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package com.jswiff;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import com.jswiff.swfrecords.tags.TagHeader;

public class ByCodeSWFReader extends FilteredSWFReader {

	private Set<Short> tagNumbers = new HashSet<Short>();
	String tagString;
	
	public ByCodeSWFReader(InputStream stream, String tagNumbers) {
		super(stream);
		Set<Integer> integerSet = convertStringToSet(tagNumbers);
		for (int i : integerSet) {
			this.tagNumbers.add((short)i);
		}
	}

	@Override
	protected int getTagAction(TagHeader tagHeader) {
		int result = tagNumbers.contains(tagHeader.getCode()) ? DO_PARSE : DO_WRITE_AS_IS;
		return result;
	}

}
