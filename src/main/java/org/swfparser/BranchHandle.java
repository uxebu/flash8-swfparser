/*
 *   BranchHandle.java
 * 	 @Author Oleg Gorobets
 *   Created: 06.09.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

/**
 * 
 */
package org.swfparser;

import com.jswiff.swfrecords.actions.Branch;

class BranchHandle {
	private Branch branch;
	private int pointer;
	private int jumpPointer;
	private int offset;
	private int jumpOffset;
	
	public BranchHandle(Branch branch, int pointer, int jumpPointer, int offset, int jumpOffset) {
		super();
		this.branch = branch;
		this.pointer = pointer;
		this.jumpPointer = jumpPointer;
		this.offset = offset;
		this.jumpOffset = jumpOffset;
	}
	
	public Branch getBranch() {
		return branch;
	}
	public void setBranch(Branch branch) {
		this.branch = branch;
	}
	public int getPointer() {
		return pointer;
	}
	public void setPointer(int pointer) {
		this.pointer = pointer;
	}
	public int getJumpPointer() {
		return jumpPointer;
	}
	public void setJumpPointer(int jumpPointer) {
		this.jumpPointer = jumpPointer;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getJumpOffset() {
		return jumpOffset;
	}
	public void setJumpOffset(int jumpOffset) {
		this.jumpOffset = jumpOffset;
	}
	
	
	
}