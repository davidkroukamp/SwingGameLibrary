/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.swinggamelibrary;

import java.util.HashMap;

/**
 *
 * @author dkrou
 */
public class AnimationCache {

    private static AnimationCache single_instance = null;
    private final HashMap<String, Animation> animationCache;

    private AnimationCache() {
        this.animationCache = new HashMap<>();
    }

    public static AnimationCache getInstance() {
        if (single_instance == null) {
            single_instance = new AnimationCache();
        }

        return single_instance;
    }

    public void addAnimation(String name, Animation animation) {
        this.animationCache.put(name, animation);
    }

    public void removeAnimation(String name) {
        this.animationCache.remove(name);
    }

    public Animation getAnimation(String name) {
        return this.animationCache.get(name);
    }

}
