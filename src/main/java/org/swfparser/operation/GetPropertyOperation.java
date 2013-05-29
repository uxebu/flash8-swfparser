/*
 *   GetPropertyOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 15.08.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import org.apache.log4j.Logger;

import org.swfparser.Operation;
import org.swfparser.Priority;
import com.jswiff.swfrecords.actions.StackValue;

/*
	
	ActionGetProperty does the following:
	1. Pops index off the stack.
	2. Pops target off the stack.
	3. Retrieves the value of the property enumerated as index from the movie clip with target
	path target and pushes the value to the stack.
	The following table lists property index values. The _quality, _xmouse and _ymouse
	properties are available in SWF 5 and later.
	Field Type Comment
	ActionGetProperty UI8 Action = 0x22
	Property Value
	_X 0
	_Y 1
	_xscale 2
	_yscale 3
	_currentframe 4
	_totalframes 5
	_alpha 6
	_visible 7
	_width 8
	_height 9
	_rotation 10
	_target 11
	_framesloaded 12
	_name 13
	_droptarget 14
	_url 15
	_highquality 16
	_focusrect 17
	_soundbuftime 18
	_quality 19
	_xmouse 20
	_ymouse 21
	
 * 
 */


public class GetPropertyOperation extends AbstractOperation {
	
	public static String[] properties = new String[] {
		"_X",
		"_Y",
		"_xscale",
		"_yscale",
		"_currentframe",
		"_totalframes",
		"_alpha",
		"_visible",
		"_width",
		"_height",
		"_rotation",
		"_target",
		"_framesloaded",
		"_name",
		"_droptarget",
		"_url",
		"_highquality",
		"_focusrect",
		"_soundbuftime",
		"_quality",
		"_xmouse",
		"_ymouse",
	};

	private static Logger logger = Logger.getLogger(GetPropertyOperation.class);
	
	private Operation target; 
	private Operation index; 
	
	public GetPropertyOperation(Stack<Operation> stack) {
		super(stack);
		index = stack.pop();  
		target = stack.pop();
		logger.debug("Get property of "+target+" with index "+index);
	}

	public int getArgsNumber() {
		return 2;
	}

	public String getStringValue(int level) {
		
		String indexValue = index.getStringValue(level);
		if (index instanceof StackValue) {
			indexValue = properties[((StackValue)index).getIntValue()];
		}

		return new StringBuffer()
		.append("getProperty(")
		.append(target.getStringValue(level))
		.append(",")
		.append(indexValue)
		.append(")")
		.toString();
	}
	
	@Override
	public String toString() {
		return "GetProperty("+target+","+index+")";
	}
	
	@Override
	public int getPriority() {
		return Priority.CALL_FUNCTION;
	}

	public List<Operation> getOperations() {
		return Arrays.asList(index,target);
	}

}
