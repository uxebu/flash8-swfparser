/*
 *   ExtractTagsToSeparateSWF.java
 * 	 @Author Oleg Gorobets
 *   Created: 01.09.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;

import com.jswiff.AsIsSWFReader;
import com.jswiff.SWFDocument;
import com.jswiff.SWFReader;
import com.jswiff.SWFWriter;
import com.jswiff.listeners.SWFDocumentReader;

/**
 * Extracts tags by numbers starting from 0 to a separate SWF file
 *
 */
public class ExtractTagsToSeparateSWF {
	
	private static Logger logger = Logger.getLogger(ExtractTagsToSeparateSWF.class);
	
	public static String swfInputFileName = "C:\\swf\\input-swf\\input.swf";
	
	public static String tagNumbers ="316";
	
	public static String swfOutputFileName = swfInputFileName+"."+tagNumbers+".swf";
	
	public static void main(String[] args) {
		try {
			new ExtractTagsToSeparateSWF().start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.debug("OK");
	}
	
	public void start() throws IOException {
		InputStream swfStream = new FileInputStream(new File(swfInputFileName));
		SWFDocument swfDoc = parseSWFDocument(swfStream);
		
		// write new SWF with selected tags only
		SWFWriter writer = new SWFWriter(swfDoc, new FileOutputStream(swfOutputFileName));
		writer.write();
	}
	
	protected SWFDocument parseSWFDocument(InputStream swfStream) {
	    SWFReader swfReader         = new AsIsSWFReader(swfStream,tagNumbers);
	    SWFDocumentReader docReader = new SWFDocumentReader();
	    swfReader.addListener(docReader);
	    swfReader.read();
	    return docReader.getDocument();
	  }
}
