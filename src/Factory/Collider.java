/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Factory;

import java.awt.Rectangle;
import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The collider class holds all active objects within the factory and checks collision. 
 * The collider will check each object to see if it has collided with another object.
 * @author Shan_
 */
public class Collider implements Runnable,ColliderInterface{
    private Vector <BouncyBall> balls;  
    private Alien alien;
    private Trampoline tramp;
    private ElectricFence eFence;
    private Laser laser;
    private Platform platform;
    private Drone drone;
    private volatile boolean running = true;
    private Rectangle a;
    private Rectangle t;
    private Rectangle ef;
    private Rectangle efX;
    private Rectangle efY;
    private Rectangle lb;
    private Rectangle pX;
    private Rectangle pY;
    private Lock lock;
    

    public Collider(Alien alien, Drone drone,Trampoline tramp, ElectricFence eFence, Laser laser, Platform platform, Lock masterLock) {
        balls = new Vector<BouncyBall>();
        this.alien = alien;
        this.drone = drone;
        this.tramp = tramp;
        this.eFence = eFence;
        this.laser = laser;
        this.platform = platform;
        
        this.lock = masterLock;
        if (alien != null) {
            a = alien.getRectangle();
        }
        if(tramp != null){
            t = tramp.getRectangle();//turns tramp into rectangle
        }
        if(eFence != null){
            efX = eFence.getRectangleX();//turns eFence into rectangle
            efY = eFence.getRectangleY();
            ef = eFence.getRectangle();
        }
        if(laser != null){
            lb = laser.getBeamRectangle();
            
        }
        if(platform != null){
            pX = platform.getRectangleX();//turns platform into rectangle
            pY = platform.getRectangleY();
        }
        
    }

    
    /**
     * sets eFence
     * @param eFence ElectricFence
     */
    public void seteFence(ElectricFence eFence) {
        this.eFence = eFence;
    }

    /**
     * returns balls
     * @return Vector
     */
    public Vector<BouncyBall> getBalls() {
        return balls;
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
     * sets drone
     * @param drone Drone
     */
    public void setDrone(Drone drone) {
        this.drone = drone;
    }
    
    
    /**
     * Sequence that runs while thread is running.
     */
    @Override
    public void run() {
        while(running){
            checkCollision();
            droneCheckCollisions();
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
     * Used to check if the drone collides with objects.
     */
    @Override
    public void droneCheckCollisions(){
       
        if (drone != null) {
            Rectangle d = new Rectangle(drone.getX(), drone.getY(), drone.getxSize(), drone.getySize());
            if (lb != null) {
                if (laser.getShoot() && d.intersects(lb)) {
                    
                    drone.setHitDeadly(true);
                    drone.setCollision(true);
                    drone = null;
                }
            }
            if (ef != null) {
                if (d.intersects(ef)) {
                    if (eFence.isState()) {
                        
                        drone.setHitDeadly(true);
                        drone.setCollision(true);
                        drone = null;

                    } 
                    else {
                        drone.setCollision(true);
                    }
                }
            }
            if (t != null) {
                if (d.intersects(t)) {
                    drone.setCollision(true);
                }
            }
            
            if (pX != null) {
                if (d.intersects(pX)) {
                    drone.setCollision(true);
                }
            }
            
            if (a != null) {
                if (d.intersects(a)) {
                    drone.setCollision(true);
                    alien.setCollision(true);
                }
            }

            if (balls != null && !balls.isEmpty()) {
                for (int index = 0; index < balls.size(); index++) {
                    if (balls.get(index).isRunning()) {
                        try{
                            lock.lock();
                            Rectangle b = balls.get(index).getRectangle();
                        if (b.intersects(d)) {
                            balls.get(index).setHitDeadly(true);
                            balls.get(index).setCollision(true);
                            balls.remove(index);
                            return;
                        }
                        }
                        catch(Exception e){}
                        finally{
                            lock.unlock();
                        }
                        
                    }
                }
            }
        }
    }
    
    /**
     * Used to check if any bouncy balls collide with objects. 
     */
    @Override
    public void checkCollision() {
        
     if(balls != null){
         
        for (int index = 0; index < balls.size(); index++) {
            lock.lock();
            
            try{
            if (balls.get(index) != null) {
                Rectangle b = new Rectangle(balls.get(index).getRectangle());//turn ball into Rectangle
                
                if (!balls.get(index).isFlipped() && a != null && b.intersects(a)) {
                    balls.get(index).setBounceUP(true);
                    balls.get(index).setCollision(true);
                    
                    alien.setCollision(true);
                } 
                else if (lb !=null && laser.getShoot() && b.intersects(lb)) {
                    
                    balls.get(index).setHitDeadly(true);
                    balls.get(index).setCollision(true);
                    balls.remove(index);
                    return;
                } 
                else if (!balls.get(index).isFlipped() && t != null && b.intersects(t)) {
                    balls.get(index).setHitTramp(true);
                    balls.get(index).setCollision(true);
                    tramp.setCollision(true);
                } 
                else if (!balls.get(index).isFlipped() && efY != null && b.intersects(efY)) {
                    
                    balls.get(index).setBounceUP(true);
                    balls.get(index).setCollision(true);
                    
                    eFence.setCollision(true);
                    if (eFence.isState()) {
                        balls.get(index).setHitDeadly(true);
                        balls.remove(index);
                        return;
                    } 
                    
                } 
                else if (!balls.get(index).isFlipped() && efX != null && b.intersects(efX)) {
                    balls.get(index).setCollision(true);
                    
                    eFence.setCollision(true);
                    if (eFence.isState()) {
                        balls.get(index).setHitDeadly(true);
                        balls.remove(index);
                        return;
                    } 
                    
                } 
                
                else if (!balls.get(index).isFlipped() && pY != null && b.intersects(pY)) {
                    balls.get(index).setBounceUP(true);
                    balls.get(index).setCollision(true);
                    platform.setCollision(true);
                } 
                else if (!balls.get(index).isFlipped() && pX != null && b.intersects(pX)) {
                    balls.get(index).setCollision(true);
                    platform.setCollision(true);
                } 
                else {
                    balls.get(index).setCollision(false);
                    balls.get(index).setFlipped(false);

                }

            }
            }
            catch(Exception e){}
            finally{
             lock.unlock();
            }
            
        }
         
     }
       
    }
    
    /**
     * Removes all deployable objects from the factory.
     */
    @Override
     public void removeAll(){
        if(balls !=null){
            for(BouncyBall b : balls){
                b.stop();
            }
            balls.clear();
        }
        if(drone != null){
            drone.stop();
            drone = null;
        }
        
    }
    
     /**
      * Adds a BouncyBall to the balls vector.
      * @param b BouncyBall
      */
     @Override
    public void addBall(BouncyBall b){
        balls.add(b);
    }
    
  
}
