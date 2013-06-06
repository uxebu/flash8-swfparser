package com.uxebu.swfparser.dump.generators;

import com.jswiff.swfrecords.ButtonCondAction;
import com.jswiff.swfrecords.actions.ActionBlockReader;
import com.jswiff.swfrecords.tags.DefineButton2;
import com.uxebu.swfparser.dump.actions.ButtonCondActionFlag;
import com.uxebu.swfparser.dump.actions.ButtonCondActionFlagMapper;
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

public class DefineButton2Generator implements CodeGenerator
{
    private LayoutManager layoutManager;
    private DefineButton2 defineButton2;

    public DefineButton2Generator(LayoutManager layoutManager)
    {
        this.layoutManager = layoutManager;
    }

    public void generate(ActionBlockContext context)
    {
        defineButton2 = (DefineButton2) context.getTag();
        ButtonCondAction[] buttonActions = defineButton2.getActions();
        context.setActionBlockNum(0);

        if (buttonActions != null)
        {
            for (ButtonCondAction buttonAction : buttonActions)
            {
                processActions(context, buttonAction.getActions());
                context.setActionBlockNum(context.getActionBlockNum() + 1);
            }
        }

        context.setActionBlockNum(ActionBlockContext.NO_ACTION_BLOCK_NUM);
    }

    private void processActions(ActionBlockContext context, ActionBlockReader actionBlock)
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

    private void processOperations(ActionBlockContext context, List<Operation> operations)
    {
        StringBuffer sourceCode = new StringBuffer();

        for (Operation op : operations)
        {
            String endOfStatement = CodeUtil.endOfStatement(op);
            String writeOp = op.getStringValue(0) + endOfStatement + "\n";
            sourceCode.append(writeOp);
        }

        int actionBlockNumber = context.getActionBlockNum();

        ButtonCondAction buttonCondAction = defineButton2.getActions()[actionBlockNumber];
        ButtonCondActionFlagMapper mapper = new ButtonCondActionFlagMapper(buttonCondAction);

        for (ButtonCondActionFlag buttonCondActionFlag : mapper.map())
        {
            layoutManager.addButton(defineButton2.getCharacterId(), buttonCondActionFlag, sourceCode.toString());
        }
    }
}
