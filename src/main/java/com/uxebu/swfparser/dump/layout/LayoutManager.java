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

    public void addButton(int characterId, String content)
    {
        dumpFile(outputDirectory + "/buttons/button-" + characterId + ".js", content);
    }

    private void dumpFile(String fileName, String content)
    {
        try
        {
            FileOutputStream spriteFile = new FileOutputStream(fileName);
            spriteFile.write(content.getBytes());
            spriteFile.flush();
            spriteFile.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException("Error creating file " + fileName);
        }
    }

    public void addSprite(int characterId, int frameNumber, String content)
    {
        dumpFile(outputDirectory + "/sprites/sprite-" + characterId + "-" + frameNumber + ".js", content);
    }

    public void addClass(String fileName, String content)
    {

    }

    public void setRootMovie(String content)
    {

    }
}
