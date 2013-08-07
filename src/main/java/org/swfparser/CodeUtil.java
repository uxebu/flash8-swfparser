/*
 *   CodeUtil.java
 * 	 @Author Oleg Gorobets
 *   Created: 24.07.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.jswiff.swfrecords.actions.StackValue;
import org.swfparser.operation.GetMemberOperation;

public class CodeUtil {
	
	private static ApplicationContext appContext = null;
	
	private static final String[] SPRING_CONFIGS =  {
		"classpath*:*.xml",
	};
	
	public static int INDENT = 4;
	
	public static String getIndent(int level) {
		int spaceSize = level*INDENT;
		StringBuffer buf = new StringBuffer();
		for (int j=0;j<spaceSize;j++) {
			buf.append(" ");
		}
		return buf.toString();
	}
	
	public static final String STATEMENT_BLOCK_READER_BEAN = "statementBlockReader";
	public static final String EXECUTION_CONTEXT_BEAN = "executionContext";
	
	public static StatementBlock getStatementBlockReader() {
		return (StatementBlock) getApplicationContext().getBean(STATEMENT_BLOCK_READER_BEAN);
	}
	
	public static ExecutionContext getExecutionContext() {
		return (ExecutionContext) getApplicationContext().getBean(EXECUTION_CONTEXT_BEAN);
	}
	
	public static synchronized ApplicationContext getApplicationContext() {
        if (appContext==null) {
        	appContext = new FileSystemXmlApplicationContext(SPRING_CONFIGS);
        }
        return appContext;
    }
	
	public int getPrintIndent() {
		return 4;
	}
	
	public static String endOfStatement(Operation op) {
		return (op.getStringValue(0).endsWith("}")) ? "" :";";
	}
	
	public static String getSimpleValue(Operation op, int level) {
		return (op instanceof StackValue 
				&& StackValue.TYPE_STRING == ((StackValue)op).getType()) 
				
				? ((StackValue)op).getString() 
						
					: op.getStringValue(level);
	}

    public static String getMemberGetExpression(Operation member, int level) {
        StringBuilder buf = new StringBuilder();

        String memberName = CodeUtil.getSimpleValue(member, level);
        boolean useBrackets = !(member instanceof StackValue && StackValue.TYPE_STRING == ((StackValue) member).getType());
        useBrackets = useBrackets || (member instanceof GetMemberOperation);
        if (useBrackets) {
            buf.append("[").append(memberName).append("]");
        } else {
            boolean containsSpecialChars = !memberName.matches("[a-zA-Z][a-zA-Z0-9]+");
            if (containsSpecialChars) {
                boolean containsSingleQuote = memberName.contains("'");
                boolean containsDoubleQuote = memberName.contains("\"");
                if (containsSingleQuote && containsDoubleQuote) {
                    buf.append("['").append(memberName.replace("'", "\\'")).append("']");
                } else if (containsSingleQuote) {
                    buf.append("[\"").append(memberName).append("\"]");
                } else {
                    buf.append("['").append(memberName).append("']");
                }
            } else {
                buf.append(".").append(memberName);
            }
        }

        return buf.toString();
    }
	

}
