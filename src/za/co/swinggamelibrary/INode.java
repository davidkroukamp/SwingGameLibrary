/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.swinggamelibrary;

import java.awt.Graphics2D;
import java.util.List;

/**
 *
 * @author dkrou
 */
public interface INode {

    public void setWorldX(int x);

    public void setWorldY(int y);

    public int getWorldX();

    public int getWorldY();

    public void setScreenX(int x);

    public void setScreenY(int y);

    public int getScreenX();

    public int getScreenY();

    public void setWidth(int width);

    public void setHeight(int height);

    public double getWidth();

    public double getHeight();

    public boolean isVisible();

    public void setVisible(boolean visible);

    public void removeFromParent();

    public boolean isRemovedFromParent();

    public void update(long elapsedTime);

    public void render(Graphics2D g2d);

    public INode getParent();

    void setParent(INode node);

    public void add(INode node);

    public void remove(INode node);

    public List<INode> getNodes();

    public void removeAll();

    boolean intersects(INode node);
    
    int getChildCount();
    
    void setZOrder(int zOrder);
    
    int getZOrder();
    
    void onEnter();
    
    void onExit();
    
    boolean hasRendered();
}
