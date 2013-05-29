/*
 *   DefineButtonBinary.java
 * 	 @Author Oleg Gorobets
 *   Created: 13.09.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package com.jswiff.swfrecords.tags;

import java.io.IOException;

import com.jswiff.io.OutputBitStream;
import com.jswiff.swfrecords.ButtonRecord;

public class DefineButtonBinary extends DefineButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3815809919162669640L;
	
	private byte[] data;
	
	public DefineButtonBinary(int characterId, ButtonRecord[] characters, byte[] data) {
		super(characterId, characters);
		this.data = data;
	}
	
	@Override
	protected void writeData(OutputBitStream outStream) throws IOException {
		outStream.writeUI16(characterId);
		for (int i = 0; i < characters.length; i++) {
			characters[i].write(outStream, false);
		}
		outStream.writeUI8((short) 0); // CharacterEndFlag;
		outStream.writeBytes(data);
	}

}
