/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import Factory.*;
import static Test.TestCase5.masterLock;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Shan_
 */
public class TestCase7 extends javax.swing.JFrame {

    /**
     * Creates new form TestCase7
     * Draws a drone and alien on canvas. 
     * Shows how they interact with each other.
     * The drone will move away from the alien when hit.
     * If the alien is hit the aliens image will change.
     */
   
    static Lock masterLock =  new ReentrantLock();
    Graphics2D graphics;
    Collider collider;
    public TestCase7() {
        initComponents();
        graphics = (Graphics2D) canvas7.getGraphics();
        Drone drone = new Drone(canvas7, 400,22,graphics,masterLock);
        Thread dThread = new Thread(drone);
        dThread.start();
        
        Alien alien = new Alien(canvas7,graphics,masterLock);
        Thread alienThread = new Thread(alien);
        alienThread.start();
        
        collider = new Collider(alien,drone,null,null,null,null, masterLock);
        Thread dcThread = new Thread(collider);
        dcThread.start();
        
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        canvas7 = new javax.swing.JPanel();
        addBallButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        canvas7.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout canvas7Layout = new javax.swing.GroupLayout(canvas7);
        canvas7.setLayout(canvas7Layout);
        canvas7Layout.setHorizontalGroup(
            canvas7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 744, Short.MAX_VALUE)
        );
        canvas7Layout.setVerticalGroup(
            canvas7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 550, Short.MAX_VALUE)
        );

        addBallButton.setBackground(new java.awt.Color(204, 255, 204));
        addBallButton.setText("Add Ball");
        addBallButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBallButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(canvas7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(299, 299, 299)
                .addComponent(addBallButton, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(canvas7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(addBallButton, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addBallButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBallButtonActionPerformed
        // TODO add your handling code here:
        BouncyBall bb = new BouncyBall(410,22,50,50,canvas7,true,false,graphics,masterLock);
        Thread bbThread = new Thread(bb);
        bbThread.start();
        collider.addBall(bb);
    }//GEN-LAST:event_addBallButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TestCase7.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TestCase7.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TestCase7.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TestCase7.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TestCase7().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBallButton;
    private javax.swing.JPanel canvas7;
    // End of variables declaration//GEN-END:variables
}