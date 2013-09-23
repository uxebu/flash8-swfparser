/*
 *   StoreRegisterOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 26.07.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.log4j.Logger;

import org.swfparser.AssignOperation;
import org.swfparser.BooleanOperation;
import org.swfparser.CodeUtil;
import org.swfparser.ExecutionContext;
import org.swfparser.Operation;
import org.swfparser.OperationFactory;
import org.swfparser.Priority;
import org.swfparser.SkipOperation;
import org.swfparser.annotations.DoNotWritePop;
import com.jswiff.swfrecords.actions.NullStackValue;
import com.jswiff.swfrecords.actions.StoreRegister;

@DoNotWritePop
public class StoreRegisterOperation extends UnaryOperation  implements OperationFactory, AssignOperation, SkipOperation {

	private static Logger logger = Logger.getLogger(StoreRegisterOperation.class);
	private int registerNumber;
	private Operation returnOperation;
	private RegisterHandle registerHandle;
	private boolean skipOperation = false;
	
	public StoreRegisterOperation(ExecutionContext context, StoreRegister action) {
		super(context.getExecStack().peek());
		this.registerNumber = action.getNumber();
		
		logger.debug("Register number = "+this.registerNumber);
		logger.debug("Adding register "+this.registerNumber+" = "+op);
		this.registerHandle = new RegisterHandle(this.registerNumber, op, this);
		context.getRegisters().set(this.registerNumber, this.registerHandle);
		String regInfo="REGS:";
		int i=1;
		for (Operation op : context.getRegisters()) {
			regInfo+=(i++)+"=>"+op+",";
		}
		logger.debug(regInfo);
		
		handleIncrementDecrement();
		
		// If operation under Enumerate/Enumerate2 and NullStackValue is assigned to register, 
		// set this RegisterHandle as variable in for..in statement and skip this statement.
		if (!context.getOperationStack().isEmpty() 
				&& (context.getOperationStack().peek() instanceof ForInOperation)
				&& op instanceof NullStackValue) {
			
			((ForInOperation)context.getOperationStack().peek()).setVariable(registerHandle);
//			((ForInOperation)context.getOperationStack().peek()).setVariable(op);
			skipOperation = true;
		}
		
	}
	
	protected void handleIncrementDecrement() {
		if (op instanceof SimpleIncrementOperation) {
			Operation underlyingOp = ((UnaryOperation)op).getOp();
			if (underlyingOp instanceof RegisterHandle && ((RegisterHandle)underlyingOp).getRegisterNumber() == this.registerNumber) {
				returnOperation = new PostIncrementOperation(underlyingOp,true);
			}
		}
		if (op instanceof SimpleDecrementOperation) {
			Operation underlyingOp = ((UnaryOperation)op).getOp();
			if (underlyingOp instanceof RegisterHandle && ((RegisterHandle)underlyingOp).getRegisterNumber() == this.registerNumber) {
				returnOperation = new PostDecrementOperation(underlyingOp, true);
			}
		}
		
		logger.debug("Return operation is " + returnOperation );
	}

	public String getStringValue(int level) {
		String val = op.getStringValue(0); // pass 0 as val shouldn't be indented
			
		return 	CodeUtil.getIndent(level)
			+"var "+
			"__R"
			+registerNumber
			+" = "
			+val;
	}
	
	public Operation getObject() {
		return returnOperation != null ? returnOperation : this;
	}
	
	public Operation getLeftPart() {
		return registerHandle;
	}

	public Operation getRightPart() {
		return op;
	}
	
	public static class RegisterHandle implements Operation, BooleanOperation {

		private int registerNumber;
		private Operation undelrlyingOp;
		private StoreRegisterOperation storeRegisterOp;
		
		public RegisterHandle(int registerNumber) {
			this.registerNumber = registerNumber;
		}
		public RegisterHandle(int registerNumber, Operation undelrlyingOp) {
			this.registerNumber = registerNumber;
			this.undelrlyingOp = undelrlyingOp;
		}
		public RegisterHandle(int registerNumber, Operation undelrlyingOp, StoreRegisterOperation storeRegisterOp) {
			this.registerNumber = registerNumber;
			this.undelrlyingOp = undelrlyingOp;
			this.storeRegisterOp = storeRegisterOp;
		}

		public int getArgsNumber() {
			return 0;
		}

		public String getStringValue(int level) {
			return "__R"+registerNumber;
		}

		public int getPriority() {
			return Priority.HIGHEST;
		}
		
		
		public int getRegisterNumber() {
			return registerNumber;
		}
		
		public void setRegisterNumber(int registerNumber) {
			this.registerNumber = registerNumber;
		}
		
		public StoreRegisterOperation getStoreRegisterOp() {
			return storeRegisterOp;
		}

		@Override
		public String toString() {
			return "RegisterHandle("+registerNumber+")";
		}

		public List<Operation> getOperations() {
			return Collections.EMPTY_LIST;
		}
		
		public Operation getUndelrlyingOp() {
			return undelrlyingOp;
		}
		
		@Override
		public boolean equals(Object obj) {
//			if (obj instanceof NotOperation) {
//				return obj.equals(this); // invert comparison
//			} else {
				if (!(obj instanceof RegisterHandle)) {
					return false;
				}
				if (obj == this) {
					return true;
				}
				
				RegisterHandle otherOp = (RegisterHandle) obj;
				return new EqualsBuilder()
					.append(this.registerNumber, otherOp.registerNumber)
					.isEquals();
//			}
		}
		public Operation getInvertedOperation() {
			return new SimpleInvertedOperation(this);
		}
		
	}

	public boolean skip() {
		return skipOperation;
	}


	
	
	

}
