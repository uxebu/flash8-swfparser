/*
 *   ExecutionStack.java
 * 	 @Author Oleg Gorobets
 *   Created: 11.09.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser;

import java.util.Stack;

import com.jswiff.swfrecords.actions.UndefinedStackValue;

public class ExecutionStack<E> extends Stack<E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 948966706452087756L;

	public ExecutionStack() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	/* (non-Javadoc)
	 * @see java.util.Stack#pop()
	 * 
	 * Return "undefined" value if stack is empty
	 * 
	 */
	@Override
	public synchronized E pop() {
		return isEmpty() ? (E)new UndefinedStackValue() : super.pop();
	}
	
	/* (non-Javadoc)
	 * @see java.util.Stack#peek()
	 * 
	 * Return "undefined" value if stack is empty
	 * 
	 */
	@Override
	public synchronized E peek() {
		return isEmpty() ? (E)new UndefinedStackValue() : super.peek();
	}

}
