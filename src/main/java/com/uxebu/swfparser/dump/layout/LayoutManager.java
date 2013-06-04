package com.uxebu.swfparser.dump.layout;

import java.io.File;

public class LayoutManager
{
    private String outputDirectory;

    public LayoutManager(String outputDirectory)
    {
        this.outputDirectory = outputDirectory;
    }

    public void init()
    {
        for (String directory : new String[]{"buttons", "classes", "sprites"})
        {
            File fileOutputDirectory = new File(outputDirectory + "/" + directory);

            if (!fileOutputDirectory.exists())
            {
                fileOutputDirectory.mkdirs();
            }
        }
    }

    public void addButton(String fileName, String content)
    {

    }

    public void addSprite(String fileName, String content)
    {

    }

    public void addClass(String fileName, String content)
    {

    }

    public void setRootMovie(String content)
    {

    }
}
