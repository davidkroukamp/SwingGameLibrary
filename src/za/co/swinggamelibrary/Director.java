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
import javax.swing.JPanel;

/**
 *
 * @author dkrou
 */
public class Director extends JPanel {

    private final GameLoop gameLoop;
    private final int width, height;
    private Scene scene;
    private boolean renderDebugInfo;
    private boolean drawDebugMasks;

    public Director(int fps, int width, int height) {
        super(true);
        this.setIgnoreRepaint(true);//we will do the repainting
        this.setSize(width, height);
        this.width = width;
        this.height = height;
        this.gameLoop = new GameLoop(fps, 0) {
            @Override
            public void update(long elapsedTime) { //updates Sprite movement and Animation
                Director.this.update(elapsedTime);
            }

            @Override
            public void render(long elapsedTime) { // draws sprites
                Director.this.repaint();
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

        scene.render(g2d);

        if (renderDebugInfo) {
            FontMetrics metrics = g2d.getFontMetrics(getFont());
            g2d.setColor(Color.WHITE);
            int textX = (int) getHeight() - metrics.getHeight() + metrics.getAscent();
            g2d.drawString("FPS: " + (int) gameLoop.getAverageFps(), 0, textX - (metrics.getHeight()));
            g2d.drawString("Objects: " + scene.getNodes().size(), 0, textX);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    public void update(long elapsedTime) {
        scene.update(elapsedTime);
    }

    public void setScene(Scene scene) {
        this.scene = scene;
        this.scene.setDirector(this);
        this.scene.setDrawDebugMasks(drawDebugMasks);
    }

    public void setRenderDebugInfo(boolean renderDebugInfo) {
        this.renderDebugInfo = renderDebugInfo;
    }

    public void setDrawDebugMasks(boolean drawDebugMasks) {
        this.drawDebugMasks = drawDebugMasks;
        if (scene != null) {
            scene.setDrawDebugMasks(drawDebugMasks);
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
