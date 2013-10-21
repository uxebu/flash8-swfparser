/*
 *   ExecutionContextImpl.java
 * 	 @Author Oleg Gorobets
 *   Created: 26.07.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.jswiff.swfrecords.actions.Action;
import com.jswiff.swfrecords.tags.Tag;

public class ExecutionContextImpl implements ExecutionContext {

	private Stack<Operation> operationStack = new Stack<Operation>();
	private Stack<Operation> execStack = new ExecutionStack<Operation>();
	private Stack<Action> actionStack = new Stack<Action>();
	private List<String> constants = new ArrayList<String>();
	private List<Operation> registers = new AutoSizeArrayList<Operation>();
//	private Map<String,Action> labels = new HashMap<String,Action>();
	private Stack<StatementBlockMoment> momentStack = new Stack<StatementBlockMoment>();
//	private PatternAnalyzer patternAnalyzer;
	private PatternAnalyzerEx patternAnalyzerEx;
	private Tag tag;
	private int frameNumber;
	
	public ExecutionContextImpl() {
		
	}
	
	public ExecutionContextImpl(Stack<Operation> operationStack, Stack<Operation> execStack,Stack<Action> actionStack, List<String> constants, List<Operation> registers, Map<String,Action> labels) {
		super();
		this.operationStack = operationStack;
		this.constants = constants;
		this.registers = registers;
		this.execStack = execStack;
//		this.labels = labels;
		this.actionStack = actionStack;
	}
	
	
	public List<String> getConstants() {
		return constants;
	}

	public void setConstants(List<String> constants) {
		this.constants = constants;
	}
	
	public Stack<Action> getActionStack() {
		return actionStack;
	}

//	public void setActionStack(Stack<Action> actionStack) {
//		this.actionStack = actionStack;
//	}

	public List<Operation> getRegisters() {
		return registers;
	}
//	public void setRegisters(List<Operation> registers) {
//		this.registers = registers;
//	}


	public Stack<Operation> getOperationStack() {
		return operationStack;
	}
	
	
	public Stack<Operation> getExecStack() {
		return execStack;
	}


	public void setExecStack(Stack<Operation> stack) {
		this.execStack = stack;
		
	}

//	public Map<String, Action> getLabels() {
//		return labels;
//	}
//
//	public void setLabels(Map<String, Action> labels) {
//		this.labels = labels;
//	}

	public void setActionStack(Stack<Action> actionStack) {
		this.actionStack = actionStack;
	}

	public Stack<StatementBlockMoment> getMomentStack() {
		return momentStack;
	}

	public void setRegisters(List<Operation> registers) {
		this.registers = registers;
		
	}

//	public PatternAnalyzer getPatternAnalyzer() {
//		return patternAnalyzer;
//	}
//
//	public void setPatternAnalyzer(PatternAnalyzer patternAnalyzer) {
//		this.patternAnalyzer = patternAnalyzer;
//	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public int getFrameNumber() {
		return frameNumber;
	}

	public void setFrameNumber(int frameNumber) {
		this.frameNumber = frameNumber;
	}

	public PatternAnalyzerEx getPatternAnalyzerEx() {
		return patternAnalyzerEx;
	}

	public void setPatternAnalyzerEx(PatternAnalyzerEx patternAnalyzerEx) {
		this.patternAnalyzerEx = patternAnalyzerEx;
	}


	
}
