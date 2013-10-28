/*
 *   WhileOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: Jul 28, 2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.swfparser.BooleanOperation;
import org.swfparser.ExecutionContext;
import org.swfparser.Operation;
import org.swfparser.exception.StatementBlockException;
import com.jswiff.swfrecords.actions.Action;

public class WhileOperation extends IfOperation {

    private List<Operation> conditionStatments = new ArrayList<>();

	public WhileOperation(Stack<Operation> stack, List<Operation> statements, List<Action> actions, ExecutionContext context) throws StatementBlockException {
		super(stack, actions, context);
        conditionStatments.add(0, this.condition);
// TODO !!!!!! GET and remove ALLLL statements until where the while-block starts (the start label)
        conditionStatments.add(0, statements.get(statements.size()-1));
        statements.remove(statements.size()-1);
		
		// if "continue" is the last operation, remove it
		if (!operations.isEmpty()) {
			Operation lastOperation = operations.get(operations.size()-1);
			if (lastOperation.equals(new SimpleOperation("continue"))) {
//				operations.set(operations.size()-1, new CommentOperation("continue skipped"));
				operations.remove(operations.size()-1);
			}
		}
		
	}
	
	@Override
	protected String getLoopHeader() {
		return "while";
	}

    private String renderConditionStatements() {
        StringBuilder buf = new StringBuilder();
        Operation lastOp = conditionStatments.get(conditionStatments.size() - 1);
        for (Operation op : conditionStatments) {
            if (lastOp != op) {
                buf.append(op.getStringValue(0));
                buf.append(", ");
            } else {
                // The last op is the one that is actually the relevant one that is evaluated, e.g. in
                //    while (__reg1--, __reg1 - 1 > -1)
                // that needs to be inverted, since IF actions work like that.
                buf.append(((BooleanOperation) op).getInvertedOperation().getStringValue(0));
            }
        }
        return buf.toString();
    }

   protected String getHeaderLine() {
        StringBuilder sourceCode = new StringBuilder();
        sourceCode.append(getLoopHeader());
        sourceCode.append(" (");
//           sourceCode.append(((BooleanOperation) condition).getInvertedOperation().getStringValue(0));
        sourceCode.append(renderConditionStatements());
        sourceCode.append(") {");
        return sourceCode.toString();
   	}

    @Override
   	public String toString() {
   		return "while(" + renderConditionStatements() + ")";
   	}

	public Operation getCondition() {
		return condition;
	}
	
	public List<Operation> getInlineOperations() {
		return operations;
	}


}
