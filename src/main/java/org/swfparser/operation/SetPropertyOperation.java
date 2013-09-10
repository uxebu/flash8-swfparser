/*
 *   SetPropertyOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 14.08.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import org.swfparser.CodeUtil;
import org.swfparser.Operation;
import com.jswiff.swfrecords.actions.StackValue;

public class SetPropertyOperation extends AbstractOperation {

	protected Operation target;
	protected Operation index;
	protected Operation value;
	
	public SetPropertyOperation(Stack<Operation> stack) {
		super(stack);
		value = stack.pop();
		index = stack.pop();
		target = stack.pop();
	}

	public int getArgsNumber() {
		return 3;
	}

	public String getStringValue(int level) {
		String indexValue = index.getStringValue(level);
		if (index instanceof StackValue) {
			indexValue = GetPropertyOperation.properties[((StackValue)index).getIntValue()];
		}
		
		StringBuilder buf = new StringBuilder();
		buf.append(CodeUtil.getIndent(level));
		buf.append("setProperty(");
		buf.append(target.getStringValue(level));
		buf.append(", '");
		buf.append(indexValue); // As per tests this always has to be a string, though AS2 allows pure `_x` too, but we generated JS, so we make it `'_x'`.
		buf.append("' ,");
		buf.append(value.getStringValue(level));
		buf.append(")");
		return buf.toString();

	}

	public List<Operation> getOperations() {
		return Arrays.asList(value,index,target);
	}

}
