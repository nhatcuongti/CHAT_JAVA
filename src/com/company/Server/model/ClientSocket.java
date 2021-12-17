package com.company.Server.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;

public class ClientSocket {
    Socket socket;
    String Username;

    public ClientSocket(Socket socket) {
        this.socket = socket;
        Username ="";
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }
}
