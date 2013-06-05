package com.uxebu.swfparser.dump.assets;

import com.jswiff.SWFDocument;
import com.jswiff.SWFReader;
import com.jswiff.listeners.AllTagDocumentReader;
import com.jswiff.listeners.SWFDocumentReader;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class AssetManager
{
    private static Logger logger = Logger.getLogger(AssetManager.class);
    private String inputDirectory;

    public AssetManager(String inputDirectory)
    {
        this.inputDirectory = inputDirectory;
    }

    public List<String> getSWFFiles()
    {
        File[] files = new File(inputDirectory).listFiles(new FileFilter()
        {
            public boolean accept(File pathName)
            {
                return pathName.getName().toLowerCase().endsWith(".swf");
            }
        });

        List<String> fileNames = new ArrayList<String>();

        for (File file : files)
        {
            fileNames.add(file.getName());
        }

        return fileNames;
    }

    public SWFDocument getSWFFile(String fileName)
    {
        try
        {
            SWFReader reader = new SWFReader(new FileInputStream(inputDirectory + "/" + fileName));
            SWFDocumentReader docReader = new AllTagDocumentReader();
            reader.addListener(docReader);
            reader.read();

            return docReader.getDocument();
        }
        catch (Exception e)
        {
            logger.error("Error reading swf file", e);
            throw new RuntimeException(e);
        }
    }
}
