package com.company.Server;

import com.company.Client1.Message;
import com.company.Server.model.ClientSocket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class TCP_Server {

        ServerSocket ss;
        ArrayList<ClientSocket> clientSockets = new ArrayList<ClientSocket>();
        BufferedWriter bw1, bw2;
        BufferedReader br1, br2;

    public TCP_Server() {
        connectServer();
    }

    public void connectServer(){
                try {
                        ss = new ServerSocket(3200);
                        while(true){
                            System.out.println("Listen to Client !!");
                            Socket s = ss.accept();

                            System.out.println("Start Chatting to client !!");
                            System.out.println(s.getPort());



                            InputStream is = s.getInputStream();
                            ObjectInputStream ois = new ObjectInputStream(is);
                            BufferedReader br = new BufferedReader(new InputStreamReader(is));

                            OutputStream os = s.getOutputStream();
                            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));

                            clientSockets.add(new ClientSocket(s, bw, br));

                            Thread threadReceived = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    while (true){
                                        try {
                                            BufferedReader bufferedReader = br;
                                            ObjectInputStream objectInputStream = ois;
                                            while(true){
                                                Message msg = (Message) objectInputStream.readObject();
                                                System.out.println("Receiver : " + msg.getReceiver());
                                                System.out.println("Sender : " + msg.getSender());
                                                System.out.println("Message : " + msg.getMsg());
                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        } catch (ClassNotFoundException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            });
                            threadReceived.start();
                        }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

    }
