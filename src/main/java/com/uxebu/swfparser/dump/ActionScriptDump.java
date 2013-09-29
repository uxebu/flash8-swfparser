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
import com.uxebu.swfparser.dump.output.ConsoleWriter;
import com.uxebu.swfparser.dump.output.FileWriter;
import com.uxebu.swfparser.dump.output.Writer;
import org.apache.log4j.Logger;
import org.swfparser.ActionBlockContext;

import java.util.List;
import java.io.File;

public class ActionScriptDump
{
    private static Logger logger = Logger.getLogger(ActionScriptDump.class);
    protected SWFDocument doc;
    protected Writer writer;

    public ActionScriptDump(Writer writer, AssetManager assetManager, String fileName)
    {
        this(writer, assetManager.getSWFFile(fileName));
    }

    public ActionScriptDump(Writer writer, SWFDocument swfDocument)
    {
        this.writer = writer;
        this.doc = swfDocument;
    }

    public static void main(String[] args) {
        String outputDir = System.getProperty("output", "");
        Writer writer;
        boolean isOutputDirectoryGiven = !outputDir.equals("");
        if (isOutputDirectoryGiven) {
            writer = new FileWriter(outputDir);
        } else {
            writer = new ConsoleWriter();
        }
        File inputFile = new File(args[0]);
        AssetManager assetManager = new AssetManager(inputFile.getParent());
        new ActionScriptDump(writer, assetManager.getSWFFile(inputFile.getName())).process();
    }

    public void process()
    {
        List<Tag> tags = doc.getTags();
        ActionBlockContext context = new ActionBlockContext(writer);
        context.setDocument(doc);
        processTags(context, tags);
    }

    public void processTags(ActionBlockContext context, List<Tag> tags)
    {
        context.setTagNum(0);

        for (Tag tag : tags)
        {
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
                ActionBlockContext newContext = new ActionBlockContext(writer);
                newContext.setParentContext(context);
                //newContext.setFrameNum(context.getFrameNum());
                newContext.setDocument(context.getDocument());
                processTags(newContext, controlTags);
            }
            context.setTagNum(context.getTagNum() + 1);
        }
    }

    private void generateDoPlaceObject2(ActionBlockContext context)
    {
        if (context.getTag() instanceof PlaceObject2)
        {
            CodeGenerator generator = new PlaceObject2Generator(writer);
            generator.generate(context);
        }
    }

    private void generateDoInitAction(ActionBlockContext context)
    {
        if (context.getTag() instanceof DoInitAction)
        {
            CodeGenerator generator = new DoInitActionGenerator(writer);
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
                generator = new DoActionGenerator(writer);
            }
            else
            {
                generator = new DoRootMovieActionGenerator(writer);
            }

            generator.generate(context);
        }

    }

    private void generateDefineButton2(ActionBlockContext context)
    {
        if (context.getTag() instanceof DefineButton2)
        {
            CodeGenerator generator = new DefineButton2Generator(writer);
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
                writer.addSprite(characterId, context.getFrameNum(), block.toString());
            }
        }
    }

    @Override
    protected void processNonActionTag(TagContext context)
    {
    }       */
}
