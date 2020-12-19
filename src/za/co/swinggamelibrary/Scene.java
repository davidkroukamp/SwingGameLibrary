/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.swinggamelibrary;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Iterator;

/**
 *
 * @author dkrou
 */
public class Scene extends Node {

    private boolean drawDebugMasks;
    private double width;
    private double height;

    @Override
    public void update(long elapsedTime) {
        super.update(elapsedTime);
        checkForCollisions();
    }

    @Override
    public void render(Graphics2D g2d) {
        // draw all Sprites to the screen which are visible and or havent been removed from the scene
        Iterator<INode> spriteIterator = nodes.iterator();
        while (spriteIterator.hasNext()) {
            INode node = (INode) spriteIterator.next();
            if (node.isVisible()) {
                if (drawDebugMasks) {
                    g2d.setColor(Color.RED);
                    g2d.drawRect((int) node.getX(), (int) node.getY(), (int) node.getWidth(), (int) node.getHeight());
                }
            }
        }

        super.render(g2d);
    }

    private void checkForCollisions() {
        synchronized (nodes) {
            nodes.stream().filter((outerNode) -> (outerNode instanceof ICollidable)).forEachOrdered((outerNode) -> {
                nodes.stream().filter((innerNode) -> (innerNode instanceof ICollidable)).forEachOrdered((innerNode) -> {
                    ICollidable outerCollidable = (ICollidable) outerNode;
                    ICollidable innerCollidable = (ICollidable) innerNode;

                    // check if the 2 nodes are colliding/intersecting
                    if (outerCollidable.intersects(innerCollidable)) {
                        // check to ensure we are not checking ourselves
                        if (!outerNode.equals(innerNode)) {
                            outerCollidable.onCollision(innerNode);
                        }
                    }
                });
            });
        }
    }

    public void setDrawDebugMasks(boolean drawDebugMasks) {
        this.drawDebugMasks = drawDebugMasks;
    }

    @Override
    public void setWidth(double width) {
        this.width = width;
    }

    @Override
    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

}
