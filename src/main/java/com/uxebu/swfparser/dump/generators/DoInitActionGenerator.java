package com.uxebu.swfparser.dump.generators;

import com.jswiff.swfrecords.actions.ActionBlockReader;
import com.jswiff.swfrecords.tags.DefineSprite;
import com.jswiff.swfrecords.tags.DoAction;
import com.jswiff.swfrecords.tags.DoInitAction;
import com.uxebu.swfparser.dump.layout.LayoutManager;
import org.swfparser.ActionBlockContext;
import org.swfparser.CodeUtil;
import org.swfparser.ExecutionContext;
import org.swfparser.Operation;
import org.swfparser.StatementBlock;
import org.swfparser.exception.StatementBlockException;
import org.swfparser.operation.ByteCodeOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DoInitActionGenerator implements CodeGenerator
{
    private LayoutManager layoutManager;

    public DoInitActionGenerator(LayoutManager layoutManager)
    {
        this.layoutManager = layoutManager;
    }

    @Override
    public void generate(ActionBlockContext context)
    {
        DoInitAction doAction = (DoInitAction) context.getTag();
        processActions(context, doAction.getInitActions());
    }

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
            operations = new ArrayList<Operation>();
            operations.add(new ByteCodeOperation(actionBlock.getData()));
        }

        processOperations(context, operations);
    }

    protected void processOperations(ActionBlockContext context, List<Operation> operations)
    {
        StringBuffer block = new StringBuffer();

//        block.append(context.getDumpString());
//        block.append("\n");

        for (Operation op : operations)
        {
            String endOfStatement = CodeUtil.endOfStatement(op);
            String writeOp = op.getStringValue(0) + endOfStatement + "\n";
            block.append(writeOp);
        }

        if (operations.size() > 0)
        {
            layoutManager.addInitClip(((DoInitAction) context.getTag()).getSpriteId(), block.toString());
        }
    }
}
