/*
 *   ExecutionContext.java
 * 	 @Author Oleg Gorobets
 *   Created: 26.07.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser;

import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.jswiff.swfrecords.actions.Action;
import com.jswiff.swfrecords.tags.Tag;

public interface ExecutionContext {

	/**
	 * Stack of operations under which the current context executes
	 * 
	 * @return
	 */
	public Stack<Operation> getOperationStack();
	
	/**
	 * Current execution stack of operations
	 * 
	 * @return
	 */
	public Stack<Operation> getExecStack();
	
	/**
	 * Set current execution stack
	 * 
	 * @param stack
	 */
	public void setExecStack(Stack<Operation> stack);
	
	
	/**
	 * @return
	 */
	public Stack<Action> getActionStack();
	/**
	 * @param actions
	 */
	public void setActionStack(Stack<Action> actions);

	/**
	 * @return
	 */
	public List<String> getConstants();

	/**
	 * @return
	 */
	public List<Operation> getRegisters();
	
	
	/**
	 * @param registers
	 */
	public void setRegisters(List<Operation> registers);
	
	/**
	 * @param labels
	 */
//	public void setLabels(Map<String,Action> labels);
	/**
	 * @return
	 */
//	public Map<String,Action> getLabels();
	
	/**
	 * @return
	 */
	public Stack<StatementBlockMoment> getMomentStack();
	
	/**
	 * Branch analyzer
	 * 
	 * @return
	 */
//	public PatternAnalyzer getPatternAnalyzer();
//	
//	public void setPatternAnalyzer(PatternAnalyzer analyzer);
	
	public PatternAnalyzerEx getPatternAnalyzerEx();
	
	public void setPatternAnalyzerEx(PatternAnalyzerEx analyzer);
	
	
	/**
	 * Current tag
	 * 
	 * @param tag
	 */
	public void setTag(Tag tag);
	public Tag getTag();
	
	/**
	 * Current frame number
	 * 
	 * @param frameNum
	 */
	public void setFrameNumber(int frameNum);
	public int getFrameNumber();
	
	
	
}
