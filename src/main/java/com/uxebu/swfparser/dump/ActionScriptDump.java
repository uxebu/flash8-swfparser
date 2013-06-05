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

import com.jswiff.swfrecords.tags.DefineSprite;
import com.jswiff.swfrecords.tags.DefinitionTag;
import com.uxebu.swfparser.dump.assets.AssetManager;
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

public class ActionScriptDump extends ActionScriptTagProcessor
{
    private static Logger logger = Logger.getLogger(ActionScriptDump.class);

    public ActionScriptDump(LayoutManager layoutManager, AssetManager assetManager, String fileName)
    {
        super(layoutManager, assetManager, fileName);
    }

    @Override
    protected void processActions(ActionBlockContext context, ActionBlockReader actionBlock)
    {
        StatementBlock statementBlock = CodeUtil.getStatementBlockReader();
        ExecutionContext executionContext = CodeUtil.getExecutionContext();
        executionContext.setTag(context.getTag());
        executionContext.setFrameNumber(context.getFrameNum());
        statementBlock.setExecutionContext(executionContext);
        List<Operation> operations;
        try
        {
            statementBlock.read(actionBlock.getActions());
            operations = statementBlock.getOperations();
        }
        catch (StatementBlockException e)
        {
            logger.error("Error reading action block.", e);
            operations = new ArrayList<Operation>();
            operations.add(new ByteCodeOperation(actionBlock.getData()));
        }

        processOperations(context, operations);
    }

    protected void processOperations(ActionBlockContext context, List<Operation> operations)
    {
        StringBuffer block = new StringBuffer();
        block.append("(function() {\n");

        block.append(context.getDumpString());
        block.append("\n");

        block.append("   return function() {\n");

        for (Operation op : operations)
        {
            String endOfStatement = CodeUtil.endOfStatement(op);
            String writeOp = op.getStringValue(0) + endOfStatement + "\n";
            block.append("      " + writeOp);
        }

        block.append("   };\n");
        block.append("})();\n");

        if (context.getTag() instanceof DefinitionTag)
        {
            int characterId = ((DefinitionTag) context.getTag()).getCharacterId();
            layoutManager.addButton(characterId, block.toString());
        }

        if (context.getParentContext() != null && operations.size() > 0)
        {
            if (context.getParentContext().getTag() instanceof DefineSprite)
            {
                int characterId = ((DefineSprite) context.getParentContext().getTag()).getCharacterId();
                layoutManager.addSprite(characterId, context.getFrameNum(), block.toString());
            }
        }
    }

    @Override
    protected void processNonActionTag(TagContext context)
    {
    }
}
