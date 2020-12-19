/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.swinggamelibrary;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author dkrou
 */
public class Sprite extends Node {

    protected Rectangle2D.Double rectangle;
    private final Animation animation;

    public Sprite(int x, int y, AnimationFrame animationFrame) {
        animation = new Animation(animationFrame);
        rectangle = new Rectangle2D.Double(x, y, animation.getCurrentImage().getWidth(), animation.getCurrentImage().getHeight());
    }

    @Override
    public void update(long elapsedTime) {
        super.update(elapsedTime);
        animation.update(elapsedTime);
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.drawImage(animation.getCurrentImage(), (int) getX(), (int) getY(), null);
        super.render(g2d);
    }

    @Override
    public void setX(double x) {
        rectangle.x = x;
    }

    @Override
    public void setY(double y) {
        rectangle.y = y;
    }

    @Override
    public void setWidth(double width) {
        rectangle.width = width;
    }

    @Override
    public void setHeight(double height) {
        rectangle.height = height;
    }

    @Override
    public double getX() {
        return rectangle.x;
    }

    @Override
    public double getY() {
        return rectangle.y;
    }

    @Override
    public double getWidth() {
        if (animation.getCurrentImage() == null) {//there might be no image (which is unwanted ofcourse but  we must not get NPE so we check for null and return 0
            return rectangle.width = 0;
        }

        return rectangle.width = animation.getCurrentImage().getWidth();
    }

    @Override
    public double getHeight() {
        if (animation.getCurrentImage() == null) {
            return rectangle.height = 0;
        }

        return rectangle.height = animation.getCurrentImage().getHeight();
    }

}
