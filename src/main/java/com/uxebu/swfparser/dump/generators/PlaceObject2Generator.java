package com.uxebu.swfparser.dump.generators;

import com.jswiff.swfrecords.ClipActionRecord;
import com.jswiff.swfrecords.ClipEventFlags;
import com.jswiff.swfrecords.actions.ActionBlockReader;
import com.jswiff.swfrecords.tags.DefineSprite;
import com.jswiff.swfrecords.tags.PlaceObject2;
import com.uxebu.swfparser.dump.actions.ClipActionFlag;
import com.uxebu.swfparser.dump.actions.ClipActionFlagMapper;
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

public class PlaceObject2Generator implements CodeGenerator
{
    private Writer writer;
    private PlaceObject2 placeObject2;
    private int frameNo;

    public PlaceObject2Generator(Writer writer)
    {
        this.writer = writer;
    }

    @Override
    public void generate(ActionBlockContext context)
    {
        placeObject2 = (PlaceObject2) context.getTag();
        frameNo = context.getFrameNum();

        if (placeObject2.getClipActions() != null)
        {
            List<ClipActionRecord> actionRecords = placeObject2.getClipActions().getClipActionRecords();
            context.setActionBlockNum(0);

            for (ClipActionRecord record : actionRecords)
            {
                processActions(context, record.getActions());
                context.setActionBlockNum(context.getActionBlockNum() + 1);
            }

            context.setActionBlockNum(ActionBlockContext.NO_ACTION_BLOCK_NUM);
        }
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
            ClipActionRecord clipActionRecord = (ClipActionRecord) placeObject2.getClipActions().getClipActionRecords().get(context.getActionBlockNum());
            ClipEventFlags eventFlags = clipActionRecord.getEventFlags();
            ClipActionFlagMapper mapper = new ClipActionFlagMapper(eventFlags);

            for (ClipActionFlag flag : mapper.map())
            {

                if (context.getParentContext() != null && context.getParentContext().getTag() instanceof DefineSprite)
                {
                    DefineSprite parentTag = (DefineSprite) context.getParentContext().getTag();
                    writer.addSpriteClipAction(parentTag.getCharacterId(), frameNo, placeObject2.getDepth(), flag, block.toString());
                }
                else
                {
                    writer.addRootMovieClipAction(frameNo, placeObject2.getDepth(), flag, block.toString());
                }
            }
        }
    }
}
