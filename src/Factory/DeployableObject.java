/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Factory;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JPanel;

/**
 * DeployableObject is an abstract class which is used for objects that move around the factory.
 * @author Shan_
 */
public abstract class DeployableObject implements Deployable{
    volatile protected int x;
    volatile protected int y;
    volatile protected int xSize;
    volatile protected int ySize;
    volatile protected double xd;
    volatile protected double yd;
    volatile protected boolean hitDeadly;
    volatile protected boolean hitBeam;
    static volatile protected JPanel canvas;
    volatile protected boolean running;
    volatile protected boolean collision;
    static volatile protected Graphics2D graphics;
    
    /**
     * DeployableObject Constructor
     * @param x int
     * @param y int
     * @param xSize int
     * @param ySize int
     * @param xd double
     * @param yd double
     * @param canvas JPanel
     * @param running boolean
     * @param collision boolean
     * @param graphics Graphics2D
     */
    public DeployableObject(int x, int y, int xSize, int ySize, double xd, double yd,JPanel canvas, boolean running, boolean collision, Graphics2D graphics) {
        this.x = x;
        this.y = y;
        this.xSize = xSize;
        this.ySize = ySize;
        this.xd = xd; //x direction/speed
        this.yd = yd; //y direction/speed
        this.hitDeadly = false; // used when hit a deadly object
        this.hitBeam = false;
        this.canvas = canvas;
        this.running = running;
        this.collision = collision;
        this.graphics = graphics;
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
     * return xd
     * @return double
     */
    public double getXd() {
        return xd;
    }

    /**
     * sets xd
     * @param xd double
     */
    public void setXd(double xd) {
        this.xd = xd;
    }

    /**
     * returns yd
     * @return double
     */
    public double getYd() {
        return yd;
    }

    /**
     * sets yd
     * @param yd double
     */
    public void setYd(double yd) {
        this.yd = yd;
    }

    /**
     * returns hitDeadly
     * @return boolean
     */
    public boolean isHitDeadly() {
        return hitDeadly;
    }

    /**
     * sets hitDeadly
     * @param hitDeadly boolean
     */
    public void setHitDeadly(boolean hitDeadly) {
        this.hitDeadly = hitDeadly;
    }

    /**
     * returns hitBeam
     * @return boolean
     */
    public boolean isHitBeam() {
        return hitBeam;
    }

    /**
     * sets hitBeam
     * @param hitLaser boolean
     */
    public void setHitBeam(boolean hitLaser) {
        this.hitBeam = hitLaser;
    }

    /**
     * returns canvas
     * @return JPanel
     */
    public JPanel getCanvas() {
        return canvas;
    }

    /**
     * sets canvas
     * @param canvas JPanel
     */
    public void setCanvas(JPanel canvas) {
        this.canvas = canvas;
    }

    /**
     * returns running
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

    
    
}
