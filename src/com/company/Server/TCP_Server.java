package com.company.Server;



import com.company.Server.Message.RequestMessage;
import com.company.Server.Message.ResponseMessage;
import com.company.Server.model.ClientSocket;
import com.company.Server.model.ClientSocket_ClientSide;
import com.company.Server.model.User;
import com.company.Server.utils.FileManager;
import com.company.Server.utils.ManageUser;
import com.google.gson.Gson;

import javax.swing.*;
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
                                    Socket socket = s;
                                    try{
                                        // Take care about Type and Status on Response Message
                                        while (true){
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

                                        }

                                        // Take care about ListUserOnline on ResponseMessage

                                        ArrayList<ClientSocket_ClientSide> clientSocket_clientSides = new ArrayList<>();
                                        for (ClientSocket cs : userOnline) {
                                            //ConvertData
                                            ClientSocket_ClientSide clientSocket_clientSide = new ClientSocket_ClientSide(cs);
                                            clientSocket_clientSides.add(clientSocket_clientSide);
                                        }

                                        responseMessageFirst.setListUserOnline(clientSocket_clientSides);

                                        //Response One User to This Client
                                        responseMessageFirst.setType("OnlineUserList");
                                        bufferedWriter.write(gson.toJson(responseMessageFirst));
                                        bufferedWriter.newLine();
                                        bufferedWriter.flush();

                                        userOnline.add(clientSocket);
                                        //Response All User
                                        for (ClientSocket cs : userOnline){
                                            if (cs.getSocket().getPort() == clientSocket.getSocket().getPort())
                                                continue;
                                            BufferedWriter bufferedWriter1 = new BufferedWriter(new OutputStreamWriter(cs.getSocket().getOutputStream()));

                                            ResponseMessage newUserResponseMessage = new ResponseMessage();
                                            newUserResponseMessage.setType("NewUser");
                                            ClientSocket_ClientSide newUser = new ClientSocket_ClientSide(clientSocket);
                                            newUserResponseMessage.setNewUser(newUser);

                                            bufferedWriter1.write(gson.toJson(newUserResponseMessage));
                                            bufferedWriter1.newLine();
                                            bufferedWriter1.flush();
                                        }

                                        // Listen to Send chat or Send File
                                        while (true){
                                            //Step 1 : Get message from user
                                            String msg = bufferedReader.readLine();
                                            ResponseMessage responseMessage = gson.fromJson(msg, ResponseMessage.class);
                                            ClientSocket_ClientSide clientReceived = responseMessage.getToUser();

                                            //Step 2 : Check it is Message or file
                                            if (responseMessage.getType().equals("Chat")){
                                                //Get MSG and ClientSocket

                                                System.out.println(clientReceived);
                                                for (ClientSocket user : userOnline) {
                                                    System.out.println("-------------------------");
                                                    System.out.println("Server-Side : ");
                                                    System.out.println(user.getUsername() + "-" + user.getSocket().getPort());
                                                    System.out.println("-------------------------");
                                                    if (clientReceived.getPort() == user.getSocket().getPort()) {
                                                        BufferedWriter bufferWriterUser = new BufferedWriter(new OutputStreamWriter(user.getSocket().getOutputStream()));
                                                        bufferWriterUser.write(msg);
                                                        bufferWriterUser.newLine();
                                                        bufferWriterUser.flush();
                                                    }
                                                }
                                            }
                                            else{
                                                DataInputStream dis = new DataInputStream(socket.getInputStream());
                                                //Get name Length File
                                                int fileNameLength = dis.readInt();
                                                byte[] fileNameBytes = new byte[fileNameLength];
                                                dis.readFully(fileNameBytes, 0, fileNameLength);

                                                int fileContentLength = dis.readInt();
                                                byte[] fileContentBytes = new byte[fileContentLength];
                                                dis.readFully(fileContentBytes, 0, fileContentLength);
                                                //Send To client

                                                for (ClientSocket user : userOnline) {
                                                    if (clientReceived.getPort() == user.getSocket().getPort()) {
                                                        //Send response message
                                                        BufferedWriter bufferWriterUser = new BufferedWriter(new OutputStreamWriter(user.getSocket().getOutputStream()));
                                                        bufferWriterUser.write(msg);
                                                        bufferWriterUser.newLine();
                                                        bufferWriterUser.flush();

                                                        //Send File
                                                        DataOutputStream dos = new DataOutputStream(user.getSocket().getOutputStream());
                                                        System.out.println("------------------------------------------------");
                                                        System.out.println("Server - Side");
                                                        System.out.println(user.getSocket().getPort());
                                                        System.out.println("File Name Length : " + fileNameLength);
                                                        System.out.println("------------------------------------------------");
                                                        dos.writeInt(fileNameLength);
                                                        dos.write(fileNameBytes);

                                                        dos.writeInt(fileContentLength);
                                                        dos.write(fileContentBytes);
                                                    }
                                                }
                                            }

                                        }
                                    } catch (Exception e) {
                                        ResponseMessage responseMessageDelete = new ResponseMessage();
                                        responseMessageDelete.setType("Delete");
                                        ClientSocket_ClientSide clientRemove = new ClientSocket_ClientSide(clientSocket);
                                        responseMessageDelete.setDeleteUser(clientRemove);

                                        System.out.println("Before Remove : " + userOnline.size());
                                        userOnline.remove(clientSocket);
                                        System.out.println("After Remove : " + userOnline.size());

                                        for (ClientSocket user : userOnline){
                                            String rmdMsg = gson.toJson(responseMessageDelete);
                                            try {
                                                bufferedWriter.write(rmdMsg);
                                                bufferedWriter.newLine();
                                                bufferedWriter.newLine();
                                            } catch (IOException ex) {
                                                ex.printStackTrace();
                                            }

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
