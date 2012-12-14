/*
 *   SWFWriterWithoutHeaderTags.java
 * 	 @Author Oleg Gorobets
 *   Created: 13.09.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package com.jswiff;

import java.io.IOException;
import java.io.OutputStream;

public class SWFWriterWithoutHeaderTags extends SWFWriter {

	public SWFWriterWithoutHeaderTags(SWFDocument document, OutputStream stream) {
		super(document, stream);
	}
	
	@Override
	protected byte[] getDocPropertiesTagsBuffer() throws IOException {
		return new byte[0];
	}

}
