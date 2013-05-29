/*
 *   PatternJumpsInfo.java
 * 	 @Author Oleg Gorobets
 *   Created: 06.09.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser;

import java.util.LinkedHashMap;
import java.util.Map;

import com.jswiff.swfrecords.actions.Action;
import com.jswiff.swfrecords.actions.Branch;

public class PatternJumpsInfo {
	
	private Map<Branch,Action> allJumpsBefore = new LinkedHashMap<Branch,Action>();
	private Map<Branch,Action> conditionalJumpsAfter = new LinkedHashMap<Branch,Action>();
	private Map<Branch,Action> unconditionalJumpsAfter = new LinkedHashMap<Branch,Action>();
	private Map<Branch,Action> allJumpsAfter = new LinkedHashMap<Branch,Action>();
	private Map<Branch,Action> conditionalEndJumps = new LinkedHashMap<Branch,Action>();
	private Map<Branch,Action> unconditionalEndJumps = new LinkedHashMap<Branch,Action>();
	private Action latestJumpBefore;
	
	public Map<Branch, Action> getAllJumpsBefore() {
		return allJumpsBefore;
	}
	public void setAllJumpsBefore(Map<Branch, Action> jumpsBefore) {
		this.allJumpsBefore = jumpsBefore;
	}
	public Map<Branch, Action> getConditionalJumpsAfter() {
		return conditionalJumpsAfter;
	}
	public void setConditionalJumpsAfter(Map<Branch, Action> conditionalJumpsAfter) {
		this.conditionalJumpsAfter = conditionalJumpsAfter;
	}
	public Map<Branch, Action> getUnconditionalJumpsAfter() {
		return unconditionalJumpsAfter;
	}
	public void setUnconditionalJumpsAfter(Map<Branch, Action> unconditionalJumpsAfter) {
		this.unconditionalJumpsAfter = unconditionalJumpsAfter;
	}
	public Map<Branch, Action> getConditionalEndJumps() {
		return conditionalEndJumps;
	}
	public void setConditionalEndJumps(Map<Branch, Action> conditionalEndJumps) {
		this.conditionalEndJumps = conditionalEndJumps;
	}
	public Map<Branch, Action> getUnconditionalEndJumps() {
		return unconditionalEndJumps;
	}
	public void setUnconditionalEndJumps(Map<Branch, Action> unconditionalEndJumps) {
		this.unconditionalEndJumps = unconditionalEndJumps;
	}
	public Action getLatestJumpBefore() {
		return latestJumpBefore;
	}
	public void setLatestJumpBefore(Action latestJumpBefore) {
		this.latestJumpBefore = latestJumpBefore;
	}
	public Map<Branch, Action> getAllJumpsAfter() {
		return allJumpsAfter;
	}
	public void setAllJumpsAfter(Map<Branch, Action> allJumpsAfter) {
		this.allJumpsAfter = allJumpsAfter;
	}
	
	
	
	
	
}
