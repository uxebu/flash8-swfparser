/*
 *   BinaryTag.java
 * 	 @Author Oleg Gorobets
 *   Created: 12.09.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package com.jswiff.swfrecords.tags;

import java.io.IOException;

import com.jswiff.io.OutputBitStream;

public class BinaryTag extends Tag {

	public BinaryTag(short code) {
		this.code = code; 
	}

	@Override
	protected void writeData(OutputBitStream outStream) throws IOException {
		// TODO Auto-generated method stub

	}

}
