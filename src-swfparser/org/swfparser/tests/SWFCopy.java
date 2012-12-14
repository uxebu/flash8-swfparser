/*
 * JSwiff is an open source Java API for Macromedia Flash file generation
 * and manipulation
 *
 * Copyright (C) 2004-2005 Ralf Terdic (contact@jswiff.com)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package org.swfparser.tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.swfparser.util.PrintfFormat;

import org.apache.log4j.Logger;

import com.jswiff.SWFDocument;
import com.jswiff.SWFReader;
import org.swfparser.CodeUtil;
import org.swfparser.StatementBlock;
import org.swfparser.exception.StatementBlockException;
import com.jswiff.listeners.SWFDocumentReader;
import com.jswiff.swfrecords.ButtonCondAction;
import com.jswiff.swfrecords.ClipActionRecord;
import com.jswiff.swfrecords.actions.Action;
import com.jswiff.swfrecords.actions.ActionConstants;
import com.jswiff.swfrecords.tags.DefineButton;
import com.jswiff.swfrecords.tags.DefineButton2;
import com.jswiff.swfrecords.tags.DoAction;
import com.jswiff.swfrecords.tags.DoInitAction;
import com.jswiff.swfrecords.tags.PlaceObject2;
import com.jswiff.swfrecords.tags.PlaceObject3;
import com.jswiff.swfrecords.tags.Tag;
import com.jswiff.swfrecords.tags.TagConstants;

/**
 * Parses an SWF file and writes it to another file
 */
public class SWFCopy {
	/**
	 * Main method.
	 * 
	 * @param args
	 *            arguments: source and destination file
	 * 
	 * @throws IOException
	 *             if an I/O error occured
	 */

	private static Logger logger = Logger.getLogger(SWFCopy.class);
	
	private static Map<Integer,Integer> usage = new HashMap<Integer, Integer>();
	private static Comparator<Map.Entry<Integer,Integer>> usageComparator = new Comparator<Map.Entry<Integer,Integer>>() {

		public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
			return o2.getValue() - o1.getValue();
		}
	};

	public static void main(String[] args) throws IOException {
		args = new String[2];
		args[0] = "C:\\!\\1\\simple.swf";
		args[1] = args[0] + ".out.swf";

		SWFReader reader = new SWFReader(new FileInputStream(args[0]));
		// SWFReader reader = new FilteredSWFReader(new
		// FileInputStream(args[0]),"");

		// SWFDocumentReader docReader = new SWFDocumentReader();
		SWFDocumentReader docReader = new SWFDocumentReader();

		reader.addListener(docReader);
		reader.read();
		SWFDocument doc = docReader.getDocument();

		List<Tag> tags = doc.getTags();
		StatementBlock statementBlock = CodeUtil.getStatementBlockReader();
//		ExecutionContext executionContext = CodeUtil.getExecutionContext();
		statementBlock.setExecutionContext(CodeUtil.getExecutionContext());
		
		for (Tag tag : tags) {
			logger.debug("#");
			logger.debug("#");
			logger.debug("# TAG "+tag+" ("+tag.getCode()+")");
			logger.debug("#");
			logger.debug("#");
			
			int frameNum = 0;
			try {
				
				switch (tag.getCode()) {
				
					case TagConstants.SHOW_FRAME:
						frameNum++;
						break;
					
				
					case TagConstants.PLACE_OBJECT_2:
						PlaceObject2 placeObject2 = (PlaceObject2) tag;
						if (placeObject2.getClipActions()!=null) {
							List<ClipActionRecord> actionRecords = placeObject2.getClipActions().getClipActionRecords();
							for (ClipActionRecord record : actionRecords) {
								List<Action> actions = record.getActions().getActions();
								process(actions);
							}
						}
						break;
					
					case TagConstants.PLACE_OBJECT_3:
						PlaceObject3 placeObject3 = (PlaceObject3) tag;
						if (placeObject3.getClipActions()!=null) {
							List<ClipActionRecord> actionRecords = placeObject3.getClipActions().getClipActionRecords();
							for (ClipActionRecord record : actionRecords) {
								List<Action> actions = record.getActions().getActions();
								process(actions);
							}
						}
						break;
				
					case TagConstants.DEFINE_BUTTON:
						DefineButton defineButton = (DefineButton) tag;
						List<Action> actions = defineButton.getActions().getActions();
						process(actions);
						break;
						
					case TagConstants.DEFINE_BUTTON_2:
						DefineButton2 defineButton2 = (DefineButton2) tag;
						ButtonCondAction[] buttonActions = defineButton2.getActions();
						if (buttonActions!=null) {
							for (ButtonCondAction buttonAction : buttonActions) {
								actions = buttonAction.getActions().getActions();
								process(actions);
							}
						}
						break;
					
					case TagConstants.DO_ACTION:
						DoAction doAction = (DoAction) tag;
						actions = doAction.getActions().getActions();
						process(actions);
						break;
					case TagConstants.DO_INIT_ACTION:
						DoInitAction doInitAction = (DoInitAction) tag;
						actions = doInitAction.getInitActions().getActions();
						process(actions);
						break;
					
					default:
						// do nothing proceed to next tag
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			// tag.
		} // for
		
		// sort usage
		List<Map.Entry<Integer, Integer>> list = new ArrayList(usage.entrySet());
		Collections.sort(list, usageComparator);
		for (Map.Entry<Integer, Integer> entry : list) {
			Integer actionCode = entry.getKey();
			String name = ActionConstants.getActionName(actionCode);
			if (name==null) {
				name = "";
			}
				logger.debug(
						
						actionFormat.sprintf(new Object[]{actionCode,name}) +
						entry.getValue()
						);
		}

//		SWFWriter writer = new FilteredSWFWriter(doc, new FileOutputStream(args[1]));
//		writer.write();
	}


	private static PrintfFormat actionFormat = new PrintfFormat("A:0x%02X (%s)");
		
	private static void process(List<Action> actions) throws StatementBlockException {
		StatementBlock statementBlock = CodeUtil.getStatementBlockReader();
		statementBlock.setExecutionContext(CodeUtil.getExecutionContext());
		statementBlock.read(actions);
		
//		countUsage(actions);
		
	}
	
//	private static void countUsage(List<Action> actions) {
//		for (Action action : actions) {
//			int actionCode = (int)action.getCode();
//			if (!usage.containsKey(actionCode)) {
//				usage.put(actionCode, 1);
//			} else {
//				int usageCount = usage.get(actionCode);
//				usage.put(actionCode,usageCount+1);
//			}
//		}
//		
//	}
//
//	public static class ActionInfo {
//		private Action action;
//		private int useCount = 0; 
//	}

}
