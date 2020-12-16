/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.swinggamelibrary;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author dkrou
 */
public class Sprite extends Animator implements INode {

    protected Rectangle2D.Double rectangle;
    protected boolean visible;
    protected boolean removedFromParent;

    public Sprite(int x, int y, Animation animation) {
        super(animation);
        rectangle = new Rectangle2D.Double(x, y, getCurrentImage().getWidth(), getCurrentImage().getHeight());
    }

    @Override
    public void update(long elapsedTime) {
        super.update(elapsedTime);
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
        if (getCurrentImage() == null) {//there might be no image (which is unwanted ofcourse but  we must not get NPE so we check for null and return 0
            return rectangle.width = 0;
        }

        return rectangle.width = getCurrentImage().getWidth();
    }

    @Override
    public double getHeight() {
        if (getCurrentImage() == null) {
            return rectangle.height = 0;
        }

        return rectangle.height = getCurrentImage().getHeight();
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public void removeFromParent() {
        removedFromParent = true;
        visible = false;
    }

    @Override
    public boolean isRemovedFromParent() {
        return removedFromParent;
    }

    @Override
    public void render(Graphics2D g2d, boolean drawDebugMasks) {
        if (drawDebugMasks) {
            g2d.setColor(Color.RED);
            g2d.drawRect((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
        }

        g2d.drawImage(getCurrentImage(), (int) getX(), (int) getY(), null);
    }

}
