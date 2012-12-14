/*
 *   AndOrOptimizerTest.java
 * 	 @Author Oleg Gorobets
 *   Created: 13.08.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import org.apache.log4j.Logger;

import org.swfparser.AndOrOptimizer;
import org.swfparser.Operation;
import org.swfparser.operation.AndOperation;
import org.swfparser.operation.GetVariableOperation;
import org.swfparser.operation.NotOperation;
import org.swfparser.operation.TrueOperation;
import org.swfparser.operation.OrOperation;
import org.swfparser.operation.FalseOperation;
import com.jswiff.swfrecords.actions.StackValue;

public class AndOrOptimizerTest {
	
	private static Logger logger = Logger.getLogger(AndOrOptimizerTest.class);
	
	@Test(groups="unit")
	public void testEq() {
		logger.debug("#test1()");
		Operation op1 = new GetVariableOperation(new StackValue("test"));
		Operation op2 = new GetVariableOperation(new StackValue("test"));
		AndOrOptimizer optimizerAnd = new AndOrOptimizer(new AndOperation(op1,op2));
		AndOrOptimizer optimizerOr = new AndOrOptimizer(new OrOperation(op1,op2));
		
		Assert.assertEquals(optimizerAnd.getOptimizedOperation() , op1); 
		Assert.assertEquals(optimizerOr.getOptimizedOperation() , op1); 
	}
	
	@Test(groups="unit")
	public void testInv() {
		logger.debug("#test1()");
		Operation op1 = new GetVariableOperation(new StackValue("test"));
		Operation op2 = new NotOperation(new GetVariableOperation(new StackValue("test")));
		AndOrOptimizer optimizerAnd = new AndOrOptimizer(new AndOperation(op1,op2));
		AndOrOptimizer optimizerOr = new AndOrOptimizer(new OrOperation(op1,op2));
		
		Assert.assertEquals(optimizerAnd.getOptimizedOperation() , new FalseOperation()); 
		Assert.assertEquals(optimizerOr.getOptimizedOperation() , new TrueOperation()); 
	}
	
	@Test(groups="unit")
	public void notTest() {
		Operation doubleNot = new NotOperation(new NotOperation(new GetVariableOperation(new StackValue("test"))));
		Operation doubleNot2 = new NotOperation(new NotOperation(new GetVariableOperation(new StackValue("test"))));
		Assert.assertEquals(doubleNot, doubleNot2);
		Assert.assertEquals(new GetVariableOperation(new StackValue("test")), doubleNot);
		Assert.assertEquals(doubleNot, new GetVariableOperation(new StackValue("test")));
		
		// stack values
		Assert.assertEquals(new NotOperation(new NotOperation(new StackValue(false))), new StackValue(false));
		Assert.assertEquals(new NotOperation(new NotOperation(new StackValue(false))), new StackValue(false));
		Assert.assertEquals(new NotOperation(new NotOperation(new StackValue(true))), new StackValue(true));
		Assert.assertEquals(new NotOperation(new NotOperation(new StackValue("test"))), new StackValue("test"));
		
		// more tests
		Operation op = new StackValue("test");
		Operation notOp = new NotOperation(new StackValue("test"));
		
		Assert.assertEquals(new NotOperation(op), notOp);
		Assert.assertEquals(notOp,new NotOperation(op));
		Assert.assertEquals(new NotOperation(notOp), op);
		Assert.assertEquals(op,new NotOperation(notOp));
		
		
		op = new GetVariableOperation(new StackValue("test"));
		notOp = new NotOperation( new GetVariableOperation(new StackValue("test")));
		
		Assert.assertEquals(new NotOperation(op), notOp);
		Assert.assertEquals(notOp,new NotOperation(op));
		Assert.assertEquals(new NotOperation(notOp), op);
		Assert.assertEquals(op,new NotOperation(notOp));
		
	}
}
