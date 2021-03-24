/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 */

public class CheckpointArray {
    ArrayList<Checkpoint> checkpointArray = new ArrayList<>();
    //Checkpoint(int x, int y)
    Checkpoint chk0 = new Checkpoint(450, 150);
    Checkpoint chk1 = new Checkpoint(220, 580);
    
    CheckpointArray(){
        checkpointArray.add(chk0);
        checkpointArray.add(chk1);
    }
    
    public void createLevelCheckpoint(int i) throws IOException {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("lev" + i + ".levch"))) {
            output.writeObject(checkpointArray);
            //System.out.println("Checkpoint level " + i + " created!");
        }
    }
    
    public ArrayList<Checkpoint> loadLevelCheckpoint(int i) throws IOException, ClassNotFoundException {
        ArrayList<Checkpoint> levch = null;
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream("lev" + i + ".levch"))) {
            levch = (ArrayList<Checkpoint>) input.readObject();
        }
        return levch;
    }

    public ArrayList<Checkpoint> getCheckpoint() {
        return checkpointArray;
    }
    
}
