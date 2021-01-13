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

    public Sprite(int worldX, int worldY, Animation animation) {
        super(worldX, worldY, 0, 0);
        this.animation = new Animator(animation);
        this.setWidth(this.animation.getCurrentImage().getWidth());
        this.setHeight(this.animation.getCurrentImage().getHeight());
    }

    @Override
    public void update(long elapsedTime) {
        super.update(elapsedTime);
        this.animation.update(elapsedTime);
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.drawImage(this.animation.getCurrentImage(), (int) this.getScreenX(), (int) this.getScreenY(), null);
        super.render(g2d);
    }

    @Override
    public void onEnter() {
        this.animation.start();
    }

}
