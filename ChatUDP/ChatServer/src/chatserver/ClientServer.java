/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author barberio.gaia
 */
public class ClientServer {
     int port;
    InetAddress indirizzo1;
    String indirizzo2 = "127.0.0.1";

      public ClientServer(InetAddress addr, int port) throws UnknownHostException {
        this.port = port;
        indirizzo1 = InetAddress.getByName(indirizzo2);
    }
      
       ClientServer(int port) {
        this.port = port;
    }
}
