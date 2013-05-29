/*
 *   SWFPrintActionScript.java
 * 	 @Author Oleg Gorobets
 *   Created: 01.09.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package com.jswiff.tests.simple;

import java.io.IOException;

import org.swfparser.tests.SWFDumpActionScript;

import org.apache.log4j.Logger;

import com.jswiff.SWFDocument;

/**
 * Simply prints actions to stdout.
 *
 */
public class SWFPrintActionScript extends SWFDumpActionScript {

	private static Logger logger = Logger.getLogger(SWFPrintActionScript.class);
	
	public SWFPrintActionScript(SWFDocument doc, String fileName) {
		super(doc, fileName);
	}

	public SWFPrintActionScript(String swfFileName) {
		super(swfFileName, null);
	}
	
	@Override
	protected void beforeProcess() {
	
	}
	
	@Override
	protected void afterProcess()  {
		
	}

	@Override
	protected void writeData(String data) throws IOException {
		System.out.println(data);
	}

}
