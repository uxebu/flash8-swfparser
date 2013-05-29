/*
 *   GetURLOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 25.07.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Collections;
import java.util.List;

import org.swfparser.CodeUtil;
import org.swfparser.Operation;
import org.swfparser.Priority;
import com.jswiff.swfrecords.actions.Action;
import com.jswiff.swfrecords.actions.GetURL;
import com.jswiff.swfrecords.actions.StackValue;

public class GetURLOperation implements Operation {
	
	private GetURL getURL;
	protected String target;
	protected String url;

	public GetURLOperation() {
	}
	
	public GetURLOperation(Action action) {
		getURL = (GetURL) action;
		target = StackValue.formatString(getURL.getTarget());
		url = StackValue.formatString(getURL.getURL());
	}

	public int getArgsNumber() {
		return 0;
	}

	public String getStringValue(int level) {
		return new StringBuffer()
			.append(CodeUtil.getIndent(level))
			.append("getURL(")
			.append(url)
			.append(",")
			.append(target)
			.append(")")
			.toString();
	}

	public int getPriority() {
		return Priority.LOWEST;
	}

	public List<Operation> getOperations() {
		return Collections.EMPTY_LIST;
	}

}
