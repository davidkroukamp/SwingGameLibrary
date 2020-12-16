/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.swinggamelibrary;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author dkrou
 */
public class Scene extends JPanel {

    private final GameLoop gameLoop;
    private final int width, height;
    protected final List<INode> nodes = Collections.synchronizedList(new ArrayList<>());
    private boolean renderDebugInfo;
    private boolean drawDebugMasks;

    public Scene(int fps, int width, int height) {
        super(true);
        setIgnoreRepaint(true);//we will do the repainting
        this.width = width;
        this.height = height;
        gameLoop = new GameLoop(fps, 0) {
            @Override
            public void update(long elapsedTime) { //updates Sprite movement and Animation
                Scene.this.update(elapsedTime);
                Scene.this.checkForCollisions();
            }

            @Override
            public void render(long elapsedTime) { // draws sprites
                repaint();
            }
        };
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g2d = (Graphics2D) graphics;
        Graphics2DHelper.applyRenderHints(g2d);

        // TODO should be able to set background of scene
        // draw background
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // draw all Sprites to the screen which are visible and or havent been removed from the scene
        Iterator<INode> spriteIterator = nodes.iterator();
        while (spriteIterator.hasNext()) {
            INode node = (INode) spriteIterator.next();
            // draw the object to JPanel
            if (node.isRemovedFromParent()) {
                spriteIterator.remove();
            } else {
                if (drawDebugMasks) {
                    g2d.setColor(Color.RED);
                    g2d.drawRect((int) node.getX(), (int) node.getY(), (int) node.getWidth(), (int) node.getHeight());
                }

                node.render(g2d);
            }
        }

        if (renderDebugInfo) {
            FontMetrics metrics = g2d.getFontMetrics(getFont());
            g2d.setColor(Color.WHITE);
            int textX = getHeight() - metrics.getHeight() + metrics.getAscent();
            g2d.drawString("FPS: " + (int) gameLoop.getAverageFps(), 0, textX - (metrics.getHeight()));
            g2d.drawString("Objects: " + nodes.size(), 0, textX);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    public void update(long elapsedTime) {
        synchronized (nodes) {
            nodes.stream().filter((node) -> (node.isVisible())).forEachOrdered((node) -> {
                // update the movement and image of the sprite
                node.update(elapsedTime);
            });
        }
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

    public void setRenderDebugInfo(boolean renderDebugInfo) {
        this.renderDebugInfo = renderDebugInfo;
    }

    public void setDrawDebugMasks(boolean drawDebugMasks) {
        this.drawDebugMasks = drawDebugMasks;
    }

    public void add(INode node) {
        synchronized (nodes) {
            nodes.add(node);
        }
    }

    public void clearNodes() {
        synchronized (nodes) {
            nodes.clear();
        }
    }

    public List<INode> getNodes() {
        synchronized (nodes) {
            return nodes;
        }
    }

    public void start() {
        gameLoop.start();
    }

    public void pause() {
        gameLoop.pause();
    }

    public void resume() {
        gameLoop.resume();
    }

    public void stop() {
        gameLoop.stop();
    }

    public boolean isPaused() {
        return gameLoop.isPaused();
    }

    public boolean isRunning() {
        return gameLoop.isRunning();
    }
}
