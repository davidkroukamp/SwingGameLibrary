/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.swinggamelibrary;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author dkrou
 */
public abstract class GameLoop {

    private final AtomicBoolean running, paused;
    private Thread gameLoopThread;
    //how many frames should be drawn in a second
    private final int FRAMES_PER_SECOND;
    //Calculate how many nano seconds each frame should take for our target frames per second.
    private final long TIME_BETWEEN_UPDATES;
    //track number of frames
    private int frameCount;
    //If you're worried about visual hitches more than perfect timing, set this to 1. else 5 should be okay
    private final int MAX_UPDATES_BETWEEN_RENDER = 1;
    private final int maxUpdatesBetweenRender;
    private long fpsCounterStartTime;

    public GameLoop(int fps, int maxUpdatesBetweenRender) {
        FRAMES_PER_SECOND = fps;
        TIME_BETWEEN_UPDATES = 1000000000 / FRAMES_PER_SECOND;
        gameLoopThread = null;
        running = new AtomicBoolean(false);
        paused = new AtomicBoolean(false);
        if (maxUpdatesBetweenRender > 0) {
            this.maxUpdatesBetweenRender = maxUpdatesBetweenRender;
        } else {
            this.maxUpdatesBetweenRender = MAX_UPDATES_BETWEEN_RENDER;
        }
    }

    public void start() {
        if (gameLoopThread != null) {
            if (gameLoopThread.isAlive()) {
                return;
            }
        }

        gameLoopThread = new Thread(() -> {
            gameLoop();
        });

        gameLoopThread.start();
        running.set(true);
    }

    private void gameLoop() {
        // we will need the last update time.
        long lastUpdateTime = System.nanoTime();
        // store the time we started this will be used for updating map and charcter animations
        long currTime = System.currentTimeMillis();
        resetFpsCounter();

        while (running.get()) {
            if (!paused.get()) {
                long now = System.nanoTime();
                long elapsedTime = System.currentTimeMillis() - currTime;
                currTime += elapsedTime;

                int updateCount = 0;
                // do as many game updates as we need to, potentially playing catchup.
                while (now - lastUpdateTime >= TIME_BETWEEN_UPDATES && updateCount < maxUpdatesBetweenRender) {
                    update(elapsedTime);//Update the entity movements and collision checks etc (all has to do with updating the games status i.e  call move() on Enitites)
                    lastUpdateTime += TIME_BETWEEN_UPDATES;
                    updateCount++;
                }

                // if for some reason an update takes forever, we don't want to do an insane number of catchups.
                // if you were doing some sort of game that needed to keep EXACT time, you would get rid of this.
                if (now - lastUpdateTime >= TIME_BETWEEN_UPDATES) {
                    lastUpdateTime = now - TIME_BETWEEN_UPDATES;
                }

                render(elapsedTime); // draw call for rendering sprites etc

                frameCount++;
                long lastRenderTime = now;

                //Yield until it has been at least the target time between renders. This saves the CPU from hogging.
                while (now - lastRenderTime < TIME_BETWEEN_UPDATES && now - lastUpdateTime < TIME_BETWEEN_UPDATES) {
                    Thread.yield();
                    now = System.nanoTime();
                }
            } else {//so we dont eat processor time when paused
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ex) {
                    //do nothing
                }
            }
        }
    }

    public void pause() {
        paused.set(true);
    }

    public void stop() {
        paused.set(false);
        running.set(false);
        gameLoopThread.interrupt();
    }

    public void resume() {
        resetFpsCounter();
        paused.set(false);
    }

    public boolean isPaused() {
        return paused.get();
    }

    public boolean isRunning() {
        return running.get();
    }

    private void resetFpsCounter() {
        fpsCounterStartTime = System.currentTimeMillis();
        frameCount = 0;
    }

    public double getAverageFps() {
        if (fpsCounterStartTime == 0) {
            return 0;
        }

        return (double) frameCount / ((System.currentTimeMillis() - fpsCounterStartTime) / 1000d);
    }

    // updtes position and animation of sprites
    public abstract void update(long elapsedTime);

    // where the actual draw calls happen
    public abstract void render(long elapsedTime);
}
