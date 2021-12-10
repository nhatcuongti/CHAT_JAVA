package com.company.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class TCP_Client implements ActionListener {

        JTextArea chatArea;
        JTextField textField;
        BufferedWriter bw;
        BufferedReader br;
        Socket s;

    public TCP_Client(JTextArea textArea, JTextField textField) {
        chatArea = textArea;
        this.textField = textField;
        connectServer();
    }


    public void connectServer(){
            try {
                System.out.println("Listen to Client !!");
                s = new Socket("localhost", 3200);
                System.out.println("Start Chatting to Server !!");


                InputStream is = s.getInputStream();
                br = new BufferedReader(new InputStreamReader(is));

                OutputStream os = s.getOutputStream();
                bw = new BufferedWriter(new OutputStreamWriter(os));

                Thread threadReceived = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true){
                            try {
                                String receivedMessage = br.readLine();
                                SwingUtilities.invokeLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        chatArea.append("Server : " + receivedMessage + "\n");
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
        System.out.println(EventQueue.isDispatchThread());

        String sendMessage = textField.getText();
        textField.setText("");

        try {
            chatArea.append("Me :" + sendMessage + "\n");
            bw.write(sendMessage);
            bw.newLine();
            bw.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
