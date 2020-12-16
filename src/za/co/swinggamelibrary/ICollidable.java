/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.swinggamelibrary;

import java.awt.geom.Rectangle2D;

/**
 *
 * @author dkrou
 */
public interface ICollidable {

    public Rectangle2D getBounds2D();

    public boolean intersects(ICollidable collidable);

    public void onCollision(INode node);

}
