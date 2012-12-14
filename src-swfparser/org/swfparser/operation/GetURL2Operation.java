/*
 *   GetURL2Operation.java
 * 	 @Author Oleg Gorobets
 *   Created: Jul 30, 2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import org.swfparser.Operation;
import com.jswiff.swfrecords.actions.GetURL2;

public class GetURL2Operation extends GetURLOperation {

	private Operation targetOp;
	private Operation urlOp;
	
	public GetURL2Operation(Stack<Operation> stack, GetURL2 action) {
		super();
		this.targetOp = stack.pop(); 
		this.urlOp = stack.pop(); 
		target = targetOp.getStringValue(0);
		url = urlOp.getStringValue(0);
	}

	@Override
	public int getArgsNumber() {
		return 2;
	}
	
	public List<Operation> getOperations() {
		return Arrays.asList(targetOp,urlOp);
	}
	
}
