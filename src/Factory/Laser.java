/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Factory;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Laser is a Factory object.
 * The Laser does not move.
 * The laser can shoot a beam that destroys deployable objects.
 * @author Shan_
 */
public class Laser extends FactoryObject implements Runnable{
    
    File laserFile = new File("src/images/laser.png");
    File laserBeamFile = new File("src/images/laserBeam.png");
    BufferedImage laserImage;
    BufferedImage beamImage;
    private Lock lock;
    boolean shoot = false;
    private int beamWidth = 1000;
    private int beamHeight = 30;
    private int beamX;
    private int beamY;

    /**
     * Laser Constructor
     * @param canvas JPanel
     * @param graphics Graphics2D
     * @param masterLock Lock
     */
    public Laser(JPanel canvas, Graphics2D graphics, Lock masterLock) {
        super(0, 0, 100, 70, canvas, true, false,graphics);
        this.lock = masterLock;
        this.x = dimension.width - xSize;
        this.y = dimension.height - 450;
        beamX = x - beamWidth;
        beamY = y + 25;
        laserImage = loadImage(laserFile);
        beamImage = loadImage(laserBeamFile);
    }

    /**
     * returns shoot 
     * @return boolean
     */
    public boolean getShoot() {
        return shoot;
    }

    /**
     * sets shoot 
     * @param shoot boolean
     */
    public void setShoot(boolean shoot) {
        this.shoot = shoot;
    }

    /**
     * returns beamWidth 
     * @return int
     */
    public int getBeamWidth() {
        return beamWidth;
    }

    /**
     * returns beamHeight
     * @return int
     */
    public int getBeamHeight() {
        return beamHeight;
    }

    /**
     * returns beamX
     * @return int
     */
    public int getBeamX() {
        return beamX;
    }

    /**
     * returns beamY
     * @return int
     */
    public int getBeamY() {
        return beamY;
    }

    /**
     * returns a rectangle for the beam. Used for the collision.
     * @return Rectangle
     */
    public Rectangle getBeamRectangle(){
        return new Rectangle(beamX,beamY,beamWidth,beamHeight);
    }
    
    /**
     * Sequence that runs while thread is running.
     */
    @Override
    public void run() {
        
        while(running){
            draw(laserImage);
            if(shoot){
                drawBeam();
            }
            try{
                Thread.sleep(100);
            }
            catch(Exception e){
                System.out.println("Error sleeping thread: " + e);
            }
            clearRect();
        }
        
    }


    /**
     * Draws the laser image on the canvas. 
     * @param i BufferedImage
     */
    @Override
    public void draw(BufferedImage i){
         
        lock.lock();
        try {
            graphics.drawImage(i, x, y, xSize, ySize, null);
        } 
        catch (Exception e) {
            System.out.println("Error drawing Image: " + e);
        } 
        finally {
            lock.unlock();
        }
     
    }
   
    /**
     * Draws the beam image on the canvas.
     */
    public void drawBeam(){
        lock.lock();
        try {
            graphics.drawImage(beamImage, beamX, beamY, beamWidth, beamHeight, null);
        } 
        catch (Exception e) {
            System.out.println("Error drawing beam Image: " + e);
        } 
        finally {
            lock.unlock();
        }
    }
    
    /**
     * Clears the current position of the LaserBeam on the canvas.
     */
    @Override
    public void clearRect() {
        lock.lock();
        try {
            graphics.setColor(java.awt.Color.WHITE);
            graphics.fillRect(beamX, beamY, beamWidth, beamHeight);
        } 
        catch (Exception e) {
            System.out.println("Error clearing Image: " + e);
        }
        finally{
            lock.unlock();
         }
    }
    
    /**
     * Loads the image from the file given. Returns BufferedImage.
     * @param file File
     * @return BufferedImage
     */
    @Override
    public BufferedImage loadImage(File file) {
        BufferedImage image = null;
        lock.lock();
        try {
            image = ImageIO.read(file);
        } catch (Exception e) {
            System.out.println("Error loading Image: " + e);
        }
        finally{
            lock.unlock();
        }
        return image;
    }
    
    
    
    
}
