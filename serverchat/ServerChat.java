/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverchat;

/**
 *
 * @author barberio.gaia
 */
public class ServerChat {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
            // TODO code application logic here
            new Runnable() {
                @Override
                public void run() {
                    new GUI();
                }
            }.run();
    }

}