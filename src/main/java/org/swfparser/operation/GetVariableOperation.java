/*
 *   GetVariableOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 24.07.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Stack;

import org.swfparser.BooleanOperation;
import org.swfparser.Operation;
import org.swfparser.Priority;
import com.jswiff.swfrecords.actions.StackValue;

// TODO: check BooleanOperation 
public class GetVariableOperation extends UnaryOperation implements BooleanOperation {

	public GetVariableOperation(Stack<Operation> stack) {
		super(stack);
	}

	public GetVariableOperation(Operation op) {
		super(op);
	}

	@Override
	public String getStringValue(int level) {
		String variableName;
        if (op instanceof StackValue && StackValue.TYPE_UNDEFINED == ((StackValue)op).getType()) {
            variableName = "undefined";
        } else if (op instanceof StackValue && StackValue.TYPE_STRING==((StackValue)op).getType()) {
            // e.g. `eval("<anything>")`
            String stringValue = ((StackValue)op).getString();
            if (stringValue.matches("[a-zA-Z_][a-zA-Z0-9_]*") &&
// TODO complete the check in the line below, or is it complete?
                !stringValue.matches("false|true|null|undefined")) {
                // e.g. `eval("x")` or `eval("_root")`
                variableName = stringValue;
            } else {
                // e.g. `eval("not looking like a var, e.g. special chars, etc.")`
                variableName = "eval("+ op.getStringValue(level) +")";
            }
        } else {
            // e.g. `eval(_global.e + '..')`
            variableName = "eval("+ op.getStringValue(level) +")";
        }
		return variableName;
	}
	
	@Override
	public String toString() {
		return "GetVariable("+op+")";
	}
	
	@Override
	public int getPriority() {
		return Priority.HIGHEST;
	}

	public Operation getInvertedOperation() {
		return new SimpleInvertedOperation(this);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof NotOperation) {
			return obj.equals(this); // invert comparison
		} else {
			return super.equals(obj);
		}
	}
	
	

}
