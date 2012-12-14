/*
 *   ProgramLineImpl.java
 * 	 @Author Oleg Gorobets
 *   Created: 26.07.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser;

public class ProgramLineImpl implements ProgramLine {
	
	private String str;
	private int level;
	
	public ProgramLineImpl(String str, int level) {
		super();
		this.str = str;
		this.level = level;
	}
	
	public ProgramLineImpl(String str) {
		super();
		this.str = str;
		this.level = 0;
	}

	@Override
	public String toString() {
		return str;
	}

	public int getLevel() {
		return level;
	}

}
