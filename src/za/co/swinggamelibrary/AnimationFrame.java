/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.swinggamelibrary;

import java.util.ArrayList;

/**
 *
 * @author dkrou
 */
public class AnimationFrame {

    private final ArrayList<SpriteFrame> spriteFrames;
    private final long delayPerFrame;
    private final int loops;

    public AnimationFrame(ArrayList<SpriteFrame> spriteFrames, long delayPerFrame, int loops) {
        this.spriteFrames = spriteFrames;
        this.delayPerFrame = delayPerFrame;
        // TODO loops needs to be implemented in animator
        this.loops = loops;
    }

    public long getDelayPerFrame() {
        return delayPerFrame;
    }

    public ArrayList<SpriteFrame> getSpriteFrames() {
        return spriteFrames;
    }

    public int getLoops() {
        return loops;
    }

}
