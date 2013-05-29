/*
 *   ButtonCondActionBinary.java
 * 	 @Author Oleg Gorobets
 *   Created: 13.09.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package com.jswiff.swfrecords;

import java.io.IOException;

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;
import com.jswiff.swfrecords.actions.ActionBlockMock;

public class ButtonCondActionBinary extends ButtonCondAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1242686239553986616L;
	
	private byte[] data;
	
	public ButtonCondActionBinary(byte[] data) {
		// TODO Auto-generated constructor stub
		super();
		actions = new ActionBlockMock();
		this.data = data;
	}

	public ButtonCondActionBinary(InputBitStream stream, byte[] data) throws IOException {
		super(stream);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void write(OutputBitStream stream) throws IOException {
		super.write(stream);
		stream.writeBytes(data);
		
	}

}
