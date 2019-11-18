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
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author barberio.gaia
 */
public class ServerSocket {

    private final ArrayList<byte[]> buffer10;
    private final DatagramSocket socket;
    private final Dict dict;

    public ServerSocket(int port) throws SocketException {
        socket = new DatagramSocket(port);
        buffer10 = new ArrayList<>(10);
        dict = new Dict();
    }

    public class Dict {

        ArrayList<InetAddress> ip;
        ArrayList<Integer> port;
        ArrayList<String> users;

        public Dict() {
            ip = new ArrayList();
            port = new ArrayList();
            users = new ArrayList();
        }

        public void removeCombination(InetAddress IP_address, int port) {
            for (int i = 0; i < ip.size(); i++) {
                if (ip.get(i).equals(IP_address) && this.port.get(i) == port) {
                    ip.remove(i);
                    this.port.remove(i);
                    users.remove(i);
                }
            }
        }

        public boolean findUser(String user) {
            for (int i = 0; i < this.users.size(); i++) {
                if (this.users.get(i).equals(user)) {
                    System.err.println("User trovato");
                    return true;

                }
            }
            System.err.println("User non trovato");
            return false;
        }

        public String getAllUser(String sep, String user) {
            StringBuilder builder = new StringBuilder();
            for (String s : users) {
                if (s.equals(user)) {
                    builder.append(s + " (You)" + sep);
                } else {
                    builder.append(s + sep);
                }
            }
            return builder.toString();
        }

        public void addCombination(InetAddress IP_address, int port, String user) {
            ip.add(IP_address);
            this.port.add(port);
            users.add(user);
        }

        public int getNConnection() {
            return ip.size();
        }

        public String getUserAt(int i) {
            return users.get(i);
        }

        public InetAddress getIPAt(int i) {
            return ip.get(i);
        }

        public int getPortAt(int i) {
            return port.get(i);
        }

    }

    public void close() throws IOException {
        String closed = "closed$#";
        for (int i = 0; i < dict.getNConnection(); i++) {
            socket.send(new DatagramPacket(closed.getBytes(), closed.getBytes().length, dict.getIPAt(i), dict.getPortAt(i)));
        }
    }

    /**
     *
     */
    public ArrayList<String> listen() {

        DatagramPacket packet;
        ArrayList<String> output = new ArrayList();
        byte[] buffer = new byte[2048];
        packet = new DatagramPacket(buffer, buffer.length);
        try {
            socket.receive(packet);
            String msg = new String(packet.getData(), packet.getOffset(), packet.getLength());
            StringTokenizer token = new StringTokenizer(msg, "$#");
            String user;
            switch (token.nextToken()) {
                case "200connect":
                    System.err.println("È un messaggio di connect");
                    System.err.println(packet.getAddress() + " " + packet.getPort() + "E' connesso");
                    user = token.nextToken();
                    if (!dict.findUser(user)) {
                        if (!buffer10.isEmpty()) {
                            for (int i = 0; i < buffer10.size(); i++) {
                                socket.send(new DatagramPacket(buffer10.get(i), 0, buffer10.get(i).length, packet.getAddress(), packet.getPort()));
                            }

                        }
                        if (dict.getNConnection() > 0) {
                            System.err.println("Più di una connessione");
                            String new_user = "new_user$#" + user;
                            System.err.println(dict.getNConnection());
                            for (int i = 0; i < dict.getNConnection(); i++) {
                                System.err.println(i);
                                System.err.println(packet.getAddress());
                                System.err.println(packet.getPort());
                                System.err.println(dict.getIPAt(i));
                                System.err.println(dict.getPortAt(i));
                                if (!dict.getIPAt(i).equals(packet.getAddress()) || dict.getPortAt(i) != packet.getPort()) {
                                    socket.send(new DatagramPacket(new_user.getBytes(), 0, new_user.getBytes().length, dict.getIPAt(i), dict.getPortAt(i)));
                                } else {
                                    System.err.println("No");
                                }
                            }
                        }
                        System.err.println(user);
                        dict.addCombination(packet.getAddress(), packet.getPort(), user);
                        String users = "all_users$#" + dict.getAllUser("$#", user);
                        System.out.println(users);
                        socket.send(new DatagramPacket(users.getBytes(), 0, users.getBytes().length, packet.getAddress(), packet.getPort()));
                        output.add("200 - OK");
                        output.add(packet.getAddress().toString());
                        output.add(Integer.toString(packet.getPort()));
                        output.add(user);
                        output.add("Connect");
                        output.add(new String(packet.getData(), 0, packet.getLength(), "UTF-8"));

                    } else {
                        System.err.println("user already in use$#");
                        byte[] error = "invalid_user".getBytes();
                        socket.send(new DatagramPacket(error, 0, error.length, packet.getAddress(), packet.getPort()));
                        output.add("400 - User already in use");
                        output.add(packet.getAddress().toString());
                        output.add(Integer.toString(packet.getPort()));
                        output.add(user);
                        output.add("Connect");
                        output.add(new String(packet.getData(), 0, packet.getLength(), "UTF-8"));

                    }

                    break;
                case "200disconnect":
                    System.err.println("È un messaggio di disconnect");
                    user = token.nextToken();
                    System.err.println(packet.getAddress() + " " + packet.getPort() + " E' disconnesso");
                    dict.removeCombination(packet.getAddress(), packet.getPort());
                    if (dict.getNConnection() > 0) {
                        System.err.println("Più di una connessione");
                        String delete_user = "delete_user$#" + user;
                        System.err.println(dict.getNConnection());
                        for (int i = 0; i < dict.getNConnection(); i++) {
                            socket.send(new DatagramPacket(delete_user.getBytes(), 0, delete_user.getBytes().length, dict.getIPAt(i), dict.getPortAt(i)));
                        }
                    }
                    output.add("200 - OK");
                    output.add(packet.getAddress().toString());
                    output.add(Integer.toString(packet.getPort()));
                    output.add(user);
                    output.add("Disconnect");
                    output.add(new String(packet.getData(), 0, packet.getLength(), "UTF-8"));

                    break;
                default:
                    for (int i = 0; i < dict.getNConnection(); i++) {
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
                        DatagramPacket send = new DatagramPacket(packet.getData(), packet.getLength(), dict.getIPAt(i), dict.getPortAt(i));
                        System.err.println("Ho mandato a " + dict.getIPAt(i) + " " + dict.getPortAt(i));
                        socket.send(send);
                        output.add("200 - OK");
                        output.add(dict.getIPAt(i).toString());
                        output.add(Integer.toString(dict.getPortAt(i)));
                        output.add(dict.getUserAt(i));
                        output.add("EchoService");
                        output.add(new String(packet.getData(), 0, packet.getLength(), "UTF-8"));

                    }
                    break;
            }
            return output;
        } catch (IOException ex) {
            Logger.getLogger(ServerSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}