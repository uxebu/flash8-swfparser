/*
 *   ASBatchPrinter.java
 * 	 @Author Oleg Gorobets
 *   Created: 01.09.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.tests;

import java.io.File;

public class ASBatchPrinter extends ASBatchDumper {
	@Override
	protected void writeSWFActionScript(File swfFile) {
//		SWFPrintActionScript swfActionScript = new SWFPrintActionScript(
//				swfFile.getAbsolutePath()
//			);
//		swfActionScript.process();
	}
	
	public static void main(String[] args) {
		new ASBatchPrinter().start();
	}
}
