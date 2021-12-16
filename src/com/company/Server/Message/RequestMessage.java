package com.company.Server.Message;

import com.company.Server.model.SocketData;
import com.company.Server.model.User;

import java.io.Serializable;

public class RequestMessage implements Serializable {
    String type;
    User fromUser;
    SocketData receivedSocket;
    String message;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public SocketData getReceivedSocket() {
        return receivedSocket;
    }

    public void setReceivedSocket(SocketData receivedSocket) {
        this.receivedSocket = receivedSocket;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
