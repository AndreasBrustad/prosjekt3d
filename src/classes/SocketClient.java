/*
 * SocketClient er en klasse for oppkobling 
 */
package classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class SocketClient {
    InputStreamReader dataConnection;
    BufferedReader readData;
    private int portnr = 1250;  // default port
    private String ip;

    public SocketClient() {
    }

    public SocketClient(int portnr, String ip) {
            this.portnr = portnr;
            this.ip = ip;
    }

    // Kobler til socketserver
    public void connect() {
        try {
            // connect to server
            Socket connection = new Socket(ip, portnr);
            System.out.println("Connection to server established");

            dataConnection = new InputStreamReader(connection.getInputStream());
            readData = new BufferedReader(dataConnection);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Leser inn data fra socketserver og returnerer dette.
    public String getString() {
        try {
            return readData.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Leser inn tekststreng med koordinater og legger dem i en float[].
    public void getCoordinates(String dataString, float[] coordinates) {
        int i = 0;
        if (dataString != null) {
            Scanner s = new Scanner(dataString);
            while (s.hasNext()) {
                coordinates[i] = Float.parseFloat(s.next());
                i++;
            }
        }
    }
    
    // Lukker socketforbindelsen
    public void close() {
        try {
            dataConnection.close();
            readData.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public String toString() {
        return ("IP: " + ip + "\n" + "PORT: " + portnr + "\n");
    }
}