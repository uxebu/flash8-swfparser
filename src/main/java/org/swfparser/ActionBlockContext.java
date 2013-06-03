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
import com.jswiff.swfrecords.tags.DefinitionTag;
import com.jswiff.swfrecords.tags.TagConstants;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ActionBlockContext extends TagContext {
		
		public static final int NO_ACTION_BLOCK_NUM = -1;
		
		/**
		 * Parent context for composite tags (e.g. DEFINE_SPRITE)
		 */
		private ActionBlockContext parentContext;
		
		
		/**
		 * Action block number inside tag (for DEFINE_BUTTON_2, PLACE_OBJECT_2, PLACE_OBJECT_3)
		 */
		private int actionBlockNum = NO_ACTION_BLOCK_NUM;

		public ActionBlockContext getParentContext() {
			return parentContext;
		}


		public void setParentContext(ActionBlockContext parentContext) {
			this.parentContext = parentContext;
		}


		public int getActionBlockNum() {
			return actionBlockNum;
		}


		public void setActionBlockNum(int actionBlockNum) {
			this.actionBlockNum = actionBlockNum;
		}
		
		public String getDumpString() {
			return getDumpString(this);
			
		}
		
		private String getDumpString(ActionBlockContext ctx) {
			
			StringBuffer buf = new StringBuffer();

            buf.append("   var tag = '")
               .append(TagConstants.getTagName(ctx.getTag().getCode()))
               .append("';\n");

            if (ctx.getTag() instanceof DefinitionTag)
            {
                buf.append("   var characterId = ")
                        .append(((DefinitionTag) ctx.getTag()).getCharacterId())
                        .append(";\n");
            }

            if (ctx.getTag() instanceof DefineButton2 && actionBlockNum >= 0)
            {
                DefineButton2 defineButton2 = (DefineButton2) ctx.getTag();
                ButtonCondAction buttonCondAction = defineButton2.getActions()[actionBlockNum];

                printActivatedFlag(buttonCondAction, buf);
            }

			return buf.toString();
		}


        private void printActivatedFlag(ButtonCondAction buttonAction, StringBuffer buf)
        {
            List<String> result = new ArrayList<String>();

            if (buttonAction.isOutDownToIdle())
            {
                result.add("isOutDownToIdle");
            }
            if (buttonAction.isOutDownToOverDown())
            {
                result.add("isOutDownToOverDown");
            }
            if (buttonAction.isIdleToOverDown())
            {
                result.add("isIdleToOverDown");
            }
            if (buttonAction.isOverDownToOutDown())
            {
                result.add("isOverDownToOutDown");
            }
            if (buttonAction.isOverDownToIdle())
            {
                result.add("isOverDownToIdle");
            }
            if (buttonAction.isOverUpToOverDown())
            {
                result.add("isOverUpToOverDown");
            }
            if (buttonAction.isOverDownToOverUp())
            {
                result.add("isOverDownToOverUp");
            }
            if (buttonAction.isOverUpToIdle())
            {
                result.add("isOverUpToIdle");
            }
            if (buttonAction.isIdleToOverUp())
            {
                result.add("isIdleToOverUp");
            }

            for (String flag : result)
            {
                buf.append("   var ").append(flag).append(" = true;\n");
            }
        }
}