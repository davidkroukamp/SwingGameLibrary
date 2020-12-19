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

    public void setX(double x);

    public void setY(double y);

    public void setWidth(double width);

    public void setHeight(double height);

    public double getX();

    public double getY();

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
}
