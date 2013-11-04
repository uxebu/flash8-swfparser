/*
 *   DefineFunctionOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 24.07.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import org.swfparser.AutoSizeArrayList;
import org.swfparser.CodeUtil;
import org.swfparser.ExecutionContext;
import org.swfparser.Operation;
import org.swfparser.PatternAnalyzerEx;
import org.swfparser.annotations.NewAnalyzer;
import org.swfparser.exception.StatementBlockException;
import com.jswiff.swfrecords.RegisterParam;
import com.jswiff.swfrecords.actions.DefineFunction2;
import com.jswiff.swfrecords.actions.StackValue;

@NewAnalyzer
public class DefineFunction2Operation extends AbstractCompoundOperation {

	private static Logger logger = Logger.getLogger(DefineFunction2Operation.class);
	
	private List<Operation> operations;
	private DefineFunction2 thisFunction;
	
	public DefineFunction2Operation(Stack<Operation> stack, DefineFunction2 action, ExecutionContext context) throws StatementBlockException {
		super(context);
		thisFunction = action;

		short registerCount = action.getRegisterCount();
		RegisterParam[] parameters = action.getParameters();
		
		List<Operation> registers = new AutoSizeArrayList<Operation>();

		for (int j = 0; j < parameters.length; j++) {
			RegisterParam registerParam = parameters[j];
			logger.debug("registerParam = "+registerParam +" "+registerParam.getClass().getName());
			registers.set(registerParam.getRegister(), new FunctionParameterOperation(registerParam));
		}
		
		/////
		logger.debug("name = " + action.getName());
		logger.debug("registerCount = " + registerCount + ", parameters.length=" + parameters.length);
		
		logger.debug("action.preloadsThis()=" + action.preloadsThis());
		int preloadVariableIndex = 1;
		if (action.preloadsThis()) {
			registers.set(preloadVariableIndex++, new StackValue("this"));
		}
		
		logger.debug("action.preloadsArguments()="+action.preloadsArguments());
		if (action.preloadsArguments()) {
			registers.set(preloadVariableIndex++, new StackValue("arguments"));
		}
		
		logger.debug("action.preloadsSuper()="+action.preloadsSuper());
		if (action.preloadsSuper()) {
            registers.set(preloadVariableIndex++, new StackValue("____super____method____"));
		}

		logger.debug("action.preloadsRoot()="+action.preloadsRoot());
		if (action.preloadsRoot()) {
			registers.set(preloadVariableIndex++, new StackValue("_root"));
		}
		
		logger.debug("action.preloadParent()="+action.preloadsParent());
		if (action.preloadsParent()) {
			registers.set(preloadVariableIndex++, new StackValue("_parent"));
		}
		
		logger.debug("action.preloadsGlobal()="+action.preloadsGlobal());
		if (action.preloadsGlobal()) {
			registers.set(preloadVariableIndex++, new StackValue("_global"));
		}

		
		//
		// Create new execution context
		//
        List<String> constants = context.getConstants();
		context = CodeUtil.getExecutionContext();
        context.setConstants(constants);
//		ExecutionContext newContext = CodeUtil.getExecutionContext();
//		newContext.setOp
		
		context.getOperationStack().push(this);
		List<Operation> currentRegisters = context.getRegisters();
		for (int j=0;j<registers.size();j++) {
			currentRegisters.set(j, registers.get(j));
		}
		
		// save execution stack before calling function2
		Stack<Operation> executionStack = context.getExecStack();
		
		// save labels before calling function2
//		Map<String,Action> labels = context.getLabels();
//		PatternAnalyzer patternAnalyzer = context.getPatternAnalyzer();
		PatternAnalyzerEx patternAnalyzer = context.getPatternAnalyzerEx();
		
		// create new execution stack and labels stack
		context.setExecStack( createEmptyExecutionStack() );
//		context.setLabels(new HashMap<String,Action>());
		context.setPatternAnalyzerEx(null);
		
		statementBlock.setExecutionContext(context);
		statementBlock.read(action.getBody().getActions());
		operations = statementBlock.getOperations();
		
		// restore execution stack
		context.setExecStack(executionStack);
		
		// restore labels
//		context.setLabels(labels);
		context.setPatternAnalyzerEx(patternAnalyzer);

		
		context.getOperationStack().pop();
	}

	public int getArgsNumber() {
		return 0;
	}

	public String getStringValue(int level) {
		StringBuffer buf = new StringBuffer();
		if (org.springframework.util.StringUtils.hasText(thisFunction.getName())) {
			buf
			.append(CodeUtil.getIndent(level))
			.append("function ")
			.append(thisFunction.getName());
		} else {
			buf
//			.append(CodeUtil.getIndent(level))
			.append("function");
		}


        List<String> params = new ArrayList<>();
        for (RegisterParam param: thisFunction.getParameters()) {
            params.add(param.getParamName());
        }


        buf.append("(");
        buf.append(StringUtils.join(params, ","));
		buf.append(")")
			.append("{\n");

        for (Operation op : operations) {
            String stringValue = op.getStringValue(level + 1);
            buf.append(CodeUtil.finalizeRenderedOperation(stringValue, CodeUtil.endOfStatement(op)));
		}
		
		buf.append(CodeUtil.getIndent(level));
		buf.append("}");
		
		return buf.toString();
	}

    @Override
	public String toString() {
		return "DefineFunction2("+thisFunction.getName()+")";
	}
	
}
