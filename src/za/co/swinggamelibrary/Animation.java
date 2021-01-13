/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.swinggamelibrary;

import java.util.LinkedList;

/**
 *
 * @author dkrou
 */
public class Animation {

    private final LinkedList<SpriteFrame> frames;
    private final float delayPerUnit;
    private final int loops;

    public static Animation createWithSpriteFrames(LinkedList<SpriteFrame> frames, float delayPerUnit, int loops) {
        return new Animation(frames, delayPerUnit, loops);
    }

    private Animation(LinkedList<SpriteFrame> frames, float delayPerUnit, int loops) {
        this.frames = frames;
        this.delayPerUnit = delayPerUnit;
        this.loops = loops;
    }

    public float getDelayPerUnit() {
        return this.delayPerUnit;
    }

    public LinkedList<SpriteFrame> getFrames() {
        return this.frames;
    }

    public int getLoops() {
        return this.loops;
    }

}
