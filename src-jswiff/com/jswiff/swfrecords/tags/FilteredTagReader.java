/*
 *   FilteredTagReader.java
 * 	 @Author Oleg Gorobets
 *   Created: 24.07.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package com.jswiff.swfrecords.tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.jswiff.io.InputBitStream;


public abstract class FilteredTagReader extends TagReaderImpl {
	
	private static Logger logger = Logger.getLogger(FilteredTagReader.class);
	
	protected Set<Short> tags = new HashSet<Short>();
	
	protected static final int DO_PARSE 		= 2; 
	protected static final int DO_WRITE_AS_IS 	= 3; 
	
	@Override
	public Tag readTag(TagHeader header, byte[] tagData, short swfVersion, boolean japanese) throws IOException {
		Tag tag;
		if (header.getCode() == TagConstants.SET_BACKGROUND_COLOR 
				|| header.getCode() == TagConstants.FILE_ATTRIBUTES
				|| header.getCode() == TagConstants.METADATA
				|| !tags.contains(header.getCode())
				) {
			
			logger.debug("TAG "+header.getCode()+" length:"+header.getLength()+" PARSING...");
			tag = super.readTag(header, tagData, swfVersion, japanese);
		} else {
			// process all tags as unknown -- simply write binary data to output
			logger.debug("TAG "+header.getCode()+" length:"+header.getLength()+" WRITING AS IS...");
			tag = new UnknownTag(header.getCode(),tagData);
		}
		
		tag.setForceLongHeader(header.isLong());
		return tag;
		
	}
	
	protected abstract int getTagAction(TagHeader header);
	
//	@Override
//	public Tag readTag(InputBitStream stream, short swfVersion, boolean shiftJIS) throws IOException {
//		return super.readTag(stream, swfVersion, shiftJIS);
//		logger.debug("#readTag() from FilteredTagReader");
//		TagHeader header = new TagHeader(stream);
//		byte[] tagData = stream.readBytes(header.getLength());
//		return new UnknownTag(header.getCode(),tagData);
//	}

	public void setTags(String tags) {
		
		String[] tokens = tags.split(",");
		for(int j=0;j<tokens.length;j++) {
			logger.debug("tokens[j]="+tokens[j]);
			tokens[j] = tokens[j].trim();
			if (tokens[j].indexOf("-")>0) {
				String[] tagBoundaries = tokens[j].split("-");
				String left = tagBoundaries[0];
				String right = tagBoundaries[1];
				int leftTagNum = Integer.valueOf(left);
				int rightTagNum = Integer.valueOf(right);
				logger.debug("leftTagNum="+leftTagNum+",rightTagNum="+rightTagNum);
				for (int k=leftTagNum;k<=rightTagNum;k++) {
					logger.debug("Tag added:"+k);
					this.tags.add((short)k);
				}
			} else if (tokens[j].length()>0) {
				logger.debug("Tag added:"+Integer.valueOf(tokens[j]));
				this.tags.add(Short.valueOf(tokens[j]));
			}
//			tags.add(tokens[j]);
		}
	}
	
	

}
