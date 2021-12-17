package com.company.Client1;

import com.company.Client1.Message.RequestMessage;
import com.company.Client1.Message.ResponseMessage;
import com.company.Client1.model.User;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

public class Main_Client1 {
    public static void main(String[] argus){
        TCP_Client client = new TCP_Client();
        BufferedWriter bw = client.getBw();
        BufferedReader br = client.getBr();
        register(client);
        login(client);


    }

    public static void login(TCP_Client client){
        BufferedWriter bw = client.getBw();
        BufferedReader br = client.getBr();
        Gson gson = new Gson();
        //Register
        User user = new User("nhathao1234", "0909845284");
        RequestMessage rm = new RequestMessage();
        rm.setFromUser(user);
        rm.setType("Login");
        String userJSON = gson.toJson(rm);



        try {
            bw.write(userJSON);
            bw.newLine();
            bw.flush();

            String responseJSON = br.readLine();
            ResponseMessage responseMessage = gson.fromJson(responseJSON, ResponseMessage.class);
            System.out.println(responseMessage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void register(TCP_Client client){
        BufferedWriter bw = client.getBw();
        BufferedReader br = client.getBr();
        Gson gson = new Gson();
        //Register
        User user = new User("nhathao123456", "0909845284");
        RequestMessage rm = new RequestMessage();
        rm.setFromUser(user);
        rm.setType("Register");
        String userJSON = gson.toJson(rm);



        try {
            bw.write(userJSON);
            bw.newLine();
            bw.flush();

            String responseJSON = br.readLine();
            ResponseMessage responseMessage = gson.fromJson(responseJSON, ResponseMessage.class);
            System.out.println(responseMessage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
