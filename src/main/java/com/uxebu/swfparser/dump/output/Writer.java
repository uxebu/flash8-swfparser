package com.uxebu.swfparser.dump.output;

import com.uxebu.swfparser.dump.actions.ButtonCondActionFlag;
import com.uxebu.swfparser.dump.actions.ClipActionFlag;

public class Writer {

    protected void dumpFile(String fileName, String content) {
        // Not writing at all, implement/override where to write to.
    }

    public void addButton(int characterId, ButtonCondActionFlag buttonCondActionFlag, String content) {
        dumpFile("button/button-" + characterId + "-" + buttonCondActionFlag + ".js", content);
    }

    public void addSprite(int characterId, int frameNumber, String content) {
        dumpFile("sprite/sprite-" + characterId + "-" + frameNumber + ".js", content);
    }

    public void addInitClip(int spriteId, String content) {
        dumpFile("initClip/initClip-" + spriteId + ".js", content);
    }

    public void addRootMovie(int frameNumber, String content) {
        dumpFile("rootMovie/rootMovie-" + frameNumber + ".js", content);
    }

    public void addSpriteClipAction(int spriteCharacterId, int frame, int depth, ClipActionFlag flag, String content) {
        dumpFile("sprite/sprite-" + spriteCharacterId + "-clipAction-" + frame + '-' + depth + "-" + flag + ".js", content);
    }

    public void addRootMovieClipAction(int frame, int depth, ClipActionFlag flag, String content) {
        dumpFile("rootMovie/rootMovie-clipAction-" + frame + '-' + depth + "-" + flag + ".js", content);
    }

}
