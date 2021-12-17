package com.company.Client1;

import com.company.Client1.Message.RequestMessage;
import com.company.Server.model.User;
import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;


public class TCP_Client  {

        BufferedWriter bw;
        BufferedReader br;
        Socket s;

    public TCP_Client() {
        connectServer();
    }

    public BufferedWriter getBw() {
        return bw;
    }

    public void setBw(BufferedWriter bw) {
        this.bw = bw;
    }

    public BufferedReader getBr() {
        return br;
    }

    public void setBr(BufferedReader br) {
        this.br = br;
    }

    public Socket getS() {
        return s;
    }

    public void setS(Socket s) {
        this.s = s;
    }

    public void connectServer(){
            try {
                System.out.println("Listen to Client !!");
                s = new Socket("localhost", 3200);
                System.out.println("Start Chatting to Server !!");

                OutputStream os = s.getOutputStream();
                bw = new BufferedWriter(new OutputStreamWriter(os));

                InputStream is = s.getInputStream();
                br = new BufferedReader(new InputStreamReader(is));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

}
