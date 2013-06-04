package com.uxebu.swfparser.dump.assets;

import java.io.File;
import java.io.FileFilter;

public class AssetManager
{
    private String inputDirectory;

    public AssetManager(String inputDirectory)
    {
        this.inputDirectory = inputDirectory;
    }

    public File[] getSWFFiles()
    {
        return new File(inputDirectory).listFiles(new FileFilter()
        {
            public boolean accept(File pathName)
            {
                return pathName.getName().toLowerCase().endsWith(".swf");
            }
        });
    }
}
