/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.swinggamelibrary;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author dkrou
 */
public class Node implements INode {

    private boolean visible;
    private boolean removedFromParent;
    private INode parent;
    private final Rectangle2D.Double rectangle;
    private final List<INode> nodes = Collections.synchronizedList(new ArrayList<>());

    public Node() {
        rectangle = new Rectangle2D.Double(0, 0, 0, 0);
    }

    public Node(double width, double height) {
        rectangle = new Rectangle2D.Double(0, 0, width, height);
    }

    public Node(int x, int y, double width, double height) {
        rectangle = new Rectangle2D.Double(x, y, width, height);
    }

    @Override
    public void update(long elapsedTime) {
        synchronized (nodes) {
            nodes.stream().filter((node) -> (node.isVisible())).forEachOrdered((node) -> {
                node.update(elapsedTime);
            });
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        // draw all Sprites to the screen which are visible and or havent been removed from the scene
        Iterator<INode> spriteIterator = nodes.iterator();
        while (spriteIterator.hasNext()) {
            INode node = (INode) spriteIterator.next();
            // draw the object to JPanel
            if (node.isRemovedFromParent()) {
                spriteIterator.remove();
            } else {
                if (node.isVisible()) {
                    node.render(g2d);
                }
            }
        }
    }

    @Override
    public void add(INode node) {
        synchronized (nodes) {
            if (node.getParent() != null) {
                return;
            }

            node.setParent(this);
            nodes.add(node);
        }
    }

    @Override
    public void remove(INode node) {
        Iterator<INode> nodeIterator = nodes.iterator();
        while (nodeIterator.hasNext()) {
            if (nodeIterator.next().equals(node)) {
                nodeIterator.remove();
            }
        }
    }

    @Override
    public void removeAll() {
        synchronized (nodes) {
            nodes.clear();
        }
    }

    @Override
    public List<INode> getNodes() {
        synchronized (nodes) {
            return nodes;
        }
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
    public INode getParent() {
        return parent;
    }

    @Override
    public void setParent(INode parent) {
        this.parent = parent;
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
        return getParent() != null ? getParent().getX() + rectangle.x : rectangle.x;
    }

    @Override
    public double getY() {
        return getParent() != null ? getParent().getY() + rectangle.y : rectangle.y;
    }

    @Override
    public void setPosition(double x, double y) {
        this.rectangle.x = x;
        this.rectangle.y = y;
    }

    @Override
    public double getWidth() {
        return rectangle.width;
    }

    @Override
    public double getHeight() {
        return rectangle.height;
    }

    @Override
    public Rectangle2D getBounds2D() {
        return rectangle.getBounds2D();
    }

    @Override
    public boolean intersects(INode node) {
        return rectangle.intersects(node.getBounds2D());
    }
}
