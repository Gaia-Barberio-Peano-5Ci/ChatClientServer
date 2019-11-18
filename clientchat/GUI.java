/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientchat;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.lang.Runtime;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author barberio.gaia
 */
public class GUI extends JFrame implements ActionListener {

    private Client client;
    private String IP_address = "127.0.0.1";
    private int port = 7;
    private String username = null;
    private ArrayList<String> users;
    private Thread tRecieve;
    Runtime end;
    private final JSocketDialog dialog;
    private final JTable table;
    private final DefaultTableModel tm;

    private final JMenuBar menubar;
    private final JMenu option;
    private final JMenu userMenu;
    private final JMenuItem changeUser;
    private final JMenuItem userConnected;
    private final JMenuItem infoServer;

    private final JScrollPane scroll;
    private final JTextField input;
    private final JButton invia;
    private final JButton drop;
    private final JButton server;
    private final JLabel login;

    public class JSocketDialog extends JDialog implements ActionListener {

        private String IP;
        private int porta;
        private boolean commit;

        private final JLabel intestazioneIP;
        private final JTextField A;
        private final JTextField B;
        private final JTextField C;
        private final JTextField D;
        private final JLabel dotA;
        private final JLabel dotB;
        private final JLabel dotC;
        private final JLabel intestazionePort;
        private final JTextField port;
        private final JButton done;
        private final JButton clear;

        public JSocketDialog() {

            commit = false;
            intestazioneIP = new JLabel("IP:");
            A = new JTextField();
            B = new JTextField();
            C = new JTextField();
            D = new JTextField();
            dotA = new JLabel(".");
            dotB = new JLabel(".");
            dotC = new JLabel(".");
            intestazionePort = new JLabel("Porta:");
            port = new JTextField();
            done = new JButton("OK");
            clear = new JButton("Clear");

            done.addActionListener(this);
            clear.addActionListener(this);
            this.paint();
            this.pack();
            this.setModal(true);

        }

        public boolean hasCommitted() {
            return commit;
        }

        public String getIP() {
            return IP;
        }

        public int getPorta() {
            return porta;
        }

        private void paint() {
            GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            this.setLayout(layout);
            layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                    .addComponent(done, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(18, 18, 18)
                                                    .addComponent(clear, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(intestazionePort)
                                                            .addComponent(intestazioneIP, javax.swing.GroupLayout.Alignment.TRAILING))
                                                    .addGap(18, 18, 18)
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(layout.createSequentialGroup()
                                                                    .addComponent(port)
                                                                    .addGap(213, 213, 213))
                                                            .addGroup(layout.createSequentialGroup()
                                                                    .addComponent(A, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                    .addComponent(dotA)
                                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                    .addComponent(B, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                    .addComponent(dotB)
                                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                    .addComponent(C, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                    .addComponent(dotC)
                                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                    .addComponent(D, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                    .addGap(30, 30, 30))
            );

            layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[]{A, B, C, D});

            layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(A, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(B, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(dotA)
                                            .addComponent(dotB)
                                            .addComponent(C, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(dotC)
                                            .addComponent(D, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(intestazioneIP))
                                    .addGap(18, 18, 18)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(intestazionePort)
                                            .addComponent(port, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(done)
                                            .addComponent(clear))
                                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            String msg = null;
            if (ae.getSource().equals(done)) {
                if (!A.getText().equals("") || !B.getText().equals("") || !C.getText().equals("") || !D.getText().equals("") || !port.getText().equals("")) {
                    boolean isIP = true;
                    if (Integer.parseInt(A.getText()) < 0 || Integer.parseInt(A.getText()) > 223) {
                        msg = "Primo numero: inserisci un numero compreso fra 0 e 223! (Classi A-C)\n";
                        A.setBackground(Color.yellow);
                        isIP = false;
                    }
                    if (Integer.parseInt(B.getText()) < 0 || Integer.parseInt(B.getText()) > 254) {
                        msg = msg + "Secondo numero: inserisci un numero compreso fra 0 e 254!\n";
                        B.setBackground(Color.yellow);
                        isIP = false;
                    }
                    if (Integer.parseInt(C.getText()) < 0 || Integer.parseInt(C.getText()) > 254) {
                        msg = msg + "Terzo numero: inserisci un numero compreso fra 0 e 254!\n";
                        C.setBackground(Color.yellow);
                        isIP = false;
                    }
                    if (Integer.parseInt(D.getText()) < 1 || Integer.parseInt(D.getText()) > 254) {
                        msg = msg + "Quarto numero: inserisci un numero compreso fra 1 e 254!\n";
                        D.setBackground(Color.yellow);
                        isIP = false;
                    }
                    if (Integer.parseInt(port.getText()) < 0 || Integer.parseInt(port.getText()) > 49151) {
                        port.setBackground(Color.yellow);
                        msg = msg + "Porta: inserisci un numero compreso fra 1 e 49151!\n";
                    }

                    if (isIP == true) {
                        IP = A.getText() + "." + B.getText() + "." + C.getText() + "." + D.getText();
                        porta = Integer.parseInt(port.getText());
                        commit = true;
                        this.setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(this, msg, "Errore di input!", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    if (A.getText().equals("")) {
                        A.setBackground(Color.yellow);
                        msg = "Primo numero: non hai inserito un numero! (0-223)\n";
                    }
                    if (B.getText().equals("")) {
                        msg = msg + "Secondo numero: non hai inserito un numero! (0-254)\n";
                        B.setBackground(Color.yellow);
                    }
                    if (C.getText().equals("")) {
                        msg = msg + "Terzo numero: non hai inserito un numero! (0-254)\n";
                        C.setBackground(Color.yellow);
                    }
                    if (D.getText().equals("")) {
                        msg = msg + "Quarto numero: non hai inserito un numero! (1-254)\n";
                        D.setBackground(Color.yellow);
                    }
                    if (port.getText().equals("")) {
                        port.setBackground(Color.yellow);
                        msg = msg + "Porta: non hai inserito un numero! (1-49151)";
                    }
                    JOptionPane.showMessageDialog(this, msg, "Errore di input!", JOptionPane.ERROR_MESSAGE);
                }
                A.setText("");
                B.setText("");
                C.setText("");
                D.setText("");
                port.setText("");
            } else {
                A.setText("");
                B.setText("");
                C.setText("");
                D.setText("");
                port.setText("");
            }
        }
    }

//    public class RowRenderer extends DefaultTableCellRenderer {
//
//        @Override
//        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
//            setOpaque(true);
//            if (tm.getValueAt(row, col).equals(username)) {
//                setBackground(new Color(40, 176, 156));
//            } else if (tm.getValueAt(row, col - 1).equals(username) && col == 1) {
//                setBackground(new Color(40, 176, 156));
//            } else if (tm.getValueAt(row, col - 2).equals(username) && col == 2) {
//                setBackground(new Color(40, 176, 156));
//            } else {
//                setBackground(new Color(215, 222, 221));
//            }
//            return super.getTableCellRendererComponent(table, value, hasFocus, hasFocus, col, col);
//        }
//    }
    public GUI() {
        users = new ArrayList();
        end = Runtime.getRuntime();
        menubar = new JMenuBar();
        option = new JMenu("Opzioni");
        userMenu = new JMenu("User");
        changeUser = new JMenuItem("Change Username");
        infoServer = new JMenuItem("Informazioni Server");
        userConnected = new JMenuItem("Users connected");

        login = new JLabel();
        dialog = new JSocketDialog();
        option.add(infoServer);
        menubar.add(option);
        userMenu.add(changeUser);
        userMenu.add(userConnected);
        menubar.add(userMenu);
        this.setJMenuBar(menubar);
        tm = new DefaultTableModel(0, 3);
        table = new JTable(tm);
        table.setShowGrid(false);
        table.setEnabled(false);
        table.setTableHeader(null);
        table.setIntercellSpacing(new Dimension(0, 0));
        scroll = new JScrollPane(table);

        table.setBackground(scroll.getBackground());
        input = new JTextField();
        input.addActionListener(this);
        invia = new JButton("Invia");

        drop = new JButton("Connetti");
        server = new JButton("Cambia server");

        userConnected.addActionListener(this);
        infoServer.addActionListener(this);
        invia.addActionListener(this);
        drop.addActionListener(this);
        server.addActionListener(this);

        input.setEnabled(false);
        invia.setEnabled(false);
        paint();
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        username = JOptionPane.showInputDialog(this, "Inserisci l'username che distingue univocamente il client\nATTENZIONE: Lasciare vuoto questo campo o annullare\ncomporta l'utilizzo di un account guest", "Login", JOptionPane.QUESTION_MESSAGE);
        if (username == null || username.equals("")) {
            username = "Guest" + LocalTime.now().toString().substring(0, 8).replace(":", "") + LocalTime.now().toString().substring(9);
        }
        login.setText("Username: " + username);

        int scelta = JOptionPane.showConfirmDialog(this, "L'echo server è attualmente impostato su localhost, vuoi cambiarlo?", "Dilemma", JOptionPane.YES_NO_OPTION);
        if (scelta == JOptionPane.YES_OPTION) {
            inputEVerifica();
        }
        this.pack();

        //Aggiungo un Thread che viene utilizzato quando il mio runtime finisce
        end.addShutdownHook(new Thread() {
            @Override
            public void run() {
                invia("200disconnect$#" + username);
            }

        });

        connectClient();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource().equals(invia) || ae.getSource().equals(input)) {
            if (!input.getText().equals("")) {
                String out = username + "$#" + LocalTime.now().toString().substring(0, 5) + "$#" + input.getText() + "$#";
                invia(out);
                input.setBackground(Color.white);

            } else {
                input.setBackground(Color.yellow);
                JOptionPane.showMessageDialog(this, "Inserisci un valore di input", "ERRORE!", JOptionPane.ERROR_MESSAGE);
            }
            input.setText("");
        } else if (ae.getSource().equals(infoServer)) {
            JOptionPane.showMessageDialog(this, "IP address: " + IP_address + "\nPort: " + port, "Informazioni server", JOptionPane.INFORMATION_MESSAGE);
        } else if (ae.getSource().equals(drop)) {
            if (drop.getText().equals("Droppa connessione")) {
                invia("200disconnect$#" + username);
                invia.setEnabled(false);
                input.setEnabled(false);
                login.setText("Username: " + username);
                users.clear();
                while (tm.getRowCount() != 0) {
                    tm.removeRow(tm.getRowCount() - 1);
                }
                drop.setText("Connetti");
            } else {
                invia("200connect$#" + username);
                invia.setEnabled(true);
                input.setEnabled(true);
                drop.setText("Droppa connessione");
            }
        } else if (ae.getSource().equals(server)) {
            inputEVerifica();
        } else if (ae.getSource().equals(userConnected)) {
            StringBuilder b = new StringBuilder();
            for (String s : users) {
                b.append(s + "\n");
            }
            JOptionPane.showMessageDialog(this, "User Connesssi:\n" + b.toString(), "ALL USERS CONNECTED TO" + IP_address, JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void paint() {
        GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(login)
                                        .addComponent(scroll)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(drop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(18, 18, 18)
                                                .addComponent(server, javax.swing.GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(input, javax.swing.GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(18, 18, 18)
                                                .addComponent(invia, javax.swing.GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(login, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                                .addComponent(scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(invia))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(drop)
                                        .addComponent(server))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }

    private void inputEVerifica() {
        dialog.setVisible(true);
        while (dialog.isVisible()) {

        }
        if (dialog.hasCommitted()) {
            port = dialog.getPorta();
            IP_address = dialog.getIP();
        } else {
            JOptionPane.showMessageDialog(this, "La modifica di IP e PORTA del server è annullata", "Confirm", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void invia(String msg) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    client.send(port, IP_address, msg);
                } catch (InterruptedException | IOException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }).start();
    }

    private void connectClient() {
        try {
            client = new Client();
        } catch (SocketException | UnknownHostException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        tRecieve = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        String out = client.recieve();
                        StringTokenizer token = new StringTokenizer(out, "$#");
                        String firstToken = token.nextToken();
                        switch (firstToken) {
                            case "invalid_user":
                                drop.setText("Connetti");
                                invia.setEnabled(false);
                                input.setEnabled(false);
                                JOptionPane.showMessageDialog(GUI.this, "L'utente inserito è già in uso o non è disponibile nel server selezionato.\nPuoi cambiarlo dalla barra del menu in\nUtente>Change Username\nCodice Errore: invalid_user:" + username, "INVALID USERNAME", JOptionPane.ERROR_MESSAGE);
                                break;
                            case "all_users":
                                while (token.hasMoreTokens()) {
                                    users.add(token.nextToken());
                                }
                                login.setText(login.getText() + " | User connessi: " + users.size());
                                break;
                            case "new_user":
                                users.add(token.nextToken());
                                login.setText("Username: " + username + " | User connessi: " + users.size());
                                break;
                            case "delete_user":
                                users.remove(token.nextToken());
                                login.setText("Username: " + username + " | User connessi: " + users.size());
                                break;
                            case "closed":
                                drop.setText("Connetti");
                                invia.setEnabled(false);
                                input.setEnabled(false);
                                login.setText("Username: " + username);
                                JOptionPane.showMessageDialog(GUI.this, "Il server è chiuso! Connettiti a un altro server fino a che non ritorna online.", "SERVER CLOSED", JOptionPane.INFORMATION_MESSAGE);
                                users.clear();
                                break;
                            default:
                                String time = token.nextToken();
                                String msg = token.nextToken();
                                int i = 0,
                                 i1 = 20;
                                StringBuilder b = new StringBuilder();
                                if (i1 < msg.length()) {
                                    while (msg.length() > 20) {
                                        b = b.append(msg.substring(i, i1) + "-\n");
                                        System.err.println(b.toString());
                                        msg = msg.substring(i, i1);
                                    }
                                    b.append(msg);
                                } else {
                                    b.append(msg);
                                }
                                String row[] = {
                                    firstToken,
                                    time,
                                    b.toString()
                                };
                                tm.addRow(row);
                                table.validate();
                                table.repaint();
                                break;
                        }

                    } catch (InterruptedException | IOException ex) {
                        Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        });
        tRecieve.start();
    }

}