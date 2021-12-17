package com.company.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class TCP_Client implements ActionListener {

        JTextField usernameField, passwordField;
        BufferedWriter bw;
        ObjectOutputStream oos;
        BufferedReader br;
        Socket s;

    public TCP_Client() {
        connectServer();
    }



    public void connectServer(){
            try {
                System.out.println("Listen to Client !!");
                s = new Socket("localhost", 3200);
                System.out.println("Start Chatting to Server !!");

                OutputStream os = s.getOutputStream();
                oos = new ObjectOutputStream(os);
                bw = new BufferedWriter(new OutputStreamWriter(os));

                InputStream is = s.getInputStream();
                br = new BufferedReader(new InputStreamReader(is));



                Thread threadReceived = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true){
                            try {
                                String receivedMessage = br.readLine();
                                SwingUtilities.invokeLater(new Runnable() {
                                    @Override
                                    public void run() {

                                    }
                                });
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });
                threadReceived.start();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
