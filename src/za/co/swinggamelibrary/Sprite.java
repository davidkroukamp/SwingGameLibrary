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

    private final Animation animation;

    public Sprite(int worldX, int worldY, AnimationFrame animationFrame) {
        super(worldX, worldY, 0, 0);
        animation = new Animation(animationFrame);
        this.setWidth(animation.getCurrentImage().getWidth());
        this.setHeight(animation.getCurrentImage().getHeight());
    }

    @Override
    public void update(long elapsedTime) {
        super.update(elapsedTime);
        animation.update(elapsedTime);
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.drawImage(animation.getCurrentImage(), (int) getScreenX(), (int) getScreenY(), null);
        super.render(g2d);
    }
}
