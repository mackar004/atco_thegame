package atco;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 *
 * @author m
 * 
 */

public class PlaneArray {
    
    ArrayList<Plane> planeArray = new ArrayList<>();
    //Plane(int altitude, int track, int Xpos, int Ypos, boolean playable, boolean active)
    Plane plane1 = new Plane(200, 90, 200, 200, true, true);
    Plane plane2 = new Plane(300, 225, 800, 0, false, true);
    Plane plane3 = new Plane(200, 45, 300, 550, false, true);
    Plane plane4 = new Plane(200, 270, 800, 210, true, true);
    Plane plane5 = new Plane(200, 115, -2, 20, false, false);
    
    PlaneArray(){
        planeArray.add(plane1);
        planeArray.add(plane2);
        planeArray.add(plane3);
        planeArray.add(plane4);
        planeArray.add(plane5);
    }
    
    public void createLevel(int i) throws IOException {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("lev" + i + ".lev"))) {
            output.writeObject(planeArray);
            //System.out.println("Level " + i + " created!");
        }
    }
    
    public ArrayList<Plane> loadLevel(int i) throws IOException, ClassNotFoundException {
        ArrayList<Plane> lev = null;
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream("lev" + i + ".lev"))) {
            lev = (ArrayList<Plane>) input.readObject();
        }
        return lev;
    }
    
    public ArrayList<Plane> getPlane() {
        return planeArray;
    }

}
