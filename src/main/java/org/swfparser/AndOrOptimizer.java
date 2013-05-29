/*
 *   AndOrOptimizer.java
 * 	 @Author Oleg Gorobets
 *   Created: 13.08.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import org.swfparser.operation.AndOperation;
import org.swfparser.operation.BinaryLogicalOperation;
import org.swfparser.operation.NotOperation;
import org.swfparser.operation.TrueOperation;
import org.swfparser.operation.OrOperation;
import org.swfparser.operation.FalseOperation;

public class AndOrOptimizer implements Operation {
	
	private static Logger logger = Logger.getLogger(AndOrOptimizer.class);
	
//	private BinaryLogicalOperation operation;
	private Operation optimizedOperation;
	
	private static int instanceId = 0;
	public AndOrOptimizer(BinaryLogicalOperation operation) {
//		this.operation = operation;
		instanceId++;
		this.optimizedOperation = optimizeOperation(operation);
	}
	
	protected Operation optimizeOperation(BinaryLogicalOperation op) {
		if (op instanceof AndOperation) {
			return optimizeAnd((AndOperation)op);
		} else if (op instanceof OrOperation) {
			return optimizeOr((OrOperation)op);
		} else {
			return op;
		}
	}
	
	
	public Operation getOptimizedOperation() {
		return optimizedOperation;
	}

	protected Operation optimizeAnd(AndOperation op) {
		logger.debug("Optimizing AND("+instanceId+"): "+op);
//		logger.debug("Optimizing AND for"+op1+" and "+op2);
		Operation op1 = op.getLeftOp();
		Operation op2 = op.getRightOp();
		
		if (op1 instanceof TrueOperation) {
			logger.debug("Result("+instanceId+") is "+op2);
			return op2;
		}
		if (op2 instanceof TrueOperation) {
			logger.debug("Result("+instanceId+") is "+op1);
			return op1;
		}
		if (op1 instanceof FalseOperation || op2 instanceof FalseOperation) {
			logger.debug("Result("+instanceId+") is false");
			return new FalseOperation();
		}
		if (isNegative(op1, op2)) {
			logger.debug("Result("+instanceId+") is false");
			return new FalseOperation();
		}
		if (isTheSame(op1, op2)) {
			logger.debug("Result("+instanceId+") is "+op1);
			return op1;
		}
		
		
		if (op1 instanceof BinaryLogicalOperation && op2 instanceof BinaryLogicalOperation) {
			return new AndOperation(optimizeOperation((BinaryLogicalOperation)op1),optimizeOperation((BinaryLogicalOperation)op2));
		} else if (op1 instanceof BinaryLogicalOperation) {
			return new AndOperation(optimizeOperation((BinaryLogicalOperation)op1),op2);
		} else if (op2 instanceof BinaryLogicalOperation) {
			return new AndOperation(op1,optimizeOperation((BinaryLogicalOperation)op2));
		}
		
		logger.debug("No optimization for AND("+instanceId+") "+op);
		
		return op;
	}

	protected Operation optimizeOr(OrOperation op) {
		logger.debug("Optimizing OR("+instanceId+"): "+op);
//		logger.debug("Optimizing AND for"+op1+" and "+op2);
		Operation op1 = op.getLeftOp();
		Operation op2 = op.getRightOp();
		
		if (op1 instanceof TrueOperation || op2 instanceof TrueOperation) {
			logger.debug("Result("+instanceId+") is true");
			return new TrueOperation();
		}
		if (op1 instanceof FalseOperation) {
			logger.debug("Result("+instanceId+") is "+op2);
			return op2;
		}
		if (op2 instanceof FalseOperation) {
			logger.debug("Result("+instanceId+") is "+op1);
			return op1;
		}
		if (isNegative(op1, op2)) {
			logger.debug("Result("+instanceId+") is true");
			return new TrueOperation();
		}
		if (isTheSame(op1, op2)) {
			logger.debug("Result("+instanceId+") is "+op1);
			return op1;
		}
		
		
		if (op1 instanceof BinaryLogicalOperation && op2 instanceof BinaryLogicalOperation) {
			return new OrOperation(optimizeOperation((BinaryLogicalOperation)op1),optimizeOperation((BinaryLogicalOperation)op2));
		} else if (op1 instanceof BinaryLogicalOperation) {
			OrOperation optimized = new OrOperation(optimizeOperation((BinaryLogicalOperation)op1),op2);
			return optimizeOr1(optimized);
		} else if (op2 instanceof BinaryLogicalOperation) {
			OrOperation optimized = new OrOperation(op1,optimizeOperation((BinaryLogicalOperation)op2));
			return optimizeOr1(optimized);
		}
		
		logger.debug("No optimization for OR("+instanceId+") "+op);
		
		return op;
	}	


	protected boolean isNegative(Operation op1,Operation op2) {
		boolean isNegative = false;
		
		if (op1 instanceof NotOperation) {
			isNegative = ((NotOperation)op1).getOp().equals(op2);
		}
		
		if (op2 instanceof NotOperation) {
			isNegative = ((NotOperation)op2).getOp().equals(op1);
		}
		
		return isNegative;
		
	}
	
	protected boolean isTheSame(Operation op1,Operation op2) {
		boolean isTheSame = false;
		
		if (op1 instanceof NotOperation && op2 instanceof NotOperation) {
			isTheSame = ((NotOperation)op1).getOp().equals(((NotOperation)op2).getOp());
		} else {
			isTheSame = op1.equals(op2);
		}
		
		return isTheSame;
		
	}
	
	/**
	 * Optimize:
	 * 1) x || (!x && y) == x || y
	 * 2) x || (x && y) == x
	 * 
	 * @return
	 */
	protected Operation optimizeOr1(OrOperation op) {
		Operation op1 = op.getLeftOp();
		Operation op2 = op.getRightOp();
		if (op1 instanceof AndOperation && !(op2 instanceof AndOperation)) {
			return optimizeOr1(op2,(AndOperation) op1);
		}
		if (op2 instanceof AndOperation && !(op1 instanceof AndOperation)) {
			return optimizeOr1(op1,(AndOperation) op2);
		}
		return op;
	}
	
	protected Operation optimizeOr1(Operation op1, AndOperation op2) {
		Operation andArg1 = op2.getLeftOp();
		Operation andArg2 = op2.getRightOp();
		if (andArg1.equals(op1) || andArg2.equals(op1)) {
			logger.debug("Result("+instanceId+") is "+op1);
			return op1;
		}
		if (andArg1.equals(new NotOperation(op1))) {
			Operation result = new OrOperation(op1,andArg2);
			logger.debug("Result("+instanceId+") is "+result);
			return result;
		}
		if (andArg2.equals(new NotOperation(op1))) {
			Operation result = new OrOperation(op1,andArg1);
			logger.debug("Result("+instanceId+") is "+result);
			return result;
		}
		
		return new OrOperation(op1,op2);
	}
	
	public int getArgsNumber() {
		return 2;
	}

	public int getPriority() {
		return 0;
	}

	public String getStringValue(int level) {
		return optimizedOperation.getStringValue(level);
	}

	public List<Operation> getOperations() {
		return Arrays.asList(optimizedOperation);
	}
	
	
}
