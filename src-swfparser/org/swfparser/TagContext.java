/*
 *   TagContext.java
 * 	 @Author Oleg Gorobets
 *   Created: 12.09.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser;

import com.jswiff.SWFDocument;
import com.jswiff.swfrecords.tags.Tag;

public class TagContext {

	/**
	 * SWF document
	 */
	protected SWFDocument document;
	
	/**
	 * Current tag
	 */
	protected Tag tag;
	
	/**
	 * Tag number from the beginning of SWF file starting with 0
	 */
	protected int tagNum = 0;
	
	/**
	 * Frame number starting with 0
	 */
	protected int frameNum = 0;
	
	/**
	 * Parent context for composite tags (e.g. DEFINE_SPRITE)
	 */
	private TagContext parentContext;
	
	
	public Tag getTag() {
		return tag;
	}


	public void setTag(Tag tag) {
		this.tag = tag;
	}


	public int getTagNum() {
		return tagNum;
	}


	public void setTagNum(int tagNum) {
		this.tagNum = tagNum;
	}


	public int getFrameNum() {
		return frameNum;
	}


	public void setFrameNum(int frameNum) {
		this.frameNum = frameNum;
	}



	public TagContext getParentContext() {
		return parentContext;
	}


	public void setParentContext(TagContext parentContext) {
		this.parentContext = parentContext;
	}


	public String getDumpString() {
		return getDumpString(this);
		
	}
	
	public SWFDocument getDocument() {
		return document;
	}


	public void setDocument(SWFDocument document) {
		this.document = document;
	}


	protected String getDumpString(TagContext ctx) {
		
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
		
		if (ctx.getParentContext()!=null) {
			buf.append(")");
		}
		
		return buf.toString();
		
	}
	

}
