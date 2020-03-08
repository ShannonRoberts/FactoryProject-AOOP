/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Factory;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Interface used for deployable objects.
 * @author Shan_
 */
public interface Deployable {
    public void draw();
    public void update();
    public void collide();
    public void clearRect();
    public void stop();
    public BufferedImage loadImage(File f);
    public int randomSpawn();
    public void move();
}
