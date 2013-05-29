/*
 *   ForInOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 03.09.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.List;
import java.util.Stack;

import org.apache.log4j.Logger;

import org.swfparser.ExecutionContext;
import org.swfparser.Operation;
import org.swfparser.exception.StatementBlockException;
import com.jswiff.swfrecords.actions.Action;
import com.jswiff.swfrecords.actions.NullStackValue;
import com.jswiff.swfrecords.actions.StackValue;

/*
 * ActionEnumerate obtains the names of all “slots” in use in an ActionScript object—that is, for
	an object obj, all names X that could be retrieved with the syntax obj.X. ActionEnumerate is
	used to implement the for..in statement in ActionScript.
	ActionEnumerate does the following:
	1. Pops the name of the object variable (which can include slash-path or dot-path syntax) off
	of the stack.
	2. Pushes a null value onto the stack to indicate the end of the slot names.
	3. Pushes each slot name (a string) onto the stack.
 */
public class ForInOperation extends IfOperation {

	private static Logger logger = Logger.getLogger(ForInOperation.class);
	private Operation variable;
	
	/**
	 * TODO: Add correct handling of variable
	 * 
	 * @param context
	 * @param actions
	 * @param varActions
	 * @throws StatementBlockException
	 */
	public ForInOperation(ExecutionContext context, List<Action> actions, List<Action> varActions) throws StatementBlockException {
		super(context);
		condition = stack.pop();
		
		logger.debug("Enumerate object = "+condition);
		
		stack.push(new NullStackValue());
		
		executeWithCopiedStack(context, varActions, this);
		
		readActions(actions);
		
		// if "continue" is the last operation, remove it
		if (!operations.isEmpty()) {
			Operation lastOperation = operations.get(operations.size()-1);
			if (lastOperation.equals(new SimpleOperation("continue"))) {
				operations.remove(operations.size()-1);
			}
		}
	
	}

	@Override
	protected String getHeaderLine() {
		
		String variableVal = "var";
		if (variable != null) {
			variableVal = (variable instanceof StackValue && StackValue.TYPE_STRING == ((StackValue)variable).getType()) 
				? ((StackValue)variable).getString() : variable.getStringValue(0);
		}
		
		String conditionVal = (condition instanceof StackValue && StackValue.TYPE_STRING == ((StackValue)condition).getType()) 
		? ((StackValue)condition).getString() : condition.getStringValue(0);
		
		return new StringBuffer()
		.append("for (")
		.append(variableVal)
		.append(" in ")
		.append(conditionVal)
		.append(") {")
		.toString();
		
	}
	
	void setVariable(Operation v) {
		this.variable = v;
	}

}
