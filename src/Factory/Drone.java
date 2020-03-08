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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;


/**
 * Drone is a deployable object.
 * It moves through the factory and interacts with the objects.
 * Only one drone in factory at any given time.
 * @author Shan_
 */
public class Drone extends DeployableObject implements Runnable{
    
    private File droneFile = new File("src/Images/drone_1.png");
    private Lock lock;
    private int direction;
    private Random r = new Random();
    private Timer timer;
    private BufferedImage image;
    
    
    /**
     * Drone constructor
     * @param canvas JPanel
     * @param x int
     * @param y int
     * @param graphics Graphics2D
     * @param masterLock Lock
     */
    public Drone(JPanel canvas, int x, int y, Graphics2D graphics,Lock masterLock) {
        super(x, y, 0, 0, 5, 5, canvas, true, false,graphics);
        xSize = 140;
        ySize = 70;
        this.lock = masterLock;
        image = loadImage(droneFile);
        updateDirection();
        timer = new Timer(1000,moveDirection);
        timer.start();
        
    }
    
   
    /**
     * Sequence that runs while thread is running.
     */
    @Override
    public void run() {

        while(running){
            if(collision){
                collide();
                collision = false;
            }
            update(); 
            draw();
            
            try 
            { 
                Thread.sleep(30); 
            } 
            catch(Exception e){
                System.out.println("Error sleeping thread: " + e);
            } 
            clearRect();
        } 
        
    }
    
    /**
     * draws drone image into screen.
     */
    @Override
    public void draw() {
        lock.lock();
        try{     
            Image droneImage = ImageIO.read(droneFile);   
            graphics.drawImage(droneImage, x, y, xSize, ySize,canvas);  
        }
        catch(Exception e){
            System.out.println("Error drawing Image: " + e);
        }
        finally{
            lock.unlock();
        }
       
    }

    /**
     * Used to move the drone to new position depending on where the drone is currently on the screen.
     */
    @Override
    public void update() {
        
        Dimension d = canvas.getSize();
        clearRect();
        
        if(x <= 0 || x >= d.width - 100){ //hits sides
            xd *= -1;
        }
        if(y <= 0 || y + ySize >= d.height -10){ //hits roof or ground
            yd *= -1;
        }

        move();

        
    }
    
    /**
     * Used to change the direction of the drone. 
     * 
     */
    public void updateDirection(){
        
        direction = r.nextInt(7); //angle
        
        if(direction == 0){
            xd = 0;
        }
        if(direction == 1){
            xd = 2;
        }
        if(direction == 2){
            xd = 5;
        }
        if(direction == 3){
            xd = 10;
        }
        if(direction == 4){
            xd = -2;
        }
        if(direction == 5){
            xd = -5;
        }
        if(direction == 6){
            xd = -10;
        }
 
    }

    /**
     * Determines how the drone reacts when colliding with another object.
     */
    @Override
    public void collide() {
        if(hitDeadly){
            stop();
            return;
        }
        xd *= -1;
        yd *= -1;
    }
    
    /**
     * Clears the current image of the drone on the canvas.
     *  
     */
    @Override
    public void clearRect() {
        lock.lock();
        try {
            graphics.setColor(Color.WHITE);
            graphics.fillRect(x, y, xSize, ySize);
        } catch (Exception e) {
            System.out.println("Error clearing Image: " + e);
        }
        finally{
            lock.unlock();
        } 
    }

    /**
     * Used to stop running. Drone will be destroyed.
     */
    @Override
    public void stop() {
        
        clearRect();
        timer.stop();
        running = false;
        
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
    
    /**
     * Method timer calls. Makes the drone change direction.
     */
    ActionListener moveDirection = (ActionEvent evt) -> {
 
            updateDirection();
    
    };
    
    /**
     * Determines where the drone spawns. Returns a random int.
     * @return int
     */
    @Override
    public int randomSpawn() {
        int spawn = r.nextInt(3);
        if(spawn == 0){
            spawn = 273;
        }
        else if(spawn == 1){
            spawn = 573;
        }
        else if(spawn == 2){
            spawn = 773;
        }
        else{
            spawn = 100;
        }
        return spawn;
    }

    /**
     * moves the drone with the x and y variable.
     */
    @Override
    public void move() {
        x+= xd;
        y += yd;
    }
    
}
