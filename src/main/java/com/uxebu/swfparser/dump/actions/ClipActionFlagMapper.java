package com.uxebu.swfparser.dump.actions;

import com.jswiff.swfrecords.ClipEventFlags;

import java.util.ArrayList;
import java.util.List;

public class ClipActionFlagMapper
{
    private ClipEventFlags clipEventFlags;

    public ClipActionFlagMapper(ClipEventFlags clipEventFlags)
    {
        this.clipEventFlags = clipEventFlags;
    }

    public List<ClipActionFlag> map()
    {
        List<ClipActionFlag> clipActionFlags = new ArrayList<ClipActionFlag>();

        if (clipEventFlags.isConstruct())
        {
            clipActionFlags.add(ClipActionFlag.construct);
        }
        if (clipEventFlags.isData())
        {
            clipActionFlags.add(ClipActionFlag.data);
        }
        if (clipEventFlags.isDragOut())
        {
            clipActionFlags.add(ClipActionFlag.dragOut);
        }
        if (clipEventFlags.isDragOver())
        {
            clipActionFlags.add(ClipActionFlag.dragOver);
        }
        if (clipEventFlags.isEnterFrame())
        {
            clipActionFlags.add(ClipActionFlag.enterFrame);
        }
        if (clipEventFlags.isInitialize())
        {
            clipActionFlags.add(ClipActionFlag.initialize);
        }
        if (clipEventFlags.isKeyDown())
        {
            clipActionFlags.add(ClipActionFlag.keyDown);
        }
        if (clipEventFlags.isKeyUp())
        {
            clipActionFlags.add(ClipActionFlag.keyUp);
        }
        if (clipEventFlags.isKeyPress())
        {
            clipActionFlags.add(ClipActionFlag.keyPress);
        }
        if (clipEventFlags.isLoad())
        {
            clipActionFlags.add(ClipActionFlag.load);
        }
        if (clipEventFlags.isUnload())
        {
            clipActionFlags.add(ClipActionFlag.unload);
        }
        if (clipEventFlags.isMouseDown())
        {
            clipActionFlags.add(ClipActionFlag.mouseDown);
        }
        if (clipEventFlags.isMouseMove())
        {
            clipActionFlags.add(ClipActionFlag.mouseMove);
        }
        if (clipEventFlags.isMouseUp())
        {
            clipActionFlags.add(ClipActionFlag.mouseUp);
        }
        if (clipEventFlags.isPress())
        {
            clipActionFlags.add(ClipActionFlag.press);
        }
        if (clipEventFlags.isRelease())
        {
            clipActionFlags.add(ClipActionFlag.release);
        }
        if (clipEventFlags.isReleaseOutside())
        {
            clipActionFlags.add(ClipActionFlag.releaseOutside);
        }
        if (clipEventFlags.isRollOut())
        {
            clipActionFlags.add(ClipActionFlag.rollOut);
        }
        if (clipEventFlags.isRollOver())
        {
            clipActionFlags.add(ClipActionFlag.rollOver);
        }

        return clipActionFlags;
    }
}
