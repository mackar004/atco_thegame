/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atco;

import static atco.ATCO.chkpt;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;

/**
 *
 * @author maciek
 */
public class DrawCheckpoints extends JComponent {
    
    public void PaintComponent(Graphics g) {
        //super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(Color.blue);
        for (Checkpoint chkpt2draw : chkpt.checkpointArray){
            g2d.rotate(Math.toRadians(45));
            g2d.drawRect(chkpt2draw.getChXpos(), chkpt2draw.getChYpos(), 9, 9);
        }
    }
}
