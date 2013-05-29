/*
 *   DoInitActionBinary.java
 * 	 @Author Oleg Gorobets
 *   Created: 13.09.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package com.jswiff.swfrecords.tags;

import java.io.IOException;

import com.jswiff.io.OutputBitStream;

public class DoInitActionBinary extends DoInitAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6450893006444354521L;
	
	private byte[] data;
	
	public DoInitActionBinary(int spriteId,byte[] data) {
		super(spriteId);
		this.data = data;
	}

	public DoInitActionBinary() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void writeData(OutputBitStream outStream) throws IOException {
		outStream.writeUI16(spriteId);
		outStream.writeBytes(data);
		
		// TODO: Should we write end action?
	}
	
	@Override
	void setData(byte[] data) throws IOException {
		this.data = data;
	}
	
	

}
