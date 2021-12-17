package com.company.Server;



import com.company.Server.Message.RequestMessage;
import com.company.Server.Message.ResponseMessage;
import com.company.Server.model.ClientSocket;
import com.company.Server.model.User;
import com.company.Server.utils.FileManager;
import com.company.Server.utils.ManageUser;
import com.google.gson.Gson;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class TCP_Server {

        ServerSocket ss;
        ArrayList<ClientSocket> userOnline = new ArrayList<>();
        BufferedWriter bw1, bw2;
        BufferedReader br1, br2;

    public TCP_Server() {
        connectServer();
    }

    public void registerManagement(RequestMessage rm, BufferedReader bufferedReader, BufferedWriter bufferedWriter, ClientSocket clientSocket){
        ManageUser manageUser = new ManageUser();
        Gson gson = new Gson();
        User user = rm.getFromUser();
        boolean isSuccessfull = manageUser.registerData(user);

        FileManager fm = new FileManager("file.txt");
        ArrayList<User> users = fm.getData();

        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setListUserOnline(users);
        responseMessage.setType("Register");
        if (isSuccessfull) {
            responseMessage.setStatus(true);
            clientSocket.setUsername(user.getUsername());
        }
        else{
            responseMessage.setStatus(false);
        }

        System.out.println(gson.toJson(responseMessage));
        try {
            bufferedWriter.write(gson.toJson(responseMessage));
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loginManagement(RequestMessage rm, BufferedReader bufferedReader, BufferedWriter bufferedWriter, ClientSocket clientSocket){
        ManageUser manageUser = new ManageUser();
        Gson gson = new Gson();
        User user = rm.getFromUser();
        boolean isSuccessfull = manageUser.loginData(user);

        FileManager fm = new FileManager("file.txt");
        ArrayList<User> users = fm.getData();

        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setListUserOnline(users);
        responseMessage.setType("Login");
        if (isSuccessfull) {
            responseMessage.setStatus(true);
            clientSocket.setUsername(user.getUsername());
        }
        else{
            responseMessage.setStatus(false);
        }

        System.out.println(gson.toJson(responseMessage));
        try {
            bufferedWriter.write(gson.toJson(responseMessage));
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connectServer(){
                try {
                        ss = new ServerSocket(3200);
                        int count = 0;
                        while(true){
                            System.out.println("Listen to Client !!");
                            Socket s = ss.accept();
                            ClientSocket clientSocket = new ClientSocket(s);

                            System.out.println("Start Chatting to client !!");
                            System.out.println(s.getPort());



                            InputStream is = s.getInputStream();
                            BufferedReader br = new BufferedReader(new InputStreamReader(is));

                            OutputStream os = s.getOutputStream();
                            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));


                            Thread threadReceived = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    while (true){
                                        try {
                                            //Initialize
                                            ManageUser manageUser = new ManageUser();
                                            Gson gson = new Gson();
                                            BufferedReader bufferedReader = br;
                                            BufferedWriter bufferedWriter = bw;
                                            String message = bufferedReader.readLine();
                                            RequestMessage rm = gson.fromJson(message, RequestMessage.class);

                                            //Register
                                            if (rm.getType().equals("Register"))
                                                registerManagement(rm, bufferedReader, bufferedWriter, clientSocket);
                                            else if (rm.getType().equals("Login"))
                                                loginManagement(rm, bufferedReader, bufferedWriter, clientSocket);

                                            System.out.println(rm);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                            break;
                                        }
                                    }
                                }
                            });
                            threadReceived.start();
                            count++;
                        }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

    }
