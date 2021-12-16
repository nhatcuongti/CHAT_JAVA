package com.company.Server.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;

public class ClientSocket {
    Socket socket;
    BufferedWriter bw;
    BufferedReader br;

    public ClientSocket(Socket socket, BufferedWriter bw, BufferedReader br) {
        this.socket = socket;
        this.bw = bw;
        this.br = br;
    }

    public Socket getSocket() {
        return socket;
    }

    public BufferedWriter getBw() {
        return bw;
    }

    public BufferedReader getBr() {
        return br;
    }
}
