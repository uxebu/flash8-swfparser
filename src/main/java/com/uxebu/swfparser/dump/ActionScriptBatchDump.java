package com.uxebu.swfparser.dump;

import com.uxebu.swfparser.dump.assets.AssetManager;
import com.uxebu.swfparser.dump.output.FileWriter;

public class ActionScriptBatchDump
{
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

        for (String fileName : assetManager.getSWFFiles())
        {
            String fileOutputDirectory = outputDirectory + "/" + fileName + "-output";

            try
            {
                FileWriter fileWriter = new FileWriter(fileOutputDirectory);

                ActionScriptDump swfActionScript = new ActionScriptDump(fileWriter, assetManager, fileName);
                swfActionScript.process();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
