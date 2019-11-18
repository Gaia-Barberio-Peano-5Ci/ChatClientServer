/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverchat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author barberio.gaia
 */
public class ServerSocket implements Runnable {

    DatagramSocket socket;

    public ServerSocket(int port) throws SocketException {
        socket = new DatagramSocket(port);
    }

    public class Dict {

        ArrayList<InetAddress> ip;
        ArrayList<Integer> port;

        public Dict() {
            ip = new ArrayList();
            port = new ArrayList();
        }

        public void removeCombination(InetAddress IP_address, int port) {
            for (int i = 0; i < ip.size(); i++) {
                if (ip.get(i).equals(IP_address) && this.port.get(i) == port) {
                    ip.remove(i);
                    this.port.remove(i);
                }
            }
        }

        public void addCombination(InetAddress IP_address, int port) {
            ip.add(IP_address);
            this.port.add(port);
        }

        public int getSize(){
            return ip.size();
        }

        public InetAddress getIPAt(int i){
            return ip.get(i);
        }

        public int getPortAt(int i){
            return port.get(i);
        }

    }

    /**
     *
     */
    @Override
    public void run() {

        ArrayList<byte[]> buffer10 = new ArrayList<>(10);
        DatagramPacket packet;
        Dict d = new Dict();

        while (true) {
            byte[] buffer = new byte[2048];
            packet = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(packet);
                String msg = new String(packet.getData(), packet.getOffset(), packet.getLength());
                System.err.println("È un messaggio di disconnect: " + msg.equals("disconnect"));
                System.err.println("È un messaggio di connect: " + msg.equals("connect"));
                switch (msg) {
                    case "connect":
                        System.err.println(packet.getAddress() + " " + packet.getPort() + " si è connesso");
                        if (!buffer10.isEmpty()) {
                            for (int i = 0; i < buffer10.size(); i++) {
                                socket.send(new DatagramPacket(buffer10.get(i), 0, buffer10.get(i).length, packet.getAddress(), packet.getPort()));
                            }
                        }   break;
                    case "disconnect":
                        System.err.println(packet.getAddress() + " " + packet.getPort() + " si è disconnesso");
                        break;
                    default:
                        for (int i = 0; i < d.getSize(); i++) {
                            if (buffer10.size() < 10) {
                                byte[] buf = packet.getData();
                                buffer10.add(buf);
                            } else {
                                for (int z = 0; z < 9; z++) {
                                    buffer10.set(z, buffer10.get(z + 1));
                                }
                                byte[] buf = packet.getData();
                                buffer10.set(9, buf);
                            }
                            DatagramPacket send = new DatagramPacket(packet.getData(), packet.getLength(), d.getIPAt(i), d.getPortAt(i));
                            System.err.println("Ho mandato a " + d.getIPAt(i) + " " + d.getPortAt(i));
                            socket.send(send);
                        }   break;
                }
            } catch (IOException ex) {
                Logger.getLogger(ServerSocket.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
