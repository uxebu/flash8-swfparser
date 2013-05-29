/*
 *   ASBatchDumper.java
 * 	 @Author Oleg Gorobets
 *   Created: 01.09.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.tests;

import java.io.File;
import java.io.FileFilter;


import org.apache.log4j.Logger;


public class ASBatchDumper {
	
	private static Logger logger = Logger.getLogger(ASBatchDumper.class);
	private static String swfInputDirectory = "input-swf/";
	private static String asOutputDirectory = "output-as/";
	
	public void start() {
		
		logger.debug("Looking "+swfInputDirectory+" for SWF's");
		
		File[] files = new File(swfInputDirectory).listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return pathname.getName().endsWith(".swf");
			}
		});
		
		try {
			for (File file : files) {
				logger.debug("Reading "+file.getName());

                try
                {
                    writeSWFActionScript(file);
                    logger.debug("OK!");
                }
                catch (Exception ee)
                {
                    ee.printStackTrace();
                }
			}
		} catch (Exception e) {
			logger.error("#start()",e);
		}
	}

	protected void writeSWFActionScript(File swfFile) {
		String fileNameWithoutExtension = swfFile.getName().substring(0,swfFile.getName().lastIndexOf("."));
		SWFDumpActionScript swfActionScript = new SWFDumpActionScript(
				swfFile.getAbsolutePath(),
				asOutputDirectory+File.separator+fileNameWithoutExtension +".as.dmp"
			);
		swfActionScript.process();
		
	}
	
	public static void main(String[] args) {
        if (System.getProperty("input") != null)
        {
            swfInputDirectory = System.getProperty("input");
        }

        if (System.getProperty("output") != null)
        {
            asOutputDirectory = System.getProperty("output");
        }

		new ASBatchDumper().start();
	}
}
