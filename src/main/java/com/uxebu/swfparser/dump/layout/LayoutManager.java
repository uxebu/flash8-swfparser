package com.uxebu.swfparser.dump.layout;

import java.io.File;

public class LayoutManager
{
    private String baseDirectory;

    public LayoutManager(String baseDirectory)
    {
        this.baseDirectory = baseDirectory;
    }

    public void init()
    {
        for (String directory : new String[]{"buttons", "classes", "sprites"})
        {
            File outputDirectory = new File(baseDirectory + "/" + directory);

            if (!outputDirectory.exists())
            {
                outputDirectory.mkdirs();
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
