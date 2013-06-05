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
            flags.add(Flag.isOutDownToIdle);
        }
        if (buttonAction.isOutDownToOverDown())
        {
            flags.add(Flag.isOutDownToOverDown);
        }
        if (buttonAction.isIdleToOverDown())
        {
            flags.add(Flag.isIdleToOverDown);
        }
        if (buttonAction.isOverDownToOutDown())
        {
            flags.add(Flag.isOverDownToOutDown);
        }
        if (buttonAction.isOverDownToIdle())
        {
            flags.add(Flag.isOverDownToIdle);
        }
        if (buttonAction.isOverUpToOverDown())
        {
            flags.add(Flag.isOverUpToOverDown);
        }
        if (buttonAction.isOverDownToOverUp())
        {
            flags.add(Flag.isOverDownToOverUp);
        }
        if (buttonAction.isOverUpToIdle())
        {
            flags.add(Flag.isOverUpToIdle);
        }
        if (buttonAction.isIdleToOverUp())
        {
            flags.add(Flag.isIdleToOverUp);
        }

        return flags;
    }
}
