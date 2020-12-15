/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.swinggamelibrary;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;

/**
 *
 * @author dkrou
 */
public class SpriteFrameCache {

    private static SpriteFrameCache single_instance = null;
    private final MultiValuedMap<String, SpriteFrame> sprites;

    private SpriteFrameCache() {
        sprites = new HashSetValuedHashMap<>();
    }

    public static SpriteFrameCache getInstance() {
        if (single_instance == null) {
            single_instance = new SpriteFrameCache();
        }

        return single_instance;
    }

    public void addSpriteFramesWithKey(String key, SpriteFrame spriteFrame) {
        sprites.put(key, spriteFrame);
    }

    public void removeSpriteFrames() {
        sprites.clear();
    }

    public void removeSpriteFrameByName(String name) {
        Iterator spriteIterator = sprites.entries().iterator();
        while (spriteIterator.hasNext()) {
            Map.Entry pair = (Map.Entry) spriteIterator.next();
            if (((SpriteFrame) pair.getValue()).getName().equals(name)) {
                spriteIterator.remove();
            }
        }
    }

    public void removeSpriteFrameByKey(String key) {
        sprites.remove(key);
    }

    public ArrayList<SpriteFrame> getSpriteFramesByKey(String key) {
        ArrayList<SpriteFrame> spriteFrames = new ArrayList<>();
        for (Map.Entry pair : sprites.entries()) {
            if (((String) pair.getKey()).equals(key)) {
                spriteFrames.add((SpriteFrame) pair.getValue());
            }
        }

        return spriteFrames;
    }

    public SpriteFrame getSpriteFrameByName(String name) {
        for (Map.Entry pair : sprites.entries()) {
            if (((SpriteFrame) pair.getValue()).getName().equals(name)) {
                return (SpriteFrame) pair.getValue();
            }
        }

        return null;
    }

}
