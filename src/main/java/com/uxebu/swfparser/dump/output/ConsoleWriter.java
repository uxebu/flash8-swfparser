package com.uxebu.swfparser.dump.output;

public class ConsoleWriter  extends Writer {

    public ConsoleWriter(){

    }

    protected void dumpFile(String fileName, String content) {
        System.out.println("// fileName: " + fileName);
        System.out.println(content);
    }

}
