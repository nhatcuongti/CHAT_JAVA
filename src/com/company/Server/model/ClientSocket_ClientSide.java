package com.company.Server.model;

import java.net.Socket;
import java.util.ArrayList;

public class ClientSocket_ClientSide {
    int port;
    String Username;

    public ClientSocket_ClientSide(ClientSocket clientSocket) {
        this.port = clientSocket.getSocket().getPort();
        Username = clientSocket.getUsername();
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "ClientSocket_ClientSide{" +
                "port=" + port +
                ", Username='" + Username + '\'' +
                '}';
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }


}
