package com.company.Client1.Message;
import com.company.Client1.model.User;

import java.io.Serializable;

public class RequestMessage implements Serializable {
    String type;
    User fromUser;
    int port;
    String message;

    public RequestMessage(){
        this.type = "";
        this.fromUser = null;
        this.port = 0;
        this.message = "";
    }

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

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "RequestMessage{" +
                "type='" + type + '\'' +
                ", fromUser=" + fromUser +
                ", port=" + port +
                ", message='" + message + '\'' +
                '}';
    }
}
