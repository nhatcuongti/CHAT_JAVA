package com.company.Client1.model;

public class ClientSocket {
    int port;
    String Username;

    public ClientSocket(int port, String username) {
        this.port = port;
        Username = username;
    }

    public ClientSocket() {
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return Username;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }


}
