/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atco;

import java.awt.Color;
import java.io.Serializable;

/**
 *
 * @author m
 */
public class Checkpoint implements Serializable {
    private int chXpos;
    private int chYpos;
    private Color color;

    public int getChXpos() {
        return chXpos;
    }

    public void setChXpos(int chXpos) {
        this.chXpos = chXpos;
    }

    public int getChYpos() {
        return chYpos;
    }

    public void setChYpos(int chYpos) {
        this.chYpos = chYpos;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    
    Checkpoint(int x, int y) {
        this.chXpos = x;
        this.chYpos = y;
        this.color = Color.gray;
    }
}
