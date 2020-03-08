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
 * ElectricFence is a Factory object.
 * The ElectricFence is a solid object and does not move.
 * ElectricFence has 2 states and interacts with other objects differently depending on which state it is in.
 * @author Shan_
 */
public class ElectricFence extends FactoryObject implements Runnable{

    File efOffFile = new File("src/Images/efOff.png");
    File efOnFile = new File("src/Images/efOn.png");
    BufferedImage image;
    private Lock lock;
    boolean state = false;
    
    /**
     * ElectricFence Constructor 
     * @param canvas JPanel
     * @param graphics Graphics2D
     * @param masterLock Lock
     */
    public ElectricFence(JPanel canvas, Graphics2D graphics, Lock masterLock) {
        super(0, 0, 180, 180, canvas, true, false,graphics);
        this.x = dimension.width - 370;
        this.y = dimension.height - ySize + 10;
        this.lock = masterLock;
        image = loadImage(efOffFile);
    }

    /**
     * sets state
     * @param state boolean
     */
    public void setState(boolean state) {
        this.state = state;
    }

    /**
     * returns state 
     * @return boolean
     */
    public boolean isState() {
        return state;
    }
    
    /**
     * returns a rectangle. Used for the collision for the side of the ElectricFence.
     * @return Rectangle
     */
    public Rectangle getRectangleX(){
        return new Rectangle(x - 20,y + 20,xSize, ySize);
    }
    
    /**
     * returns a rectangle. Used for the collision for the top of the ElectricFence.
     * @return Rectangle
     */
    public Rectangle getRectangleY(){
        return new Rectangle(x + 5,y - 20,xSize, ySize - 140);
    }
    
    /**
     * returns a rectangle. Used for the collision.
     * @return Rectangle
     */
    public Rectangle getRectangle(){
        return new Rectangle(x,y,xSize, ySize);
    }
    
    /**
     * Sequence that runs while thread is running.
     */
    @Override
    public void run() {
        while (running){
            checkState();
            
            draw(image);
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
     * Draws the image on the canvas. The image is dependant on what image is given.
     * @param image BufferedImage
     */
    @Override
    public void draw(BufferedImage image){
        lock.lock();
        try {
            graphics.drawImage(image, x, y, xSize, ySize, null);
        } 
        catch (Exception e) {
            System.out.println("Error drawing Image: " + e);
        } 
        finally {
            lock.unlock();
        }
    }
    /**
     * Clears the current position of the ElectricFence on the canvas.
     */
    @Override
    public void clearRect() {
        lock.lock();
         try {
            graphics.setColor(java.awt.Color.WHITE);
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
    
    /**
     * changes the image depending on the state.
     */
    public void checkState(){
        if(!state){
            image = loadImage(efOffFile);
        }
        else{
            image = loadImage(efOnFile);
        }
    }
    
    
}
