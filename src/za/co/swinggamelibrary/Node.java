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
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

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
    private int zOrder = 0;
    private boolean hasRendered;

    public Node() {
        this.visible = true;
        this.rectangle = new Rectangle2D.Double(0, 0, 0, 0);
    }

    public Node(int width, int height) {
        this.visible = true;
        this.rectangle = new Rectangle2D.Double(0, 0, width, height);
    }

    public Node(int worldX, int worldY, int width, int height) {
        this.visible = true;
        this.rectangle = new Rectangle2D.Double(worldX, worldY, width, height);
    }

    @Override
    public void update(long elapsedTime) {
        this.getNodes().stream().filter((node) -> (node.isVisible())).forEachOrdered((node) -> {
            node.update(elapsedTime);
        });
    }

    @Override
    public void render(Graphics2D g2d) {
        // draw all Sprites to the screen which are visible and or havent been removed from the scene
        Iterator<INode> spriteIterator = this.getNodes().iterator();
        while (spriteIterator.hasNext()) {
            INode node = (INode) spriteIterator.next();
            // draw the object to JPanel
            if (node.isRemovedFromParent()) {
                node.onExit();
                remove(node);
            } else {
                if (node.isVisible()) {
                    if (!node.hasRendered()) {
                        node.onEnter();
                    }
                    node.render(g2d);
                }
            }
        }
        this.hasRendered = true;
    }

    @Override
    public void add(INode node) {
        synchronized (this.nodes) {
            // TODO perhaps throw an exception
            if (node.getParent() != null) {
                return;
            }

            node.setParent(this);
            this.nodes.add(node);

            // sort nodes by z order
            List<INode> unsortedNodes = new ArrayList<>(this.nodes);
            this.nodes.clear();
            this.nodes.addAll(unsortedNodes.stream().sorted(Comparator.comparingInt(n -> n.getZOrder())).collect(Collectors.toList()));
        }
    }

    @Override
    public void remove(INode node) {
        synchronized (this.nodes) {
            Iterator<INode> nodeIterator = this.nodes.iterator();
            while (nodeIterator.hasNext()) {
                if (nodeIterator.next().equals(node)) {
                    node.setParent(null);
                    node.removeFromParent();
                    nodeIterator.remove();
                }
            }
        }
    }

    @Override
    public void removeAll() {
        synchronized (this.nodes) {
            Iterator<INode> nodeIterator = this.nodes.iterator();
            while (nodeIterator.hasNext()) {
                INode node = nodeIterator.next();
                node.setParent(null);
                node.removeFromParent();
                nodeIterator.remove();
            }
        }
    }

    @Override
    public List<INode> getNodes() {
        synchronized (this.nodes) {
            return new ArrayList<>(this.nodes);
        }
    }

    @Override
    public boolean isVisible() {
        return this.visible;
    }

    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public void removeFromParent() {
        this.removedFromParent = true;
        this.visible = false;
    }

    @Override
    public boolean isRemovedFromParent() {
        return this.removedFromParent;
    }

    @Override
    public INode getParent() {
        return this.parent;
    }

    @Override
    public void setParent(INode parent) {
        this.parent = parent;
    }

    @Override
    public void setX(int x) {
        this.rectangle.x = x;
    }

    @Override
    public void setY(int y) {
        this.rectangle.y = y;
    }

    @Override
    public int getX() {
        return this.getParent() != null ? this.getParent().getX() + (int) this.rectangle.x : (int) this.rectangle.x;
    }

    @Override
    public int getY() {
        return this.getParent() != null ? this.getParent().getY() + (int) this.rectangle.y : (int) this.rectangle.y;
    }

    @Override
    public int getScreenX() {
        return (int) (ImageScaler.getInstance().getWidthScaleFactor() * getX());
    }

    @Override
    public int getScreenY() {
        return (int) (ImageScaler.getInstance().getHeightScaleFactor() * getY());
    }

    @Override
    public void setWidth(int width) {
        this.rectangle.width = width;
    }

    @Override
    public void setHeight(int height) {
        this.rectangle.height = height;
    }

    @Override
    public double getWidth() {
        return this.rectangle.width;
    }

    @Override
    public double getHeight() {
        return this.rectangle.height;
    }

    @Override
    public boolean intersects(INode node) {
        if ((this.getWidth() <= 0.0 || this.getHeight() <= 0.0) || node.getWidth() <= 0 || node.getHeight() <= 0) {
            return false;
        }
        double x = node.getScreenX();
        double y = node.getScreenY();
        double x0 = this.getScreenX();
        double y0 = this.getScreenY();
        return (x + node.getWidth() > x0
                && y + node.getHeight() > y0
                && x < x0 + this.getWidth()
                && y < y0 + this.getHeight());
    }

    @Override
    public int getChildCount() {
        int childCount = this.nodes.size();
        Iterator<INode> nodeIterator = this.nodes.iterator();
        while (nodeIterator.hasNext()) {
            INode node = nodeIterator.next();
            childCount += node.getChildCount();
        }

        return childCount;
    }

    @Override
    public void setZOrder(int zOrder) {
        this.zOrder = zOrder;
    }

    @Override
    public int getZOrder() {
        return this.zOrder;
    }

    @Override
    public boolean hasRendered() {
        return this.hasRendered;
    }

    @Override
    public void onEnter() {
    }

    @Override
    public void onExit() {
    }

}
