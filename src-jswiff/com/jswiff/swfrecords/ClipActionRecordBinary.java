/*
 *   ClipActionRecordBinary.java
 * 	 @Author Oleg Gorobets
 *   Created: 14.09.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package com.jswiff.swfrecords;

import java.io.IOException;

import com.jswiff.io.OutputBitStream;


public class ClipActionRecordBinary extends ClipActionRecord {
	
	private byte[] actionBuffer;

	public ClipActionRecordBinary(ClipEventFlags eventFlags, byte[] data) {
		super(eventFlags);
		this.actionBuffer = data;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void write(OutputBitStream stream, short swfVersion) throws IOException {
		
//		super.write(stream, swfVersion);
		
		eventFlags.write(stream, swfVersion);
//	    OutputBitStream actionStream = new OutputBitStream();
//	    actionStream.setANSI(stream.isANSI());
//	    actionStream.setShiftJIS(stream.isShiftJIS());
//	    actions.write(actionStream, true);
//	    byte[] actionBuffer  = actionStream.getData();
//	    int actionRecordSize = actionBuffer.length;
		
		int actionRecordSize = actionBuffer.length;
		
	    if (eventFlags.isKeyPress()) {
	      actionRecordSize++; // because of keyCode
	    }
	    stream.writeUI32(actionRecordSize);
	    if (eventFlags.isKeyPress()) {
	      stream.writeUI8(keyCode);
	    }
	    stream.writeBytes(actionBuffer);
		
	}

//	public ClipActionRecordBinary(InputBitStream stream, short swfVersion) throws IOException {
//		super(stream, swfVersion);
//		// TODO Auto-generated constructor stub
//	}

}
