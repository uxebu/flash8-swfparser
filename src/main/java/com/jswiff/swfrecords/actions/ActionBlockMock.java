/*
 *   ActionBlockMock.java
 * 	 @Author Oleg Gorobets
 *   Created: 13.09.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package com.jswiff.swfrecords.actions;

import java.io.IOException;
import java.util.List;

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

public class ActionBlockMock implements ActionBlockReader {

	public void addAction(Action action) {
		// TODO Auto-generated method stub

	}

	public List getActions() {
		// TODO Auto-generated method stub
		return null;
	}

	public byte[] getData() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void read(InputBitStream stream) throws IOException {
		// TODO Auto-generated method stub

	}

	public boolean removeAction(Action action) {
		// TODO Auto-generated method stub
		return false;
	}

	public Action removeAction(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setSkipGarbage(boolean skipGarbage) {
		// TODO Auto-generated method stub

	}

	public void write(OutputBitStream stream, boolean writeEndAction) throws IOException {
		// TODO Auto-generated method stub

	}

}
