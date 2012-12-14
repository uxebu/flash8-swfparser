/*
 *   FilteredSWFReader.java
 * 	 @Author Oleg Gorobets
 *   Created: 03.09.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package com.jswiff;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.jswiff.swfrecords.SWFHeader;
import com.jswiff.swfrecords.tags.MalformedTag;
import com.jswiff.swfrecords.tags.Tag;
import com.jswiff.swfrecords.tags.TagConstants;
import com.jswiff.swfrecords.tags.TagHeader;
import com.jswiff.swfrecords.tags.TagReaderImpl;
import com.jswiff.swfrecords.tags.UnknownTag;

public abstract class FilteredSWFReader extends SWFReader {

	private static Logger logger = Logger.getLogger(FilteredSWFReader.class);
	
	protected static final int DO_SKIP = 1;

	protected static final int DO_PARSE = 2;

	protected static final int DO_WRITE_AS_IS = 3;

	public FilteredSWFReader(InputStream stream) {
		super(stream);
	}

	protected abstract int getTagAction(TagHeader tagHeader);
	
	@Override
	public void read() {
		preProcess();
		SWFHeader header;
		try {
			header = new SWFHeader(bitStream);
		} catch (Exception e) {
			// invoke error processing
			processHeaderReadError(e);
			return; // without header we cannot do anything...
		}
		processHeader(header);
		do {
			// we check this because of an OpenOffice export bug
			// (END tag written as a UI8 (00)instead of an UI16 (00 00))
			if ((header.getFileLength() - bitStream.getOffset()) < 2) {
				break;
			}
			TagHeader tagHeader = null;
			try {
				tagHeader = TagReaderImpl.getInstance().readTagHeader(bitStream);
			} catch (Exception e) {
				processTagHeaderReadError(e);
				break; // cannot continue without tag header
			}
			processTagHeader(tagHeader);
			Tag tag = null;
			byte[] tagData = null;
			
			// added
			int tagAction = getTagAction(tagHeader);
			
			// added end.
			
			try {
				tagData = TagReaderImpl.getInstance().readTagData(bitStream, tagHeader);
				switch (tagAction) {
					case DO_SKIP:
						logger.debug("Skipping tag "+tagHeader.getCode());
						break;
					case DO_PARSE:
						logger.debug("Parsing tag "+tagHeader.getCode());
						tag = new TagReaderImpl().readTag(tagHeader, tagData, header.getVersion(), japanese);
						break;
					case DO_WRITE_AS_IS:
						logger.debug("Writing AS-IS tag "+tagHeader.getCode());
						tag = new UnknownTag(tagHeader.getCode(),tagData);
						tag.setForceLongHeader(tagHeader.isLong());
						break;
				}
				
				if (tag!=null && tag.getCode() == TagConstants.END) {
					break;
				}
			} catch (Exception e) {
				// invoke error processing
				if (processTagReadError(tagHeader, tagData, e)) {
					break;
				}
				tag = new MalformedTag(tagHeader, tagData, e);
			}
			if (tag!=null) {
				processTag(tag, bitStream.getOffset());
			}
		} while (true);
		
		postProcess();
		
		try {
			bitStream.close();
		} catch (Exception e) {
			// empty on purpose - don't need to propagate errors which occur
			// while closing
		}
	}
	
	protected Set<Integer> convertStringToSet(String tags) {
		Set<Integer> set = new HashSet<Integer>();
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
					set.add(k);
				}
			} else if (tokens[j].length()>0) {
				logger.debug("Tag added:"+Integer.valueOf(tokens[j]));
				set.add(Integer.valueOf(tokens[j]));
			}

		}
		return set;
	}

}
