/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.swinggamelibrary;

import java.awt.image.BufferedImage;

/**
 *
 * @author dkrou
 */
public class SpriteFrame {

    private final String name;
    private final BufferedImage image;

    // TODO this will be changed or another constructor added to accept the file or path to the file?
    public SpriteFrame(String name, BufferedImage image) {
        this.name = name;
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

}
