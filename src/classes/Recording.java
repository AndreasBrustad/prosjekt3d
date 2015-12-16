/*
 * Reads a coordinate file and haz aMaZzZiNG meth0dz.
 * 
 */
package classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Recording {
    private String filename;
    private int numberOfCoordinates;
    private int numberOfTimestamps;
    private int numberOfMarkers;
    private ArrayList<ArrayList<Float>> list = new ArrayList<ArrayList<Float>>();
    private File file;
    private BufferedReader reader = null;
    private int tall;
    private float forholdx = 1, forholdy = 1;
    
    public Recording (String filename, int tall) {
        this.filename = filename;
        readFile();
        numberOfCoordinates = list.get(0).size();
        numberOfTimestamps = list.size();
        numberOfMarkers = numberOfCoordinates / 3;
        this.tall = tall;
    }
    
    public void readFile() {
        try {
            file = new File(filename);
            reader = new BufferedReader(new FileReader(file));
            String text = null;
            int i = 0;
            
           while ((text = reader.readLine()) != null) {  
            Scanner s = new Scanner(text);
            list.add(new ArrayList<Float>());
                while (s.hasNext()) {
                    list.get(i).add(Float.parseFloat(s.next()));
                }
                i++;
            }                        
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public float[] getCoordinatesMarker(int marker) {
        if(tall == 1) {
            forholdx = 414/60;
            forholdy = 670/100;
        }
        
        float[] markerCoordinates = new float[numberOfTimestamps*3];
        if (marker > numberOfMarkers) {
            markerCoordinates[0] = -666.0f;
            return markerCoordinates;
        }
        int counter = 0;
        for (int i = 0; i < numberOfTimestamps; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(getCoordinate(i, j+((marker-1)*3)) + ", ");
                if(j == 0) {
                    markerCoordinates[counter] = (getCoordinate(i, j+((marker-1)*3)))/forholdx;
                }else if(j == 1) {
                    markerCoordinates[counter] = (getCoordinate(i, j+((marker-1)*3)))/forholdy;
                }else {
                    markerCoordinates[counter] = getCoordinate(i, j+((marker-1)*3));
                }
                counter++;
            }
            System.out.println();
        }
        return markerCoordinates;
    }
    
    public float getCoordinate(int timestamp, int coordinateIndex) {
        if ((numberOfTimestamps < timestamp) || (numberOfCoordinates < coordinateIndex)) {
            return -666.0f;
        }
        return list.get(timestamp).get(coordinateIndex);
    }
    
    public int getNumberOfMarkers() {
        return numberOfMarkers;
    }
    
    public int getNumberOfCoordinates() {
        return numberOfCoordinates;
    }
    
    public int getNumberOfTimestamps() {
        return numberOfTimestamps;
    }
   
    public String toString() {
        return ("filename: " + filename + "\n" + "timestamps: " + numberOfTimestamps +
                "\n" + "markers: " + numberOfMarkers);
    }
}
