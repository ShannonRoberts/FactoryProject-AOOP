/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Factory;

/**
 * Interface used for Collider.
 * @author Shan_
 */
public interface ColliderInterface {
    public void checkCollision();
    public void droneCheckCollisions();
    public void removeAll();
    public void addBall(BouncyBall ball);
}
