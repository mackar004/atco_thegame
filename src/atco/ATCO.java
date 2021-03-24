package atco;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author m
 */

public class ATCO {
    
    static PlaneArray planes = new PlaneArray();
    static CheckpointArray chkpt = new CheckpointArray();
    static Checker checkPlanes = new Checker();
    static DrawScreen radarScreen = new DrawScreen();
    static ArrayList<Plane> level;
    static ArrayList<Checkpoint> levelCheckpoint;
    
    public static void main(String[] args) throws IOException {
        planes.createLevel(3);
        chkpt.createLevelCheckpoint(3);
        GUI apGUI = new GUI();
        apGUI.createGUI();
    }
}
