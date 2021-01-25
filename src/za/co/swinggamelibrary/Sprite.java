/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.swinggamelibrary;

import java.awt.Graphics2D;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.Set;

/**
 *
 * @author dkrou
 */
public class Sprite extends Node {

    private final Animator animator;
    private boolean isFlippedX;
    private boolean isFlippedY;
    private boolean restrictMovement;
    private INode movementRestrictedNode;
    private Set<Direction> restrictMovementDirections;

    public Sprite(String spriteFrameName) {
        super(0, 0, 0, 0);
        SpriteFrame spriteFrame = SpriteFrameCache.getInstance().getSpriteFrameByName(spriteFrameName);
        LinkedList<SpriteFrame> spriteFrames = new LinkedList<>();
        spriteFrames.add(spriteFrame);
        this.animator = new Animator(Animation.createWithSpriteFrames(spriteFrames, 0, 0));
        this.setWidth(this.animator.getCurrentImage().getWidth());
        this.setHeight(this.animator.getCurrentImage().getHeight());
        this.setFlippedX(false);
        this.setMovementRestrictedToParent(false, EnumSet.of(Direction.NONE));
        this.setMovementRestrictedToNode(null, EnumSet.of(Direction.NONE));
    }

    public Sprite(Animation animation) {
        super(0, 0, 0, 0);
        this.animator = new Animator(animation);
        this.setWidth(this.animator.getCurrentImage().getWidth());
        this.setHeight(this.animator.getCurrentImage().getHeight());
        this.setFlippedX(false);
        this.setMovementRestrictedToParent(false, EnumSet.of(Direction.NONE));
        this.setMovementRestrictedToNode(null, EnumSet.of(Direction.NONE));
    }

    public Sprite(SpriteFrame spriteFrame) {
        super(0, 0, spriteFrame.getImage().getWidth(), spriteFrame.getImage().getHeight());
        LinkedList<SpriteFrame> spriteFrames = new LinkedList<>();
        spriteFrames.add(spriteFrame);
        this.animator = new Animator(Animation.createWithSpriteFrames(spriteFrames, 0, 0));
        this.setFlippedX(false);
        this.setMovementRestrictedToParent(false, EnumSet.of(Direction.NONE));
        this.setMovementRestrictedToNode(null, EnumSet.of(Direction.NONE));
    }

    @Override
    public void update(long elapsedTime) {
        super.update(elapsedTime);
        this.animator.update(elapsedTime);
    }

    @Override
    public void render(Graphics2D g2d) {
        int x = this.getScreenX();
        int y = this.getScreenY();
        int width = (int) this.getWidth();
        int height = (int) this.getHeight();

        if (isFlippedX) {
            x = x + width;
            width = -width;
        }
        
        if (isFlippedY) {
            y = y + height;
            height = -height;
        }

        g2d.drawImage(this.animator.getCurrentImage(), x, y, width, height, null);

        // flip it vertically i.e. isFlippedY
        // g2.drawImage(image, x, y + height, width, -height, null);
        super.render(g2d);
    }

    @Override
    public void onEnter() {
        this.animator.start();
    }

    public void setFlippedX(boolean isFlippedX) {
        this.isFlippedX = isFlippedX;
    }

    public boolean getFlippedX() {
        return this.isFlippedX;
    }

    public void setFlippedY(boolean isFlippedY) {
        this.isFlippedY = isFlippedY;
    }

    public boolean getFlippedY() {
        return this.isFlippedY;
    }

    public void setMovementRestrictedToParent(boolean restrictMovementToParent, Set<Direction> directions) {
        this.restrictMovement = restrictMovementToParent;
        this.restrictMovementDirections = directions;
        this.movementRestrictedNode = null;
    }

    public boolean isMovementRestricted() {
        return this.restrictMovement;
    }

    public void setMovementRestrictedToNode(INode node, Set<Direction> directions) {
        this.movementRestrictedNode = node;
        this.restrictMovementDirections = directions;
        this.restrictMovement = true;
    }

    public INode getMovementRestrictedToNode() {
        return this.movementRestrictedNode == null ? this.getParent() : this.movementRestrictedNode;
    }

    public void onMovementRestricted() {
    }

    @Override
    public void setX(int x) {
        // we must have a parent thus be added to a node
        if (this.getParent() == null || !this.restrictMovement) {
            super.setX(x);
            return;
        }

        // movement is restricted
        int newScreenX = (int) (ImageScaler.getInstance().getWidthScaleFactor() * x);
        double widthRestriction = this.movementRestrictedNode != null ? this.movementRestrictedNode.getWidth() : this.getParent().getWidth();
        if (newScreenX < 0
                && (this.restrictMovementDirections.contains(Direction.LEFT) || this.restrictMovementDirections.contains(Direction.ALL))) {
            onMovementRestricted();
        } else if (newScreenX + getWidth() > widthRestriction
                && (this.restrictMovementDirections.contains(Direction.RIGHT) || this.restrictMovementDirections.contains(Direction.ALL))) {
            onMovementRestricted();
        } else {
            super.setX(x);
        }
    }

    @Override
    public void setY(int y) {
        // we must have a parent thus be added to a node
        if (this.getParent() == null || !this.restrictMovement) {
            super.setY(y);
            return;
        }

        // movement is restricted
        int newScreenY = (int) (ImageScaler.getInstance().getHeightScaleFactor() * y);
        double heightRestriction = this.movementRestrictedNode != null ? this.movementRestrictedNode.getHeight() : this.getParent().getHeight();
        if (newScreenY < 0
                && (this.restrictMovementDirections.contains(Direction.TOP) || this.restrictMovementDirections.contains(Direction.ALL))) {
            onMovementRestricted();
        } else if (newScreenY + this.getHeight() > heightRestriction
                && (this.restrictMovementDirections.contains(Direction.BOTTOM) || this.restrictMovementDirections.contains(Direction.ALL))) {
            onMovementRestricted();
        } else {
            super.setY(y);
        }
    }

    public enum Direction {
        NONE, ALL, TOP, BOTTOM, LEFT, RIGHT
    }
}
