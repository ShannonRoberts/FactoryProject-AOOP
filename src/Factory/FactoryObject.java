/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Factory;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.concurrent.locks.Lock;
import javax.swing.JPanel;

/**
 * FactoryObject is an abstract class used for solid objects that don't move around the factory.
 * @author Shan_
 */
public abstract class FactoryObject implements SolidObject{
    
    volatile protected int x;
    volatile protected int y;
    volatile protected int xSize;
    volatile protected int ySize;
    volatile protected JPanel canvas;
    volatile protected boolean running;
    volatile protected boolean collision;
    volatile protected Dimension dimension;
    volatile protected Graphics2D graphics;
    
    /**
     * FactoryObject Constructor
     * @param x int
     * @param y int
     * @param xSize int
     * @param ySize int
     * @param canvas JPanel
     * @param running boolean
     * @param collision boolean
     * @param graphics Graphics2D
     */
    public FactoryObject(int x, int y, int xSize, int ySize, JPanel canvas, boolean running, boolean collision, Graphics2D graphics) {
        this.x = x;
        this.y = y;
        this.xSize = xSize;
        this.ySize = ySize;
        this.canvas = canvas;
        this.running = running;
        this.collision = collision;
        this.graphics = graphics;
        this.dimension = canvas.getSize();
    }
    
    /**
     * returns x
     * @return int
     */
    public int getX() {
        return x;
    }

    /**
     * sets x
     * @param x int
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * returns y
     * @return int
     */
    public int getY() {
        return y;
    }

    /**
     * sets y
     * @param y int
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * returns xSize
     * @return int
     */
    public int getxSize() {
        return xSize;
    }

    /**
     * sets xSize
     * @param xSize int
     */
    public void setxSize(int xSize) {
        this.xSize = xSize;
    }

    /**
     * returns ySize
     * @return int
     */
    public int getySize() {
        return ySize;
    }

    /**
     * sets ySize
     * @param ySize int
     */
    public void setySize(int ySize) {
        this.ySize = ySize;
    }

    /**
     * return canvas
     * @return JPanel
     */
    public JPanel getCanvas() {
        return canvas;
    }

    /**
     * set canvas
     * @param canvas JPanel
     */
    public void setCanvas(JPanel canvas) {
        this.canvas = canvas;
    }

    /**
     * return running
     * @return boolean
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * sets running
     * @param running boolean
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * returns collision
     * @return boolean
     */
    public boolean isCollision() {
        return collision;
    }

    /**
     * sets collision
     * @param collision boolean
     */
    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    /**
     * returns dimension
     * @return Dimension
     */
    public Dimension getDimension() {
        return dimension;
    }

    /**
     * sets dimension
     * @param dimension Dimension
     */
    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }
    
    
    
}
