/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.swinggamelibrary;

import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;
import org.xml.sax.SAXException;

/**
 *
 * @author dkrou
 */
public class SpriteFrameCache {

    private static SpriteFrameCache single_instance = null;
    private final MultiValuedMap<String, LinkedList<SpriteFrame>> sprites;

    private SpriteFrameCache() {
        this.sprites = new HashSetValuedHashMap<>();
    }

    public static SpriteFrameCache getInstance() {
        if (single_instance == null) {
            single_instance = new SpriteFrameCache();
        }

        return single_instance;
    }

    public void addSpriteFrameWithKey(String key, SpriteFrame spriteFrame) {
        if (this.sprites.get(key).isEmpty()) {
            LinkedList<SpriteFrame> spriteFrames = new LinkedList<>();
            spriteFrames.add(spriteFrame);
            this.sprites.put(key, spriteFrames);
        } else {
            //this.sprites.put(key, spriteFrames);
        }
    }

    public void addSpriteFramesWithKey(String key, LinkedList<SpriteFrame> spriteFrames) {
        this.sprites.put(key, spriteFrames);
    }

    // use Texturepacker or https://github.com/leafsax/SpriteSplitter/tree/master to gen plist for spritesheets
    // this calls addSpriteFrameWithKey for each sprite in the sprite sheet, usong the plist file as the key
    // otherwise simply use getSpriteFrameByName to get specific sprites off of the sheet usoing the names in the plist
    public void addSpriteFramesWithFile(String key, File pListFile, BufferedImage spriteSheet) throws ParserConfigurationException, SAXException, IOException {
        XML xml = new XMLDocument(pListFile);
        List<XML> keys = xml.nodes("/plist/dict/dict/key");
        List<XML> dicts = xml.nodes("/plist/dict/dict/dict");
        LinkedList<SpriteFrame> spriteFrames = new LinkedList<>();

        for (int i = 0; i < dicts.size(); i++) {
            String textureRect = "";
            List<XML> dictForKeyNodes = dicts.get(i).nodes("*");
            for (int x = 0; x < dictForKeyNodes.size(); x++) {
                if (dictForKeyNodes.get(x).node().getTextContent().equals("textureRect")) {
                    textureRect = dictForKeyNodes.get(x + 1).node().getTextContent();
                    break;
                }
            }

            textureRect = textureRect.replace("{", "").replace("}", "");
            List<Integer> spriteRect = Arrays.stream(textureRect.split(",")).map(Integer::parseInt).collect(Collectors.toList());
            SpriteFrame spriteFrame = new SpriteFrame(keys.get(i).node().getTextContent(), spriteSheet.getSubimage(spriteRect.get(0), spriteRect.get(1), spriteRect.get(2), spriteRect.get(3)));
            spriteFrames.add(spriteFrame);
        }

        this.addSpriteFramesWithKey(key, spriteFrames);
    }

    public void removeSpriteFrames() {
        this.sprites.clear();
    }

    public void removeSpriteFrameByName(String name) {
        Iterator spriteIterator = this.sprites.entries().iterator();
        while (spriteIterator.hasNext()) {
            Map.Entry pair = (Map.Entry) spriteIterator.next();
            if (((SpriteFrame) pair.getValue()).getName().equals(name)) {
                spriteIterator.remove();
            }
        }
    }

    public void removeSpriteFrameByKey(String key) {
        this.sprites.remove(key);
    }

    public LinkedList<SpriteFrame> getSpriteFramesByKey(String key) {
        LinkedList spriteFrames = new LinkedList<>();

        this.sprites.entries().stream().filter((pair) -> (((String) pair.getKey()).equals(key))).forEachOrdered((pair) -> {
            spriteFrames.addAll(pair.getValue());
        });

        return spriteFrames;
    }

    public SpriteFrame getSpriteFrameByName(String name) {
        for (Map.Entry pair : this.sprites.entries()) {
            LinkedList<SpriteFrame> spriteFrames = (LinkedList<SpriteFrame>) pair.getValue();
            Optional<SpriteFrame> foundSpriteFrame = spriteFrames.stream().filter((sp) -> sp.getName().equals(name)).findFirst();
            if (foundSpriteFrame.isPresent()) {
                return foundSpriteFrame.get();
            }
        }

        return null;
    }

}
