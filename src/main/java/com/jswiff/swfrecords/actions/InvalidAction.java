/*
 *   InvalidAction.java
 * 	 @Author Oleg Gorobets
 *   Created: 11.09.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package com.jswiff.swfrecords.actions;

import java.io.IOException;

import com.jswiff.io.OutputBitStream;

public class InvalidAction extends Action {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7326362852921219087L;
	

	public InvalidAction() {
		code = -2;
	}
	
	@Override
	protected void writeData(OutputBitStream dataStream, OutputBitStream mainStream) throws IOException {
		// do nothing
	}
}
