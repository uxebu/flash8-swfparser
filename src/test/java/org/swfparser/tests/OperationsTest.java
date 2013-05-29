/*
 *   OperationsTest.java
 * 	 @Author Oleg Gorobets
 *   Created: 13.08.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import org.swfparser.Operation;
import org.swfparser.operation.AndOperation;
import org.swfparser.operation.GetVariableOperation;
import org.swfparser.operation.OrOperation;
import com.jswiff.swfrecords.actions.StackValue;

public class OperationsTest {
	
	@Test(groups="unit")
	public void testEquals() {
		
		Assert.assertEquals(new StackValue("test"), new StackValue("test"));
		
		Operation op1 = new GetVariableOperation(new StackValue("test"));
		Operation op2 = new GetVariableOperation(new StackValue("test"));
		Assert.assertEquals(op1, op2);
		
		
		Assert.assertEquals(new AndOperation(op1,op2), new AndOperation(op1,op2));
		Assert.assertEquals(new OrOperation(op1,op2), new OrOperation(op1,op2));
	}
}
