/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atco;

import static atco.ATCO.level;
import static atco.ATCO.levelCheckpoint;
import static java.awt.Color.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

/**
 *
 * @author m
 */
class DrawScreen extends JComponent {

    @Override
    public void paint(Graphics g) {
        Image radarImg = new ImageIcon("/radar.png").getImage();

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(black);
        //g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
        g2d.drawImage(radarImg, 0,0, this);

        //////////////
        //RYSOWANIE CHECKPOINTÓW
        //////////////
        for (Checkpoint chkpt2draw : levelCheckpoint){
            g2d.setColor(chkpt2draw.getColor());
            g2d.drawLine(chkpt2draw.getChXpos()-5, chkpt2draw.getChYpos(), chkpt2draw.getChXpos(), chkpt2draw.getChYpos()-5);
            g2d.drawLine(chkpt2draw.getChXpos(), chkpt2draw.getChYpos()-5, chkpt2draw.getChXpos()+5, chkpt2draw.getChYpos());
            g2d.drawLine(chkpt2draw.getChXpos()+5, chkpt2draw.getChYpos(), chkpt2draw.getChXpos(), chkpt2draw.getChYpos()+5);
            g2d.drawLine(chkpt2draw.getChXpos(), chkpt2draw.getChYpos()+5, chkpt2draw.getChXpos()-5, chkpt2draw.getChYpos());  
        }

        //////////////
        //RYSOWANIE SAMOLOTÓW
        //////////////        
        for (Plane currentPlane : level) {
            if (currentPlane.isActive()) {  
                g2d.setColor(currentPlane.getColor());
            //rysowanie pozycji
                g2d.fillRect((int)currentPlane.getXpos(0) - 4, (int)currentPlane.getYpos(0) - 4, 9, 9);
            //rysowanie zasięgu    
                g2d.drawOval((int)currentPlane.getXpos(0) - 50, (int)currentPlane.getYpos(0) - 50, 100, 100);
                //g2d.drawOval((int)currentPlane.getXpos(0) - 10, (int)currentPlane.getYpos(0) - 10, 20, 20);

            //Wyświetlanie informacji
                if (currentPlane.isPlayable()) {
                    if (currentPlane.isSelected()){
                        g2d.setColor(green);
                    } else {
                        g2d.setColor(white);
                    }
                } else {
                    g2d.setColor(gray);
                }
                
                String asc;
                if (currentPlane.getAscending() > 0) {
                    asc = " \u21E7 ";
                } else if (currentPlane.getAscending() < 0) {
                    asc = " \u21E9 ";
                } else {
                    asc = " = ";
                }
                g2d.drawString(currentPlane.getId(), (int)currentPlane.getXpos(0) + 15, (int)currentPlane.getYpos(0) + 15);
                g2d.drawString((int)currentPlane.getAltitude() + asc + (int)currentPlane.getNewAlt(), (int)currentPlane.getXpos(0) + 15, (int)currentPlane.getYpos(0) + 28);
                //g2d.drawString((int)currentPlane.getAltitude() + asc + (int)currentPlane.getNewTrack(1), (int)currentPlane.getXpos(0) + 15, (int)currentPlane.getYpos(0) + 28);
                
            //rysowanie śladu
                g2d.setColor(getHSBColor(2, (float)0.5, (float)0.5));
                for (int i = 1; i < 6; i++) {          
                    g2d.fillRect((int)currentPlane.getXpos(i) - 1, (int)currentPlane.getYpos(i) - 1, 3, 3);
                }
            }
        }
    }
}