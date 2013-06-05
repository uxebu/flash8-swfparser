package com.uxebu.swfparser.dump.actions;

import com.jswiff.swfrecords.ButtonCondAction;

import java.util.ArrayList;
import java.util.List;

public class ButtonCondActionFlagMapper
{
    private ButtonCondAction buttonAction;

    public ButtonCondActionFlagMapper(ButtonCondAction buttonAction)
    {
        this.buttonAction = buttonAction;
    }

    public List<Flag> map()
    {
        List<Flag> flags = new ArrayList<Flag>();

        if (buttonAction.isOutDownToIdle())
        {
            flags.add(Flag.releaseOutside);
        }
        if (buttonAction.isOutDownToOverDown() || buttonAction.isIdleToOverDown())
        {
            flags.add(Flag.dragOver);
        }
        if (buttonAction.isOverDownToOutDown() || buttonAction.isOverDownToIdle())
        {
            flags.add(Flag.dragOut);
        }
        if (buttonAction.isOverUpToOverDown())
        {
            flags.add(Flag.press);
        }
        if (buttonAction.isOverDownToOverUp())
        {
            flags.add(Flag.release);
        }
        if (buttonAction.isOverUpToIdle())
        {
            flags.add(Flag.rollOut);
        }
        if (buttonAction.isIdleToOverUp())
        {
            flags.add(Flag.rollOver);
        }

        return flags;
    }
}
