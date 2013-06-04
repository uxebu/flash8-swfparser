package com.uxebu.swfparser.dump;

import java.io.File;

import com.uxebu.swfparser.dump.assets.AssetManager;
import com.uxebu.swfparser.dump.layout.LayoutManager;
import org.apache.log4j.Logger;

public class ActionScriptBatchDump
{
    private static Logger logger = Logger.getLogger(ActionScriptBatchDump.class);
    private String inputDirectory;
    private String outputDirectory;

    public ActionScriptBatchDump(String inputDirectory, String outputDirectory)
    {
        this.inputDirectory = inputDirectory;
        this.outputDirectory = outputDirectory;
    }

    public static void main(String[] args)
    {
        String inputDirectory = getPropertyIfDefined("input", "swf");
        String outputDirectory = getPropertyIfDefined("output", "as2");

        new ActionScriptBatchDump(inputDirectory, outputDirectory).start();
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
        AssetManager assetManager = new AssetManager(inputDirectory);

        for (File file : assetManager.getSWFFiles())
        {
            String fileOutputDirectory = outputDirectory + "/" + file.getPath() + "-output";
            String fileAbsolutePath = file.getAbsolutePath();

            LayoutManager layoutManager = new LayoutManager(fileOutputDirectory);

            SWFDumpActionScript swfActionScript = new SWFDumpActionScript(layoutManager, fileAbsolutePath);
            swfActionScript.process();
        }
    }
}
