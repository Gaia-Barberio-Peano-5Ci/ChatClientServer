/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author barberio.gaia
 */
public class Inviare implements Runnable{

    DatagramSocket s;
    InetAddress indirizzo;
    int port;

    public Inviare(DatagramSocket s, InetAddress indirizzo, int port) {
        this.s = s;
        this.indirizzo = indirizzo;
        this.port = port;
    }
    
    
   @Override
    public void run() {
        byte[] buffer;
        String mex;
        Scanner tastiera = new Scanner(System.in);
        DatagramPacket pacchetto;

        try {
            
            do {

                mex = tastiera.nextLine();

                buffer = mex.getBytes("UTF-8");

                pacchetto = new DatagramPacket(buffer, buffer.length, indirizzo, port);

                s.send(pacchetto);
            } while (mex.compareTo("quit") != 0);
        } catch (IOException ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    }
