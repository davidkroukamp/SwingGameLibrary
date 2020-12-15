/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.swinggamelibrary;

import java.awt.Color;
import java.awt.Dimension;
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
    protected final List<Sprite> sprites = Collections.synchronizedList(new ArrayList<>());

    public Scene(int fps, int width, int height) {
        super(true);
        setIgnoreRepaint(true);//we will do the repainting
        this.width = width;
        this.height = height;
        gameLoop = new GameLoop(fps, 0) {
            @Override
            public void update(long elapsedTime) { //updates Sprite movement and Animation
                Scene.this.update(elapsedTime);
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
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // draw all Sprites to the screen which are visible and or havent been removed from the scene
        Iterator<Sprite> spriteIterator = sprites.iterator();
        while (spriteIterator.hasNext()) {
            Sprite sprite = (Sprite) spriteIterator.next();
            // draw the object to JPanel
            if (sprite.isRemovedFromParent()) {
                spriteIterator.remove();
            } else {
                g2d.drawImage(sprite.getCurrentImage(), (int) sprite.getX(), (int) sprite.getY(), null);
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    public void update(long elapsedTime) {
        synchronized (sprites) {
            sprites.stream().filter((sprite) -> (sprite.isVisible())).forEachOrdered((sprite) -> {
                // update the movement and image of the sprite
                sprite.update(elapsedTime);
            });
        }
    }

    public void addSprite(Sprite sprite) {
        synchronized (sprites) {
            sprites.add(sprite);
        }
    }

    public void clearSprites() {
        synchronized (sprites) {
            sprites.clear();
        }
    }

    public List<Sprite> getSprites() {
        synchronized (sprites) {
            return sprites;
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
