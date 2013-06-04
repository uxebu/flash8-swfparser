/*
 *   ASTagsProcessor.java
 * 	 @Author Oleg Gorobets
 *   Created: 16.08.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package com.uxebu.swfparser.dump;

import java.io.FileInputStream;
import java.util.List;

import org.swfparser.ActionBlockContext;
import org.swfparser.TagContext;

import org.apache.log4j.Logger;

import com.jswiff.SWFDocument;
import com.jswiff.SWFReader;
import com.jswiff.listeners.AllTagDocumentReader;
import com.jswiff.listeners.SWFDocumentReader;
import com.jswiff.swfrecords.ButtonCondAction;
import com.jswiff.swfrecords.ClipActionRecord;
import com.jswiff.swfrecords.actions.ActionBlockReader;
import com.jswiff.swfrecords.tags.DefineButton;
import com.jswiff.swfrecords.tags.DefineButton2;
import com.jswiff.swfrecords.tags.DefineSprite;
import com.jswiff.swfrecords.tags.DoAction;
import com.jswiff.swfrecords.tags.DoInitAction;
import com.jswiff.swfrecords.tags.PlaceObject2;
import com.jswiff.swfrecords.tags.PlaceObject3;
import com.jswiff.swfrecords.tags.Tag;
import com.jswiff.swfrecords.tags.TagConstants;

public abstract class ASTagsProcessor {
	
	private static Logger logger = Logger.getLogger(ASTagsProcessor.class);
	
	protected SWFDocument doc;
	
	public ASTagsProcessor(String swfFileName) {
		super();
		try {
			logger.debug("Reading "+swfFileName);
			SWFReader reader = new SWFReader(new FileInputStream(swfFileName));
			SWFDocumentReader docReader = new AllTagDocumentReader();
			reader.addListener(docReader);
			reader.read();
			this.doc = docReader.getDocument();
		} catch (Exception e) {
			logger.error("#ASTagsProcessor()",e);
			throw new RuntimeException(e);
		}
	}
	
	public void process() {
		logger.debug("Processing tags...");
		List<Tag> tags = doc.getTags();
		try {
			ActionBlockContext context = new ActionBlockContext();
			context.setDocument(doc);
			processTags(tags, context);
		} catch (Exception e) {
			logger.error("Error opening writer ",e);
		}
	}
	
	public void processTags(List<Tag> tags, ActionBlockContext context) {
			
//		boolean isRootContext = context.getParentContext() == null;
		
		context.setTagNum(0);
		for (Tag tag : tags) {
			
			context.setTag(tag);
			
			logger.debug("#");
			logger.debug("#");
			logger.debug("# TAG " + tag + " (" + tag.getCode() + ") "+context.getDumpString());
			logger.debug("#");
			logger.debug("#");
			
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
			
			context.setTagNum(context.getTagNum()+1);
			
			// tag.
		} // for

	}
	
	protected abstract void processActions(ActionBlockContext context, ActionBlockReader actionBlock);
	
	protected abstract void processNonActionTag(TagContext context);
	
}
