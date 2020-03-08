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
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.imageio.ImageIO;
import javax.swing.JPanel;



/**
 * BouncyBall is a deployable object.
 * It moves through the factory and interacts with the objects.
 * Only have 5 bouncy balls at one time
 * @author Shan_
 */
public class BouncyBall extends DeployableObject implements Runnable{

    private boolean hitTramp;
    private Random r = new Random();
    private int direction = 0;
    private final double gravity = 1;
    private File ballImageFile = new File("src/Images/ball.jpg");
    private boolean bouncing = false;
    private Lock lock;
    private boolean bounceUP = false;
    private boolean flipped = false; //makes sure its not constantly being flipped
    private BufferedImage image;

    /**
     * BouncyBall Constructor
     * @param x int
     * @param y int
     * @param xSize int
     * @param ySize int
     * @param canvas JPanel
     * @param running boolean
     * @param collision boolean
     * @param graphics Graphics2D
     * @param masterLock Lock
     */
    public BouncyBall(int x, int y, int xSize, int ySize, JPanel canvas, boolean running, boolean collision,Graphics2D graphics, Lock masterLock) {
        super(x, y, xSize, ySize, 5,5,canvas, running, collision,graphics);
        this.lock = masterLock;
        image = loadImage(ballImageFile);
        updateDirection(); //so each ball starts in a different direction
    }
    
    /**
     * Sets the hitTramp.
     * @param hitTramp boolean
     */
    public void setHitTramp(boolean hitTramp) {
        this.hitTramp = hitTramp;
    }
    
    /**
     * Sets the bounceUp.
     * @param bounceUP boolean
     */
    public void setBounceUP(boolean bounceUP) {
        this.bounceUP = bounceUP;
    }

    /**
     * returns flipped. 
     * @return boolean
     */
    public boolean isFlipped() {
        return flipped;
    }

    
    /**
     * sets flipped. 
     * @param flipped boolean
     */
    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }
    
    /**
     * return a rectangle of the ball size. Used for collision.
     * @return Rectangle
     */
    public Rectangle getRectangle(){ //used for hitting the side of an object
        return new Rectangle(x , y , xSize + 5 , ySize  +5);
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
                Thread.sleep(23); 
            } 
            catch(Exception e){
                System.out.println("Error sleeping thread: " + e);
            } 
            clearRect();
        }
        
    }
    
    /**
     * draws ball image into screen.
     */
    @Override
    public void draw() {
 
        lock.lock();
        try{     
            graphics.drawImage(image, x, y, xSize, ySize,canvas);  
        }
        catch(Exception e){
            System.out.println("Error drawing Image: " + e);
        }
        finally{
            lock.unlock();
        }

    }
    
    
    
    /**
     * Sets the direction of the ball. 
     * Used when ball is first created to make the balls path more random.
     */
    public void updateDirection(){
        direction = r.nextInt(6); //angle
       
        if(direction == 0){
            xd = 2;
        }
        if(direction == 1){
            xd = 5;
        }
        if(direction == 2){
            xd = 10;
        }
        if(direction == 3){
            xd = -2;
        }
        if(direction == 4){
            xd = -5;
        }
        if(direction == 5){
            xd = -10;
        }
        
    }
    
    /**
     * Used to move the ball to new position depending on where the ball is currently on the screen.
     */
    @Override
    public void update() {
        Dimension d = canvas.getSize();
        clearRect();
        
        if(x < 0 || x > d.width - 50){ //hits sides
            xd *= -1; 
        }
        if(y < 0 || y + ySize > d.height -10){ //hits roof or ground
            yd *= -1; 
        }
        
        if(y + ySize >= d.height){ // keeps it bouncing on the ground. Stops it from going through the floor.
            
            y = d.height - ySize - 10;
            
            if(!bouncing){ // without this the ball can get stuck in the ground

                yd = -yd* 0.8; // 0.8 is air friction
                bouncing = true;
            }
        }
        else{  
            yd += gravity;
            bouncing = false;
        }
        
        move();

       
    }
    
    /**
     * Determines how the ball reacts when colliding with another object.
     */
    @Override
    public void collide() {
        if(hitDeadly){
            stop();
            return;
        }
        if(bounceUP){
            flipYD();
            yd -= 2;
            y = y - 10;
        }
        else{
            flipXD();

        }

        if(hitTramp){
            flipYD();
            yd -= 4;
 
        }
        if(yd < 42){ // stops from forever increasing speed;
            yd += 3;
        }
        hitTramp = false;
        bounceUP = false;
        
    }
    
    /**
     * Clears the current image of the ball on the canvas.
     * 
     */
    @Override
    public void clearRect() {
        
        lock.lock();
        try {
            graphics.setColor(Color.WHITE);
            graphics.fillRect(x, y, xSize, ySize);
        } 
        catch (Exception e){
            System.out.println("Error clearing Image: " + e);
        }
        finally{
            lock.unlock();
        } 
    }
    
    /**
     * Used to stop running. Ball will be destroyed.
     */
    @Override
    public void stop() {
        clearRect();
        running = false;
    }
    
    /**
     * flips the x direction. sets flipped to true. 
     */
    public void flipXD(){
        xd *= -1;
        flipped = true;
    }
    
    /**
     * flips the y direction. sets flipped to true.
     */
    public void flipYD(){
        yd *= -1;
        flipped = true;
    }
    
    /**
     * moves the ball using the x and y variables.
     */
    @Override
    public void move(){
        x+= xd; // changes x postion
        y += yd; // changes y postion

    }
    
    /**
     * Determines where the ball spawns. Returns a random int.
     * @return int
     */
    @Override
    public int randomSpawn(){
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
