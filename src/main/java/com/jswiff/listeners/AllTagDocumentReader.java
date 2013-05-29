/*
 *   AllTagDocumentReader.java
 * 	 @Author Oleg Gorobets
 *   Created: 03.09.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package com.jswiff.listeners;

import com.jswiff.swfrecords.tags.FileAttributes;
import com.jswiff.swfrecords.tags.Metadata;
import com.jswiff.swfrecords.tags.SetBackgroundColor;
import com.jswiff.swfrecords.tags.Tag;
import com.jswiff.swfrecords.tags.TagConstants;

public class AllTagDocumentReader extends SWFDocumentReader {

	public AllTagDocumentReader() {
		super();
	}
	
	@Override
	public void processTag(Tag tag, long streamOffset) {
//		super.processTag(tag, streamOffset);
		
		if (tag instanceof SetBackgroundColor) {
	        document.setBackgroundColor(((SetBackgroundColor) tag).getColor());
		} else if (tag instanceof FileAttributes) {
	        setFileAttributes((FileAttributes) tag);
		} else if(tag instanceof Metadata) {
	        setMetadata((Metadata) tag);
	    }
		
	    document.addTag(tag);
	    
	}

}
