/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.swinggamelibrary;

import java.awt.geom.Rectangle2D;

/**
 *
 * @author dkrou
 */
public class Sprite extends Animator {

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

    public void setX(double x) {
        rectangle.x = x;
    }

    public void setY(double y) {
        rectangle.y = y;
    }

    public void setWidth(double width) {
        rectangle.width = width;
    }

    public void setHeight(double height) {
        rectangle.height = height;
    }

    public double getX() {
        return rectangle.x;
    }

    public double getY() {
        return rectangle.y;
    }

    public double getWidth() {
        if (getCurrentImage() == null) {//there might be no image (which is unwanted ofcourse but  we must not get NPE so we check for null and return 0
            return rectangle.width = 0;
        }

        return rectangle.width = getCurrentImage().getWidth();
    }

    public double getHeight() {
        if (getCurrentImage() == null) {
            return rectangle.height = 0;
        }
        return rectangle.height = getCurrentImage().getHeight();
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Rectangle2D getBounds2D() {
        return rectangle.getBounds2D();
    }

    public boolean intersects(Sprite sprite) {
        return rectangle.intersects(sprite.getBounds2D());
    }

    public void removeFromParent() {
        removedFromParent = true;
        visible = false;
    }
    
    public boolean isRemovedFromParent() {
        return removedFromParent;
    }

}
