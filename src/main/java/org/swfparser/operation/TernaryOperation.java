/*
 *   TernaryOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 18.08.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Arrays;
import java.util.List;

import org.swfparser.BooleanOperation;
import org.swfparser.CodeUtil;
import org.swfparser.DualUse;
import org.swfparser.Operation;

public class TernaryOperation extends AbstractOperation implements BooleanOperation, DualUse {

	protected Operation condition;
	protected Operation trueOp;
	protected Operation falseOp;
	
	private boolean isStatement = false;

	public TernaryOperation(Operation condition, Operation trueOp, Operation falseOp) {
		super();
		this.condition = condition;
		this.trueOp = trueOp;
		this.falseOp = falseOp;
	}

	public int getArgsNumber() {
		return 3;
	}

	public String getStringValue(int level) {
		StringBuilder buf = new StringBuilder();
		buf.append(isStatement ? CodeUtil.getIndent(level) : "");
        buf.append("(");
        buf.append(condition.getStringValue(level));
        buf.append(" ? ");
        buf.append(trueOp.getStringValue(level));
        buf.append(" : ");
        buf.append(falseOp.getStringValue(level));
        buf.append(")");
        return buf.toString();
				
	}
	
	@Override
	public String toString() {
		return "TernaryOperation("+condition+","+trueOp+","+falseOp+")";
	}

	public Operation getInvertedOperation() {
		return new SimpleInvertedOperation(this);
	}

	public List<Operation> getOperations() {
		return Arrays.asList(condition,trueOp,falseOp);
	}

	public void markAsStatement() {
		isStatement = true;
	}

}
