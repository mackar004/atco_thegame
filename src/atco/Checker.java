/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atco;

import static atco.ATCO.levelCheckpoint;
import static atco.ATCO.level;
import static atco.GUI.gameOver;
import java.awt.Color;

/**
 *
 * @author m
 */

public class Checker {
        double x1,x2,y1,y2,z1,z2,dist,chkdist;
    
    public void checkDistance(Plane currentPlane, Plane referencePlane){
        //Opóźnienie checkera wymaga sprawdzania punktów przedostatnich (indeks 1 a nie 0)
        //Z tego powodu aby sprawdzić separację 50, sprawdzamy czy <= 70
        x1 = currentPlane.getXpos(1);
        x2 = referencePlane.getXpos(1);
        y1 = currentPlane.getYpos(1);
        y2 = referencePlane.getYpos(1);
        z1 = currentPlane.getAltitude();
        z2 = referencePlane.getAltitude();
        dist = Math.sqrt(Math.pow((x1-x2),2) + Math.pow((y2-y1),2) + Math.pow((z2-z1), 2));
        if (dist <= 10) {
            levelCheckpoint.get(currentPlane.getNextCheckpoint()).setColor(Color.gray);       //dezaktywacja checkopintu w przypadku zniszczenia
            referencePlane.setDestroyed(true);
            currentPlane.setDestroyed(true);
            gameOver = true;
        } else if (dist <= 70) {
            currentPlane.setInDanger(true);
            referencePlane.setInDanger(true);
        } else {
            //
        }
    }
    
    public void checkCheckpoint(Plane currentPlane, Checkpoint checkpoint){
        x1 = (int) currentPlane.getXpos(0);
        y1 = (int) currentPlane.getYpos(0);
        x2 = (int) checkpoint.getChXpos();
        y2 = (int) checkpoint.getChYpos();
        chkdist = Math.sqrt(Math.pow((x1-x2),2) + Math.pow((y2-y1),2));
        if (chkdist <= 50) {
            levelCheckpoint.get(currentPlane.getNextCheckpoint()).setColor(Color.gray);
            currentPlane.setNextCheckpoint(currentPlane.getNextCheckpoint()+1);
            if (currentPlane.getNextCheckpoint() < levelCheckpoint.size()) {
                levelCheckpoint.get(currentPlane.getNextCheckpoint()).setColor(Color.green);
            }
            if (currentPlane.getNextCheckpoint() >= levelCheckpoint.size()){
                currentPlane.setSelected(false);
                currentPlane.setPlayable(false);
            }
        } else {
            //System.out.println("checkpoint out of range");
        }               
    }
    
    public boolean outOfBounds(Plane currentPlane) {
        
        return false;
    }
    
    public boolean endLevel() {
        boolean ret = false;
        for (Plane currentPlane : level){
            if (currentPlane.isPlayable()){
                ret = true;
            }
        }
        return ret;
    }
}
