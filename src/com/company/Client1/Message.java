package com.company.Client1;

import java.io.Serializable;

public class Message implements Serializable {
    private String Sender, Receiver, msg;

    public Message(String sender, String receiver, String msg) {
        Sender = sender;
        Receiver = receiver;
        this.msg = msg;
    }

    public String getSender() {
        return Sender;
    }

    public String getReceiver() {
        return Receiver;
    }

    public String getMsg() {
        return msg;
    }
}
