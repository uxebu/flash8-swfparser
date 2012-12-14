/*
 *   GotoFrame2Operation.java
 * 	 @Author Oleg Gorobets
 *   Created: 26.07.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Stack;

import org.apache.log4j.Logger;

import org.swfparser.CodeUtil;
import org.swfparser.Operation;
import com.jswiff.swfrecords.actions.GoToFrame2;

public class GotoFrame2Operation extends UnaryOperation /*implements ActionAware*/ {

	private static Logger logger = Logger.getLogger(GotoFrame2Operation.class);
	
//	private boolean unknownFrame = false;
//	private Integer frameNum;
//	private String frameName;
	private GoToFrame2 gotoFrame2;
//	private String action;
	
	public GotoFrame2Operation(Stack<Operation> stack, GoToFrame2 action) {
		super(stack);
		this.gotoFrame2 = action;
		
	}

	public String getStringValue(int level) {
		
		String funcName = gotoFrame2.play() ? "gotoAndPlay" : "gotoAndStop";
		String sceneBias = gotoFrame2.getSceneBias()>0 ? ","+gotoFrame2.getSceneBias() : "";
		return new StringBuffer()
			.append(CodeUtil.getIndent(level))
			.append(funcName)
			.append("(")
			.append(op.getStringValue(level))
			.append(sceneBias)
			.append(")")
			.toString();
		

	}
	
	

}
