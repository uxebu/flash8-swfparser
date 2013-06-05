package com.uxebu.swfparser.dump.generators;

import org.swfparser.ActionBlockContext;

public interface CodeGenerator
{
    void generate(ActionBlockContext context);
}
