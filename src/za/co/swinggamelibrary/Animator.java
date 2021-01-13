/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.swinggamelibrary;

import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author dkrou
 */
public class Animator {

    private final Animation animation;
    private int frameIndex;
    private int loopCount = 1;
    private long animationElapsedTime;
    private final AtomicBoolean stopped;

    public Animator(Animation animation) {
        this.frameIndex = 0;
        this.animationElapsedTime = 0;
        this.animation = animation;
        this.stopped = new AtomicBoolean(true);
    }

    public void start() {
        this.stopped.set(false);
    }

    public void stop() {
        this.stopped.set(true);
    }

    public void reset() {
        this.animationElapsedTime = 0;
        this.frameIndex = 0;
        this.stopped.set(true);
    }

    public void update(long elapsedTime) {
        if (this.stopped.get()) {
            return;
        }

        int frameCount = this.animation.getFrames().size();
        int animationLoops = this.animation.getLoops();

        if (frameCount > 1) {
            this.animationElapsedTime += elapsedTime;
            if (this.animationElapsedTime >= this.animation.getDelayPerUnit() * 1000) {
                // we reached the end of the animation
                if (this.frameIndex + 1 >= frameCount) {
                    this.animationElapsedTime = 0;
                    this.frameIndex = 0;
                    // check if we need to loop the animation 
                    if (this.loopCount < animationLoops) {
                        this.loopCount++;
                    } else {
                         // only stop the animation if it had a loop count greater then 0 as 0 means indefinitely
                        if (animationLoops > 0) {
                            this.stopped.set(true);
                        }
                    }
                } else {
                    this.animationElapsedTime = 0;
                    this.frameIndex++;
                }
            }
        }
    }

    public BufferedImage getCurrentImage() {
        return this.animation.getFrames().get(frameIndex).getImage();
    }

}
