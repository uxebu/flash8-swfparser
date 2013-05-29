/*
 *   SimpleOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 26.07.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import org.swfparser.CodeUtil;
import org.swfparser.Operation;
import org.swfparser.Priority;

public class SimpleOperation implements Operation {

	protected String opName;
	
	public SimpleOperation(String opName) {
		super();
		this.opName = opName;
	}

	public int getArgsNumber() {
		return 0;
	}

	public String getStringValue(int level) {
		return CodeUtil.getIndent(level)+opName;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof SimpleOperation)) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		SimpleOperation op = (SimpleOperation) obj;
		return new EqualsBuilder()
			.append(opName, op.opName)
			.isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(opName)
			.toHashCode();
	}

	public int getPriority() {
		return Priority.LOWEST;
	}

	public List<Operation> getOperations() {
		return Collections.EMPTY_LIST;
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName()+"("+opName+")";
	}

}
