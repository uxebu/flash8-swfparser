/*
 *   DoActionBinary.java
 * 	 @Author Oleg Gorobets
 *   Created: 13.09.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package com.jswiff.swfrecords.tags;

import java.io.IOException;

import com.jswiff.io.OutputBitStream;

public class DoActionBinary extends DoAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3636623215678789859L;
	
	private byte[] data;
	
	public DoActionBinary(byte[] data) {
		super();
		this.data = data;
	}
	
	@Override
	protected void writeData(OutputBitStream outStream) throws IOException {
		outStream.writeBytes(data);
		
		// TODO: Should we write end action?
	}
	
	@Override
	void setData(byte[] data) throws IOException {
		this.data = data;
	}

}
