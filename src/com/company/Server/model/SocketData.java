package com.company.Server.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;

public class SocketData {
    public String username;
    public Integer port;
    BufferedWriter bw;
    BufferedReader br;

    public SocketData(String username, Integer port, BufferedWriter bw, BufferedReader br) {
        this.username = username;
        this.port = port;
        this.bw = bw;
        this.br = br;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
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
}
