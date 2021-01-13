/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.swinggamelibrary;

import java.awt.Graphics2D;

/**
 *
 * @author dkrou
 */
public class Sprite extends Node {

    private final Animator animation;
    private boolean isFlippedX;

    public Sprite(int worldX, int worldY, Animation animation) {
        super(worldX, worldY, 0, 0);
        this.animation = new Animator(animation);
        this.setWidth(this.animation.getCurrentImage().getWidth());
        this.setHeight(this.animation.getCurrentImage().getHeight());
        this.setFlippedX(false);
    }

    @Override
    public void update(long elapsedTime) {
        super.update(elapsedTime);
        this.animation.update(elapsedTime);
    }

    @Override
    public void render(Graphics2D g2d) {
        if (this.isFlippedX) {
            // flip horizontally
            g2d.drawImage(this.animation.getCurrentImage(), (int) this.getScreenX() + (int) this.getWidth(), (int) this.getScreenY(), (int) -this.getWidth(), (int) this.getHeight(), null);
        } else {
            g2d.drawImage(this.animation.getCurrentImage(), (int) this.getScreenX(), (int) this.getScreenY(), null);
        }

        // flip it vertically i.e. isFlippedY
        
        // g2.drawImage(image, x, y + height, width, -height, null);
        super.render(g2d);
    }

    @Override
    public void onEnter() {
        this.animation.start();
    }

    public void setFlippedX(boolean isFlippedX) {
        this.isFlippedX = isFlippedX;
    }

    public boolean getFlippedX() {
        return this.isFlippedX;
    }

}
