/*
 *   GarbageAction.java
 * 	 @Author Oleg Gorobets
 *   Created: 23.07.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package com.jswiff.swfrecords.actions;

import java.io.IOException;

import com.jswiff.io.InputBitStream;

public class GarbageAction extends UnknownAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7284558771770838494L;

	public GarbageAction(byte[] data) {
		super((short)-1, data);
		// TODO Auto-generated constructor stub
	}

	public GarbageAction(InputBitStream stream) throws IOException {
		super(stream, (short)-1);
		// TODO Auto-generated constructor stub
	}

}
