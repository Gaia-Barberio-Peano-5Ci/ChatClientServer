/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author barberio.gaia
 */
public class Ricevere implements Runnable {

      DatagramSocket s;

    public Ricevere(DatagramSocket s) {
        this.s = s;
    }
    
    
    
    @Override
    public void run() {
        byte[] buffer = new byte[100];
        String mex;
        DatagramPacket pacchetto;

        try {

            pacchetto = new DatagramPacket(buffer, buffer.length);

            while (!Thread.interrupted()) {
                s.receive(pacchetto);
                mex = new String(pacchetto.getData(), 0, pacchetto.getLength(), "ISO-8859-1");
                System.out.println("Il server invia /n" + mex);
                
            }

        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Ricevere.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Ricevere.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    }
    