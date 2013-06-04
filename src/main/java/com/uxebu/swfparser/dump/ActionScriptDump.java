/*
 *   SWFDumpActionScript.java
 * 	 @Author Oleg Gorobets
 *   Created: 30.08.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package com.uxebu.swfparser.dump;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.uxebu.swfparser.dump.layout.LayoutManager;
import org.apache.log4j.Logger;

import org.swfparser.ActionBlockContext;
import org.swfparser.CodeUtil;
import org.swfparser.ExecutionContext;
import org.swfparser.Operation;
import org.swfparser.StatementBlock;
import org.swfparser.TagContext;
import org.swfparser.exception.StatementBlockException;
import org.swfparser.operation.ByteCodeOperation;
import com.jswiff.swfrecords.actions.ActionBlockReader;

public class ActionScriptDump extends ASTagsProcessor {

	private static Logger logger = Logger.getLogger(ActionScriptDump.class);

    private String outputDirectory;

    public ActionScriptDump(LayoutManager layoutManager, String swfFileName) {
		super(swfFileName);

        outputDirectory = swfFileName + "-out";

        File newDirectory = new File(outputDirectory);

        if (!newDirectory.exists())
        {
            newDirectory.mkdirs();
        }
	}

    @Override
	protected void processActions(ActionBlockContext context, ActionBlockReader actionBlock) {
		StatementBlock statementBlock = CodeUtil.getStatementBlockReader();
		ExecutionContext executionContext = CodeUtil.getExecutionContext();
		executionContext.setTag(context.getTag());
		executionContext.setFrameNumber(context.getFrameNum());
		statementBlock.setExecutionContext(executionContext);
		List<Operation> operations;
		try {
			statementBlock.read(actionBlock.getActions());
			operations = statementBlock.getOperations();
		} catch (StatementBlockException e) {
			logger.error("Error reading action block.", e);
			operations = new ArrayList<Operation>();
			operations.add(new ByteCodeOperation(actionBlock.getData()));
		}

		processOperations(context, operations);
	}

	protected void processOperations(ActionBlockContext context, List<Operation> operations) {
		try {
            FileOutputStream fileOutputStream = new FileOutputStream(outputDirectory + "/frame-" + context.getFrameNum() + ".js");

            Writer writer = new OutputStreamWriter(fileOutputStream, "UTF-8");

			// write header
            writer.write("(function() {\n");

            writer.write(context.getDumpString());
            writer.write("\n");

            writer.write("   return function() {\n");

			for (Operation op : operations) {
				String endOfStatement = CodeUtil.endOfStatement(op);
				String writeOp = op.getStringValue(0) + endOfStatement + "\n";
                writer.write("      " + writeOp);
			}

            writer.write("   };\n");
            writer.write("})();\n");

            writer.close();
            fileOutputStream.close();

		} catch (IOException e) {
			logger.error("Error writing data ", e);
		}
	}

	@Override
	protected void processNonActionTag(TagContext context) {
		// TODO Auto-generated method stub
		
	}

}
