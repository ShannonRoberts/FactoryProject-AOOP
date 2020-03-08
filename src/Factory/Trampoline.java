/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Factory;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.locks.Lock;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Trampoline is a Factory object.
 * The trampoline is a solid object and does not move.
 * Each time the trampoline is hit the trampoline image changes to show a bounce.
 * @author Shan_
 */
public class Trampoline extends FactoryObject implements Runnable, IAnimation{
    private File trampFile1 = new File("src/Images/trampoline1.png");
    private File trampFile2 = new File("src/Images/trampoline2.png");
    private BufferedImage image;
    private BufferedImage image2;
    
    private Lock lock;

    /**
     * Trampoline Constructor
     * @param canvas JPanel
     * @param graphics Graphics2D
     * @param masterLock Lock
     */
    public Trampoline(JPanel canvas, Graphics2D graphics, Lock masterLock) {
        super(0, 0, 230, 120, canvas, true, false, graphics);
        this.x = 0 + 240;
        this.y = dimension.height - ySize +45;
        this.lock = masterLock;
        image = loadImage(trampFile1);
        image2 = loadImage(trampFile2);
        
    }

    /**
     * returns rectangle of trampoline. Used for collision.
     * @return Rectangle
     */
    public Rectangle getRectangle(){
        return new Rectangle(x,y - 10,xSize,ySize + 10);
    }
    
    /**
     * Sequence that runs while thread is running.
     */
    @Override
    public void run() {
        while(running){
            if(collision){
                hitAnimation();
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
            clearRect();
        }
    }
    
    /**
     * draws a sequence of images to act like an animation.
     */
    @Override
    public void hitAnimation() {
        draw(image2);
        
    }
    
    /**
     * Draws the image on the canvas. Image is dependant on which image is given.
     * @param i BufferedImage
     */
    @Override
    public void draw(BufferedImage i) {
       lock.lock();
        try{     
        graphics.drawImage(i, x, y, xSize, ySize, canvas);  
        }
        catch(Exception e){
            System.out.println("Error drawing Image: " + e);
        }
        finally{
            lock.unlock();
        }
        try{
            Thread.sleep(100); // shows images during animation
        }
        catch(Exception e){
            System.out.println("Error sleeping thread: " + e);
        }
    }
    
    /**
     * Clears the current position of the Trampoline on the canvas.
     */
    @Override
    public void clearRect() {
        lock.lock();
        try {
            graphics.setColor(Color.WHITE);
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
        } 
        catch (Exception e) {
            System.out.println("Error loading Image: " + e);
        }
        finally{
            lock.unlock();
        }
      
        return image;
    }

   
}
