package atco;

import java.awt.Color;
//import static java.awt.Color.*;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;
import java.util.Random;

/**
 *
 * @author m
 */
public class Plane implements Serializable {

    private String id;
    private double altitude;
    private int newAlt;
    private int track;
    private double[] Xpos = new double[6];
    private double[] Ypos = new double[6];
    private int[] newTrack = {0, track, 0}; //{direction, newTrack, finalTrack}
    private boolean active;
    private boolean inDanger = false;
    private boolean destroyed;
    private boolean playable;
    private boolean selected;
    private int ascending = 0;
    private Color color;
    private int nextCheckpoint;
    private double startX;
    private double startY;

    public int getNextCheckpoint() {
        return nextCheckpoint;
    }

    public void setNextCheckpoint(int nextCheckpoint) {
        this.nextCheckpoint = nextCheckpoint;
        }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public int getNewAlt() {
        return newAlt;
    }

    public void setNewAlt(int newAlt) {
        this.newAlt = newAlt;
    }

    public int getTrack() {
        return track;
    }

    public void setTrack(int track) {
        this.track = track;
    }

    public int getNewTrack(int n) {
        return newTrack[n];
    }

    public void setNewTrack(int n, int value) {
        this.newTrack[n] = value;
    }

    public double getXpos(int n) {
        return Xpos[n];
    }

    public void setXpos(double[] Xpos) {
        this.Xpos = Xpos;
    }

    public double getYpos(int n) {
        return Ypos[n];
    }

    public void setYpos(double[] Ypos) {
        this.Ypos = Ypos;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isInDanger() {
        return inDanger;
    }

    public void setInDanger(boolean inDanger) {
        this.inDanger = inDanger;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.setActive(false);
        this.destroyed = destroyed;
    }

    public boolean isPlayable() {
        return playable;
    }

    public void setPlayable(boolean playable) {
        this.playable = playable;
    }

    public int getAscending() {
        return ascending;
    }

    public void setAscending(int ascending) {
        this.ascending = ascending;
    }

    public Color getColor() {
        if (!isInDanger()) {
            if (isPlayable()) {
                if (isSelected()){
                    setColor(Color.green);
                } else {
                    setColor(Color.white);
                }
            } else {
                setColor(Color.gray);
            }
        } else if (isInDanger()){
            setColor(Color.red);
        }
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
    boolean isSelected() {
        return selected;
    }

    public Plane(int altitude, int track, int startX, int startY, boolean playable, boolean active) {
        this.altitude = altitude;
        this.track = track;
        this.startX = startX;
        this.startY = startY;
//        this.Xpos[0] = Xpos;
//        this.Ypos[0] = Ypos;
        this.playable = playable;
        this.active = active;
        
        Xpos[0] = startX;
        Ypos[0] = startY;        
        color = Color.gray;
        createPath();
        createID();
        ascending = 0;
        newAlt = altitude;
        newTrack[1] = track;
        selected = false;
        


        if (playable) {
            color = Color.white; 
            nextCheckpoint = 0;
        }
    }

    private void createID() {
        Random r = new Random();
        StringBuilder sb = new StringBuilder(1);
        char c = (char) (r.nextInt(26) + 'A');
        for (int i = 0; i < 2; i++) {
            id = sb.append((char) (r.nextInt(26) + 'A')).toString();
        }
        for (int i = 0; i < 3; i++) {
            id = sb.append(r.nextInt(10)).toString();
        }
    }

    public final void createPath() {
        for (int i = 1; i < 6; i++) {
            Xpos[i] = (10 * -sin(toRadians(track)) + Xpos[i - 1]);
            Ypos[i] = (10 * cos(toRadians(track)) + Ypos[i - 1]);
        }
    }

    public void updatePath() {
        for (int i = 5; i > 0; i--) {
            Xpos[i] = Xpos[i - 1];
            Ypos[i] = Ypos[i - 1];
        }
    }

    public void nextPosition(int direction, int nextTrack) {
        if (direction == 0) {  //lot na wprost
            Xpos[0] = (10 * sin(toRadians(track)) + Xpos[0]);   //wyliczenie nowej pozycji X
            Ypos[0] = (10 * -cos(toRadians(track)) + Ypos[0]);  //wyliczenie nowej pozycji Y
            newTrack[1] = track;
        } else {
            if (direction == 1) { //zwrot w lewo
                if (abs(track - newTrack[2]) < 15) {    /////////////
                    track = newTrack[2];                //zakończenie zwrotu - ostatni krok to delikatne wyrównanie kursu jeśli ostatni zwrot miał mniej niż 15*
                    newTrack[0] = 0;                    /////////////  
                } else {                                //w przeciwnym wypadku
                    track = track - 15;                 /////////////
                }                                       //ustawienie nowego kursu
                                                        /////////////
                if (track < 0) {                        /////////////
                    track = 360 + track;                //kurs w zakresie 0-360
                }                                       /////////////
            }
            
            if (direction == 2) {  //zwrot w prawo
                if (abs(track - newTrack[2]) < 15) {    /////////////
                    track = newTrack[2];                //zakończenie zwrotu - ostatni krok to delikatne wyrównanie kursu jeśli ostatni zwrot miał mniej niż 15*
                    newTrack[0] = 0;                    /////////////  
                }  else {                               //w przeciwnym wypadku
                    track = track + 15;                 /////////////
                }                                       //ustawienie nowego kursu
                                                        /////////////                                             
                if (track == 360){                      /////////////
                    track = 0;                          //przeiczenie kursu 360 na 0
                }                                       /////////////
                                                
                if (track > 360) {                      /////////////
                    track = track - 360;                //kurs w zakresie 0-360
                }                                       /////////////
            }
            
            newTrack[1] = track;
            Xpos[0] = (int) (9 * sin(toRadians(track)) + Xpos[0]);   //wyliczenie nowej pozycji X
            Ypos[0] = (int) (9 * -cos(toRadians(track)) + Ypos[0]);  //wyliczenie nowej pozycji Y             
        }     
    }

    //input: newAlt(int)
    public void nextAltitude(int nextAlt) {
        if (nextAlt > altitude) {
            setAscending(1);
            altitude = (100 * sin(toRadians(15)) + altitude);
            if (altitude > newAlt) {
                altitude = newAlt;
            }
            //maksymalny kąt 30

        } else if (nextAlt < this.altitude) {
            setAscending(-1);
            altitude = (-100 * sin(toRadians(15)) + altitude);
            if (altitude < newAlt) {
                altitude = newAlt;
            }
            //maksymalny kąt 15
        } else {
            setAscending(0);
            //brak zmiany wysokości
        }
    }
    
    public Rectangle2D getBounds() {
        return new Rectangle2D.Double(Xpos[0] - 4, Ypos[0] - 4, 9, 9);
        //mouseListener dla ramki:
        //return new Rectangle2D.Double(Xpos[0] + 3, Ypos[0] + 26, 9, 9);
    }

}
