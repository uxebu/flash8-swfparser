package com.uxebu.swfparser.dump.layout;

import java.io.File;
import java.io.FileOutputStream;

public class LayoutManager
{
    private String outputDirectory;

    public LayoutManager(String outputDirectory)
    {
        this.outputDirectory = outputDirectory;

        init();
    }

    private void init()
    {
        createDirectoryIfNotExists(outputDirectory);

        for (String directory : new String[]{"buttons", "classes", "sprites"})
        {
            createDirectoryIfNotExists(outputDirectory + "/" + directory);
        }
    }

    private void createDirectoryIfNotExists(String directory)
    {
        File fileOutputDirectory = new File(directory);

        if (!fileOutputDirectory.exists())
        {
            fileOutputDirectory.mkdirs();
        }
    }

    public void addButton(String fileName, String content)
    {

    }

    public void addSprite(String fileName, String content)
    {
        try
        {
            FileOutputStream spriteFile = new FileOutputStream(outputDirectory + "/sprites/" + fileName);
            spriteFile.write(content.getBytes());
            spriteFile.flush();
            spriteFile.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException("Error creating spring file " + fileName);
        }
    }

    public void addClass(String fileName, String content)
    {

    }

    public void setRootMovie(String content)
    {

    }
}
