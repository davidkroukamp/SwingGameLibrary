/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.swinggamelibrary;

import java.awt.Graphics2D;
import java.util.LinkedList;

/**
 *
 * @author dkrou
 */
public class Sprite extends Node {

    private final Animator animator;
    private boolean isFlippedX;

    public Sprite(String spriteFrameName) {
        super(0, 0, 0, 0);
        SpriteFrame spriteFrame = SpriteFrameCache.getInstance().getSpriteFrameByName(spriteFrameName);
        LinkedList<SpriteFrame> spriteFrames = new LinkedList<>();
        spriteFrames.add(spriteFrame);
        this.animator = new Animator(Animation.createWithSpriteFrames(spriteFrames, 0, 0));
        this.setWidth(this.animator.getCurrentImage().getWidth());
        this.setHeight(this.animator.getCurrentImage().getHeight());
        this.setFlippedX(false);
    }

    public Sprite(Animation animation) {
        super(0, 0, 0, 0);
        this.animator = new Animator(animation);
        this.setWidth(this.animator.getCurrentImage().getWidth());
        this.setHeight(this.animator.getCurrentImage().getHeight());
        this.setFlippedX(false);
    }

    public Sprite(SpriteFrame spriteFrame) {
        super(0, 0, spriteFrame.getImage().getWidth(), spriteFrame.getImage().getHeight());
        LinkedList<SpriteFrame> spriteFrames = new LinkedList<>();
        spriteFrames.add(spriteFrame);
        this.animator = new Animator(Animation.createWithSpriteFrames(spriteFrames, 0, 0));
        this.setFlippedX(false);
    }

    @Override
    public void update(long elapsedTime) {
        super.update(elapsedTime);
        this.animator.update(elapsedTime);
    }

    @Override
    public void render(Graphics2D g2d) {
        if (this.isFlippedX) {
            // flip horizontally
            g2d.drawImage(this.animator.getCurrentImage(), (int) this.getScreenX() + (int) this.getWidth(), (int) this.getScreenY(), (int) -this.getWidth(), (int) this.getHeight(), null);
        } else {
            g2d.drawImage(this.animator.getCurrentImage(), (int) this.getScreenX(), (int) this.getScreenY(), null);
        }

        // flip it vertically i.e. isFlippedY
        // g2.drawImage(image, x, y + height, width, -height, null);
        super.render(g2d);
    }

    @Override
    public void onEnter() {
        this.animator.start();
    }

    public void setFlippedX(boolean isFlippedX) {
        this.isFlippedX = isFlippedX;
    }

    public boolean getFlippedX() {
        return this.isFlippedX;
    }

}
