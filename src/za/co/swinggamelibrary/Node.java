package za.co.swinggamelibrary;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author dkrou
 */
public class Node implements INode {

    private boolean visible;
    private boolean removedFromParent;
    private INode parent; 
    protected final List<INode> nodes = Collections.synchronizedList(new ArrayList<>());

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
    }

    @Override
    public void setY(double y) {
    }

    @Override
    public void setWidth(double width) {
    }

    @Override
    public void setHeight(double height) {
    }

    @Override
    public double getX() {
        return 0.0;
    }

    @Override
    public double getY() {
        return 0.0;
    }

    @Override
    public double getWidth() {
        return 0.0;
    }

    @Override
    public double getHeight() {
        return 0.0;
    }
}
