/*
 *   IfHandle.java
 * 	 @Author Oleg Gorobets
 *   Created: 06.09.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser;

import com.jswiff.swfrecords.actions.If;

public class IfHandle extends BranchHandle {

	public IfHandle(If ifAction, int pointer, int jumpPointer, int offset, int jumpOffset) {
		super(ifAction, pointer, jumpPointer, offset, jumpOffset);
	}
	
	public If getIf() {
		return (If) getBranch();
	}

}
