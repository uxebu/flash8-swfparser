package com.uxebu.swfparser.dump.generators;

import com.jswiff.swfrecords.actions.ActionBlockReader;
import com.jswiff.swfrecords.tags.DoAction;
import com.uxebu.swfparser.dump.output.Writer;
import org.swfparser.ActionBlockContext;
import org.swfparser.CodeUtil;
import org.swfparser.ExecutionContext;
import org.swfparser.Operation;
import org.swfparser.StatementBlock;
import org.swfparser.exception.StatementBlockException;
import org.swfparser.operation.ByteCodeOperation;

import java.util.ArrayList;
import java.util.List;

public class DoRootMovieActionGenerator implements CodeGenerator
{
    private Writer writer;

    public DoRootMovieActionGenerator(Writer writer)
    {
        this.writer = writer;
    }

    @Override
    public void generate(ActionBlockContext context)
    {
        DoAction doAction = (DoAction) context.getTag();
        processActions(context, doAction.getActions());
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

        if (operations.size() > 0 && context.getParentContext() == null)
        {
            writer.addRootMovie(context.getFrameNum(), block.toString());
        }
    }
}
