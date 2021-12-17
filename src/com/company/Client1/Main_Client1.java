package com.company.Client1;

import com.company.Client1.Message.RequestMessage;
import com.company.Client1.Message.ResponseMessage;
import com.company.Client1.View.Login;
import com.company.Client1.model.User;
import com.google.gson.Gson;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

public class Main_Client1 {
    public static void main(String[] argus){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Login().setVisible(true);
            }
        });

    }

    public static void login(TCP_Client client, String username, String password){
        BufferedWriter bw = client.getBw();
        BufferedReader br = client.getBr();
        Gson gson = new Gson();
        //Register
        User user = new User(username, password);
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

    public static void register(TCP_Client client, String Username, String Password){
        BufferedWriter bw = client.getBw();
        BufferedReader br = client.getBr();
        Gson gson = new Gson();
        //Register
        User user = new User(Username, Password);
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
