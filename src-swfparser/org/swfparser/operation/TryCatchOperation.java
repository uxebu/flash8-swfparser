/*
 *   TryCatchOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 14.08.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.List;
import java.util.Stack;

import org.apache.log4j.Logger;

import org.swfparser.CodeUtil;
import org.swfparser.ExecutionContext;
import org.swfparser.Operation;
import org.swfparser.annotations.NewAnalyzer;
import org.swfparser.exception.StatementBlockException;
import org.swfparser.util.PrintfFormat;

import com.jswiff.swfrecords.actions.Action;
import com.jswiff.swfrecords.actions.ActionBlock;
import com.jswiff.swfrecords.actions.ActionConstants;
import com.jswiff.swfrecords.actions.Branch;
import com.jswiff.swfrecords.actions.Try;

@NewAnalyzer
public class TryCatchOperation extends AbstractCompoundOperation {
	
	private static Logger logger = Logger.getLogger(TryCatchOperation.class);
	
	private List<Operation> tryOperations;
	private List<Operation> catchOperations;
	private List<Operation> finallyOperations;
	
	protected Try action;

	private static PrintfFormat actionFormat = new PrintfFormat("@@@@@@A %08X: [%s] 0x%02X (%s) %s");
	private static PrintfFormat offsetFormat = new PrintfFormat("0x%08X");
	
	public TryCatchOperation(Stack<Operation> stack, ExecutionContext context, Try action) throws StatementBlockException {
		super(context);
		this.action = action;
		
		logger.debug("Starting try-catch block...");
		logger.debug("@try actions");
		printActions(action.getTryBlock().getActions());
		logger.debug("@catch actions");
		printActions(action.getCatchBlock().getActions());
		
		List<Action> tryActions = prepareTryActions(action.getTryBlock().getActions());
		tryOperations = executeWithCopiedStack(context, tryActions, this).getOperations();
		
		if (action.hasCatchBlock()) {
			catchOperations = executeWithCopiedStack(context, action.getCatchBlock().getActions(), this).getOperations();
		}
		
		if (action.hasFinallyBlock()) {
			finallyOperations = executeWithCopiedStack(context, action.getFinallyBlock().getActions(), this).getOperations();
		}
	}

	public String getStringValue(int level) {
		
		logger.debug("Writing try with ");
		StringBuffer buf = new StringBuffer()
		.append(CodeUtil.getIndent(level))
		.append("try {\n");
	
		for (Operation operation : tryOperations) {
			buf
			.append(operation.getStringValue(level+1))
			.append(CodeUtil.endOfStatement(operation))
			.append("\n");
		}
		
		buf
		.append(CodeUtil.getIndent(level))
		.append("}");
		
		if (action.hasCatchBlock()) {
			String catchName;
			if (action.catchInRegister()) {
				short registerNum = action.getCatchRegister();
				catchName = new StoreRegisterOperation.RegisterHandle(registerNum).getStringValue(0);
			} else {
				catchName = action.getCatchVariable();
			}
			
			buf.append(" catch (").append(catchName).append(") {\n");
			for (Operation operation : catchOperations) {
				buf
				.append(operation.getStringValue(level+1))
				.append(CodeUtil.endOfStatement(operation))
				.append("\n");
			}
			buf
			.append(CodeUtil.getIndent(level))
			.append("}");
		}
		
		if (action.hasFinallyBlock()) {
			buf.append(" finally {\n");
			for (Operation operation : finallyOperations) {
				buf
				.append(operation.getStringValue(level+1))
				.append(CodeUtil.endOfStatement(operation))
				.append("\n");
			}
			buf
			.append(CodeUtil.getIndent(level))
			.append("}");
		}
		
		
		return buf.toString();
	}
	
	private void printActions(List<Action> actions) {
		for (Action action : actions) {
			String actionInfo="";
			if (action instanceof Branch) {
				Branch brnch = (Branch) action;
//				Action jumpDestination = labels.get(brnch.getBranchLabel());
				actionInfo += " branch label = "+brnch.getBranchLabel() +", branch offset = "+ brnch.getBranchOffset()/*+"("+ ( jumpDestination!=null ? offsetFormat.sprintf( jumpDestination.getOffset() ) : "[null]")+")"*/;
			}
			logger.debug(actionFormat.sprintf(new Object[]{action.getOffset(),(action.getLabel()==null)?"":action.getLabel(),action.getCode(),ActionConstants.getActionName(action.getCode()),actionInfo}));
		}
	}
	
	protected List<Action> prepareTryActions(List<Action> actions) {
		logger.debug("Preparing try-actions");
		for (Action action : actions) {
			if (action instanceof Branch) {
				Branch branch = (Branch) action;
				if (ActionBlock.LABEL_OUT.equals(branch.getBranchLabel())) {
					logger.debug("Action "+action+" is pointing out of the try block. Setting to "+ActionBlock.LABEL_END);
					branch.setBranchLabel(ActionBlock.LABEL_END);
				}
			}
		}
		return actions;
	}

}
