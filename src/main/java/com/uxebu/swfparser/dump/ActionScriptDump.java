/*
 *   SWFDumpActionScript.java
 * 	 @Author Oleg Gorobets
 *   Created: 30.08.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package com.uxebu.swfparser.dump;

import com.jswiff.SWFDocument;
import com.jswiff.swfrecords.tags.DefineButton2;
import com.jswiff.swfrecords.tags.DefineSprite;
import com.jswiff.swfrecords.tags.DoAction;
import com.jswiff.swfrecords.tags.DoInitAction;
import com.jswiff.swfrecords.tags.PlaceObject2;
import com.jswiff.swfrecords.tags.ShowFrame;
import com.jswiff.swfrecords.tags.Tag;
import com.uxebu.swfparser.dump.assets.AssetManager;
import com.uxebu.swfparser.dump.generators.CodeGenerator;
import com.uxebu.swfparser.dump.generators.DefineButton2Generator;
import com.uxebu.swfparser.dump.generators.DoActionGenerator;
import com.uxebu.swfparser.dump.generators.DoRootMovieActionGenerator;
import com.uxebu.swfparser.dump.generators.DoInitActionGenerator;
import com.uxebu.swfparser.dump.generators.PlaceObject2Generator;
import com.uxebu.swfparser.dump.layout.LayoutManager;
import org.apache.log4j.Logger;
import org.swfparser.ActionBlockContext;

import java.util.List;

public class ActionScriptDump
{
    private static Logger logger = Logger.getLogger(ActionScriptDump.class);
    protected SWFDocument doc;
    protected LayoutManager layoutManager;
    protected AssetManager assetManager;

    public ActionScriptDump(LayoutManager layoutManager, AssetManager assetManager, String fileName)
    {
        this.layoutManager = layoutManager;
        this.assetManager = assetManager;

        this.doc = assetManager.getSWFFile(fileName);
    }

    public void process()
    {
        List<Tag> tags = doc.getTags();
        ActionBlockContext context = new ActionBlockContext(layoutManager);
        context.setDocument(doc);
        processTags(context, tags);
    }

    public void processTags(ActionBlockContext context, List<Tag> tags)
    {
        context.setTagNum(0);

        for (Tag tag : tags)
        {

            /*

			try {

				switch (tag.getCode()) {

				case TagConstants.SHOW_FRAME:
					context.setFrameNum(context.getFrameNum()+1);
					break;

				case TagConstants.PLACE_OBJECT_2:
					PlaceObject2 placeObject2 = (PlaceObject2) tag;
					if (placeObject2.getClipActions() != null) {
						List<ClipActionRecord> actionRecords = placeObject2.getClipActions().getClipActionRecords();
						context.setActionBlockNum(0);
						for (ClipActionRecord record : actionRecords) {
							processActions(context, record.getActions());
							context.setActionBlockNum(context.getActionBlockNum()+1);
						}
						context.setActionBlockNum(ActionBlockContext.NO_ACTION_BLOCK_NUM);
					}
					break;

				case TagConstants.PLACE_OBJECT_3:
					PlaceObject3 placeObject3 = (PlaceObject3) tag;
					if (placeObject3.getClipActions() != null) {
						List<ClipActionRecord> actionRecords = placeObject3.getClipActions().getClipActionRecords();
						context.setActionBlockNum(0);
						for (ClipActionRecord record : actionRecords) {
							processActions(context, record.getActions());
							context.setActionBlockNum(context.getActionBlockNum()+1);
						}
						context.setActionBlockNum(ActionBlockContext.NO_ACTION_BLOCK_NUM);
					}
					break;

				case TagConstants.DEFINE_BUTTON:
					DefineButton defineButton = (DefineButton) tag;
					processActions(context, defineButton.getActions());
					break;

				case TagConstants.DEFINE_BUTTON_2:
					DefineButton2 defineButton2 = (DefineButton2) tag;
					ButtonCondAction[] buttonActions = defineButton2.getActions();
					context.setActionBlockNum(0);
					if (buttonActions != null) {
						for (ButtonCondAction buttonAction : buttonActions) {
							processActions(context, buttonAction.getActions());
							context.setActionBlockNum(context.getActionBlockNum()+1);
						}
					}
					context.setActionBlockNum(ActionBlockContext.NO_ACTION_BLOCK_NUM);
					break;

				case TagConstants.DO_ACTION:
					DoAction doAction = (DoAction) tag;
					processActions(context, doAction.getActions());
					break;
				case TagConstants.DO_INIT_ACTION:
					DoInitAction doInitAction = (DoInitAction) tag;
					processActions(context, doInitAction.getInitActions());
					break;

				case TagConstants.DEFINE_SPRITE:
					DefineSprite sprite = (DefineSprite) tag;
					List<Tag> controlTags = sprite.getControlTags();
					ActionBlockContext newContext = new ActionBlockContext();
					newContext.setParentContext(context);
					newContext.setFrameNum(context.getFrameNum());
					newContext.setDocument(context.getDocument());
					processTags(controlTags, newContext);
					break;

				default:
					// do nothing proceed to next tag
					processNonActionTag(context);
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			*/

            context.setTag(tag);

            generateShowFrame(context);
            generateDefineButton2(context);
            generateDoInitAction(context);
            generateDoAction(context);
            generateDoPlaceObject2(context);

            if (context.getTag() instanceof DefineSprite)
            {
                DefineSprite defineSprite = (DefineSprite) context.getTag();

                List<Tag> controlTags = defineSprite.getControlTags();
                ActionBlockContext newContext = new ActionBlockContext(layoutManager);
                newContext.setParentContext(context);
                newContext.setFrameNum(context.getFrameNum());
                newContext.setDocument(context.getDocument());
                processTags(newContext, controlTags);
            }

            context.setTagNum(context.getTagNum() + 1);

            // tag.
        } // for

    }

    private void generateDoPlaceObject2(ActionBlockContext context)
    {
        if (context.getTag() instanceof PlaceObject2)
        {
            CodeGenerator generator = new PlaceObject2Generator(layoutManager);
            generator.generate(context);
        }
    }

    private void generateDoInitAction(ActionBlockContext context)
    {
        if (context.getTag() instanceof DoInitAction)
        {
            CodeGenerator generator = new DoInitActionGenerator(layoutManager);
            generator.generate(context);
        }
    }

    private void generateShowFrame(ActionBlockContext context)
    {
        if (context.getTag() instanceof ShowFrame)
        {
            context.setFrameNum(context.getFrameNum()+1);
        }
    }

    private void generateDoAction(ActionBlockContext context)
    {
        if (context.getTag() instanceof DoAction)
        {
            CodeGenerator generator;

            if (context.getParentContext() != null)
            {
                generator = new DoActionGenerator(layoutManager);
            }
            else
            {
                generator = new DoRootMovieActionGenerator(layoutManager);
            }

            generator.generate(context);
        }

    }

    private void generateDefineButton2(ActionBlockContext context)
    {
        if (context.getTag() instanceof DefineButton2)
        {
            CodeGenerator generator = new DefineButton2Generator(layoutManager);
            generator.generate(context);
        }
    }

    /*
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
    }       */
}
