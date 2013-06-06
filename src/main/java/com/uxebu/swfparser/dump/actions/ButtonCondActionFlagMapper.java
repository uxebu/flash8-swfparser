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

    public List<ButtonCondActionFlag> map()
    {
        List<ButtonCondActionFlag> buttonCondActionFlags = new ArrayList<ButtonCondActionFlag>();

        if (buttonAction.isOutDownToIdle())
        {
            buttonCondActionFlags.add(ButtonCondActionFlag.releaseOutside);
        }
        if (buttonAction.isOutDownToOverDown() || buttonAction.isIdleToOverDown())
        {
            buttonCondActionFlags.add(ButtonCondActionFlag.dragOver);
        }
        if (buttonAction.isOverDownToOutDown() || buttonAction.isOverDownToIdle())
        {
            buttonCondActionFlags.add(ButtonCondActionFlag.dragOut);
        }
        if (buttonAction.isOverUpToOverDown())
        {
            buttonCondActionFlags.add(ButtonCondActionFlag.press);
        }
        if (buttonAction.isOverDownToOverUp())
        {
            buttonCondActionFlags.add(ButtonCondActionFlag.release);
        }
        if (buttonAction.isOverUpToIdle())
        {
            buttonCondActionFlags.add(ButtonCondActionFlag.rollOut);
        }
        if (buttonAction.isIdleToOverUp())
        {
            buttonCondActionFlags.add(ButtonCondActionFlag.rollOver);
        }

        return buttonCondActionFlags;
    }
}
