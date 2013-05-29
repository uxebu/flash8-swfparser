/*
 *   StatementBlockContextImpl.java
 * 	 @Author Oleg Gorobets
 *   Created: 26.07.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser;

import com.jswiff.SWFDocument;
import com.jswiff.swfrecords.tags.Tag;

public class StatementBlockContextImpl implements StatementBlockContext {

	private SWFDocument document;
	private Tag tag;
	
	public StatementBlockContextImpl(SWFDocument document, Tag tag) {
		super();
		this.document = document;
		this.tag = tag;
	}

	public SWFDocument getDocument() {
		// TODO Auto-generated method stub
		return null;
	}

	public Tag getTag() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
