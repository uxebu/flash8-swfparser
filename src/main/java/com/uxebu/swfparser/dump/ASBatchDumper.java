package com.uxebu.swfparser.dump;

import java.io.File;

import com.uxebu.swfparser.dump.assets.AssetManager;
import com.uxebu.swfparser.dump.layout.LayoutManager;
import org.apache.log4j.Logger;

public class ASBatchDumper
{
    private static Logger logger = Logger.getLogger(ASBatchDumper.class);
    private String inputDirectory;
    private String outputDirectory;

    public ASBatchDumper(String inputDirectory, String outputDirectory)
    {
        this.inputDirectory = inputDirectory;
        this.outputDirectory = outputDirectory;
    }

    public static void main(String[] args)
    {
        String inputDirectory = getPropertyIfDefined("input", "swf");
        String outputDirectory = getPropertyIfDefined("output", "as2");

        new ASBatchDumper(inputDirectory, outputDirectory).start();
    }

    private static String getPropertyIfDefined(String propertyName, String defaultValue)
    {
        String inputDirectory = defaultValue;

        if (System.getProperty(propertyName) != null)
        {
            inputDirectory = System.getProperty(propertyName);
        }

        return inputDirectory;
    }

    public void start()
    {
        logger.debug("Looking " + inputDirectory + " for SWF's");

        LayoutManager layoutManager = new LayoutManager(outputDirectory);
        layoutManager.init();

        AssetManager assetManager = new AssetManager(inputDirectory);

        for (File swfFile : assetManager.getSWFFiles())
        {
            logger.debug("Reading " + swfFile.getName());

            try
            {
                String swfFileAbsolutePath = swfFile.getAbsolutePath();

                SWFDumpActionScript swfActionScript = new SWFDumpActionScript(swfFileAbsolutePath);
                swfActionScript.process();

                logger.debug("OK!");
            }
            catch (Exception ee)
            {
                ee.printStackTrace();
            }
        }
    }
}
