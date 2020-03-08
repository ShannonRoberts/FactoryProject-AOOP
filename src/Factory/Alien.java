/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Factory;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.locks.Lock;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Alien is a Factory object.
 * The alien is a solid object and does not move.
 * Each time the alien is hit the alien image changes to show a crack in the glass.
 * @author Shan_
 */
public class Alien extends FactoryObject implements Runnable, IAnimation{
    
    private File alienImageFile1 = new File("src/Images/alienTank1.png");
    private File alienImageFile2 = new File("src/Images/alienTank2.png");
    private File alienImageFile3 = new File("src/Images/alienTank3.png");
    private File alienImageFile4 = new File("src/Images/alienTank4.png");
    private File alienImageFile5 = new File("src/Images/alienTank5.png");
    private File alienImageFile6 = new File("src/Images/alienTank6.png");
    private File alienImageFile7 = new File("src/Images/alienTank7.png");
    private File alienImageFile8 = new File("src/Images/alienTank8.png");
    private BufferedImage image1;
    private BufferedImage image2;
    private BufferedImage image3;
    private BufferedImage image4;
    private BufferedImage image5;
    private BufferedImage image6;
    private BufferedImage image7;
    private BufferedImage image8;
    private int hit = 1;
    private Lock lock;
    
    /**
     * Alien Constructor
     * @param canvas JPanel
     * @param graphics Graphics2D
     * @param masterLock Lock
     */
    public Alien(JPanel canvas, Graphics2D graphics, Lock masterLock) {
        super(0,0, 150, 200, canvas, true, false, graphics);
        this.x = dimension.width - xSize;
        this.y = dimension.height - ySize;
        this.lock = masterLock;
        image1 = loadImage(alienImageFile1);
        image2 = loadImage(alienImageFile2);
        image3 = loadImage(alienImageFile3);
        image4 = loadImage(alienImageFile4);
        image5 = loadImage(alienImageFile5);
        image6 = loadImage(alienImageFile6);
        image7 = loadImage(alienImageFile7);
        image8 = loadImage(alienImageFile8);
    }
    
    /**
     * returns a rectangle of the aliens size. Used for collision.
     * @return Rectangle
     */
    public Rectangle getRectangle(){
        return new Rectangle(x,y - 10, xSize,ySize);
    }
    
    /**
     * determines what image is shown depending on how many times it has been hit. 
     * On the 6th hit it will run an animation to heal itself and go back to the first image.
     */
    @Override
    public void hitAnimation(){
        
        if(hit == 1){
            draw(image1);
        }
        else if(hit == 2){
            draw(image2);
        }
        else if(hit == 3){
            draw(image3);
        }
        else if(hit == 4){
            draw(image4);
        }
        else if(hit == 5){
            draw(image5);
        }
        else if(hit == 6){
           draw(image6);
           
           draw(image7);
           
           draw(image6);
           
           draw(image8);
           
           hit = 1;
           
        }
      
    }
    
    /**
     * Draws the image on the canvas. The image is dependant on what image is given.
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
            Thread.sleep(100);// wait inbetween images for animation
        }
        catch(Exception e){
            System.out.println("Error sleeping thread: " + e);
        }
        
    }
    
    /**
     * Sequence that runs while thread is running.
     */
    @Override
    public void run() {
        while(running){
            if(collision){
                hit++;
                hitAnimation();
                collision = false;
            }
            hitAnimation();

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
     * Clears the current position of the Alien on the canvas.
     * 
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
        } catch (Exception e) {
            System.out.println("Error loading Image: " + e);
        }
        finally{
            lock.unlock();
        }
   
        return image;
    }

    
}
