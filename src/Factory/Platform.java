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
 * Platform is a Factory object.
 * The Platform is a solid object and does not move.
 * @author Shan_
 */
public class Platform extends FactoryObject implements Runnable{
    private BufferedImage image;
    private File platformFile = new File("src/images/platform.png");
    private Lock lock;

    /**
     * Platform Constructor
     * @param canvas JPanel
     * @param graphics Graphics2D
     * @param masterLock Lock
     */
    public Platform(JPanel canvas, Graphics2D graphics, Lock masterLock) {
        super(0, 0, 200, 60, canvas, true, false, graphics);
        this.lock = masterLock;
        this.x = 0;
        this.y = dimension.height - 380;
        image = loadImage(platformFile);
    }

    /**
     * returns a rectangle. Used for the collision for the side of the platform.
     * @return Rectangle
     */
    public Rectangle getRectangleX(){
        return new Rectangle(x + 20,y,xSize + 10, ySize - 5);
    }
    
    /**
     * returns a rectangle. Used for the collision for the top of the platform.
     * @return Rectangle
     */
    public Rectangle getRectangleY(){
        return new Rectangle(x,y - 10 ,xSize - 5, ySize + 30);
    }
    
    /**
     * sequence that runs while thread is running.
     */
    @Override
    public void run() {
        
        while(running){
            if(collision){
                clearRect();
                collision = false;
            }
            draw(image);
            try 
            { 
                Thread.sleep(100); 
            } 
            catch(Exception e){
                System.out.println("Error sleeping thread: " + e);
            } 
        }
     
    }

    
    /**
     * Draws the image on the canvas.
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
     * Clears the current position of the platform on the canvas.
     */
    @Override
    public void clearRect() {
        lock.lock();
        try {
            graphics.fillRect(x, y, xSize, ySize);
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
