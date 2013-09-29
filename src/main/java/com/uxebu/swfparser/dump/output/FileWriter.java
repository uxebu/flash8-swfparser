package com.uxebu.swfparser.dump.output;

import java.io.File;
import java.io.FileOutputStream;

public class FileWriter extends Writer {
    private String outputDirectory;

    public FileWriter(String outputDirectory) {
        this.outputDirectory = outputDirectory;
        init();
    }

    private void init() {
        createDirectoryIfNotExists(outputDirectory);
        for (String directory : new String[]{"rootMovie", "button", "initClip", "sprite"}) {
            createDirectoryIfNotExists(outputDirectory + "/" + directory);
        }
    }

    private void createDirectoryIfNotExists(String directory) {
        File fileOutputDirectory = new File(directory);
        if (!fileOutputDirectory.exists()) {
            fileOutputDirectory.mkdirs();
        }
    }

    protected void dumpFile(String fileName, String content) {
        try {
            FileOutputStream file = new FileOutputStream(outputDirectory + "/" + fileName);
            file.write(content.getBytes());
            file.flush();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error creating file " + fileName);
        }
    }

}
