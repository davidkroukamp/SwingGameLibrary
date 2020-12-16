/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.swinggamelibrary;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author dkrou
 */
public class AudioEngine {

    private static AudioEngine single_instance = null;

    private AudioEngine() {
    }

    public static AudioEngine getInstance() {
        if (single_instance == null) {
            single_instance = new AudioEngine();
        }

        return single_instance;
    }

    public int play(String file, double volume) {
        throw new NotImplementedException();
    }

    public int play(String file, boolean loop, double volume) {
        throw new NotImplementedException();
    }

    public void pause(int audioId) {

    }

    public void pauseAll() {

    }

    public void stop(int audioId) {

    }

    public void stopAll() {

    }

    public void resume(int audioId) {

    }

    public void resumeAll() {

    }

    public void uncache(int audioId) {

    }

    public void uncacheAll() {

    }
}
