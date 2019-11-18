/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientchat;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
/**
 *
 * @author barberio.gaia
 */
public class Client {
    private DatagramSocket socket;
    public Client() throws SocketException, UnknownHostException {
        socket = new DatagramSocket();
    }
    public void send(int destinationPort, String IP, String msg) throws UnknownHostException, InterruptedException, IOException {
        InetAddress IP_address = InetAddress.getByName(IP);
        DatagramPacket packet;
        packet = new DatagramPacket(msg.getBytes("UTF-8"), msg.getBytes("UTF-8").length, IP_address, destinationPort);
        socket.send(packet);
    }
    public boolean isClosed(){
        if(socket.isClosed()){
            return true;
        }else{
            return false;
        }
    }
    public void close() {
        socket.close();
    }
    public String recieve() throws InterruptedException, IOException {
        DatagramPacket packet;
        byte[] buffer = new byte[2048];
        packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
    System.err.println(new String(packet.getData(), packet.getOffset(), packet.getLength(), "UTF-8"));
        String out = new String(packet.getData(), packet.getOffset(), packet.getLength(), "UTF-8");
        return out;
    }
}