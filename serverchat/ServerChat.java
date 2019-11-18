/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverchat;

import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author barberio.gaia
 */
public class ServerChat {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            new Runnable() {
                @Override
                public void run() {
                    new GUI();
                }
            }.run();

            ServerSocket ss = new ServerSocket(7);
            Thread thread = new Thread(ss);
            thread.start();
            thread.join();
        } catch (SocketException | InterruptedException ex) {
            Logger.getLogger(ServerChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}