/*
 * JSwiff is an open source Java API for Macromedia Flash file generation
 * and manipulation
 *
 * Copyright (C) 2004-2005 Ralf Terdic (contact@jswiff.com)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.jswiff.swfrecords.actions;

import java.util.List;

/**
 * Base class for branch actions (<code>Jump</code>, <code>If</code>).
 */
public abstract class Branch extends Action {
	
//	public static final int TYPE_INVALID	= -1;
//	public static final int TYPE_UNKNOWN	= 0;
//	public static final int TYPE_IF 		= 1;
//	public static final int TYPE_FOR 		= 2;
//	public static final int TYPE_WHILE 		= 3;
//	public static final int TYPE_BREAK 		= 4;
//	public static final int TYPE_CONTINUE 	= 5;
//	public static final int TYPE_IF_BREAK	= 6;
//	public static final int TYPE_IF_CONTINUE= 7;
//	public static final int TYPE_SKIP 		= 8;
//	public static final int TYPE_IF_ELSE 	= 9;
//	public static final int TYPE_DO_WHILE 	= 10;

	
//	protected List<Action> continueActions;
//	protected List<Action> breakSkipActions;
//	protected List<Action> blockActions;
	
	/**
	 * Returns the label of the action the execution is supposed to continue at,
	 * if the condition is fulfilled.
	 * 
	 * @return branch label
	 */
	public abstract String getBranchLabel();

	public abstract void setBranchLabel(String branchLabel);

	abstract void setBranchOffset(short branchOffset);

	public abstract short getBranchOffset();

//	public abstract int getBranchType();
//	
//	public abstract void setBranchType(int type);

//	public List<Action> getContinueActions() {
//		return continueActions;
//	}
//
//	public void setContinueActions(List<Action> continueActions) {
//		this.continueActions = continueActions;
//	}
//
//	public List<Action> getBreakSkipActions() {
//		return breakSkipActions;
//	}
//
//	public void setBreakSkipActions(List<Action> breakSkipActions) {
//		this.breakSkipActions = breakSkipActions;
//	}

//	public List<Action> getBlockActions() {
//		return blockActions;
//	}
//
//	public void setBlockActions(List<Action> blockActions) {
//		this.blockActions = blockActions;
//	}
	
//	public void setContinueActions(List<Action> actions) {
//		
//	}
	
}
