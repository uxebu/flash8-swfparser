/*
 *   ActionBlockContext.java
 * 	 @Author Oleg Gorobets
 *   Created: 31.08.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

/**
 *
 */
package org.swfparser;


import com.jswiff.swfrecords.ButtonCondAction;
import com.jswiff.swfrecords.tags.DefineButton2;
import com.jswiff.swfrecords.tags.DefineSprite;
import com.jswiff.swfrecords.tags.DefinitionTag;
import com.jswiff.swfrecords.tags.TagConstants;
import com.uxebu.swfparser.dump.actions.ButtonCondActionFlagMapper;
import com.uxebu.swfparser.dump.actions.ButtonCondActionFlag;
import com.uxebu.swfparser.dump.output.FileWriter;

public class ActionBlockContext extends TagContext
{

    public static final int NO_ACTION_BLOCK_NUM = -1;
    /**
     * Parent context for composite tags (e.g. DEFINE_SPRITE)
     */
    private ActionBlockContext parentContext;
    /**
     * Action block number inside tag (for DEFINE_BUTTON_2, PLACE_OBJECT_2, PLACE_OBJECT_3)
     */
    private int actionBlockNum = NO_ACTION_BLOCK_NUM;
    private FileWriter fileWriter;

    public ActionBlockContext(FileWriter fileWriter)
    {
        this.fileWriter = fileWriter;
    }

    public ActionBlockContext getParentContext()
    {
        return parentContext;
    }

    public void setParentContext(ActionBlockContext parentContext)
    {
        this.parentContext = parentContext;
    }

    public int getActionBlockNum()
    {
        return actionBlockNum;
    }

    public void setActionBlockNum(int actionBlockNum)
    {
        this.actionBlockNum = actionBlockNum;
    }

    public String getDumpString()
    {
        return getDumpString(this);

    }

    private String getDumpString(ActionBlockContext ctx)
    {

        StringBuffer buf = new StringBuffer();

        buf.append("var tag = '")
                .append(TagConstants.getTagName(ctx.getTag().getCode()))
                .append("';\n");

        if (ctx.getTag() instanceof DefinitionTag)
        {
            buf.append("var characterId = ")
                    .append(((DefinitionTag) ctx.getTag()).getCharacterId())
                    .append(";\n");
        }

        if (ctx.getTag() instanceof DefineButton2 && actionBlockNum >= 0)
        {
            DefineButton2 defineButton2 = (DefineButton2) ctx.getTag();
            ButtonCondAction buttonCondAction = defineButton2.getActions()[actionBlockNum];

            printActivatedFlag(buttonCondAction, buf);
        }

        if (ctx.getParentContext() != null)
        {
            if (ctx.getParentContext().getTag() instanceof DefineSprite)
            {
                int characterId = ((DefineSprite) ctx.getParentContext().getTag()).getCharacterId();

                buf.append("var characterId = ")
                        .append(characterId)
                        .append(";\n");

                buf.append("var frameNumber = ")
                        .append(ctx.getFrameNum())
                        .append(";\n");
            }
        }

        return buf.toString();
    }

    private void printActivatedFlag(ButtonCondAction buttonAction, StringBuffer buf)
    {
        ButtonCondActionFlagMapper mapper = new ButtonCondActionFlagMapper(buttonAction);

        for (ButtonCondActionFlag buttonCondActionFlag : mapper.map())
        {
            buf.append("   var action = '").append(buttonCondActionFlag).append("';\n");
        }
    }
}
