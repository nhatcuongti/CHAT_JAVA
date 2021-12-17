package com.company.Server;



import com.company.Server.Message.RequestMessage;
import com.company.Server.Message.ResponseMessage;
import com.company.Server.model.ClientSocket;
import com.company.Server.model.ClientSocket_ClientSide;
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

    public Boolean registerManagement(RequestMessage rm, ResponseMessage responseMessage, ClientSocket clientSocket){
        ManageUser manageUser = new ManageUser();
        User user = rm.getFromUser();
        boolean isSuccessfull = manageUser.registerData(user);

        FileManager fm = new FileManager("file.txt");

        responseMessage.setType("Register");


        if (isSuccessfull) {
            responseMessage.setStatus(true);
            clientSocket.setUsername(user.getUsername());
            return true;
        }

        return false;
    }

    public Boolean loginManagement(RequestMessage rm, ResponseMessage responseMessage, ClientSocket clientSocket){
        ManageUser manageUser = new ManageUser();
        User user = rm.getFromUser();
        boolean isSuccessfull = manageUser.loginData(user);

        FileManager fm = new FileManager("file.txt");

        responseMessage.setType("Login");


        if (isSuccessfull) {
            responseMessage.setStatus(true);
            clientSocket.setUsername(user.getUsername());
            return true;
        }

        return false;
    }

    public void connectServer(){
                try {
                        ss = new ServerSocket(3200);
                        int count = 0;
                        while(true){
                            System.out.println("Listen to Client !!");
                            Socket s = ss.accept();
                            ClientSocket cl = new ClientSocket(s);

                            System.out.println("Start Chatting to client !!");
                            System.out.println(s.getPort());



                            InputStream is = s.getInputStream();
                            BufferedReader br = new BufferedReader(new InputStreamReader(is));

                            OutputStream os = s.getOutputStream();
                            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));


                            Thread threadReceived = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    //Initialize
                                    ManageUser manageUser = new ManageUser();
                                    Gson gson = new Gson();
                                    BufferedReader bufferedReader = br;
                                    BufferedWriter bufferedWriter = bw;
                                    ClientSocket clientSocket = cl;
                                    ResponseMessage responseMessageFirst = new ResponseMessage();

                                    // Take care about Type and Status on Response Message
                                    while (true){
                                        try {
                                            String message = bufferedReader.readLine();
                                            RequestMessage rm = gson.fromJson(message, RequestMessage.class);

                                            //Register
                                            Boolean isSuccessful = false;
                                            if (rm.getType().equals("Register"))
                                                isSuccessful = registerManagement(rm, responseMessageFirst, clientSocket);
                                            else if (rm.getType().equals("Login"))
                                                isSuccessful = loginManagement(rm, responseMessageFirst, clientSocket);

                                            if (isSuccessful)
                                                break;
                                            else{
                                                bufferedWriter.write(gson.toJson(responseMessageFirst));
                                                bufferedWriter.newLine();
                                                bufferedWriter.flush();
                                            }

                                        } catch (IOException e) {
                                            e.printStackTrace();
                                            break;
                                        }
                                    }

                                    // Take care about ListUserOnline on ResponseMessage
                                    userOnline.add(clientSocket);

                                    ArrayList<ClientSocket_ClientSide> clientSocket_clientSides = new ArrayList<>();
                                    for (ClientSocket cs : userOnline) {
                                        //ConvertData
                                        ClientSocket_ClientSide clientSocket_clientSide = new ClientSocket_ClientSide(cs);
                                        clientSocket_clientSides.add(clientSocket_clientSide);
                                    }

                                    responseMessageFirst.setListUserOnline(clientSocket_clientSides);

                                    try {
                                        //Response One User to This Client
                                        bufferedWriter.write(gson.toJson(responseMessageFirst));
                                        bufferedWriter.newLine();
                                        bufferedWriter.flush();

                                        //Response All User
                                        for (ClientSocket cs : userOnline){
                                            if (cs.getSocket().getPort() == clientSocket.getSocket().getPort())
                                                continue;
                                            BufferedWriter bufferedWriter1 = new BufferedWriter(new OutputStreamWriter(cs.getSocket().getOutputStream()));
                                            responseMessageFirst.setType("NewUser");

                                            bufferedWriter1.write(gson.toJson(responseMessageFirst));
                                            bufferedWriter1.newLine();
                                            bufferedWriter1.flush();
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
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
