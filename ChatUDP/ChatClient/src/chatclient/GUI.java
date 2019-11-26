/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 *
 * @author barberio.gaia
 */
public class GUI extends JFrame implements ActionListener {
    
    
   
     private JTextField Ar1; 
     private JTextField Ar2;
     private JTextArea panel;
     private JButton invio = new JButton("INVIO");
     byte [] buffer1;
     byte [] buffer2;
     
     DatagramSocket client;
     String messaggio;
    
     String ip = "127.0.0.1";
     
     
     JPanel pannello1;
     JPanel pannello2;
     JPanel pannello3;
     InetAddress address = InetAddress.getByName(ip);
     
     
    

   
    public GUI () throws  SocketException,UnknownHostException{
        
        
        
        setTitle("CHAT-UDP");
      
        Ar1 = new JTextField();
        Ar2 = new JTextField();
        buffer1 = new byte[1024];
        buffer2 = new byte[1024];
        panel = new JTextArea();
        

       
        pannello1 = new JPanel();
        pannello1.setBorder(new TitledBorder("                   USERNAME                                             MESSAGGIO"));
        pannello1.setLayout(new GridLayout(1, 2));
        pannello1.add(Ar2);
        pannello1.add(Ar1);
        pannello1.add(invio);
        
        
        
        
        
        pannello2 = new JPanel();
        
        pannello2.setLayout(new GridLayout(1, 2));
        pannello2.add(panel);
                
                
                
        pannello3 = new JPanel();
        JScrollPane p = new JScrollPane(pannello3);
        
        pannello3.setLayout(new GridLayout(4, 4));
        this.setLayout(new GridLayout(4, 4));
        this.add(pannello1);
        pannello3.add(pannello2);
        
        this.add(p);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        

        this.getContentPane().add(pannello3, BorderLayout.NORTH);
        invio.addActionListener(this);
        pack();
        setSize(600, 400);
        setVisible(true);
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    client = new DatagramSocket();
                    while (true) {
                        DatagramPacket pacchetto = new DatagramPacket(buffer1, buffer1.length);
                        client.receive(pacchetto);
                        String messaggio = new String(pacchetto.getData());
                        panel.append("Server: " + messaggio );
                    }
                } catch (Exception e) {
                }
            }
        }).start();

    }
        
        
    
    
     @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(invio)) {
            try {
                String message = Ar1.getText();
                String username1 = Ar2.getText();
                String messaggio = message + " & " + username1;
                buffer1 = messaggio.getBytes();
                DatagramPacket sendpack = new DatagramPacket(buffer1, buffer1.length, InetAddress.getLoopbackAddress(), 9999);
                client.send(sendpack);
                panel.append("l'utente e': " + username1 + "   ed il messaggio e' :  " + message );
                
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
   
    
