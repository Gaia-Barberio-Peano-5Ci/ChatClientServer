/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

/**
 *
 * @author barberio.gaia
 */

public class GUI extends JFrame implements ActionListener{
     ClientServer client = new ClientServer(7777);
     
     private JTextArea Ar; 
    
     DatagramSocket Client;
     DatagramSocket server;
     byte [] buffer;
     String messaggio;
     JPanel pannello1;
     JPanel pannello2;
     ArrayList<String> mess = new ArrayList<>();
    
     private HashMap<InetAddress, Integer> porte = new HashMap<>();

     
     
     
     public GUI(int porta) throws SocketException, UnknownHostException{
        
         
         setTitle("CHAT-UDP");
      
        Ar = new JTextArea(5,5);
        Ar.setEditable(false);
       
        
        
        
        
         pannello1 = new JPanel();
         pannello1.setLayout(new BorderLayout());
         pannello1.setBorder(new TitledBorder("CONVERSAZIONE CON TUTTI I CLIENT"));
         pannello1.add(Ar, BorderLayout.NORTH);
         
         
         pannello2 = new JPanel();
         pannello2.setLayout(new GridLayout(1, 1));
         pannello2.add(pannello1);
         buffer = new byte[1024];
         
         
        
         this.getContentPane().setBackground(Color.BLUE);
         this.getContentPane().add(pannello2, BorderLayout.CENTER);
         

         

        
         setSize(600, 400);
         setVisible(true);
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
              
         
         
         
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    server = new DatagramSocket(7777);
                    while (true) {
                        DatagramPacket pacchetto = new DatagramPacket(buffer, buffer.length);
                        server.receive(pacchetto);
                        String messaggio = new String(pacchetto.getData());
                        Ar.append("ricevuto:" + messaggio + " da: " + pacchetto.getAddress() + "" + pacchetto.getPort());
                        server.send(new DatagramPacket(pacchetto.getData(), pacchetto.getLength(), pacchetto.getAddress(), pacchetto.getPort()));
                        Ar.append("inviato:" + messaggio + " a: " + pacchetto.getAddress() + "" + pacchetto.getPort());
                        if (!porte.containsKey(pacchetto.getAddress()) && !porte.containsValue(pacchetto.getPort())) {
                            Ar.append("L'IP non risulta nell'elenco,perciò lo salvo!");
                            porte.put(pacchetto.getAddress(), pacchetto.getPort());
                            if (mess.size() > 10) {
                                for (int i = mess.size() - 11; i < mess.size(); i++) {
                                    byte[] buff = mess.get(i).getBytes();
                                    DatagramPacket ultimiMex = new DatagramPacket(buff, buff.length, pacchetto.getAddress(), pacchetto.getPort());
                                    server.send(ultimiMex);
                                    Ar.append("inviato:" + new String(buff) + " a: " + pacchetto.getAddress() + " " + pacchetto.getPort());
                                }
                            } else {
                                for (int i = 0; i < mess.size(); i++) {
                                    byte[] buffer2 = mess.get(i).getBytes();
                                    DatagramPacket ultimiMex = new DatagramPacket(buffer2, buffer2.length, pacchetto.getAddress(), pacchetto.getPort());
                                    server.send(ultimiMex);
                                    Ar.append("inviato:" + new String(buffer2) + " a: " + pacchetto.getAddress() + " " + pacchetto.getPort());
                                }
                            }
                        }
                        mess.add(messaggio);
                    }
                } catch (IOException ex) {
                }
            }
        }).start();

    }
  

     @Override
    public void actionPerformed(ActionEvent e) {
    }
}