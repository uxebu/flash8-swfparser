/*
 *   CommentOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 14.08.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import org.swfparser.CodeUtil;

public class CommentOperation extends SimpleOperation {

	public CommentOperation(String comment) {
		super(comment);
	}
	public String getStringValue(int level) {
		return CodeUtil.getIndent(level)+"// "+ opName;
	}

}
