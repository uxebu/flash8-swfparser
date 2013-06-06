package com.uxebu.swfparser.dump.layout;

import com.uxebu.swfparser.dump.actions.Flag;

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

        for (String directory : new String[]{"button", "initclip", "sprite"})
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

    public void addButton(int characterId, Flag flag, String content)
    {
        dumpFile(outputDirectory + "/button/button-" + characterId + "-" + flag + ".js", content);
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
        dumpFile(outputDirectory + "/sprite/sprite-" + characterId + "-" + frameNumber + ".js", content);
    }

    public void addInitClip(int spriteId, String content)
    {
        dumpFile(outputDirectory + "/initclip/initclip-" + spriteId + ".js", content);
    }

    public void addRootMovie(int frameNumber, String content)
    {
        dumpFile(outputDirectory + "/rootMovie-" + frameNumber + ".js", content);
    }
}
