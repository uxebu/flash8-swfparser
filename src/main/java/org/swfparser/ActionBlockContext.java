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
			
			if (ctx.getParentContext()!=null) {
				buf
				.append(getDumpString(ctx.getParentContext()))
				.append("(");
			}
			
			buf.append("#")
			.append(ctx.getTagNum())
			.append(":")
			.append(ctx.getTag().getCode());
			
			// append action block number inside tag
			if (actionBlockNum != NO_ACTION_BLOCK_NUM) {
				buf.append(":").append(actionBlockNum);
			}
			
			if (ctx.getParentContext()!=null) {
				buf.append(")");
			}
			
			return buf.toString();
			
		}
		

		
	}