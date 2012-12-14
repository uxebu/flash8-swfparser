/*
 *   OptimizedActionBlockReader.java
 * 	 @Author Oleg Gorobets
 *   Created: 24.07.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package com.jswiff.swfrecords.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.swfparser.util.PrintfFormat;
import org.swfparser.util.UnsignedByte;

import org.apache.log4j.Logger;

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

public class OptimizedActionBlockReader extends ActionBlock {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(OptimizedActionBlockReader.class);
	private static  PrintfFormat actionFormat = new PrintfFormat("%02X");
	private byte[] data;
	
	private boolean skipGarbage = false;
	
	public void setSkipGarbage(boolean skipGarbage) {
		this.skipGarbage = skipGarbage;
	}

	private void saveBinaryData(InputBitStream stream) {
		int offset = (int)stream.getOffset();
		try {
			data = stream.readBytes(stream.available());
			stream.reset();
			stream.readBytes(offset);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public byte[] getData() {
		return data;
	}
	
	@Override
	public void read(InputBitStream stream) throws IOException {
//		saveBinaryData(stream);
//		int startOffset = (int) stream.getOffset();
		readBlock(stream);
	}
	
	
	public void readBlock(InputBitStream stream) throws IOException {
		int xStartOffset = (int) stream.getOffset();
		saveBinaryData(stream);
		int startOffset = (int) stream.getOffset();
		logger.debug("===============================================");
		logger.debug("Starting optimized action block with offset "+startOffset);
		logger.debug("===============================================");
		boolean hasEndAction = false;
		
		
		logger.debug("########");
		logger.debug("Pass 1");
		logger.debug("########");
		
		// this will handle all JUMP or IF pointers in absolute
		// from start of action block
		Set<Integer> absoluteLabelOffsets = new HashSet<Integer>();
		while (stream.available() > 0) {
			Action record = ActionReader.readRecord(stream);

			String actionInfo = "(1)ACTION:"+UnsignedByte.hex(record.getOffset()-startOffset)+":"+actionFormat.sprintf(record.getCode())+"("+ActionConstants.getActionName(record.getCode());
			
			if (record.code != ActionConstants.END) {
				actions.add(record);
			} else {
				hasEndAction = true;
				break;
			}

			if (record instanceof Branch) {
				int branchOffset = ((Branch)record).getBranchOffset();
				int absoluteLabelOffset = (record.getOffset()-startOffset) + record.getSize()+ branchOffset ;
				actionInfo+=",branchOffset="+branchOffset+"("+UnsignedByte.hex(absoluteLabelOffset)+")";
				logger.debug("ALO:"+UnsignedByte.hex(absoluteLabelOffset));
				absoluteLabelOffsets.add(absoluteLabelOffset);
			}
			
			logger.debug(actionInfo);
			
			if (record instanceof Jump) {
				short branchOffset = ((Jump) record).getBranchOffset();
				if (branchOffset > 0) {
					int absoluteLabelOffset = (record.getOffset()-startOffset) + record.getSize()+ branchOffset;
					
					boolean writeGarbageAction = true;
					int garbageActionSize = branchOffset;
					
					// check whether there is a label in interval
					// (record.getOffset()-startOffset) + record.getSize() <----> absoluteLabelOffset
					Integer attemptedBranch = null;
					logger.debug("Checking range: "+UnsignedByte.hex((record.getOffset()-startOffset) + record.getSize())+" --- "+UnsignedByte.hex(absoluteLabelOffset));
					for (int j=(record.getOffset()-startOffset) + record.getSize(); j<absoluteLabelOffset; j++) {
						if (absoluteLabelOffsets.contains(new Integer(j))) {
							attemptedBranch=j;
							break;
						}
					}
					
					if (skipGarbage) {
						logger.debug("Skipping next " + branchOffset + "("+UnsignedByte.hex(absoluteLabelOffset)+") bytes as Garbage action");
						if (attemptedBranch!=null) {
							int relativeSkip = (attemptedBranch - (record.getOffset()-startOffset) - record.getSize());
							logger.debug("Attempt to branch is "+attemptedBranch+". Relative skip = "+relativeSkip);
							logger.debug("Finally skipping "+relativeSkip+" byres");
							garbageActionSize = relativeSkip;
							if (relativeSkip==0) {
								logger.debug("Just proceeding to next action...");
								writeGarbageAction=false;
							}
						}
						
						if (writeGarbageAction) {
							logger.debug("Adding garbage action...");
							int garbageActionOffset = (int)stream.getOffset();
							byte[] b = stream.readBytes(garbageActionSize);
							Action garbageAction = new GarbageAction(b);
							garbageAction.setOffset(garbageActionOffset);
							actions.add(garbageAction);
						}
					}
				}
			}
			
			

		}
		if (actions.size() == 0) {
			return;
		}
		
		logger.debug("########");
		logger.debug("Pass 2 - "+UnsignedByte.hex(stream.getOffset()));
		logger.debug("########");

		// end offset (relative to start offset, end action ignored)
		int relativeEndOffset = (int) stream.getOffset() - startOffset - (hasEndAction ? 1 : 0);
		
		logger.debug("relativeEndOffset = "+UnsignedByte.hex(relativeEndOffset));

		// correct offsets, setting to relative to first action (not to start of
		// stream)
		// also, populate the label map with integers containing the
		// corresponding offsets
		int labelCounter = 0;
		Map actionMap = new HashMap(); // contains offset->action mapping
		for (int i = 0; i < actions.size(); i++) {
			Action action = (Action) actions.get(i);
			int newOffset = action.getOffset() - startOffset;
			action.setOffset(newOffset);
			actionMap.put(new Integer(newOffset), action);
			
			String actionInfo = "(2)ACTION:"+UnsignedByte.hex(newOffset)+":"+actionFormat.sprintf(action.getCode())+"("+ActionConstants.getActionName(action.getCode()); 
			
			
			// collect labels from Jump and If actions
			if ((action.getCode() == ActionConstants.IF) || (action.getCode() == ActionConstants.JUMP)) {
				Branch branchAction = (Branch) action;
				
				// temporarily put the offset into the label map
				// later on, the offset will be replaced with the corresponding
				// action instance
				int branchOffset = getBranchOffset(branchAction);
				actionInfo+=",branchOffset="+branchAction.getBranchOffset()+"("+UnsignedByte.hex(branchOffset)+")";
				String branchLabel;
				if (branchOffset < relativeEndOffset) {
					Integer branchOffsetObj = new Integer(branchOffset);

					// check if branch target isn't already assigned a label
					String oldLabel = (String) inverseLabelMap.get(branchOffsetObj);
					if (oldLabel == null) {
						branchLabel = "L_" + instCounter + "_" + labelCounter++;
						actionInfo+=" branchLabel:"+branchLabel+",totalBranchOffset="+branchOffsetObj;
						labelMap.put(branchLabel, branchOffsetObj);
						inverseLabelMap.put(branchOffsetObj, branchLabel);
					} else {
						branchLabel = oldLabel;
					}
				} else if (branchOffset == relativeEndOffset) {
					branchLabel = LABEL_END;
				} else {
					branchLabel = LABEL_OUT;
				}
				branchAction.setBranchLabel(branchLabel);
			}
			
			logger.debug(actionInfo);
		}

		logger.debug("########");
		logger.debug("Replacing offsets from label map with corresponding actions");
		logger.debug("########");
		
		// now replace offsets from label map with corresponding actions
		Set keys = labelMap.keySet();
		for (Iterator i = keys.iterator(); i.hasNext();) {
			String label = (String) i.next();
			Object branchOffset = labelMap.get(label);
			Action action = (Action) actionMap.get(branchOffset);
			if (action != null) {
				logger.debug("LABEL:"+label+",branchOffset="+branchOffset+",action="+UnsignedByte.hex(action.getOffset())+" "+action);
				// action == null when label == LABEL_OUT
				action.setLabel(label);
				labelMap.put(label, action);
			}
		}
		instCounter++;
	}
	
	@Override
	public void write(OutputBitStream stream, boolean writeEndAction) throws IOException {
		// Remove garabage actions
		logger.debug("Removing garabage actions before writing... "+actions.size());
		List<Action> cleanActions = new ArrayList<Action>();
		for (Object actionObj : actions) {
			Action action = (Action) actionObj;
			if (!(action instanceof GarbageAction)) {
				cleanActions.add(action);
			}
		}
		
		actions = cleanActions;
		
		// TODO: remove it
//		logger.debug("~~~ Reading statements ~~~");
//		StatementBlock statementBlock = CodeUtil.getStatementBlockReader();
//		
//		try {
//			statementBlock.setExecutionContext(CodeUtil.getExecutionContext());
//			statementBlock.read(actions);
//		} catch (StatementBlockException e) {
//			throw new RuntimeException("ZZZ");
//		}
		
		super.write(stream, writeEndAction);
	}
}
