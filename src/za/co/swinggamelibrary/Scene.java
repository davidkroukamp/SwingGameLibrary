/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.swinggamelibrary;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author dkrou
 */
public class Scene extends Node {

    private boolean drawDebugMasks;
    private Director director;

    @Override
    public void update(long elapsedTime) {
        super.update(elapsedTime);
        this.checkForCollisions();
    }

    @Override
    public void render(Graphics2D g2d) {
        // draw all Sprites to the screen which are visible and or havent been removed from the scene
        Iterator<INode> spriteIterator = this.getNodes().iterator();
        while (spriteIterator.hasNext()) {
            INode node = (INode) spriteIterator.next();
            if (node.isVisible()) {
                if (this.drawDebugMasks) {
                    g2d.setColor(Color.RED);
                    g2d.drawRect((int) node.getScreenX(), (int) node.getScreenY(), (int) node.getWidth(), (int) node.getHeight());
                }
            }
        }

        super.render(g2d);
    }

    private void checkForCollisions() {
        List<INode> nodes = this.getNodes();
        nodes.stream().filter((outerNode) -> (outerNode instanceof ICollidable)).forEachOrdered((outerNode) -> {
            nodes.stream().filter((innerNode) -> (innerNode instanceof ICollidable)).forEachOrdered((innerNode) -> {
                INode outerCollidable = (INode) outerNode;
                INode innerCollidable = (INode) innerNode;

                // check if the 2 nodes are colliding/intersecting
                if (outerCollidable.intersects(innerCollidable)) {
                    // check to ensure we are not checking ourselves
                    if (!outerNode.equals(innerNode)) {
                        ((ICollidable) outerCollidable).onCollision(innerNode);
                    }
                }
            });
        });
    }

    public void setDrawDebugMasks(boolean drawDebugMasks) {
        this.drawDebugMasks = drawDebugMasks;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    @Override
    public double getWidth() {
        return this.director == null ? 0 : this.director.getWidth();
    }

    @Override
    public double getHeight() {
        return this.director == null ? 0 : this.director.getHeight();
    }
    
}
