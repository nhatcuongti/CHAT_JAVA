package com.company.Client1;

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
        ObjectOutputStream oos;
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
        System.out.println(EventQueue.isDispatchThread());

        String sendMessage = textField.getText();
        textField.setText("");

        try {
            chatArea.append("Me :" + sendMessage + "\n");
            Message message = new Message("nhatcuongti", "nhathao", sendMessage);
            oos.writeObject(message);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
