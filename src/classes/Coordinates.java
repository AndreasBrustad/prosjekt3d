/*
 * Reads a coordinate file and serves amazing methods.
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

public class Coordinates {
    private String filename;
    private ArrayList<ArrayList<Double>> list = new ArrayList<ArrayList<Double>>();
    private File file;
    private BufferedReader reader = null;
    
    public Coordinates (String filename) {
        this.filename = filename;
        readFile();
    }
    
    public void readFile() {
        try {
            file = new File(filename);
            reader = new BufferedReader(new FileReader(file));
            String text = null;
            int i = 0;
            
           while ((text = reader.readLine()) != null) {  
            Scanner s = new Scanner(text);
            list.add(new ArrayList<Double>());
                while (s.hasNext()) {
                    list.get(i).add(Double.parseDouble(s.next()));
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
    
    public double getCoordinate(int timestamp, int coordinateIndex) {
        if ((numberOfTimestamps() < timestamp) || (numberOfCoordinates() < coordinateIndex)) {
            return -666.0;
        }
        return list.get(timestamp).get(coordinateIndex);
    }
    
    public int numberOfTimestamps() {
        return list.size();
    }
    
    public int numberOfCoordinates() {
        return list.get(0).size();
    }
    
    public String toString() {
        return ("filename: " + filename);
    }
}
