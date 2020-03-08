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
 * Interface used for factory objects.
 * @author Shan_
 */
public interface SolidObject {
    
    public void draw(BufferedImage i);
    public void clearRect();
    public BufferedImage loadImage(File f);
}
