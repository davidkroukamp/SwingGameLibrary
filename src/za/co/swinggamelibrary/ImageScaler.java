/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.swinggamelibrary;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author dkrou
 */
public class ImageScaler {

    private static ImageScaler single_instance = null;

    public static ImageScaler initialise(Dimension designResolution, Dimension dimension) {
        if (single_instance == null) {
            single_instance = new ImageScaler(designResolution, dimension);
        }

        return single_instance;

    }

    public static ImageScaler getInstance() {
        if (single_instance == null) {
            // TODO throw exception Director needs to be rrated with desgn metrics set before this is instanitiated and usable
        }

        return single_instance;
    }

    private final Dimension origScreenSize;
    private final Dimension newScreenSize;

    private ImageScaler(Dimension origScreenSize, Dimension newScreenSize) {
        this.origScreenSize = origScreenSize;
        this.newScreenSize = newScreenSize;
    }

    public double getWidthScaleFactor() {
        return (double) newScreenSize.width / origScreenSize.width;
    }

    public double getHeightScaleFactor() {
        return (double) newScreenSize.height / origScreenSize.height;
    }

    public BufferedImage scaleImage(BufferedImage img) {
        int width = (int) (img.getWidth() * getWidthScaleFactor());
        int height = (int) (img.getHeight() * getHeightScaleFactor());
        return scaleImage(img, width, height);
    }

    public ArrayList<BufferedImage> scaleImages(ArrayList<BufferedImage> images) {
        ArrayList<BufferedImage> imgs = new ArrayList<>();
        images.forEach((bImg) -> {
            int width = (int) (bImg.getWidth() * getWidthScaleFactor());
            int height = (int) (bImg.getHeight() * getHeightScaleFactor());
            imgs.add(ImageScaler.scaleImage(bImg, width, height));
        });
        return imgs;
    }

    public static BufferedImage scaleImage(BufferedImage bimg, int width, int height) {
        BufferedImage bi;
        try {
            bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
            Graphics2D g2d = (Graphics2D) bi.createGraphics();
            Graphics2DHelper.applyRenderHints(g2d); // TODO move out to utils class
            g2d.drawImage(bimg, 0, 0, width, height, null);
        } catch (Exception e) {
            return null;
        }
        return bi;
    }
}
