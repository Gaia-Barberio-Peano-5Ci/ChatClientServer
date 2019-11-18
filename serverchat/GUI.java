/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverchat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author barberio.gaia
 */
public class GUI extends JFrame implements ActionListener {

    private ServerSocket socket;
    private Runtime end;
    private final DefaultTableModel tm;
    private final JTable table;
    private final JScrollPane scroll;
    
    public GUI() {
        String[] colNames = {"Status", "IP Source", "Port Source", "Username", "Service", "Message"};
        tm = new DefaultTableModel(colNames, 0);
        table = new JTable(tm);
        table.setShowGrid(false);
        scroll = new JScrollPane(table);
        end = Runtime.getRuntime();
        int p;
        
        this.add(scroll);
        this.pack();
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        String port = JOptionPane.showInputDialog(this, "Inserisci la porta nella quale il server sarà in ascolto\nATTENZIONE! : Se lasciata vuota,verrà assegnata la porta 7", "SERVER PORT", JOptionPane.QUESTION_MESSAGE);
        if (port == null || port.equals("") || Integer.parseInt(port) < 1 || Integer.parseInt(port) > 49151) {
            p = 7;
        } else {
            p = Integer.parseInt(port);
        }
        connectServer(p);
        end.addShutdownHook(new Thread(){
            @Override
            public void run() {
                try {
                    socket.close();
                } catch (IOException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

    }

    private void connectServer(int port) {
        try {
            socket = new ServerSocket(port);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        ArrayList<String> output = socket.listen();
                        if (output != null) {
                            for (int i = 0; i < output.size(); i += 6) {
                                String row[] = new String[6];
                                for (int z = 0; z < 6; z++) {
                                    row[z] = output.get(z);
                                }
                                tm.addRow(row);
                            }
                            table.validate();
                            table.repaint();
                        }
                    }
                }
            }).start();
        } catch (SocketException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}