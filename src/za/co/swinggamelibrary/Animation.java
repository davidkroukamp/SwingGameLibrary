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
public class Animation {

    private final AnimationFrame animation;
    private int currIndex;
    private int loops = 1;
    private long animationTime;
    private final AtomicBoolean done;

    public Animation(AnimationFrame animation) {
        currIndex = 0;
        animationTime = 0;
        done = new AtomicBoolean(false);
        this.animation = animation;
    }

    public boolean isDone() {
        return done.get();
    }

    public void reset() {
        animationTime = 0;
        currIndex = 0;
        done.getAndSet(false);
    }

    public void update(long elapsedTime) {
        if (done.get()) {
            return;
        }

        if (this.animation.getSpriteFrames().size() > 1) {
            animationTime += elapsedTime;
            // TODO add loops check
            if (animationTime >= this.animation.getDelayPerFrame()) { // animation time has elapsed and frame shoiuld be changed
                if (currIndex + 1 >= this.animation.getSpriteFrames().size()) { // we reached the end of the animation
                    animationTime = 0;
                    currIndex = 0;
                    if (loops < animation.getLoops()) {
                        loops++;
                    } else {
                        if (animation.getLoops() > 0) {
                            done.getAndSet(true);
                        }
                    }
                } else {
                    animationTime = 0;
                    currIndex++;
                }
            }
        }
    }

    public BufferedImage getCurrentImage() {
        if (this.animation.getSpriteFrames().isEmpty()) {
            return null;
        } else {
            return this.animation.getSpriteFrames().get(currIndex).getImage();
        }
    }
}
