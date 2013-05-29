/*
 *   InitObjectOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 26.07.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.log4j.Logger;

import org.swfparser.Operation;
import com.jswiff.swfrecords.actions.StackValue;

public class InitObjectOperation extends InitOperation {
	private static Logger logger = Logger.getLogger(InitObjectOperation.class);
	protected List<Map.Entry<Operation, Operation>> values;
	
	public InitObjectOperation(Stack<Operation> stack) {
		super(stack);
	}
	
	@Override
	protected void readArguments() {
		values = new ArrayList<Map.Entry<Operation, Operation>>(numberOfArgs);
		for (int j=0;j<numberOfArgs;j++) {
			Operation value = stack.pop();
			Operation key = stack.pop();
			Map.Entry<Operation, Operation> entry = new Entry(key,value);
			values.add(entry);
		}
		Collections.reverse(values);
	}

	public String getStringValue(int level) {
		StringBuffer buf = new StringBuffer();
		buf.append("{");
		
		int idx=0;
		for (Map.Entry<Operation, Operation> entry : values) {
			if (idx++ > 0) {
				buf.append(",");
			}
			String memberName = ( entry.getKey() instanceof StackValue 
					 && StackValue.TYPE_STRING == ((StackValue)entry.getKey()).getType()) 
					 ? ((StackValue)entry.getKey()).getString()
							 : entry.getKey().getStringValue(level);
			buf.append(memberName);
			buf.append(":");
			buf.append(entry.getValue().getStringValue(level));
		}
		
		buf.append("}");
		
		return buf.toString();
	}
	
	
	private class Entry implements Map.Entry {
		
		private Operation key;
		private Operation value;
		
		public Entry(Operation key, Operation value) {
			super();
			this.key = key;
			this.value = value;
		}

		public Object getKey() {
			return key;
		}
	
		public Object getValue() {
			return value;
		}
	
		public Object setValue(Object value) {
			Object oldValue = this.value;
			this.value = (Operation) value;
			return oldValue;
		}
	}


	public List<Operation> getOperations() {
		for (Map.Entry<Operation, Operation> entry : values) {
			underOperations.add(entry.getValue());
			underOperations.add(entry.getKey());
		}
		return underOperations;
	}


	

}
