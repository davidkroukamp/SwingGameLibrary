/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.swinggamelibrary;

import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 *
 * @author dkrou
 */
public class Graphics2DHelper {

    private final static RenderingHints TEXT_RENDER_HINT = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    private final static RenderingHints IMAGE_RENDER_HINTS = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    private final static RenderingHints COLOR_RENDER_HINTS = new RenderingHints(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
    private final static RenderingHints INTERPOLATION_RENDER_HINTS = new RenderingHints(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
    private final static RenderingHints RENDER_HINTS = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    private final static RenderingHints STROKE_HINTS = new RenderingHints(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);

    public static void applyRenderHints(Graphics2D g2d) {
        g2d.setRenderingHints(TEXT_RENDER_HINT);
        g2d.setRenderingHints(IMAGE_RENDER_HINTS);
        g2d.setRenderingHints(COLOR_RENDER_HINTS);
        g2d.setRenderingHints(INTERPOLATION_RENDER_HINTS);
        g2d.setRenderingHints(RENDER_HINTS);
        g2d.setRenderingHints(STROKE_HINTS);
    }
}
